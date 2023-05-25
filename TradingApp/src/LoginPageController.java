import Model.DatabaseHandler;
import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginPageController {
     
    @FXML
    private Label invalid;
    @FXML
    private TextField inputUsername;

    @FXML
    private TextField inputPassword;
    
    private  User user;

    private DatabaseHandler dbh=new DatabaseHandler();

    @FXML
    void clickedLogin(MouseEvent event) {
        boolean isValidUser = dbh.checkUser(inputUsername.getText(), dbh.encryptPassword(inputPassword.getText()));
    if (isValidUser) {
        user = dbh.getUser();
        if (user.getRole().equals("Admin")) {
            App.setRoot("AdministratorInfo.fxml");
        } else if (user.getRole().equals("User")) {
            App.setRoot("TradingDashboard.fxml");
        }
    } else {
        invalid.setText("Invalid Username or Password");
    }
}

    @FXML
    void clickedSignUp(MouseEvent event) {
        App.setRoot("RegistrationForm.fxml");
    }
    
    @FXML
    void clickedForgotPassword(MouseEvent event) {
        App.setRoot("ForgotPassword.fxml");
    }
    @FXML
    void clickedExit(MouseEvent event) {
       System.exit(0);
    }
   

}

