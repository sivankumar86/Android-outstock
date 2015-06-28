package com.stock.sankari.ourstock.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.stock.sankari.ourstock.beans.SettingBean;
import com.stock.sankari.ourstock.beans.StockViewBean;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sivankumar86 on 6/20/2015.
 */
public class SettingDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SettingStock.db";
    public static final String STOCKS_TABLE_NAME = "setting";
    public static final String SETTING_COLUMN_NAME = "background";
    public static final String SETTING_COLUMN_FREQUENCY = "frequency";

    public SettingDBHelper(){
        super(null, DATABASE_NAME , null, 1);
    }

    public SettingDBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table setting " +
                        "(background int,frequency int)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS setting");
        onCreate(db);
    }

  private void truncate(SQLiteDatabase database){
      database.execSQL("DELETE from setting");

  }

    public boolean insetSetting  (String background, String frequency)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        truncate(db);
        ContentValues contentValues = new ContentValues();
        contentValues.put("background", background);
        contentValues.put("frequency", frequency);
        db.insert("setting", null, contentValues);
        return true;
    }



    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, STOCKS_TABLE_NAME);
        return numRows;
    }



    public SettingBean getSettings()
    {

        SettingBean settingBean=new SettingBean();
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from setting", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
           // array_list.add(readCursor(res));
            settingBean.setBacground(res.getInt(res.getColumnIndex(SETTING_COLUMN_NAME)));
            settingBean.setFrequency(res.getInt(res.getColumnIndex(SETTING_COLUMN_FREQUENCY)));
            res.moveToNext();
        }
       return settingBean;

    }



}
