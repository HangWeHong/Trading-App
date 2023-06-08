import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Model.DatabaseHandler;
import Model.TradeHistory;
import Model.User;

public class TradingDashboardController {
    @FXML
    private Label balance;
    
    @FXML
    private Label position;

    @FXML
    private Label points;
    @FXML
    private Label username;

    @FXML
    private HBox dashboard;
    
    @FXML
    private VBox tradeHistoryLayout;

    private DatabaseHandler dbh=new DatabaseHandler();
  private User user=dbh.getUser();
    
    @FXML
    void initialize() {
        
         username.setText(user.getUsername());
         balance.setText(String.valueOf(user.getBalance()));
         points.setText(String.valueOf(user.getPL_Points()));
         position.setText(String.valueOf(getPosition()));

       List<TradeHistory> list=new ArrayList<>(tradeHistory());
       //here insert to get balance,points and position
       for(int i=0;i<list.size();i++){
        FXMLLoader fxmlLoader=new FXMLLoader(); //create an fxml object which direct to TradeHistory.fxml
        fxmlLoader.setLocation(getClass().getResource("TradeHistory.fxml"));
        try{
            HBox hBox=fxmlLoader.load();        //we are loadng Hbox into the list
            TradeHistoryController thc=fxmlLoader.getController();  //the controller we use for loading List for TradeHistory 
            thc.setData(list.get(i));
            tradeHistoryLayout.getChildren().add(hBox); //add to the VBox in the ScrollPane;
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }

    }
    @FXML
    void clickedProfileInfo(MouseEvent event) {
        App.setRoot("ProfileInfo.fxml");
    }
    @FXML
    void clickedNews(MouseEvent event) {
        App.setRoot("News.fxml");
    }
    @FXML
    void clickedLeaderboard(MouseEvent event) {
        App.setRoot("Leaderboard.fxml");
    }
    
    @FXML
    void clickedHelpNSupport(MouseEvent event) {
        App.setRoot("HelpSupport.fxml");
    }

    private List<TradeHistory> tradeHistory(){      //use this to set the field other dont care;
        List<TradeHistory> list=new ArrayList<>();
        TradeHistory tradehistory=new TradeHistory();

        tradehistory.setOrderNo(1);
        tradehistory.setStockName("Google");

        tradehistory.setShares(100);
        tradehistory.setPrice(500);
        tradehistory.setTime();

        list.add(tradehistory);
        
        tradehistory.setOrderNo(1);
        tradehistory.setStockName("Google");

        tradehistory.setShares(100);
        tradehistory.setPrice(500);
        tradehistory.setTime();

        list.add(tradehistory);
        
        tradehistory.setOrderNo(1);
        tradehistory.setStockName("Google");

        tradehistory.setShares(100);
        tradehistory.setPrice(500);
        tradehistory.setTime();

        list.add(tradehistory);
        
        tradehistory.setOrderNo(1);
        tradehistory.setStockName("Google");

        tradehistory.setShares(100);
        tradehistory.setPrice(500);
        tradehistory.setTime();

        list.add(tradehistory);
        
        tradehistory.setOrderNo(1);
        tradehistory.setStockName("Google");

        tradehistory.setShares(100);
        tradehistory.setPrice(500);
        tradehistory.setTime();

        list.add(tradehistory);
        tradehistory.setOrderNo(1);
        tradehistory.setStockName("Google");

        tradehistory.setShares(100);
        tradehistory.setPrice(500);
        tradehistory.setTime();

        list.add(tradehistory);
        
        tradehistory.setOrderNo(1);
        tradehistory.setStockName("Google");

        tradehistory.setShares(100);
        tradehistory.setPrice(500);
        tradehistory.setTime();

        list.add(tradehistory);
        
        tradehistory.setOrderNo(1);
        tradehistory.setStockName("Google");

        tradehistory.setShares(100);
        tradehistory.setPrice(500);
        tradehistory.setTime();

        list.add(tradehistory);
        
        tradehistory.setOrderNo(1);
        tradehistory.setStockName("Google");

        tradehistory.setShares(100);
        tradehistory.setPrice(500);
        tradehistory.setTime();

        list.add(tradehistory);
        
        tradehistory.setOrderNo(1);
        tradehistory.setStockName("Google");

        tradehistory.setShares(100);
        tradehistory.setPrice(500);
        tradehistory.setTime();

        list.add(tradehistory);

        return list;
}
private int getPosition() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("Leaderboard.fxml"));
        try {
            Parent root = fxmlLoader.load();
            LeaderboardController leaderboardController = fxmlLoader.getController();
            return leaderboardController.getUserPosition(user.getUsername());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
