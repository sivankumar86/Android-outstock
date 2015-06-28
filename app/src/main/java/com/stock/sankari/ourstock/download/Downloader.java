package com.stock.sankari.ourstock.download;

import com.stock.sankari.ourstock.R;
import com.stock.sankari.ourstock.beans.StockViewBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by sivankumar86 on 6/23/2015.
 */
public class Downloader {

    private static String url="http://finance.yahoo.com/webservice/v1/symbols/";//WIPRO.NS/quote?format=json&view=detail";

    public void getYahooData(List<StockViewBean> viewBean) throws JSONException, IOException {
        String stockName=getName(viewBean);
        String rurl=url+stockName+"/quote?format=json&view=detail";
        JSONObject jsonObject= new JSONObject(downloadUrl(rurl));
        JSONObject list=jsonObject.getJSONObject("list");
        JSONArray jsonArray=list.getJSONArray("resources");
        for(int i=0;i<jsonArray.length();i++){
            JSONObject object=jsonArray.getJSONObject(i);
            JSONObject resource=object.getJSONObject("resource");
            JSONObject fields=resource.getJSONObject("fields");
            float mkt=Float.parseFloat(fields.getString("price"));
            String symbol=fields.getString("symbol");
            for(StockViewBean bean:viewBean){
                if(bean.getStockName().equals(symbol)){
                    bean.setCurrentprice(String.format("%.2f",mkt));

                }
            }
        }
    }

    private String getName(List<StockViewBean> stockViewBeanList){
        StringBuilder sb= new StringBuilder();
        for(StockViewBean  stockViewBean:stockViewBeanList) {
            sb.append(stockViewBean.getStockName());
            sb.append(",");

        }
        return sb.toString();
    }




    public String downloadUrl(String myUrl) throws IOException {
        InputStream is = null;

        try {
            URL url = new URL(myUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            conn.connect();

            is = conn.getInputStream();

            // Convert the InputStream into a string
            return readIt(is);

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    // Reads an InputStream and converts it to a String.
    private String readIt(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "iso-8859-1"), 128);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
