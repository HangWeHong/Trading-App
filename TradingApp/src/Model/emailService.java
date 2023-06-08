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
    public static void main(String[] args) {
        try {
            String content = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "  <title>Stock Trading App</title>\n" +
            "</head>\n" +
            "<body style=\"font-family: Arial, sans-serif; text-align: center; margin: 0; padding: 0; background-color: #f2f2f2;\">\n" +
            "  <img src=\"https://i.ibb.co/n76MGW0/hexagon.png\" alt=\"hexagon\" style=\"display: block; margin: 0 auto; width: 200px; height: auto;\">\n" +
            "  <div class=\"header\" style=\"background-color: #f2f2f2; padding: 20px; text-align: center; font-size: 32px; font-weight: bold; color: rgb(60, 70, 84);\">TradeGPT's Stock Trading App</div>\n" +
            "  <div class=\"sub-header\" style=\"text-align: center; font-size: 24px; font-weight: bold; margin-top: 5px; color: rgb(60, 70, 84);\">Buy Order Completed</div>\n" +
            "  <div class=\"content\" style=\"margin-top: 30px;\">\n" +
            "    <p>Company Name: Google</p>\n" +
            "    <p>Stock Symbol: 000.1f</p>\n" +
            "    <p>Stock Price: RM20/share</p>\n" +
            "    <p>You have successfully entered position 1</p>\n" +
            "  </div>\n" +
            "  <div class=\"line\" style=\"width: 80%; margin: 20px auto; border-top: 1px solid #ccc;\"></div>\n" +
            "</body>\n" +
            "</html>";
            sendEmail("s2175788@siswa.um.edu.my", "Stock Trading App Notification", content);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public static void sendEmail(String recipientEmail, String subject, String content) throws MessagingException {
        // SMTP server configuration (change the values accordingly)
        String host = "smtp.gmail.com";
        String port = "587";
        String username = "natsuhang@gmail.com";
        String password = "bmsvmrwizwqowdlb";
        
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
