package Model;

import java.sql.*;


public class StockSearch {
    public static StockWrapper getStockData(String name) {
        StockWrapper stockData =null;
        final String JDBC_URL = "jdbc:mysql://localhost:3306/tradingapp";
        final String USERNAME = "root";
        final String PASSWORD = "root12345";
        String sql = "SELECT StockInfo.Symbol, StockInfo.Name, StockInfo.Currency, StockInfo.Exchange, StockInfo.Country, StockInfo.Type, StockList.Price,StockList.Updated_Date "
                +
                "FROM StockInfo " +
                "JOIN StockList ON StockList.Symbol = StockInfo.Symbol " +
                "WHERE StockInfo.Name = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String stockSymbol = resultSet.getString("Symbol");
                String Stockname = resultSet.getString("Name");
                String currency = resultSet.getString("Currency");
                String exchange = resultSet.getString("Exchange");
                // String micCode = resultSet.getString("Mic_Code");
                String country = resultSet.getString("Country");
                String type = resultSet.getString("Type");
                double price = resultSet.getDouble("Price");
                String Updated_Date = resultSet.getString("Updated_Date");

                stockData = new StockWrapper (stockSymbol,Stockname, currency,  exchange,  country,  type,price,Updated_Date);
                
            }

            resultSet.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stockData;
    }

    

 
}

