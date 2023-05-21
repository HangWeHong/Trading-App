import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class AdministratorInfoController {

    @FXML
    void clickedSettings(MouseEvent event) {
        App.setRoot("Settings.fxml");
    }

}
