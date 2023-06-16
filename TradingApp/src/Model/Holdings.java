package Model;

public class Holdings {
    String symbol;
    Double price;
    int quantity;

    
    public Holdings( String symbol, Double price, int quantity) {
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
    }
   

    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    

}
