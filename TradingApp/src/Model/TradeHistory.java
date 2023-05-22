package Model;

import java.sql.Time;

public class TradeHistory{
    private  int orderNo;
    private String stockName;
    private int shares;
    private int Price;
    private Time time;

    public  int getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }
    public String getStockName() {
        return stockName;
    }
    public void setStockName(String stockName) {
        this.stockName = stockName;
    }
    public int getShares() {
        return shares;
    }
    public void setShares(int shares) {
        this.shares = shares;
    }
    public int getPrice() {
        return Price;
    }
    public void setPrice(int price) {
        Price = price;
    }
    public Time getTime() {
        long now = System.currentTimeMillis();  //get milliseconds of time
        Time time=new Time(now);
        this.time=time;
        return this.time;
    }
    public void setTime() {
        long now = System.currentTimeMillis();
        Time time=new Time(now);
        this.time=time;
        
    }
    
}
