package Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.PriorityQueue;

public class StockPriceRetriever {

    private static final String API_KEY = "UM-3b63b32b4332a782a2c7e0fb511b71240c08cd8f57f2627515692a0a55baa83a";

    public static void main(String[] args) throws Exception {
        // updateTable();
        insertTable();
    }

    public static void updateTable() throws Exception {
        ArrayList<StockWrapper> symbols = StockInfoRetriever.retrieveStockInfo();

        for (StockWrapper symbol : symbols) {
            String temp = symbol.getSymbol();
            String apiUrl = "https://wall-street-warriors-api-um.vercel.app/price?apikey=" + API_KEY + "&interval=5&symbol=" + temp;

            URL url = new URL(apiUrl);
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.equals("null")) {
                    result.append(line);
                }
            }
            reader.close();

            String json = result.toString();
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONObject data = jsonObject.getJSONObject(temp);
                Iterator<String> datetimeKeys = data.getJSONObject("Open").keys();

                PriorityQueue<StockPrice> prices = new PriorityQueue<>(Comparator.comparingLong(StockPrice::getDateTime).reversed());

                while (datetimeKeys.hasNext()) {
                    String dateTime = datetimeKeys.next();
                    double open = data.getJSONObject("Open").getDouble(dateTime);
                    long datetime = Long.parseLong(dateTime);
                    StockPrice stockPrice = new StockPrice(datetime, open);
                    prices.offer(stockPrice);
                }

                StockPrice stockPrice = prices.poll();

                double latestPrice = stockPrice.getPrice();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                Date dateTime = new Date(stockPrice.getDateTime());
                String formattedDateTime = sdf.format(dateTime);

                DatabaseHandler.insertStockPrice(temp, latestPrice, formattedDateTime);
                DatabaseHandler.updateStockPrice(temp, latestPrice, formattedDateTime);
                System.out.println(temp + ": " + latestPrice + " (" + formattedDateTime + ")");
                System.out.print(symbol.getSymbol() + "\t");
                System.out.println(symbol.getName());
                System.out.print(sdf.format(new Date(stockPrice.getDateTime())));
                System.out.println("\t" + symbol.getPrice());
            } catch (JSONException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public static void insertTable() throws Exception {
        ArrayList<StockWrapper> symbols = StockInfoRetriever.retrieveStockInfo();

        for (StockWrapper symbol : symbols) {
            String temp = symbol.getSymbol();
            String apiUrl = "https://wall-street-warriors-api-um.vercel.app/price?apikey=" + API_KEY + "&interval=5&symbol=" + temp;

            URL url = new URL(apiUrl);
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.equals("null")) {
                    result.append(line);
                }
            }
            reader.close();

            String json = result.toString();
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONObject data = jsonObject.getJSONObject(temp);
                Iterator<String> datetimeKeys = data.getJSONObject("Open").keys();

                PriorityQueue<StockPrice> prices = new PriorityQueue<>(Comparator.comparingLong(StockPrice::getDateTime).reversed());

                while (datetimeKeys.hasNext()) {
                    String dateTime = datetimeKeys.next();
                    double open = data.getJSONObject("Open").getDouble(dateTime);
                    long datetime = Long.parseLong(dateTime);
                    StockPrice stockPrice = new StockPrice(datetime, open);
                    prices.offer(stockPrice);
                }

                StockPrice stockPrice = prices.poll();

                double latestPrice = stockPrice.getPrice();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                Date dateTime = new Date(stockPrice.getDateTime());
                String formattedDateTime = sdf.format(dateTime);

                DatabaseHandler.insertStockPrice(temp, latestPrice, formattedDateTime);
                System.out.println(temp + ": " + latestPrice + " (" + formattedDateTime + ")");

            } catch (JSONException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

}

class StockPrice {

    private long dateTime;
    private double price;

    public StockPrice(long dateTime, double price) {
        this.dateTime = dateTime;
        this.price = price;
    }

    public long getDateTime() {
        return dateTime;
    }

    public double getPrice() {
        return price;
    }
}