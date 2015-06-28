package com.stock.sankari.ourstock.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.stock.sankari.ourstock.beans.StockViewBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.stock.sankari.ourstock.provider.DBHelper.CONTACTS_COLUMN_BUYPRICE;

/**
 * Created by sivankumar86 on 6/20/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "StockDetails.db";
    public static final String STOCKS_TABLE_NAME = "stocks";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "stockCode";
    public static final String CONTACTS_COLUMN_EMAIL = "email";
    public static final String CONTACTS_COLUMN_BUYPRICE = "buyprice";
    public static final String CONTACTS_COLUMN_NOSTOCK = "noStock";
    public static final String CONTACTS_COLUMN_CHANGE = "change";
    private HashMap hp;
    public DBHelper(){
        super(null, DATABASE_NAME , null, 1);
    }
    private static StockViewBean stockHeaderBean=new StockViewBean();

    static{
        stockHeaderBean.setId(0);
        stockHeaderBean.setStockName("Stock");
        stockHeaderBean.setCurrentprice("MktPrice");
        stockHeaderBean.setBuyprice("Bought");
        stockHeaderBean.setChange("Change");
    }

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table stocks " +
                        "(id integer primary key AUTOINCREMENT,stockCode text,buyprice text,email text, noStock text,change text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS stocks");
        onCreate(db);
    }



    public boolean insertContact  (String stockCode, String buyprice, String email, String stock,String change)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("stockCode", stockCode);
        contentValues.put("buyprice", buyprice);
        contentValues.put("email", email);
        contentValues.put("noStock", stock);
        contentValues.put("change", change);
        db.insert("stocks", null, contentValues);
        return true;
    }

    public StockViewBean getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from stocks where id="+id, null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
            return readCursor(res);
        }
        return null;

    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, STOCKS_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, String stockCode, String buyprice, String email, String stock,String change)
    {
        //if(s)
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("stockCode", stockCode);
        contentValues.put("buyprice", buyprice);
        contentValues.put("email", email);
        contentValues.put("noStock", stock);
        contentValues.put("change", change);

        db.update("stocks", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("stocks",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<StockViewBean> getAllStocks()
    {


        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from stocks", null );
       return readStock(res);

    }

    private ArrayList<StockViewBean> readStock(Cursor res) {
        ArrayList<StockViewBean> array_list = new ArrayList<>();
        res.moveToFirst();
    array_list.add(getHeader());
        while(res.isAfterLast() == false){
            array_list.add(readCursor(res));
            res.moveToNext();
        }
        return array_list;
    }

    public StockViewBean getHeader(){
       return stockHeaderBean;
    }


    private StockViewBean readCursor(Cursor res) {
        StockViewBean stockViewBean=new StockViewBean();
        stockViewBean.setId(res.getInt(res.getColumnIndex(CONTACTS_COLUMN_ID)));
        stockViewBean.setStockName(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
        stockViewBean.setBuyprice(res.getString(res.getColumnIndex(CONTACTS_COLUMN_BUYPRICE)));
        stockViewBean.setChange(res.getString(res.getColumnIndex(CONTACTS_COLUMN_CHANGE)));
        stockViewBean.setNoStock(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NOSTOCK)));
        stockViewBean.setEmail(res.getString(res.getColumnIndex(CONTACTS_COLUMN_EMAIL)));
      return stockViewBean;
    }
}
