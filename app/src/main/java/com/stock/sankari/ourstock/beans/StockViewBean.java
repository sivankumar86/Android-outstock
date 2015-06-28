package com.stock.sankari.ourstock.beans;

/**
 * Created by sivankumar86 on 6/20/2015.
 */
public class StockViewBean {
    private int id;
    private String stockName;
    private String buyprice;
    private String currentprice="0.0";
    private String change;
    private String noStock;
    private String email;

    public StockViewBean(String stockName) {
        this.stockName = stockName;
    }

    public StockViewBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoStock() {
        return noStock;
    }

    public void setNoStock(String noStock) {
        this.noStock = noStock;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getBuyprice() {
        return buyprice;
    }

    public void setBuyprice(String buyprice) {
        this.buyprice = buyprice;
    }

    public String getCurrentprice() {
        return currentprice;
    }

    public void setCurrentprice(String currentprice) {
        this.currentprice = currentprice;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }


}
