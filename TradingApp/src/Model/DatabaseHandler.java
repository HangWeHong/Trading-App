package Model;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHandler {
    private static final String JDBC_URL = "jdbc:mysql://34.143.154.148:3306/Testing";
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
                    user.setRole(resultSet.getString("PL_Points"));
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

    public User getUser(){
        return user;
    }
    public static void setUser(User user1){
        user=user1;
    }

}