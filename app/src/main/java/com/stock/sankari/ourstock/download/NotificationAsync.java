package com.stock.sankari.ourstock.download;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.stock.sankari.ourstock.NotificationActivity;
import com.stock.sankari.ourstock.R;
import com.stock.sankari.ourstock.beans.StockViewBean;
import com.stock.sankari.ourstock.service.AlarmReceiver;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by sivankumar86 on 6/23/2015.
 */
public class NotificationAsync  extends AsyncTask<Context,String, List<StockViewBean>> {

    private static AlarmReceiver context;
    private static List<StockViewBean> stocks;
    public Downloader downloader;
    public NotificationAsync(AlarmReceiver mContext,List<StockViewBean> mstocks){
        context=mContext;
        stocks=mstocks;
        downloader=new Downloader();
    }


    @Override
    protected List<StockViewBean> doInBackground(Context... params) {
        try {
            //for(StockViewBean viewBean:stocks){
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
        super.onPostExecute(stockViewBeanList);
        context.notification(stocks);

    }
}
