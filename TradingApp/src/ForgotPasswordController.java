import Model.User;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class ForgotPasswordController {

    @FXML
    void clickedBack(MouseEvent event) {
        App.setRoot("LoginPage.fxml");
        
    }

    @FXML
    void clickedSend(MouseEvent event) {
        App.setRoot("LoginPage.fxml");
    }

}
