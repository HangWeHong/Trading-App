import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserCheck {
    public static User user;
    
    public static void main(String[] args) {
        
        String url = "jdbc:mysql://localhost:3306/Testing";
        String username = "user1";
        String password = "12345";
        

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter username: ");
            String inputUsername = scanner.nextLine();

            System.out.print("Enter password: ");
            String inputPassword = scanner.nextLine();

            String selectQuery = "SELECT Name, Email, Password, Nationality, Age, PhoneNumber FROM users " +
                                 "WHERE Name = ? AND Password = ?";

            PreparedStatement statement = connection.prepareStatement(selectQuery);
            statement.setString(1, inputUsername);
            statement.setString(2, inputPassword);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String Name = resultSet.getString("Name");
                String email = resultSet.getString("Email");
                String Password = resultSet.getString("Password");
                String nationality = resultSet.getString("Nationality");
                int age = resultSet.getInt("Age");
                int phoneNum = resultSet.getInt("PhoneNumber");

                 user = new User(Name, email, Password, nationality, age, phoneNum);

                // Do something with the user object
                System.out.println("User found:");
                System.out.println("Username: " + user.getUsername());
                System.out.println("Email: " + user.getEmail());
                System.out.println("Password: " + user.getPassword());
                System.out.println("Nationality: " + user.getNationality());
                System.out.println("Age: " + user.getAge());
                System.out.println("Phone Number: " + user.getPhoneNum());
            } else {
                System.out.println("User not found.");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
