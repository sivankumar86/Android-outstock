package com.stock.sankari.ourstock.download;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;

import com.stock.sankari.ourstock.R;
import com.stock.sankari.ourstock.StockActivity;
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
 * Created by sivankumar86 on 6/20/2015.
 */
public class YahooFinance extends AsyncTask<Context,String, List<StockViewBean>> {
  private static StockActivity context;
    private static List<StockViewBean> stocks;
    public Downloader downloader;
     public YahooFinance(StockActivity mContext,List<StockViewBean> mstocks){
         context=mContext;
         stocks=mstocks;
         downloader=new Downloader();
     }


    @Override
    protected List<StockViewBean> doInBackground(Context[] params) {
        try {
           // for(StockViewBean viewBean:stocks){
                downloader.getYahooData(stocks);
            //}
           // if (BuildConfig.DEBUG) Log.v(StockActivity.REFACTOR_L;OG_TAG, "First url: " + urls[0]);
            return stocks;
        } catch (IOException | JSONException e) {
            //Log.e("JSON", "DownloadURL IO error.");
            e.printStackTrace();
        }

       return null;
    }



    @Override
    protected void onPostExecute(List<StockViewBean> stockViewBeanList) {
        super.onPostExecute(stocks);
       //context.update(stocks);
        context.dataChanged();
    }


}
