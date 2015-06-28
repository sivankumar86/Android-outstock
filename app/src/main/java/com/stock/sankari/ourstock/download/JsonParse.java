package com.stock.sankari.ourstock.download;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sivankumar86 on 6/24/2015.
 */
public class JsonParse {
    Pattern pattern = Pattern.compile("(YAHOO.Finance.SymbolSuggest.ssCallback\\()(.*)(\\))");

    public List<String> getParseJsonWCF(String sName) {
        List<String> ListData = new ArrayList<String>();
        try {
            StringBuilder stringBuilder = new StringBuilder();
            String temp = sName.replace(" ", "%20");
            URL js = new URL("http://d.yimg.com/autoc.finance.yahoo.com/autoc?callback=YAHOO.Finance.SymbolSuggest.ssCallback&query=" + temp);
            URLConnection jc = js.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(jc.getInputStream()));
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = reader.readLine();
            }
            Matcher matcher = pattern.matcher(stringBuilder.toString());
            if (matcher.find()) {
                String json = matcher.group(2);
                //String json="{ResultSet:{Query:wipro,Result:[{symbol:WIT,name:Wipro Ltd.,exch:NYQ,type:S,exchDisp:NYSE,typeDisp:Equity},{symbol:WIPRO.NS,name:Wipro Ltd.,exch:NSI,type:S,exchDisp:NSE,typeDisp:Equity},{symbol:WIPRO.BO,name:Wipro Ltd.,exch:BSE,type:S,exchDisp:Bombay,typeDisp:Equity},{symbol:WIPRO6.BO,name:WIPRO LTD,exch:BSE,type:S,exchDisp:Bombay,typeDisp:Equity},{symbol:WIPRO4.BO,name:WIPRO LTD,exch:BSE,type:S,exchDisp:Bombay,typeDisp:Equity},{symbol:WITN.MX,name:WIPRO SP.ADR,exch:MEX,type:S,exchDisp:Mexico,typeDisp:Equity},{symbol:WIOA.F,name:WIPRO SP.ADR,exch:FRA,type:S,exchDisp:Frankfurt,typeDisp:Equity},{symbol:WIOA.BE,name:WIPRO SP.ADR,exch:BER,type:S,exchDisp:Berlin,typeDisp:Equity},{symbol:WIOA.MU,name:WIPRO SP.ADR,exch:MUN,type:S,exchDisp:Munich,typeDisp:Equity}]}}";
                JSONObject jsonResponse = new JSONObject(json);
                jsonResponse=jsonResponse.getJSONObject("ResultSet");
                JSONArray jsonArray = jsonResponse.getJSONArray("Result");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject r = jsonArray.getJSONObject(i);
                    ListData.add(r.getString("symbol"));
                }
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return ListData;

    }


    public static void main(String args[]) {
        JsonParse jsonParse=new JsonParse();
       System.out.print(jsonParse.getParseJsonWCF("Wipro"));
    }
}
