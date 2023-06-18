import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import Model.DatabaseHandler;
import Model.User;

public class UserManagementController {

    @FXML
    private TableColumn<User, Integer> col_Age;

    @FXML
    private TableColumn<User, Double> col_Balance;

    @FXML
    private TableColumn<User, String> col_Email;

    @FXML
    private TableColumn<User, String> col_Nationality;

    @FXML
    private TableColumn<User, String> col_PhoneNumber;

    @FXML
    private TableColumn<User, Double> col_Points;

    @FXML
    private TableColumn<User, String> col_Username;

    @FXML
    private Label username;
    
    @FXML
    private TableView<User> usersTable;
    
    @FXML
    private TextField inputUsername;

    @FXML
    private TableColumn<User,Boolean> col_Qualified;
  
  
    ObservableList<User> listM;
    private DatabaseHandler dbh=new DatabaseHandler();
    private User user=dbh.getUser();
    @FXML
    void initialize() {
    
      username.setText(user.getUsername());
      col_Username.setCellValueFactory(new PropertyValueFactory<User,String>("username"));
      col_Age.setCellValueFactory(new PropertyValueFactory<User,Integer>("age"));
      col_Email.setCellValueFactory(new PropertyValueFactory<User,String>("email"));
      col_Nationality.setCellValueFactory(new PropertyValueFactory<User,String>("nationality"));
      col_PhoneNumber.setCellValueFactory(new PropertyValueFactory<User,String>("phoneNum"));
      col_Balance.setCellValueFactory(new PropertyValueFactory<User,Double>("balance"));
      col_Points.setCellValueFactory(new PropertyValueFactory<User,Double>("PL_Points"));
      col_Qualified.setCellValueFactory(new PropertyValueFactory<User,Boolean>("qualified"));

      listM = dbh.displayUsers();
      usersTable.setItems(listM);
    }
    @FXML
    void clickedAdminInfo(MouseEvent event) {
        App.setRoot("AdministratorInfo.fxml");
    }

    @FXML
    void clickedSettings(MouseEvent event) {
        App.setRoot("Settings.fxml");
    }
    @FXML
    void clickedDelete(MouseEvent event) {
       boolean isDeleted= dbh.deleteUser(inputUsername.getText());
       // Create an alert of type INFORMATION
       Alert alert = new Alert(AlertType.INFORMATION);
        // Set the title and content text for the alert
        alert.setTitle("Successful or nah");
        alert.setHeaderText("Result:");
        
       if(isDeleted){
        alert.setContentText("User Is Successfully Deleted!");
        dbh.deleteOrderDisqualified(inputUsername.getText());
    }else{
        alert.setContentText("User Is Not Found!");
       } 
        // Get the dialog pane of the alert
        DialogPane dialogPane = alert.getDialogPane();

        // Apply CSS styles to the dialog pane
        dialogPane.getStylesheets().add(getClass().getResource("/Model/stylesheet.css").toExternalForm());
        dialogPane.getStyleClass().add("gradient-background-admin");
       // Display the prompt and wait for user response
       alert.showAndWait();
       App.setRoot("UserManagement.fxml");
    }
        @FXML
    void clickedReset(MouseEvent event) {
        boolean isQualified= dbh.setQualified(inputUsername.getText());
       // Create an alert of type INFORMATION
       Alert alert = new Alert(AlertType.INFORMATION);
        // Set the title and content text for the alert
        alert.setTitle("Successful or nah");
        alert.setHeaderText("Result:");
        
       if(isQualified){
        alert.setContentText("User Is Successfully Reset to Qualified!");
       }else{
        alert.setContentText("User Is Not Found!");
       } 
        // Get the dialog pane of the alert
        DialogPane dialogPane = alert.getDialogPane();

        // Apply CSS styles to the dialog pane
        dialogPane.getStylesheets().add(getClass().getResource("/Model/stylesheet.css").toExternalForm());
        dialogPane.getStyleClass().add("gradient-background-admin");
       // Display the prompt and wait for user response
       alert.showAndWait();
       App.setRoot("UserManagement.fxml");
        
    }
    
   
}
