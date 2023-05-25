import Model.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
        
        if(confirmPassword.getText().equals(password.getText())){
            boolean successfulReg=dbh.insertUser(username.getText(), dbh.encryptPassword(password.getText()), email.getText(), age.getText(), phoneNumber.getText(), nationality.getText());
            if(successfulReg){
                statusRegister.setText("You have Successfully Registered!");
            }else{
                statusRegister.setText("You have Failed to Register!");
            }
        }else{
            notMatch.setText("Password Does Not Match");
        }
        
        
    }

    
}

