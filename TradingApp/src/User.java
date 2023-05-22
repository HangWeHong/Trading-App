import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private String username;
    private String email;
    private String password;
    private String nationality;
    private int age;
    private int phoneNum;
    private String role;

    public User(String username, String email, String password, String nationality, int age, int phoneNum) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nationality = nationality;
        this.age = age;
        this.phoneNum = phoneNum;
    }


  


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(int phoneNum) {
        this.phoneNum = phoneNum;
    }
    
    public boolean checkUser(String inputUsername,String inputPassword){
        String url = "jdbc:mysql://localhost:3306/Testing";
        String username = "user1";
        String password = "12345";
        

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
         

            String selectQuery = "SELECT Name, Email, Password, Nationality, Age, PhoneNumber, Role FROM users " +
                                 "WHERE Name = ? AND Password = ?";

            PreparedStatement statement = connection.prepareStatement(selectQuery);
            statement.setString(1, inputUsername);
            statement.setString(2, inputPassword);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                setUsername( resultSet.getString("Name"));
                setEmail( resultSet.getString("Email"));
                setPassword(resultSet.getString("Password"));
                setNationality(resultSet.getString("Nationality"));
                setAge(resultSet.getInt("Age"));
                setPhoneNum( resultSet.getInt("PhoneNumber"));
                setRole(resultSet.getString("Role"));

                 

                // Do something with the user object
                System.out.println("User found:");
                System.out.println("Username: " + getUsername());
                System.out.println("Email: " + getEmail());
                System.out.println("Password: " + getPassword());
                System.out.println("Nationality: " + getNationality());
                System.out.println("Age: " + getAge());
                System.out.println("Phone Number: " + getPhoneNum());
                return true;
            } else {
                System.out.println("User not found.");
                
            }

            resultSet.close();
            statement.close();
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public String getRole() {
        return role;
    }


    public void setRole(String role) {
        this.role = role;
    }
}



    

    
    