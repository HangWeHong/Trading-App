import java.io.IOException;
import java.util.ArrayList;
import java.awt.Desktop;

import java.net.URI;
import java.net.URISyntaxException;



import Model.DatabaseHandler;
import Model.newsService;
import Model.User;
import Model.newsData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class NewsController {

    @FXML
    private HBox dashboard;

    @FXML
    private Label description1;

    @FXML
    private Label description2;

    @FXML
    private Label description3;

    private String link1;

    private String link2;
    private String link3;

    @FXML
    private Label time1;

    @FXML
    private Label time2;

    @FXML
    private Label time3;

    @FXML
    private Label title1;

    @FXML
    private Label title2;

    @FXML
    private Label title3;

    @FXML
    private Label username;
     private DatabaseHandler dbh=new DatabaseHandler();
     private User user=dbh.getUser();
    private static  newsService newsService=new newsService();
    @FXML
    void initialize() {
         username.setText(user.getUsername());
        ArrayList<newsData> newsList;
        try {
            newsList = newsService.newsServices();
            title1.setText(newsList.get(0).getTitle());
            description1.setText(newsList.get(0).getDescription());
            time1.setText(String.valueOf(newsList.get(0).getPublishedAt()));
            link1=newsList.get(0).getUrl();
            title2.setText(newsList.get(1).getTitle());
            description2.setText(newsList.get(1).getDescription());
            time2.setText(String.valueOf(newsList.get(1).getPublishedAt()));
            link2=newsList.get(1).getUrl();
            title3.setText(newsList.get(2).getTitle());
            description3.setText(newsList.get(2).getDescription());
            time3.setText(String.valueOf(newsList.get(2).getPublishedAt()));
            link3=newsList.get(2).getUrl();
            
        
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

    
    }
    @FXML
    void clickedLink1(MouseEvent event) {
        String url = link1; 
    try {
        Desktop.getDesktop().browse(new URI(url));
    } catch (IOException | URISyntaxException e) {
        e.printStackTrace();
    }
    }

    @FXML
    void clickedLink2(MouseEvent event) {
        String url = link2; 
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void clickedLink3(MouseEvent event) {
        String url = link3; 
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void clickedProfileInfo(MouseEvent event) {
        App.setRoot("ProfileInfo.fxml");
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
    void clickedLeaderboard(MouseEvent event) {
        App.setRoot("Leaderboard.fxml");
    }

}
