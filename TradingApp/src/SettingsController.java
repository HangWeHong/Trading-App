import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class SettingsController {

    @FXML
    void clickedAdminInfo(MouseEvent event) {
        App.setRoot("AdministratorInfo.fxml");
    }
    @FXML
    void clickedSignOut(MouseEvent event) {
        App.setRoot("LoginPage.fxml");
    }

}
