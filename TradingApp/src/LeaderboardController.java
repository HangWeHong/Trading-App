import java.util.Comparator;

import Model.DatabaseHandler;
import Model.User;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class LeaderboardController {

    @FXML
    private TableColumn<User, Integer> col_Position;

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
    private TableView<User> usersTable;

    @FXML
    private Label username;

    @FXML
    private Label username1;

    @FXML
    private Label username2;

    @FXML
    private Label username3;
    
    @FXML
    private VBox champion;

    @FXML
    private VBox firstRunnerUp;

    @FXML
    private VBox secondRunnerUp;


    private DatabaseHandler dbh = new DatabaseHandler();
    private User user = dbh.getUser();

    @FXML
    void initialize() {
        username.setText(user.getUsername());
        col_Position.setCellValueFactory(cellData -> {
            int rowIndex = usersTable.getItems().indexOf(cellData.getValue());
            return new SimpleIntegerProperty(rowIndex + 1).asObject();
        });
        col_Username.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_Age.setCellValueFactory(new PropertyValueFactory<>("age"));
        col_Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_Nationality.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        col_PhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        col_Balance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        col_Points.setCellValueFactory(new PropertyValueFactory<>("PL_Points"));

        ObservableList<User> sortedList = FXCollections.observableArrayList(dbh.displayUsers());
        sortedList.sort(Comparator.comparingDouble(User::getPL_Points).reversed());
        ObservableList<User> top10Users = FXCollections.observableArrayList(sortedList.subList(0, Math.min(sortedList.size(), 10)));
        usersTable.setItems(top10Users);
        if (!top10Users.isEmpty()) {
            username1.setText(top10Users.get(0).getUsername());
            if (top10Users.size() > 1) {
                username2.setText(top10Users.get(1).getUsername());
            }
            if (top10Users.size() > 2) {
                username3.setText(top10Users.get(2).getUsername());
            }
        }
        TranslateTransition championAnimation=new TranslateTransition(Duration.millis(1500), champion);
        TranslateTransition firstRunnerUpAnimation = new TranslateTransition(Duration.millis(3000), firstRunnerUp);
        TranslateTransition secoondRunnerUpAnimation = new TranslateTransition(Duration.millis(4500), secondRunnerUp);
        championAnimation.setFromY(0);
        championAnimation.setToY(-420);
        // Start the animation
        firstRunnerUpAnimation.setFromY(0);
        firstRunnerUpAnimation.setToY(-346);
        secoondRunnerUpAnimation.setFromY(0);
        secoondRunnerUpAnimation.setToY(-275);
        
        // Start the animation
        championAnimation.play();
        firstRunnerUpAnimation.play();
        secoondRunnerUpAnimation.play();
    }
    

    public int getUserPosition(String username) {
        ObservableList<User> sortedList = FXCollections.observableArrayList(dbh.displayUsers());
        sortedList.sort(Comparator.comparingDouble(User::getPL_Points).reversed());

        for (int i = 0; i < sortedList.size(); i++) {
            if (sortedList.get(i).getUsername().equals(username)) {
                return i + 1; // Add 1 to convert index to position (1-indexed)
            }
        }

        return -1; // User not found in the leaderboard
    }
    

  
    @FXML
    void clickedDashboard(MouseEvent event) {
        App.setRoot("TradingDashboard.fxml");
    }

    @FXML
    void clickedHelpNSupport(MouseEvent event) {
        App.setRoot("HelpSupport.fxml");
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
    void clickedTrading(MouseEvent event) {
        App.setRoot("Trading.fxml");
    }
}