
import Model.DatabaseHandler;
import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class SettingsController {
    
    @FXML
    private Label username;
    
    private DatabaseHandler dbh=new DatabaseHandler();
    private User user=dbh.getUser();
    
    @FXML
    void initialize() {
        username.setText(user.getUsername());
    }
    @FXML
    void clickedAdminInfo(MouseEvent event) {
        App.setRoot("AdministratorInfo.fxml");
    }
    @FXML
    void clickedSignOut(MouseEvent event) {
        App.setRoot("LoginPage.fxml");
    }
    
    @FXML
    void clickedUserManagement(MouseEvent event) {
        App.setRoot("UserManagement.fxml");
    }

}
