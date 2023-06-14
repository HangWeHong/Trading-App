import Model.DatabaseHandler;
import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class ProfileInfoController {

    @FXML
    private Label age;

    @FXML
    private Label email;

    @FXML
    private Label nationality;

    @FXML
    private Label phoneNum;

    @FXML
    private Label name;
    @FXML
    private Label username;
    private DatabaseHandler dbh=new DatabaseHandler();
    private User user=dbh.getUser();
      

    @FXML
    void initialize() {
        name.setText(user.getUsername());
      age.setText(String.valueOf(user.getAge()));
      email.setText(user.getEmail());
      nationality.setText(user.getNationality());
      phoneNum.setText(user.getPhoneNum());
      username.setText(user.getUsername());

    }
    @FXML
    void clickedDashboard(MouseEvent event) {
        App.setRoot("TradingDashboard.fxml");
    }
    @FXML
    void clickedSignOut(MouseEvent event) {
        App.setRoot("LoginPage.fxml");
    }
    @FXML
    void clickedNews(MouseEvent event) {
        App.setRoot("News.fxml");
    }
    @FXML
    void clickedHelpSupport(MouseEvent event) {
        App.setRoot("HelpSupport.fxml");
    }

    @FXML
    void clickedLeaderboard(MouseEvent event) {
        App.setRoot("Leaderboard.fxml");
    }
    @FXML
    void clickedTrading(MouseEvent event) {
        App.setRoot("Trading.fxml");
    }

}
