

import Model.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class RegistrationFormController {
    
   
    @FXML
    private PasswordField confirmPassword;

    @FXML
    private TextField email;

    @FXML
    private ComboBox<String>  nationality;

    @FXML
    private Label notMatch;

    @FXML
    private PasswordField password;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField username;
    @FXML
    private Label passwordLength;

    @FXML
    private Label notValidE;
   
    @FXML
    private Label notValidPN;
    @FXML
    private ComboBox<Integer> age;

    private DatabaseHandler dbh=new DatabaseHandler();
    @FXML
    void clickedBack(MouseEvent event) {
        App.setRoot("LoginPage.fxml");
       
    }
    @FXML
    void initialize() {
        age.setItems(FXCollections.observableArrayList(
                18, 19, 20, 21, 22, 23, 24, 25, 26, 27,
                28, 29, 30, 31, 32, 33, 34, 35, 36, 37,
                38, 39, 40, 41, 42, 43, 44, 45, 46, 47,
                48, 49, 50, 51, 52, 53, 54, 55, 56, 57,
                58, 59, 60
        ));
        nationality.setItems(FXCollections.observableArrayList(
            "Malaysian","Non-Malaysian"
        ));

    }

    @FXML
    void clickedRegister(MouseEvent event) {
        
        Alert alert = new Alert(AlertType.INFORMATION);
         alert.setTitle("Successful or nah");
         alert.setHeaderText("Result:");
         
       
       
            if(username.getText().trim().isEmpty()|| password.getText().trim().isEmpty()||email.getText().trim().isEmpty()  ||phoneNumber.getText().trim().isEmpty()|| nationality.getSelectionModel().isEmpty()|| age.getSelectionModel().isEmpty()){
                alert.setContentText("Please Fill in All the Infomation Required!");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/Model/stylesheet.css").toExternalForm());
                dialogPane.getStyleClass().add("gradient-background-sign-up-page");
                alert.showAndWait();
                notMatch.setText("");}
            else if(password.getLength()!=5||!confirmPassword.getText().equals(password.getText())|| !phoneNumber.getText().startsWith("01")|| phoneNumber.getText().length()<10 || phoneNumber.getText().length()>11 || !email.getText().contains(".com") || !email.getText().contains("@")){ 
                if(!confirmPassword.getText().equals(password.getText())){
                notMatch.setText("Password Does Not Match");
                }else{
                    notMatch.setText("");
                }
                if(password.getLength()!=5){
                    passwordLength.setText("Password Must Be Length of 5");
                }else{
                    passwordLength.setText("");
                }

                if(!phoneNumber.getText().startsWith("01")|| phoneNumber.getText().length()<10 || phoneNumber.getText().length()>11){
                  notValidPN.setText("Invalid Phone Number");
                }else{
                    notValidPN.setText("");
                }
                if(!email.getText().contains(".com") || !email.getText().contains("@")){
                    notValidE.setText("Invalid Email");
                }else{
                    notValidE.setText("");
                }
                
            
            }else{
                passwordLength.setText("");
                notMatch.setText("");
                notValidPN.setText("");
                notValidE.setText("");

            boolean successfulReg=dbh.insertUser(username.getText(), dbh.encryptPassword(password.getText()), email.getText(), String.valueOf(age.getValue()), phoneNumber.getText(), nationality.getValue());
            if(successfulReg){
                alert.setContentText("User Is Successfully Registered!");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/Model/stylesheet.css").toExternalForm());
                dialogPane.getStyleClass().add("gradient-background-sign-up-page");
                alert.showAndWait();
                App.setRoot("RegistrationForm.fxml");
            }else{
                alert.setContentText("Unexpected Error Occured");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/Model/stylesheet.css").toExternalForm());
                dialogPane.getStyleClass().add("gradient-background-sign-up-page");
                alert.showAndWait();
                notMatch.setText("");
            }
             
        }
    
        
    }
}

    
    

