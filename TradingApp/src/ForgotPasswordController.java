import Model.DatabaseHandler;
import Model.User;
import Model.emailService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class ForgotPasswordController {
    @FXML
    private TextField inputEmail;
    @FXML
    void clickedBack(MouseEvent event) {
        App.setRoot("LoginPage.fxml");
        
    }

    @FXML
    void clickedSend(MouseEvent event) {
   
       Alert alert = new Alert(AlertType.INFORMATION);

       alert.setTitle("Successful or nah");
       alert.setHeaderText("Result:");
       if((!inputEmail.getText().contains(".com")&& !inputEmail.getText().contains("siswa.um.edu.my")) || !inputEmail.getText().contains("@") || inputEmail.getText().isEmpty() || !DatabaseHandler.checkEmailExists(inputEmail.getText())){
      
        if((!inputEmail.getText().contains(".com") && !inputEmail.getText().contains("siswa.um.edu.my")) || !inputEmail.getText().contains("@") || inputEmail.getText().isEmpty()){
        alert.setContentText("Please Enter a Valid Email!");
        DialogPane dialogPane = alert.getDialogPane();
        
       dialogPane.getStylesheets().add(getClass().getResource("/Model/stylesheet.css").toExternalForm());
       dialogPane.getStyleClass().add("gradient-background-sign-up-page");
    alert.showAndWait();
      }else if(!DatabaseHandler.checkEmailExists(inputEmail.getText())){
        alert.setContentText("This Email is Not A Registered Email!");
        DialogPane dialogPane = alert.getDialogPane();
        
       dialogPane.getStylesheets().add(getClass().getResource("/Model/stylesheet.css").toExternalForm());
       dialogPane.getStyleClass().add("gradient-background-sign-up-page");
    alert.showAndWait();
      }
    }else{
        
        try {
            emailService.sendPassword(inputEmail.getText());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       alert.setContentText("The Correct Password is Sent to Your Email");
       DialogPane dialogPane = alert.getDialogPane();
        
       dialogPane.getStylesheets().add(getClass().getResource("/Model/stylesheet.css").toExternalForm());
       dialogPane.getStyleClass().add("gradient-background-sign-up-page");
      alert.showAndWait();
        App.setRoot("LoginPage.fxml");
       }
    }

}
