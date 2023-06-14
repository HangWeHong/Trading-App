package Model;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class StockInfo {
    public static ArrayList<StockWrapper> getStock() throws Exception {
        ArrayList<StockWrapper> stockList = new ArrayList<>();
        String apiUrl = "https://wall-street-warriors-api-um.vercel.app/mylist?apikey=UM-3b63b32b4332a782a2c7e0fb511b71240c08cd8f57f2627515692a0a55baa83a";

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

    public static void main(String[] args) {
        try {
            ArrayList<StockWrapper> stockList = getStock();
            for (StockWrapper stock : stockList) {
                String symbol = stock.getSymbol();
                String name = stock.getName();
                String currency = stock.getCurrency();
                String exchange = stock.getExchange();
                // String mic_code = stock.getMic_code();
                String country = stock.getCountry();
                String type = stock.getType();

                // Call the storeInfo method with the stock information
                DatabaseHandler.insertStockInfo(symbol, name, currency, exchange, country, type);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

    }
}

