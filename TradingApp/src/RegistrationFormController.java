import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class RegistrationFormController {

    @FXML
    void clickedBack(MouseEvent event) {
        App.setRoot("LoginPage.fxml");
    }

    @FXML
    void clickedRegister(MouseEvent event) {
        App.setRoot("LoginPage.fxml");
    }

}
