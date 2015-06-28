package com.stock.sankari.ourstock;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.stock.sankari.ourstock.beans.SettingBean;
import com.stock.sankari.ourstock.provider.SettingDBHelper;
import com.stock.sankari.ourstock.service.AlarmReceiver;
import com.stock.sankari.ourstock.service.StockStatus;

import java.util.Calendar;

public class Appsettings extends Activity {
    private   SettingDBHelper settingDBHelper;
    private PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appsettings);
         /* Retrieve a PendingIntent that will perform a broadcast */
        Intent alarmIntent = new Intent(Appsettings.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(Appsettings.this, 0, alarmIntent, 0);

        settingDBHelper=new SettingDBHelper(this);
        final SettingBean settingBean=settingDBHelper.getSettings();
        final CheckBox satView = (CheckBox)findViewById(R.id.background);
        final EditText etext = (EditText)findViewById(R.id.frequency);
        etext.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                insert();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
        if(settingBean.getFrequency()>0)
            satView.setChecked(true);
        else
            satView.setChecked(false);
        etext.setText(String.valueOf(settingBean.getBacground()));
        satView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                  startAt10(satView.getText().toString());
                }else{
                    cancel();
                }
                insert();
            }
        });
    }

    public void cancel() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }

    public void startAt10(String hours) {
        if (hours.isEmpty()){
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        float interval = 1000 * 60 * Float.parseFloat(hours);
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), Math.round(interval), pendingIntent);
        Toast.makeText(this, "Alarm Set Hr:" + hours, Toast.LENGTH_SHORT).show();
    }
    }
    @Override
    public void onBackPressed() {
        finish();
        Toast.makeText(getApplicationContext(), "Back", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(),StockActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
    private void insert(){
        CheckBox satView = (CheckBox)findViewById(R.id.background);
        EditText etext = (EditText)findViewById(R.id.frequency);
        if(satView.isChecked()){
            settingDBHelper.insetSetting("1",etext.getText().toString());
        }else
            settingDBHelper.insetSetting("0",etext.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_appsettings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
