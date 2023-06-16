package Model;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;

public class StockInfoRetriever {
   public static ArrayList<StockWrapper> retrieveStockInfo() throws Exception {

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
            if(SymbolValid(symbol)==true){
                StockWrapper stock = new StockWrapper(symbol, name, currency, exchange, country, type);
            stockList.add(stock);
                }else{
                    System.out.println("Not Available");
                }
            
        }

        return stockList;
    }

    public static void main(String[] args) {
        try {
            ArrayList<StockWrapper> stockList = retrieveStockInfo();
            for (StockWrapper stock : stockList) {
                String symbol = stock.getSymbol();
                String name = stock.getName();
                String currency = stock.getCurrency();
                String exchange = stock.getExchange();
                String country = stock.getCountry();
                String type = stock.getType();
                DatabaseHandler.insertStockInfo(symbol, name, currency, exchange, country, type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean SymbolValid(String symbol){
        ArrayList<String> temp = new ArrayList <>(Arrays.asList("0041PA.KL","0103PA.KL","0107OR.KL","0150PA.KL","0165PA.KL","0174PA.KL","03001.KL","03002.KL","03005.KL","03008.KL","03011.KL","03012.KL","03018.KL","03023.KL","03025.KL","03027.KL","03029.KL","03030.KL","03036.KL","03038P.KL","03040.KL","03046.KL","03047.KL","03048.KL","03049.KL","03051.KL","03052.KL","03055.KL","03057.KL","0821EA.KL","0829EB.KL","0830EA.KL","0831EA.KL","0832EA.KL","0836EA.KL","2305.KL","3484.KL","5011.KL","5047.KL","5049.KL","5167.KL","5268.KL","5270.KL","5279.KL","6149.KL","6556PA.KL","7017OR.KL","7053.KL","7066PA.KL","7079LR.KL","7082OR.KL","7108PA.KL","7145.KL","7145PA.KL","7203.KL","7226.KL","7245PA.KL","7811.KL","8117PA.KL","8141.KL","8605PA.KL","8648.KL","8664PB.KL","8931.KL","9369.KL"));
        if(temp.contains(symbol)){
            return false;
        }else{
            return true;
        }
    }


}

