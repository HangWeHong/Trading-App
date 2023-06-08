import Model.DatabaseHandler;
import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class HelpSupportController {

    @FXML
    private Label username;
    private DatabaseHandler dbh=new DatabaseHandler();
    private User user=dbh.getUser();
      
      @FXML
      void initialize() {
           username.setText(user.getUsername());
      }

    @FXML
    void clickedDashboard(MouseEvent event) {
        App.setRoot("TradingDashboard.fxml");
    }

    @FXML
    void clickedNews(MouseEvent event) {
        App.setRoot("News.fxml");
    }

    @FXML
    void clickedProfileInfo(MouseEvent event) {
        App.setRoot("ProfileInfo.fxml");
    }

    @FXML
    void clickedGenerate(MouseEvent event) {

    }
}