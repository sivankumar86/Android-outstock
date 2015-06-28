package com.stock.sankari.ourstock.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.stock.sankari.ourstock.NotificationActivity;
import com.stock.sankari.ourstock.R;
import com.stock.sankari.ourstock.beans.StockViewBean;
import com.stock.sankari.ourstock.download.NotificationAsync;
import com.stock.sankari.ourstock.provider.DBHelper;

import java.util.List;

/**
 * Created by sivankumar86 on 6/25/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {
    private Context  mcontext;
    @Override
    public void onReceive(Context context, Intent intent) {
        DBHelper dbHelper=new DBHelper(context);
        mcontext=context;
        List<StockViewBean> stockViewBeans=dbHelper.getAllStocks();
         new NotificationAsync(this,stockViewBeans);

    }
    public void notification(List<StockViewBean> stockViewBeans){
        NotificationManager notificationManager = (NotificationManager) mcontext.getSystemService(Context.NOTIFICATION_SERVICE);
        @SuppressWarnings("deprecation")

        Notification notification = new Notification(R.mipmap.ic_launcher,"New Message", System.currentTimeMillis());
        Intent notificationIntent = new Intent(mcontext,NotificationActivity.class);
        notificationIntent.setClass(mcontext,NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mcontext, 0,notificationIntent, 0);

        notification.setLatestEventInfo(mcontext, "OurStock","Got good profit", pendingIntent);
        notificationManager.notify(9999, notification);

        // For our recurring task, we'll just display a message
        Toast.makeText(mcontext, "I'm running", Toast.LENGTH_SHORT).show();
    }


}
