package com.stock.sankari.ourstock;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.stock.sankari.ourstock.adapters.ListViewAdapter;
import com.stock.sankari.ourstock.beans.StockViewBean;
import com.stock.sankari.ourstock.download.YahooFinance;
import com.stock.sankari.ourstock.provider.DBHelper;

import java.util.List;

/**
 * It is main activity class to show the list of trade details .
 */

public class StockActivity extends Activity {
    private static DBHelper mydb;
    private static ListView obj;
    private List<StockViewBean> array_list;
    private ListViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new DBHelper(this);
        array_list = mydb.getAllStocks();
        mydb.close();
        update();
        new YahooFinance(this,array_list).execute(this);
    }

    public void dataChanged(){
        update();
    }

    public  void update(){
        obj = (ListView)findViewById(R.id.stockList);
        adapter=new ListViewAdapter(this, array_list);
        obj.setAdapter(adapter);
        obj.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
            {
                AlertDialog.Builder adb=new AlertDialog.Builder(StockActivity.this);
                adb.setTitle("Stock?");
                adb.setMessage("Do you want ?");
                final int positionToRemove = position;
                TextView sid=(TextView) view.findViewById(R.id.stock_Code);
                 final int stockid = Integer.parseInt(sid.getTag().toString());
                adb.setNegativeButton("Update", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(),StockAddActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("stockid", stockid);
                        intent.putExtras(bundle);
                        startActivity(intent);
                       // finish();
                    }});
                adb.setPositiveButton("Delete", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        array_list.remove(positionToRemove-1);
                        mydb.deleteContact(stockid);
                        adapter.notifyDataSetChanged();

                    }});
                adb.show();

               // Toast.makeText(getApplicationContext(),sid.getText()+" Clicked", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.add:
                Intent intent = new Intent(getApplicationContext(),StockAddActivity.class);
                startActivity(intent);
               // finish();
                return true;
            case R.id.setting:
                Intent settingActivity = new Intent(getApplicationContext(),com.stock.sankari.ourstock.Appsettings.class);
                startActivity(settingActivity);
                //finish();
                return true;
            case R.id.exit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

    }
}

