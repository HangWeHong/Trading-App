package Model;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class emailService {
    public static void sendEmail(StockWrapper stockSearched,String type,int quantity,Double price,String email,int position)throws InterruptedException {
        try {
           double totalPrice=price*quantity*100;
        String totalPriceFormatted = String.format("%.2f", totalPrice);
            String content = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "  <title>Stock Trading App</title>\n" +
            "</head>\n" +
            "<body style=\"font-family: Arial, sans-serif; text-align: center; margin: 0; padding: 0; background-color: #f2f2f2;\">\n" +
            "  <img src=\"https://i.ibb.co/n76MGW0/hexagon.png\" alt=\"hexagon\" style=\"display: block; margin: 0 auto; width: 200px; height: auto;\">\n" +
            "  <div class=\"header\" style=\"background-color: #f2f2f2; padding: 20px; text-align: center; font-size: 32px; font-weight: bold; color: rgb(60, 70, 84);\">TradeGPT's Stock Trading App</div>\n" +
            "  <div class=\"sub-header\" style=\"text-align: center; font-size: 24px; font-weight: bold; margin-top: 5px; color: rgb(60, 70, 84);\">"+type+" Order Completed</div>\n" +
            "  <div class=\"content\" style=\"margin-top: 30px;\">\n" +
            "    <p>Company Name: "+stockSearched.getName()+"</p>\n" +
            "    <p>Stock Symbol: "+stockSearched.getSymbol()+"</p>\n" +
            "    <p>Stock Price: RM"+stockSearched.getPrice()+"/shares</p>\n" +
            "    <p>"+type+"ing Price: RM"+price+"/shares</p>\n" +
            "    <p>Volume: "+quantity*100+"</p>\n" +
            "    <p>Total Price: RM"+totalPriceFormatted+"</p>\n" +
            "    <p></p>\n" +
            "    <p>You Are in Position "+position+"</p>\n" +
            "  </div>\n" +
            "  <div class=\"line\" style=\"width: 80%; margin: 20px auto; border-top: 1px solid #ccc;\"></div>\n" +
            "</body>\n" +
            "</html>";
            emailSettings(email, "Stock Trading App Notification", content);
        } catch (MessagingException e) {
            e.printStackTrace();
        
    }
        
    }
    public static void sendPassword(String email)throws InterruptedException {
        try {
          String passString=DatabaseHandler.getPassword(email);
          String foundPassword=DatabaseHandler.findPassword(passString);
            String content = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "  <title>Stock Trading App</title>\n" +
            "</head>\n" +
            "<body style=\"font-family: Arial, sans-serif; text-align: center; margin: 0; padding: 0; background-color: #f2f2f2;\">\n" +
            "  <img src=\"https://i.ibb.co/n76MGW0/hexagon.png\" alt=\"hexagon\" style=\"display: block; margin: 0 auto; width: 200px; height: auto;\">\n" +
            "  <div class=\"header\" style=\"background-color: #f2f2f2; padding: 20px; text-align: center; font-size: 32px; font-weight: bold; color: rgb(60, 70, 84);\">TradeGPT's Stock Trading App</div>\n" +
            "  <div class=\"sub-header\" style=\"text-align: center; font-size: 24px; font-weight: bold; margin-top: 5px; color: rgb(60, 70, 84);\">Account Recovery - Retrieve Password</div>\n" +
            "  <div class=\"content\" style=\"margin-top: 30px;\">\n" +
            "    <p>Email: "+email+"</p>\n" +
            "    <p>Password: "+foundPassword+"</p>\n" +
            "    <p></p>\n" +
            "    <p>Please Take Note of Your Password! </p>\n" +
            "  </div>\n" +
            "  <div class=\"line\" style=\"width: 80%; margin: 20px auto; border-top: 1px solid #ccc;\"></div>\n" +
            "</body>\n" +
            "</html>";
            emailSettings(email, "Stock Trading App Notification", content);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        
    }

    public static void emailSettings(String recipientEmail, String subject, String content) throws MessagingException {
        // SMTP server configuration (change the values accordingly)
        String host = "smtp.gmail.com";
        String port = "587";
        String username = "natsuhang@gmail.com";
        String password = "xxxxxxxxxxxxxxxxx";
        
        // Sender and recipient email addresses
        String senderEmail = "natsuhang@gmail.com";
    
        // Create properties object for the SMTP server
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
    
        // Create authenticator for password authentication
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
       
        // Create a session with the properties and authenticator
        Session session = Session.getInstance(props, authenticator);
    
        try {
            // Create a MimeMessage object
            Message message = new MimeMessage(session);
            // Set the sender address
            message.setFrom(new InternetAddress(senderEmail));
            // Set the recipient address
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            // Set the email subject
            message.setSubject(subject);
            // Set the email content
            
            message.setContent(content, "text/html"); //if not html can use this message.setText(content);
            // Send the email
            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException ex) {
            System.out.println("Failed to send email. Error message: " + ex.getMessage());
            throw ex;
        }
    }
}
