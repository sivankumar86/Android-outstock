package com.stock.sankari.ourstock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.stock.sankari.ourstock.adapters.SuggestionAdapter;
import com.stock.sankari.ourstock.beans.StockViewBean;
import com.stock.sankari.ourstock.provider.DBHelper;

public class StockAddActivity extends Activity {
    private DBHelper mydb ;
    private AutoCompleteTextView stockCode ;
    private TextView buyPrice;
    private TextView email;
    private TextView change;
    private TextView noStock;
    private boolean update=false;
    private  int stockname;

    @Override
    public void onBackPressed() {
        finish();
        Toast.makeText(getApplicationContext(), "Back", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(),StockActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stockadd);
        mydb = new DBHelper(this);
        stockCode = (AutoCompleteTextView) findViewById(R.id.stockCode);
        SuggestionAdapter adapter=new SuggestionAdapter(this,stockCode.getText().toString());
        stockCode.setAdapter(adapter);
        stockCode.setThreshold(4);
         //stockCode=(TextView) findViewById(R.id.stockCode); ;
         buyPrice=(TextView) findViewById(R.id.buyprice);
         email=(TextView) findViewById(R.id.email);
         change=(TextView) findViewById(R.id.change);
         noStock=(TextView) findViewById(R.id.nostocks);
      Bundle bundle=  getIntent().getExtras();

        if(bundle !=null){
            stockname=bundle.getInt("stockid");
            setValue(stockname);
            update=true;
        }
        else{
            update=false;
        }
    }
    private void setValue(int sname){
      StockViewBean stockViewBean=mydb.getData(sname);
        stockCode.setTag(stockViewBean.getId());
        stockCode.setText(stockViewBean.getStockName());
        buyPrice.setText(stockViewBean.getBuyprice());
        email.setText(stockViewBean.getEmail());
        change.setText(stockViewBean.getChange());
        noStock.setText(stockViewBean.getChange());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stockadd, menu);
        return true;
    }

    public void saveStock(View view) {
        String stockcode=stockCode.getText().toString();
        String bprice=buyPrice.getText().toString();
        String emails=email.getText().toString();
        String noS=noStock.getText().toString();
        String stockChange =change.getText().toString();
        if(stockcode.isEmpty() || bprice.isEmpty()||emails.isEmpty()||noS.isEmpty()||stockChange.isEmpty()){
            TextView  add_error=(TextView) findViewById(R.id.add_error);
            add_error.setVisibility(View.VISIBLE);
        }else {
            if (update) {
                mydb.updateContact(stockname, stockcode, bprice, emails, noS, stockChange);
            } else {
                mydb.insertContact(stockcode, bprice, emails, noS, stockChange);
            }
            finish();
            Toast.makeText(getApplicationContext(), "Created", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), StockActivity.class);
            startActivity(intent);
        }

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
