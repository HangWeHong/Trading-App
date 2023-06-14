package Model;

public class StockWrapper {
    private String symbol;
    private String name;
    private String currency;
    private String exchange;
    private String country;
    private String type;
   private double price;
    private String Updated_Date;

    
    public StockWrapper(String symbol, String name,String currency, String exchange, String country, String type) {
        this(symbol,name,currency,exchange,country,type,0,"");
    }
    public StockWrapper(String symbol, String name, String currency, String exchange, String country,
            String type, double price, String Updated_Date) {
        this.symbol = symbol;
        this.name = name;
        this.currency=currency;
        this.exchange=exchange;
        this.country=country;
        this.type=type;
        this.price = price;
        this.Updated_Date = Updated_Date;
    }

    public String getSymbol() {
        return symbol;
    }
    public String getName() {
        return name;
    }
    public String getCurrency() {
        return currency;
    }
    public String getExchange() {
        return exchange;
    }
    
    public String getCountry() {
        return country;
    }
    public String getType() {
        return type;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setType(String type) {
        this.type = type;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getUpdated_Date() {
        return Updated_Date;
    }
    public void setUpdated_Date(String updated_Date) {
        Updated_Date = updated_Date;
    }
}




