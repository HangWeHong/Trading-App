
import java.sql.*;
public class PushUser {
public static void main(String[] args) {
    try {
    String url = "jdbc:mysql://localhost:3306/Testing";
        Connection conn = DriverManager.getConnection(url, "root", "12345");
    
        // Create a PreparedStatement to insert user data into the database
        String sql = "INSERT INTO users (Name, Age, Nationality, Password, Email, Role) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, "Admin1");
        stmt.setInt(2, 18);
        stmt.setString(3, "Afican");
        stmt.setString(4, "123456");
        stmt.setString(5, "Admin@gmail.com");
        stmt.setString(6, "Admin");
    
        // Execute the INSERT query
        int rowsInserted = stmt.executeUpdate();
    
        if (rowsInserted > 0) {
            System.out.println("User registered successfully.");
        } else {
            System.out.println("Failed to register user.");
        }
    
        // Close the database resources
        stmt.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
}
}


       