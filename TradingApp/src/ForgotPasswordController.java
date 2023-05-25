import Model.User;
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
       if(inputEmail.getText().contains("@gmail.com") && !inputEmail.getText().equals("")){
       alert.setContentText("The Correct Password is Sent to Your Email");
       DialogPane dialogPane = alert.getDialogPane();
        
       dialogPane.getStylesheets().add(getClass().getResource("/Model/stylesheet.css").toExternalForm());
       dialogPane.getStyleClass().add("gradient-background-sign-up-page");
      alert.showAndWait();
        App.setRoot("LoginPage.fxml");
    }else{
        alert.setContentText("Please Enter a Valid Email!");
        DialogPane dialogPane = alert.getDialogPane();
        
       dialogPane.getStylesheets().add(getClass().getResource("/Model/stylesheet.css").toExternalForm());
       dialogPane.getStyleClass().add("gradient-background-sign-up-page");
    alert.showAndWait();
       }
    }

}
