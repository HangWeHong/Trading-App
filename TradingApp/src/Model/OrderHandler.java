package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.lang.model.element.QualifiedNameable;

import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import javafx.scene.chart.PieChart.Data;

public class OrderHandler {
 
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/tradingapp";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root12345";
     DatabaseHandler dbh=new DatabaseHandler();

    public OrderHandler() {
    }

  
   
   
    public void handleBuyScenario(String username,  double desiredPrice, int quantity,StockWrapper stockSearched) {
    String stockName=stockSearched.getName();
    double stockPrice = stockSearched.getPrice();
    String stockSymbol = stockSearched.getSymbol();
    


    // Check if desired price matches the fetched price
    if (desiredPrice == stockPrice) {
        // Check for matching sell orders
        Order matchingSellOrder = findMatchingSellOrder(stockSymbol,desiredPrice);
        while(matchingSellOrder!=null){
        if (matchingSellOrder != null && matchingSellOrder.getQuantity() <= quantity) {
            int boughtQuantity = matchingSellOrder.getQuantity();
            // Remove sell order from orders table
            removeOrder(matchingSellOrder);
            dbh.decreaseAccountBalance(username, boughtQuantity*desiredPrice*100);  //*100 as 1 lot = 100 shares 
            dbh.increaseAccountBalance(matchingSellOrder.getUsername(),boughtQuantity*desiredPrice*100);
            insertTransaction(username, "Buy", stockSymbol, boughtQuantity,desiredPrice,"Successful");   
            updateTransaction(matchingSellOrder.getUsername(), "Sell", stockSymbol, boughtQuantity, stockPrice);  
            
            // Update holdings table with purchased stock
            updateHoldings(username, stockSymbol, stockPrice, boughtQuantity);
            quantity -= boughtQuantity;
        } else if (matchingSellOrder != null && matchingSellOrder.getQuantity() > quantity) {
            // Reduce the quantity of the matching sell order
            reduceOrderQuantity(matchingSellOrder, quantity);
            dbh.decreaseAccountBalance(username, quantity*desiredPrice*100);
            dbh.increaseAccountBalance(matchingSellOrder.getUsername(),quantity*desiredPrice*100);
            // Update holdings table with purchased stock
            updateHoldings(username, stockSymbol, stockPrice, quantity);
            insertTransaction(username, "Buy", stockSymbol, quantity,desiredPrice,"Successful");
            updateTransaction(matchingSellOrder.getUsername(), "Sell", stockSymbol, quantity, desiredPrice);  


            quantity = 0;
        }
        matchingSellOrder = findMatchingSellOrder(stockSymbol,desiredPrice);
    }
        // Check if there is enough quantity in lot pool
        if (quantity > 0 && checkLotPool(stockSymbol)) {
             int limit=5;
             if(limit<=quantity){
            // Subtract purchased quantity from lot pool
            subtractLotPool(stockSymbol, limit);
            // Update holdings table with purchased stock
            updateHoldings(username, stockSymbol, stockPrice, limit);
            dbh.decreaseAccountBalance(username, limit*desiredPrice*100);
            quantity-=limit;
            insertTransaction(username, "Buy", stockSymbol, limit,desiredPrice,"Successful");

             }else{
                
                // Subtract purchased quantity from lot pool
                 subtractLotPool(stockSymbol, quantity);
                 // Update holdings table with purchased stock
                 updateHoldings(username, stockSymbol, stockPrice, quantity);
                 dbh.decreaseAccountBalance(username, quantity*desiredPrice*100);
                 quantity=0;
                insertTransaction(username, "Buy", stockSymbol, quantity,desiredPrice,"Successful");

             }
             if (quantity > 0) {
            // Create buy order in orders table
            createOrder(username, stockSymbol, stockPrice, quantity, "Buy");
            dbh.decreaseAccountBalance(username, quantity*desiredPrice*100);
            insertTransaction(username, "Buy", stockSymbol, quantity,desiredPrice,"Pending");

        }
            
        }else{
              createOrder(username, stockSymbol, stockPrice, quantity, "Buy");
            dbh.decreaseAccountBalance(username, quantity*desiredPrice*100);
            insertTransaction(username, "Buy", stockSymbol, quantity,desiredPrice,"Pending");

        }
        } else  {
        // Check for matching sell orders
        Order matchingSellOrder = findMatchingSellOrder(stockSymbol,desiredPrice);
        if (matchingSellOrder != null && matchingSellOrder.getQuantity() <= quantity) {
            int boughtQuantity = matchingSellOrder.getQuantity();
            dbh.decreaseAccountBalance(username, boughtQuantity*desiredPrice*100);
            dbh.increaseAccountBalance(matchingSellOrder.getUsername(),boughtQuantity*desiredPrice*100);
            // Remove sell order from orders table
            removeOrder(matchingSellOrder);
            // Update holdings table with purchased stock
            updateHoldings(username, stockSymbol, desiredPrice, boughtQuantity);
            quantity -= boughtQuantity;
            insertTransaction(username, "Buy", stockSymbol, boughtQuantity,desiredPrice,"Successful");
            updateTransaction(matchingSellOrder.getUsername(), "Sell", stockSymbol, boughtQuantity, desiredPrice);  


        } else if (matchingSellOrder != null && matchingSellOrder.getQuantity() > quantity) {
            // Reduce the quantity of the matching sell order
            reduceOrderQuantity(matchingSellOrder, quantity);
            dbh.decreaseAccountBalance(username, quantity*desiredPrice*100);
            dbh.increaseAccountBalance(matchingSellOrder.getUsername(),quantity*desiredPrice*100);
            // Update holdings table with purchased stock
            updateHoldings(username, stockSymbol, desiredPrice, quantity);
            insertTransaction(username, "Buy", stockSymbol, quantity,desiredPrice,"Successful");
            updateTransaction(matchingSellOrder.getUsername(), "Sell", stockSymbol, quantity, desiredPrice);  


            quantity = 0;
        } else if (quantity > 0) {
            // Create buy order in orders table
            createOrder(username, stockSymbol, desiredPrice, quantity, "Buy");
            dbh.decreaseAccountBalance(username, quantity*desiredPrice*100);
            insertTransaction(username, "Buy", stockSymbol, quantity,desiredPrice,"Pending");

        }
    }
}

public void handleSellScenario(String username, double desiredPrice, int quantity,StockWrapper stockSearched) {
    String stockName=stockSearched.getName();
    String stockSymbol = stockSearched.getSymbol();
    
        // Check for matching buy orders
        Order matchingBuyOrder = findMatchingBuyOrder(stockSymbol,desiredPrice);
        
        while(matchingBuyOrder!=null){
        if (matchingBuyOrder != null && matchingBuyOrder.getQuantity() <= quantity) {
             
            int boughtQuantity = matchingBuyOrder.getQuantity();
            dbh.increaseAccountBalance(username, boughtQuantity*desiredPrice*100);
            // Remove buy order from orders table
            removeOrder(matchingBuyOrder);
            quantity -= boughtQuantity;
            if(getHoldingsQuantity(username, stockSymbol)>boughtQuantity){
                subtractHoldings(username, stockSymbol,boughtQuantity);
            }else{
                 removeHoldings(username, stockSymbol);
            }
            updateHoldings(matchingBuyOrder.getUsername(), stockSymbol, desiredPrice, boughtQuantity);
            insertTransaction(username, "Sell", stockSymbol, boughtQuantity, desiredPrice,"Successful");
            updateTransaction(matchingBuyOrder.getUsername(), "Buy", stockSymbol, quantity, desiredPrice);  


          
        } else if (matchingBuyOrder != null && matchingBuyOrder.getQuantity() > quantity) {
            // Reduce the quantity of the matching buy order
            reduceOrderQuantity(matchingBuyOrder, quantity);
            dbh.increaseAccountBalance(username, quantity*desiredPrice*100);
            // Update holdings table by subtracting sold quantity
           updateHoldings(matchingBuyOrder.getUsername(), stockSymbol, desiredPrice, quantity);
            if(getHoldingsQuantity(username, stockSymbol)>quantity){
                subtractHoldings(username, stockSymbol,quantity);
            }else{
                 removeHoldings(username, stockSymbol);
            }
            insertTransaction(username, "Sell", stockSymbol, quantity, desiredPrice,"Successful");
            updateTransaction(matchingBuyOrder.getUsername(), "Buy", stockSymbol, quantity, desiredPrice);  


            quantity = 0;
        }matchingBuyOrder = findMatchingBuyOrder(stockSymbol,desiredPrice);
    }
     if (quantity > 0) {
            // Create sell order in orders table
              if(getHoldingsQuantity(username, stockSymbol)>quantity){
                subtractHoldings(username, stockSymbol,quantity);
            }else{
                 removeHoldings(username, stockSymbol);
            }
            createOrder(username, stockSymbol, desiredPrice, quantity, "Sell");
            insertTransaction(username, "Sell", stockSymbol, quantity, desiredPrice,"Pending");

        }
    }
    

public int getHoldingsQuantity(String username, String stockSymbol) {
    // Query the holdings table to get the quantity of holdings for the specified stock
    String query = "SELECT Quantity FROM holdings WHERE Username = ? AND Symbol = ? Limit 1";
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, username);
        statement.setString(2, stockSymbol);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int quantity = resultSet.getInt("Quantity");
            return quantity;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0; // If no record is found, return 0 as the default quantity
}
    // Function to find a matching sell order
    private Order findMatchingSellOrder(String stockSymbol,double desiredPrice) {
        // Query the orders table to find a sell order with the desired price
        String query = "SELECT * FROM orders WHERE Type = 'Sell' AND Price = ?  AND Symbol = ? LIMIT 1";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, desiredPrice);
            statement.setString(2, stockSymbol);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Create and return the matching sell order object
                Order sellOrder = new Order(resultSet.getString("Username"),
                        resultSet.getString("Symbol"), resultSet.getDouble("Price"),
                        resultSet.getInt("Quantity"), resultSet.getString("Type"));
                return sellOrder;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // Function to remove the entire row from the holdings table
    private void removeHoldings(String username, String stockSymbol) {
    // Delete the row from the holdings table
    String query = "DELETE FROM holdings WHERE Username = ? AND Symbol = ?";
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, username);
        statement.setString(2, stockSymbol);
        statement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    // Function to remove an order from the orders table
    private void removeOrder(Order order) {
        // Delete the order from the orders table
        String query = "DELETE FROM orders WHERE Username = ? AND Symbol = ? AND Price = ? AND Quantity = ? AND Type = ? LIMIT 1";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, order.getUsername());
            statement.setString(2, order.getSymbol());
            statement.setDouble(3, order.getPrice());
            statement.setInt(4, order.getQuantity());
            statement.setString(5, order.getType());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   private void updateHoldings(String username, String stockSymbol, double price, int quantity) {
    // Check if a holding already exists for the user and stock
    boolean holdingExists = checkHoldingExists(username, stockSymbol, price);

    if (holdingExists) {
        // Update the existing holding with the purchased stock
        String query = "UPDATE holdings SET Quantity = Quantity + ? WHERE Username = ? AND Symbol = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quantity);
            statement.setString(2, username);
            statement.setString(3, stockSymbol);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } else {
        // Create a new holding for the user and stock
        String query = "INSERT INTO holdings (Username, Symbol, Quantity, Price) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, stockSymbol);
            statement.setInt(3, quantity);
            statement.setDouble(4, price);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// Function to check if a holding exists for the user and stock
private boolean checkHoldingExists(String username, String stockSymbol, double price) {
    // Query the holdings table to check if a holding exists
     String query = "SELECT COUNT(*) AS Count FROM holdings WHERE Username = ? AND Symbol = ? AND Price = ?";
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, username);
        statement.setString(2, stockSymbol);
         statement.setDouble(3, price);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int count = resultSet.getInt("Count");
            return count > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
public int numLotPool(String stockSymbol) {
        // Query the stocklist table to check if the lot pool for the stock is sufficient
        String query = "SELECT LotPool FROM stocklist WHERE Symbol = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, stockSymbol);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int lotPool = resultSet.getInt("LotPool");
                return lotPool;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Function to check the lot pool for a given stock and quantity
    public boolean checkLotPool(String stockSymbol) {
        // Query the stocklist table to check if the lot pool for the stock is sufficient
        String query = "SELECT LotPool FROM stocklist WHERE Symbol = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, stockSymbol);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int lotPool = resultSet.getInt("LotPool");
                return 0 < lotPool;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Function to subtract the purchased quantity from the lot pool
    private void subtractLotPool(String stockSymbol, int quantity) {
        // Update the lot pool in the stocklist table by subtracting the purchased quantity
        String query = "UPDATE stocklist SET LotPool = LotPool - ? WHERE Symbol = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quantity);
            statement.setString(2, stockSymbol);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function to create an order in the orders table
    private void createOrder(String username, String stockSymbol, double price, int quantity, String type) {
        // Insert a new order into the orders table
         boolean orderExists = checkOrderExists(username, stockSymbol, price,type);
         if (orderExists) {
        // Update the existing holding with the purchased stock
        String query = "UPDATE orders SET Quantity = Quantity + ? WHERE Username = ? AND Symbol = ? AND Price = ? AND Type = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quantity);
            statement.setString(2, username);
            statement.setString(3, stockSymbol);
            statement.setDouble(4, price);
            statement.setString(5, type);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } else {
        // Create a new holding for the user and stock
        String query = "INSERT INTO orders (Username, Symbol, Price, Quantity, Type) VALUES (?, ?, ?, ?, ?)";

           try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, stockSymbol);
            statement.setDouble(3, price);
            statement.setInt(4, quantity);
            statement.setString(5, type);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     
    }
  

// Function to check if a holding exists for the user and stock
private boolean checkOrderExists(String username, String stockSymbol, double price,String type) {
    // Query the holdings table to check if a holding exists
     String query = "SELECT COUNT(*) AS Count FROM orders WHERE Username = ? AND Symbol = ? AND Price = ? AND Type=?";
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, username);
        statement.setString(2, stockSymbol);
         statement.setDouble(3, price);
         statement.setString(4, type);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int count = resultSet.getInt("Count");
            return count > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
    // Check if the user has the stock and enough quantity in holdings
    public boolean checkHoldings(String username, String stockSymbol, int quantity) {
        // Query the holdings table to check if the user has the stock and enough quantity
        String query = "SELECT Quantity FROM holdings WHERE Username = ? AND Symbol = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, stockSymbol);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int holdingsQuantity = resultSet.getInt("Quantity");
                return quantity <= holdingsQuantity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Function to find a matching buy order
    private Order findMatchingBuyOrder(String stockSymbol,double desiredPrice) {
        // Query the orders table to find a buy order with the desired price
        String query = "SELECT * FROM orders WHERE Type = 'Buy' AND Price = ? AND Symbol = ? LIMIT 1";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, desiredPrice);
            statement.setString(2, stockSymbol);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Create and return the matching buy order object
                Order buyOrder = new Order(
                        resultSet.getString("Username"),
                        resultSet.getString("Symbol"),
                        resultSet.getDouble("Price"),
                        resultSet.getInt("Quantity"),
                        resultSet.getString("Type")
                );
                return buyOrder;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void reduceOrderQuantity(Order order, int quantity) {
    // Reduce the quantity of the order
    int remainingQuantity = order.getQuantity() - quantity;
    order.setQuantity(remainingQuantity);

    // If the order quantity becomes zero, remove the order from the orders table
    if (remainingQuantity == 0) {
        removeOrder(order);
    } else {
        // Update the order in the orders table with the reduced quantity
        updateOrder(order);
    }
}
     private void subtractHoldings(String username, String stockSymbol, int quantity) {
        // Update the holdings table by subtracting the sold quantity
        String query = "UPDATE holdings SET Quantity = Quantity - ? WHERE Username = ? AND Symbol = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quantity);
            statement.setString(2, username);
            statement.setString(3, stockSymbol);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void updateOrder(Order order) {
    // Update the order in the orders table
    String query = "UPDATE orders SET Quantity = ? WHERE Username = ? AND Symbol = ? AND Price = ? AND Type = ? Limit 1";
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, order.getQuantity());
        statement.setString(2, order.getUsername());
        statement.setString(3, order.getSymbol());
        statement.setDouble(4, order.getPrice());
        statement.setString(5, order.getType());
        statement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
public boolean cancelOrder(String username, String orderType) {
        String query = "SELECT Price, Quantity, Symbol FROM orders WHERE Username = ? AND Type = ? ORDER BY (Price * Quantity) DESC LIMIT 1";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, orderType);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                double highestAmount = resultSet.getDouble("Price") * resultSet.getInt("Quantity");
                DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        
            // Format the double value
                String formattedHighestNumber = decimalFormat.format(highestAmount);
                highestAmount=Double.parseDouble(formattedHighestNumber);
                // Delete the order with the highest amount
                deleteOrder(username, orderType, highestAmount);
                if(orderType.equals("Buy")){
                    dbh.increaseAccountBalance(username,highestAmount*100);
                }else{
                    updateHoldings(username, resultSet.getString("Symbol"), resultSet.getDouble("Price")  , resultSet.getInt("Quantity"));
                }
                
                cancelTransaction(username, orderType,resultSet.getString("Symbol"), resultSet.getDouble("Price"),resultSet.getInt("Quantity"));

                return true;
            } else {
                System.out.println("No orders found for cancellation.");
                
            }
            resultSet.close();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void deleteOrder(String username, String orderType, double highestAmount) {
        String deleteQuery = "DELETE FROM orders WHERE Username = ? AND Type = ? AND (Price * Quantity) = ? ";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(deleteQuery)) {

            statement.setString(1, username);
            statement.setString(2, orderType);
            statement.setDouble(3, highestAmount);

            System.out.println(statement);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Order cancellation successful.");
            } else {
                System.out.println("Failed to cancel order.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private boolean insertTransaction(String username, String orderType, String stockSymbol,int quantity,double price,String status) {
        String sql = "INSERT INTO transactions (Username, Symbol, Price, Quantity, Type, Status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, stockSymbol);
            statement.setDouble(3, price);
            statement.setInt(4, quantity);
            statement.setString(5, orderType);
            statement.setString(6, status);

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    private boolean updateTransaction(String username, String orderType, String stockSymbol,int quantity,double price) {
        String query = "UPDATE transactions SET Status = ? WHERE Username = ? AND Symbol = ? AND Price = ? AND Type = ? AND Status = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, "Successful");
            statement.setString(2, username);
            statement.setString(3, stockSymbol);
            statement.setDouble(4, price);
            statement.setString(5, orderType);
            statement.setString(6, "Pending");
            statement.executeUpdate();
            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
     private void cancelTransaction(String username, String orderType, String stockSymbol,double price,int quantity) {
        // Update the holdings table by subtracting the sold quantity
        String query = "UPDATE transactions SET Status = ? WHERE Username = ? AND Symbol = ? AND Price = ? AND Type = ? AND Status = ? AND Quantity = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "Cancelled");
            statement.setString(2, username);
            statement.setString(3, stockSymbol);
            statement.setDouble(4, price);
            statement.setString(5, orderType);
            statement.setString(6, "Pending");
            statement.setInt(7, quantity);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

