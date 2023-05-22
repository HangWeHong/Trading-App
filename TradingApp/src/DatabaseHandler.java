import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseHandler {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/Testing";
    private static final String USERNAME = "user1";
    private static final String PASSWORD = "12345";

    public DatabaseHandler() {

    }

    public boolean insertUser(String username, String password, String email, String age, String phoneNumber,
            String nationality) {
        String sql = "INSERT INTO users (Name, Password, Email, Age, PhoneNumber, Nationality) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, email);
            statement.setString(4, age);
            statement.setString(5, phoneNumber);
            statement.setString(6, nationality);

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
