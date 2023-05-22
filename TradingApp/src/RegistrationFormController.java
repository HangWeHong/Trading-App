import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class RegistrationFormController {

    @FXML
    private TextField inputUsername;

    @FXML
    private TextField inputPassword;

    @FXML
    private TextField inputEmail;

    @FXML
    private TextField inputAge;

    @FXML
    private TextField inputPhoneNumber;

    @FXML
    private TextField inputNationality;

    @FXML
    private Label registrationMessage;

    @FXML
    void clickedRegister(MouseEvent event) {
        String username = inputUsername.getText();
        String password = inputPassword.getText();
        String email = inputEmail.getText();
        String age = inputAge.getText();
        String phoneNumber = inputPhoneNumber.getText();
        String nationality = inputNationality.getText();

        DatabaseHandler databaseHandler = new DatabaseHandler();
        boolean success = databaseHandler.insertUser(username, password, email, age, phoneNumber, nationality);

        if (success) {
            registrationMessage.setText("Registration successful!");
        } else {
            registrationMessage.setText("Registration failed. Please try again.");
        }
    }

    @FXML
    void clickedBack(MouseEvent event) {
        App.setRoot("LoginPage.fxml");
    }
}

