package Model;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatabaseHandler {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/tradingapp";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root12345";
    private static User user;

    public DatabaseHandler() {

    }
    

    public boolean insertUser(String username, String password, String email, String age, String phoneNumber,String nationality) {
        String sql = "INSERT INTO users (Username, Password, Email, Age, PhoneNumber, Nationality, Role, balance, PL_Points, Qualified, Cost, Revenue, InitialBalance) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, email);
            statement.setString(4, age);
            statement.setString(5, phoneNumber);
            statement.setString(6, nationality);
            statement.setString(7, "user");
            statement.setDouble(8, 50000);
            statement.setDouble(9, 0);
            statement.setBoolean(10, true);
            statement.setDouble(11, 0);
            statement.setDouble(12, 0);
            statement.setDouble(13, 50000);

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean checkUser(String inputUsername,String inputPassword){
       
        
        

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
         

            String selectQuery = "SELECT Username, Password, Age, Email, Nationality, PhoneNumber, Role, Notification_Language, Department, balance, PL_Points, Qualified  FROM users " +
                                 "WHERE Username = ? AND Password = ?";

            PreparedStatement statement = connection.prepareStatement(selectQuery);
            statement.setString(1, inputUsername);
            statement.setString(2, inputPassword);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user=new User();
                user.setUsername( resultSet.getString("Username"));
                user.setEmail( resultSet.getString("Email"));
                user.setPassword(resultSet.getString("Password"));
                user.setNationality(resultSet.getString("Nationality"));
                user.setAge(resultSet.getInt("Age"));
                user.setPhoneNum( resultSet.getString("PhoneNumber"));
                user.setRole(resultSet.getString("Role"));
                if(user.getRole().equals("Admin")){
                    user.setNotificationLanguage(resultSet.getString("Notification_Language"));
                    user.setDepartment(resultSet.getString("Department"));
                }else if(user.getRole().equals("user")){
                    user.setBalance( resultSet.getDouble("balance"));
                    user.setPL_Points(resultSet.getDouble("PL_Points"));
                    user.setQualified(resultSet.getBoolean("Qualified"));
                }
                 

               
                return true;
            } 

            resultSet.close();
            statement.close();
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean deleteUser(String username) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String deleteQuery = "DELETE FROM users WHERE Username = ?";
            PreparedStatement statement = connection.prepareStatement(deleteQuery);
            statement.setString(1, username);
    
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return true; // User deleted successfully
            }
    
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // User not found or deletion failed
    }
    public String encryptPassword(String password) {
        try {
            // Create a SHA-256 MessageDigest instance
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Convert the password string to bytes
            byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);

            // Apply the digest calculation to the password bytes
            byte[] hashedBytes = digest.digest(passwordBytes);

            // Convert the hashed bytes to a hexadecimal representation
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    public ObservableList<User> displayUsers(){
        ObservableList<User> list=FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
         
            
            String selectQuery = "SELECT Username, Age, Email, Nationality, PhoneNumber, balance, PL_Points , Qualified  FROM users  WHERE Role = 'user'";

            PreparedStatement statement = connection.prepareStatement(selectQuery);
          

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                list.add(new User(resultSet.getString("Username"), resultSet.getString("Email"), resultSet.getString("Nationality"), resultSet.getInt("Age"), resultSet.getString("PhoneNumber"), resultSet.getDouble("balance"), resultSet.getDouble("PL_Points"),resultSet.getBoolean("Qualified")));
            }
            resultSet.close();
            statement.close();
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public User getUser(){
        return user;
    }
    public static void setUser(User user1){
        user=user1;
    }
    public static void insertStockPrice(String Symbol, double price, String date) {
        String sql = "INSERT INTO StockList (Symbol, Price, Updated_Date) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, Symbol);
            statement.setDouble(2, price);
            statement.setString(3, date);

            statement.executeUpdate();

           
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Fail to insert");
        }
    }

    public static void updateStockPrice(String symbol, double newPrice, String newDate) {
        String sql = "UPDATE StockList SET Price = ?, Updated_Date = ? WHERE Symbol = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, newPrice);
            statement.setString(2, newDate);
            statement.setString(3, symbol);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Stock price updated successfully.");
            } else {
                System.out.println("No matching stock found for the symbol: " + symbol);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void insertStockInfo(String symbol, String name, String currency, String exchange,
                                     String country, String type) {
        String sql = "INSERT INTO StockInfo (Symbol, Name, Currency, Exchange, Country, Type) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, symbol);
            statement.setString(2, name);
            statement.setString(3, currency);
            statement.setString(4, exchange);
            statement.setString(5, country);
            statement.setString(6, type);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Stock data inserted successfully.");
            } else {
                System.out.println("Failed to insert stock data.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Fail");
        }
    }
    public static void scheduleLotPoolReplenishment() {
        Timer timer = new Timer();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        // Set the first execution time to tomorrow 0:00
        Date firstExecutionTime = calendar.getTime();
        if (firstExecutionTime.before(new Date())) {
            // If the first execution time has already passed for today, move it to tomorrow
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            firstExecutionTime = calendar.getTime();
        }

        // Schedule the task to run daily at 0:00
        timer.scheduleAtFixedRate(new LotPoolReplenishmentTask(), firstExecutionTime, 24 * 60 * 60 * 1000);
    }

    // Custom TimerTask implementation for replenishing LotPool
    static class LotPoolReplenishmentTask extends TimerTask {
        @Override
        public void run() {
            replenishLotPool();
        }
    }
    

    public static void replenishLotPool() {
        LocalDate currentDate = LocalDate.now();
        LocalDate threeDaysAfterStartDate = TradingRestrictions.getStartDate().plusDays(3);

        int replenishValue = currentDate.isBefore(threeDaysAfterStartDate) ? Integer.MAX_VALUE : 500;

        String sql = "UPDATE StockList SET LotPool = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, replenishValue);

            int rowsUpdated = statement.executeUpdate();
            System.out.println(rowsUpdated + " rows updated.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
   public static void schedulePLPointsUpdate() {
    Timer timer = new Timer();
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.HOUR_OF_DAY, 10);
    calendar.set(Calendar.MINUTE, 19);
    calendar.set(Calendar.SECOND, 0);
    

    Date firstExecutionTime = calendar.getTime();
   
        if (firstExecutionTime.before(new Date())) {
            // If the first execution time has already passed for today, move it to tomorrow
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            firstExecutionTime = calendar.getTime();
        }
    
    // Schedule the task to run daily at 0:00 from Monday to Friday
    timer.scheduleAtFixedRate(new PLPointsUpdateTask(), firstExecutionTime, 24 * 60 * 60 * 1000);
}
// Custom TimerTask implementation for updating PL_Points
static class PLPointsUpdateTask extends TimerTask {
    @Override
    public void run() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();
        double PL_Points=0;
        // Check if it's a weekday (Monday to Friday)
        if (currentDate.getDayOfWeek().getValue() >= 1 && currentDate.getDayOfWeek().getValue() <= 5) {
            // Get the users with Role="user" from the users table
            List<User> userList = getUsersWithRole("user");
            for (User user : userList) {
                // Get the revenue, cost, and initial balance
                double revenue = user.getRevenue();
                double cost = user.getCost();
                double initialBalance = user.getInitialBalance();

                // Calculate profitOrLoss and PL_Points
                double profitOrLoss = revenue - cost;
                 PL_Points = (profitOrLoss / initialBalance) * 100;
                // Update the PL_Points in the users table
                updatePL_Points(user.getUsername(), PL_Points);
                 insertPoints(user.getUsername(),PL_Points);
                // Reset cost and revenue to 0, and update the initial balance
                resetCostAndRevenue(user.getUsername(), user.getBalance());
                
            }
        }
           

    }

    private List<User> getUsersWithRole(String role) {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT Username, Revenue, Cost, balance, InitialBalance FROM users WHERE Role = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, role);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setUsername(resultSet.getString("Username"));
                user.setRevenue(resultSet.getDouble("Revenue"));
                user.setCost(resultSet.getDouble("Cost"));
                user.setBalance(resultSet.getDouble("balance"));
                user.setInitialBalance(resultSet.getDouble("InitialBalance"));

                userList.add(user);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    private void updatePL_Points(String username, double PL_Points) {
    String sql = "UPDATE users SET PL_Points = PL_Points + ? WHERE Username = ?";
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setDouble(1, PL_Points);
        statement.setString(2, username);

        int rowsUpdated = statement.executeUpdate();
        System.out.println(rowsUpdated + " rows updated.");

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    private void resetCostAndRevenue(String username, double currentBalance) {
        String sql = "UPDATE users SET Revenue = 0, Cost = 0, InitialBalance = ? WHERE Username = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, currentBalance);
            statement.setString(2, username);

            int rowsUpdated = statement.executeUpdate();
            System.out.println(rowsUpdated + " rows updated.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
public static void scheduleUserBalanceCheck() {
    Timer timer = new Timer();
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.HOUR_OF_DAY, 17);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);

    Date firstExecutionTime = calendar.getTime();
    if (firstExecutionTime.before(new Date())) {
        // If the first execution time has already passed for today, move it to tomorrow
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        firstExecutionTime = calendar.getTime();
    }

    // Schedule the task to run daily at 17:00 (5:00 PM) from Monday to Friday
    timer.schedule(new UserBalanceCheckTask(), firstExecutionTime, 24 * 60 * 60 * 1000);
}

// Custom TimerTask implementation for checking user balance
static class UserBalanceCheckTask extends TimerTask {
    @Override
    public void run() {
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();
        // Check if it's a weekday (Monday to Friday) and before 17:00
        if (now.getDayOfWeek().getValue() >= 1 && now.getDayOfWeek().getValue() <= 5 &&
                now.toLocalTime().isBefore(LocalTime.of(17, 0))) {

            // Get the users with Role="user" from the users table
            List<User> userList = getUsersWithRole("user");
            for (User user : userList) {
                double balance = user.getBalance();
                double initialBalance = user.getInitialBalance();

                // Check if the balance is non-negative and less than 0.5 * InitialBalance
                if (balance < 0 || balance >= 0.5 * initialBalance) {
                     System.out.println("hi4");
                    // Update the Qualified column to false in the users table
                    updateQualifiedStatus(user.getUsername(), false);
                }
            }
        }
    }
     private List<User> getUsersWithRole(String role) {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT Username, balance, InitialBalance FROM users WHERE Role = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, role);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setUsername(resultSet.getString("Username"));
                user.setBalance(resultSet.getDouble("balance"));
                user.setInitialBalance(resultSet.getDouble("InitialBalance"));

                userList.add(user);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    private void updateQualifiedStatus(String username, boolean qualified) {
        String sql = "UPDATE users SET Qualified = ? WHERE Username = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBoolean(1, qualified);
            statement.setString(2, username);

            int rowsUpdated = statement.executeUpdate();
            System.out.println(rowsUpdated + " rows updated.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
public static void refreshStockPrice(){
      Timer timer = new Timer();

        // Schedule the task to run every 5 minutes
        timer.schedule(new RefreshDataTask(), 0, 5 * 60 * 1000);
}
  static class RefreshDataTask extends TimerTask {
        @Override
        public void run() {
            try {
                // Fetch and display the data from the API
                StockPriceRetriever.updateTable();

            } catch (Exception e) {
                System.out.println("Error fetching data from the API: " + e.getMessage());
            }
        }
    }
   public void increaseAccountBalance(String username, double amount) {
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
        String updateQuery = "UPDATE users SET balance = balance + ?, Revenue = Revenue + ? WHERE Username = ?";
        PreparedStatement statement = connection.prepareStatement(updateQuery);
        statement.setDouble(1, amount);
        statement.setDouble(2, amount);
        statement.setString(3, username);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Account balance increased successfully.");
        } else {
            System.out.println("Failed to increase account balance.");
        }
        statement.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public void decreaseAccountBalance(String username, double amount) {
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
        String updateQuery = "UPDATE users SET balance = balance - ?, Cost = Cost + ? WHERE Username = ?";
        PreparedStatement statement = connection.prepareStatement(updateQuery);
        statement.setDouble(1, amount);
        statement.setDouble(2, amount);
        statement.setString(3, username);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Account balance decreased successfully.");
        } else {
            System.out.println("Failed to decrease account balance.");
        }
        statement.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    public List<String> getCompanyNames() {
    List<String> companyNames = new ArrayList<>();

    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
        String selectQuery = "SELECT Name FROM stockinfo";
        PreparedStatement statement = connection.prepareStatement(selectQuery);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String companyName = resultSet.getString("Name");
            companyNames.add(companyName);
        }

        statement.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return companyNames;
}
 public double  getLatestBalance(String username) {
        String sql = "SELECT balance FROM users WHERE Username = ?";
        Double balance=0.0;
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
                
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
            balance = resultSet.getDouble("balance");
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return balance;
    }
     public double getLatestPoints(String username) {
        String sql = "SELECT PL_Points FROM users WHERE Username = ?";
        Double points=0.0;
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
                
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
            points = resultSet.getDouble("PL_Points");
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return points;
    }
    public static void insertPoints(String username,Double points){
        String sql="INSERT INTO Point (Username, PL_Point) VALUES (?, ?)";
         try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setDouble(2,points);
            statement.executeUpdate(); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<Transaction> loadTransactionData() {
        ArrayList<Transaction> list = new ArrayList<>();
        try {
            // Establish a database connection
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // Create a statement
            Statement statement = connection.createStatement();

            // Execute a query to retrieve data from the table
            ResultSet resultSet = statement.executeQuery("SELECT * FROM transaction ORDER BY date_time ASC");

            // Populate the list with the retrieved data
            while (resultSet.next()) {
                String dateTime = resultSet.getString("date_time");
                String symbol = resultSet.getString("symbol");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                String Order_Type = resultSet.getString("Order_Type");
                String Status = resultSet.getString("Status");

                Transaction transaction = new Transaction(dateTime, symbol, price, quantity, Order_Type, Status);
                list.add(transaction);
            }

            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<PLPoint> loadPLPoint() {
        ArrayList<PLPoint> list = new ArrayList<>();
        try {
            // Establish a database connection
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // Create a statement
            Statement statement = connection.createStatement();

            // Execute a query to retrieve data from the table
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Point ORDER BY date ASC");

            // Populate the list with the retrieved data
            while (resultSet.next()) {
                String Username = resultSet.getString("Username");
                String date = resultSet.getString("Date");
                double PLPoint = resultSet.getDouble("PL_Point");

                PLPoint points = new PLPoint(PLPoint,Username,date);
                list.add(points);
            }

            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }



}