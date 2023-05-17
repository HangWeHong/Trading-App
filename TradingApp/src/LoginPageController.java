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
        App.setRoot("TradingDashboard.fxml");
        System.out.println(inputUsername.getText());
        System.out.println(inputPassword.getText());
        
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
