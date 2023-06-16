package Model;

public class Transaction {
    private String dateTime;
    private String symbol;
    private String Order_Type;
    private int quantity;
    private double price;
    private String Status;



    public Transaction( String dateTime, String symbol, double price, int quantity, String Order_Type, String Status) {
        this.dateTime = dateTime;
        this.symbol = symbol;
        this.Order_Type = Order_Type;
        this.quantity = quantity;
        this.price = price;
        this.Status = Status;
    }
    // Getters and Setters

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrder_Type() {
        return Order_Type;
    }

    public void setOrder_Type(String Order_Type) {
        this.Order_Type = Order_Type;
    }

}

