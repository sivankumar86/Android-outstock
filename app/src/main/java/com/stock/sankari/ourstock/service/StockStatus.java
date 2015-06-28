package com.stock.sankari.ourstock.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.stock.sankari.ourstock.NotificationActivity;
import com.stock.sankari.ourstock.R;
import com.stock.sankari.ourstock.beans.StockViewBean;
import com.stock.sankari.ourstock.download.Downloader;
import com.stock.sankari.ourstock.download.NotificationAsync;
import com.stock.sankari.ourstock.download.YahooFinance;
import com.stock.sankari.ourstock.provider.DBHelper;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by sivankumar86 on 6/22/2015.
 */
public class StockStatus extends Service {


    /** indicates how to behave if the service is killed */
    int mStartMode;

    private DBHelper mydb;
   private Downloader downloader;
    /** interface for clients that bind */
    IBinder mBinder;

    /** indicates whether onRebind should be used */
    boolean mAllowRebind;

    /** Called when the service is being created. */
    @Override
    public void onCreate() {
        mydb = new DBHelper(this);
        downloader=new Downloader();

    }

    /** The service is starting, due to a call to startService() */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
      // while(true) {
            List<StockViewBean> array_list = mydb.getAllStocks();

                    //new NotificationAsync(getApplicationContext(),array_list).execute();
                   // downloader.getYahooData(stockViewBean);




       // }
        return START_STICKY;
    }

    /** A client is binding to the service with bindService() */
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /** Called when all clients have unbound with unbindService() */
    @Override
    public boolean onUnbind(Intent intent) {
        return mAllowRebind;
    }

    /** Called when a client is binding to the service with bindService()*/
    @Override
    public void onRebind(Intent intent) {

    }

    /** Called when The service is no longer used and is being destroyed */
    @Override
    public void onDestroy() {

    }

}
