import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginPageController {
    

    @FXML
    private TextField inputUsername;

    @FXML
    private TextField inputPassword;

    

    @FXML
    void clickedLogin(MouseEvent event) {
        User user=new User(null, null, null, null, 0, 0); 
        user.checkUser(inputUsername.getText(), inputPassword.getText());
        if(user.getRole().equals("Admin")){
            App.setRoot("AdministratorInfo.fxml");
        }else if(user.getRole().equals("User")){
            App.setRoot("TradingDashboard.fxml");
        }else{
            App.setRoot("ErrorFound.fxml");
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


}

