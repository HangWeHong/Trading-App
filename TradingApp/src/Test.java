import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;
import Model.DatabaseHandler;
import Model.User;
import Model.newsData;
import Model.newsService;
import javafx.collections.ObservableList;


public class Test {
    private User user;
    private static DatabaseHandler dbh=new DatabaseHandler();
    private static  newsService newsService=new newsService();
    public static void main(String[] args) {
        System.out.println(encryptPassword(""));
                

        //dbh.insertUser("lala", encryptPassword("12345"), "lala@gmail.com", "25", "0164715315", "Malaysian");
        // Scanner input=new Scanner(System.in);
        // String name=input.nextLine();
        // String password=input.nextLine();
        // System.out.println(dbh.checkUser(name, encryptPassword(password)));
        // User user=dbh.getUser();
        // System.out.println(user.getRole());
    //     ObservableList<User> listM;
    //     listM = dbh.displayUsers();
    // System.out.println("User List Size: " + listM.size());
    //     for (User user : listM) {
    // System.out.println("Username: " + user.getUsername());
    
    //     ArrayList<newsData> newsList;
    //     try {
    //         newsList = newsService.newsServices();
        
    //     //Print the stock list
    //     System.out.print("newsInfo\n");
    //     System.out.println("----------------------------------------");
    //     for (newsData news : newsList) {
    //         System.out.println("Title: " + news.getTitle());
    //         System.out.println("Description: " + news.getDescription());
    //         System.out.println("URL: " + news.getUrl());
    //         System.out.println("Published At: " + news.getPublishedAt());
    //         System.out.println();
    //     }
    // } catch (IOException e) {
    //     // TODO Auto-generated catch block
    //     e.printStackTrace();
    // }
    // }
}

    
    private static String encryptPassword(String password) {
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
}
