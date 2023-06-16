package Model;

  public class Order {
    private String username;
    private String symbol;
    private double price;
    private int quantity;
    private String type;

    public Order(String username, String symbol, double price, int quantity, String type) {
        this.username = username;
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
    }
    public Order( String symbol, double price, int quantity, String type) {
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getType() {
        return type;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setType(String type) {
        this.type = type;
    }


   
}