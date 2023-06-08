package Model;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class newsService {
   
    public  ArrayList<newsData> newsServices() throws IOException {
    ArrayList<newsData> newsList = new ArrayList<>();
    String apiUrl = "https://api.marketaux.com/v1/news/all?filter_entities=true&api_token=xGIHLnVOuatdh3WM0vMLXGxvyijvQPLGgUxSLWKz&language=en&countries=my";

    URL url = new URL(apiUrl);
    URLConnection conn = url.openConnection();
    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

    String line;
    StringBuilder result = new StringBuilder();
        while((line =reader.readLine())!=null)

    {
        result.append(line);
    }
        reader.close();
        try{
        JSONObject jsonObject = new JSONObject(result.toString());
        JSONArray data = jsonObject.getJSONArray("data");

        for (int i = 0; i < data.length(); i++) {
            JSONObject article = data.getJSONObject(i);
            String title = article.getString("title");
            String description = article.getString("description");
            String urlLink = article.getString("url");
            String publishedAtStr = article.getString("published_at");
            LocalDateTime publishedAt = LocalDateTime.parse(publishedAtStr, DateTimeFormatter.ISO_OFFSET_DATE_TIME);

            newsData stockWrapper = new newsData(title, description, urlLink, publishedAt);
            newsList.add(stockWrapper);
        }
    }catch(JSONException e){
        e.printStackTrace();
    }

        return newsList;
    }


}
