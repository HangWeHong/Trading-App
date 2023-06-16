package Model;
public class PLPoint{
        private Double PLPoint;
    private String username;
    private String date;
    
    public PLPoint(Double PLPoint, String username, String date) {
        this.PLPoint = PLPoint;
        this.username = username;
        this.date = date;
    }
    
    public Double getPLPoint() {
        return PLPoint;
    }
    
    public void setPLPoint(Double PLPoint) {
        this.PLPoint = PLPoint;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
}