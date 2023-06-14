package Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class StockAPI {
    private static final String API_KEY = "UM-3b63b32b4332a782a2c7e0fb511b71240c08cd8f57f2627515692a0a55baa83a";

    private static Map<String, Double> latestPrices = new TreeMap<>();

    public static void main(String[] args) throws Exception {
        // updateTable();
        insertTable();
    }

    public static void updateTable() throws Exception {
        ArrayList<StockWrapper> symbols = getStockList();

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

                String latestDatetimeKey = null;
                while (datetimeKeys.hasNext()) {
                    latestDatetimeKey = datetimeKeys.next();
                }
                
                if (latestDatetimeKey != null) {
                    double latestPrice = data.getJSONObject("Open").getDouble(latestDatetimeKey);
                    latestPrices.put(temp, latestPrice);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    long timestamp = Long.parseLong(latestDatetimeKey);
                    Date dateTime = new Date(timestamp);
                    String formattedDateTime = sdf.format(dateTime);

                    DatabaseHandler.insertStockPrice(temp, latestPrice, formattedDateTime);
                    DatabaseHandler.updateStockPrice(temp, latestPrice, formattedDateTime);
                    System.out.println(temp + ": " + latestPrice + " (" + formattedDateTime + ")");
                }
            } catch (JSONException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public static void insertTable() throws Exception {
        ArrayList<StockWrapper> symbols = getStockList();

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

                String latestDatetimeKey = null;
                while (datetimeKeys.hasNext()) {
                    latestDatetimeKey = datetimeKeys.next();
                }

                if (latestDatetimeKey != null) {
                    double latestPrice = data.getJSONObject("Open").getDouble(latestDatetimeKey);
                    latestPrices.put(temp, latestPrice);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    long timestamp = Long.parseLong(latestDatetimeKey);
                    Date dateTime = new Date(timestamp);
                    String formattedDateTime = sdf.format(dateTime);

                    DatabaseHandler.insertStockPrice(temp, latestPrice, formattedDateTime);
                    System.out.println(temp + ": " + latestPrice + " (" + formattedDateTime + ")");
                }
            } catch (JSONException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public static ArrayList<StockWrapper> getStockList() throws Exception {
        ArrayList<StockWrapper> stockList = new ArrayList<>();
        String apiUrl = "https://wall-street-warriors-api-um.vercel.app/mylist?apikey=" + API_KEY;

        URL url = new URL(apiUrl);
        URLConnection conn = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line;
        StringBuilder result = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        reader.close();

        JSONArray jsonArray = new JSONArray(result.toString());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject stockJson = jsonArray.getJSONObject(i);
            String symbol = stockJson.getString("symbol");
            String name = stockJson.getString("name");
            String currency = stockJson.getString("currency");
            String exchange = stockJson.getString("exchange");
            String country = stockJson.getString("country");
            String type = stockJson.getString("type");
            StockWrapper stockWrapper = new StockWrapper(symbol, name, currency, exchange, country, type);
            stockList.add(stockWrapper);
        }

        return stockList;
    }
}