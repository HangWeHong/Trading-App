package Model;


import java.util.Timer;
import java.util.TimerTask;



public class RefreshService {
  
    public static void main(String[] args) {
        Timer timer = new Timer();

        // Schedule the task to run every 5 minutes
        timer.schedule(new RefreshDataTask(), 0, 5 * 60 * 1000);
    }

    private static class RefreshDataTask extends TimerTask {
        @Override
        public void run() {
            try {
                // Fetch and display the data from the API
                StockAPI.updateTable();

            } catch (Exception e) {
                System.out.println("Error fetching data from the API: " + e.getMessage());
            }
        }
    }
    
}