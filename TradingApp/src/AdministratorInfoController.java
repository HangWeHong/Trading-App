import Model.DatabaseHandler;
import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class AdministratorInfoController {
    @FXML
    private Label department;

    @FXML
    private Label email;

    @FXML
    private Label name;

    @FXML
    private Label nationality;

    @FXML
    private Label notificationLanguage;

    @FXML
    private Label phoneNum;

    @FXML
    private Label username;

    private DatabaseHandler dbh=new DatabaseHandler();
    private User user=dbh.getUser();
    
    @FXML
    void initialize() {
        username.setText(user.getUsername());
        name.setText(user.getUsername());
        department.setText(user.getDepartment());
        email.setText(user.getEmail());
        nationality.setText(user.getNationality());
        notificationLanguage.setText(user.getNotificationLanguage());
        phoneNum.setText(String.valueOf(user.getPhoneNum()));
    }
    @FXML
    void clickedSettings(MouseEvent event) {
        App.setRoot("Settings.fxml");
    }

}