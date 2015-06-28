package com.stock.sankari.ourstock.adapters;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.TextView;



import com.stock.sankari.ourstock.R;
import com.stock.sankari.ourstock.beans.StockViewBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sivankumar86 on 6/20/2015.
 */
public class ListViewAdapter extends BaseAdapter {
    public List<StockViewBean> list;
    Activity activity;
    TextView txtFirst;
    TextView txtSecond;
    TextView txtThird;
    TextView txtFourth;

    public ListViewAdapter(Activity activity,List<StockViewBean> list){
        super();
        this.activity=activity;
        this.list=list;
    }
    public void swapItems(List<StockViewBean> items) {
        this.list = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=activity.getLayoutInflater();

        if(convertView == null){
            convertView=inflater.inflate(R.layout.column_rows, null);
            txtFirst=(TextView) convertView.findViewById(R.id.stock_Code);
            txtSecond=(TextView) convertView.findViewById(R.id.buy_price);
            txtThird=(TextView) convertView.findViewById(R.id.mk_price);
            txtFourth=(TextView) convertView.findViewById(R.id.chage_per);

        }
        StockViewBean map=list.get(position);
        if(position>0) {
            txtFirst.setTag(map.getId());
            txtFirst.setText(map.getStockName());
            txtSecond.setText(map.getBuyprice());
            txtThird.setText(map.getCurrentprice());
            String bprice = map.getBuyprice();
            String mktprice = map.getCurrentprice();
            if (!bprice.isEmpty() && !mktprice.isEmpty()) {
                float base = Float.parseFloat(bprice);
                float mkt = Float.parseFloat(mktprice);
                float change = ((mkt - base) / base) * 100;
                if (!(change == -100.00)) {
                    txtFourth.setText(String.format("%.2f", change));
                    if (change > 0) {
                        txtFourth.setTextColor(Color.GREEN);
                    } else {
                        txtFourth.setTextColor(Color.RED);
                    }
                } else
                    txtFourth.setText("0.0");
            } else
                txtFourth.setText("0.0");
        }else{
            txtFirst.setTag(map.getId());
            txtFirst.setText(map.getStockName());
            txtSecond.setText(map.getBuyprice());
            txtThird.setText(map.getCurrentprice());
            txtFourth.setText(map.getChange());
            txtFourth.setTextColor(Color.BLACK);
        }
        return convertView;
    }


}
