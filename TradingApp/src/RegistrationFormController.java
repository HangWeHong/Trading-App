import Model.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class RegistrationFormController {
    
    @FXML
    private TextField age;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private TextField email;

    @FXML
    private TextField nationality;

    @FXML
    private Label notMatch;

    @FXML
    private PasswordField password;

    @FXML
    private TextField phoneNumber;

    @FXML
    private Label statusRegister;

    @FXML
    private TextField username;


    private DatabaseHandler dbh=new DatabaseHandler();
    @FXML
    void clickedBack(MouseEvent event) {
        App.setRoot("LoginPage.fxml");
       
    }

    @FXML
    void clickedRegister(MouseEvent event) {
        
        Alert alert = new Alert(AlertType.INFORMATION);
         alert.setTitle("Successful or nah");
         alert.setHeaderText("Result:");
         
       
        if(confirmPassword.getText().equals(password.getText())){
            boolean successfulReg=dbh.insertUser(username.getText(), dbh.encryptPassword(password.getText()), email.getText(), age.getText(), phoneNumber.getText(), nationality.getText());
            if(successfulReg){
                alert.setContentText("User Is Successfully Registered!");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/Model/stylesheet.css").toExternalForm());
                dialogPane.getStyleClass().add("gradient-background-sign-up-page");
                alert.showAndWait();
                App.setRoot("RegistrationForm.fxml");
            }else{
                alert.setContentText("Please Fill in All the Infomation Required!");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/Model/stylesheet.css").toExternalForm());
                dialogPane.getStyleClass().add("gradient-background-sign-up-page");
                alert.showAndWait();
                notMatch.setText("");
            }
             
        }else{
            notMatch.setText("Password Does Not Match");
        }
        
        
    }

    
}

