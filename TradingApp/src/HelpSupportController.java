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
    private ReportGenerator rg=new ReportGenerator();
      
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
        try {
            rg.generateReport();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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