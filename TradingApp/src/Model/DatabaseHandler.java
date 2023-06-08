package Model;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatabaseHandler {
    private static final String JDBC_URL = "jdbc:mysql://34.142.203.161:3306/TradingApp";
    private static final String USERNAME = "user1";
    private static final String PASSWORD = "12345";
    private static User user;

    public DatabaseHandler() {

    }

    public boolean insertUser(String username, String password, String email, String age, String phoneNumber,String nationality) {
        String sql = "INSERT INTO users (Username, Password, Email, Age, PhoneNumber, Nationality, Role, balance, PL_Points) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, email);
            statement.setString(4, age);
            statement.setString(5, phoneNumber);
            statement.setString(6, nationality);
            statement.setString(7, "user");
            statement.setInt(8, 50000);
            statement.setInt(9, 0);

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean checkUser(String inputUsername,String inputPassword){
       
        
        

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
         

            String selectQuery = "SELECT Username, Password, Age, Email, Nationality, PhoneNumber, Role, Notification_Language, Department, Balance, PL_Points  FROM users " +
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
                    user.setBalance( resultSet.getInt("Balance"));
                    user.setPL_Points(resultSet.getInt("PL_Points"));
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
         
            
            String selectQuery = "SELECT Username, Age, Email, Nationality, PhoneNumber, Balance, PL_Points  FROM users  WHERE Role = 'user'";

            PreparedStatement statement = connection.prepareStatement(selectQuery);
          

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                list.add(new User(resultSet.getString("Username"), resultSet.getString("Email"), resultSet.getString("Nationality"), resultSet.getInt("Age"), resultSet.getString("PhoneNumber"), resultSet.getInt("Balance"), resultSet.getInt("PL_Points")));
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

}