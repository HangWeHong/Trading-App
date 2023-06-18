import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.print.DocFlavor.STRING;

import Model.DatabaseHandler;
import Model.Holdings;
import Model.Order;
import Model.TradeHistory;
import Model.Transaction;
import Model.User;

public class TradingDashboardController {
       @FXML
    private Label balance;

    @FXML
    private TableColumn<Transaction, String> col_DateTimeT;

    @FXML
    private TableColumn<Transaction,Double> col_PriceT;

    @FXML
    private TableColumn<Transaction,Integer> col_QuantityT;

    @FXML
    private TableColumn<Transaction,String> col_StatusT;

    @FXML
    private TableColumn<Transaction,String> col_SymbolT;

    @FXML
    private TableColumn<Transaction,String> col_TypeT;

    @FXML
    private HBox dashboard;

    @FXML
    private Label points;

    @FXML
    private Label position;

    @FXML
    private TableView<Transaction> transactionsTable;

    @FXML
    private TableView<Holdings> holdingsTable;

    @FXML
    private TableView<Order> ordersTable;

    @FXML
    private Label username;

    @FXML
    private TableColumn<Holdings, Double> col_PriceH;

    @FXML
    private TableColumn<Holdings, Integer> col_QuantityH;

    @FXML
    private TableColumn<Holdings, String> col_SymbolH;
    
    @FXML
    private TableColumn<Order, Double> col_PriceO;

    @FXML
    private TableColumn<Order, Integer> col_QuantityO;

    @FXML
    private TableColumn<Order, String> col_SymbolO;

    @FXML
    private TableColumn<Order, String> col_TypeO;


    private DatabaseHandler dbh=new DatabaseHandler();
    private User user=dbh.getUser();
    
    ObservableList<Transaction> listT;
    ObservableList<Holdings> listH;
    ObservableList<Order> listO;

    @FXML
    void initialize() {
        
         username.setText(user.getUsername());
         balance.setText(String.valueOf(dbh.getLatestBalance(user.getUsername())));
         points.setText(String.valueOf(dbh.getLatestPoints(user.getUsername())));
         position.setText(String.valueOf(getPosition()));
         
        col_SymbolT.setCellValueFactory(new PropertyValueFactory<Transaction,String>("symbol"));
        col_PriceT.setCellValueFactory(new PropertyValueFactory<Transaction,Double>("price"));
        col_QuantityT.setCellValueFactory(new PropertyValueFactory<Transaction,Integer>("quantity"));
        col_TypeT.setCellValueFactory(new PropertyValueFactory<Transaction,String>("Order_Type"));
        col_StatusT.setCellValueFactory(new PropertyValueFactory<Transaction,String>("Status"));
        col_DateTimeT.setCellValueFactory(new PropertyValueFactory<Transaction,String>("dateTime"));
        listT=dbh.displayTransactions(user.getUsername());
         transactionsTable.setItems(listT);

        col_SymbolH.setCellValueFactory(new PropertyValueFactory<Holdings,String>("symbol"));
        col_PriceH.setCellValueFactory(new PropertyValueFactory<Holdings,Double>("price"));
        col_QuantityH.setCellValueFactory(new PropertyValueFactory<Holdings,Integer>("quantity"));
        listH=dbh.displayHoldings(user.getUsername());
        holdingsTable.setItems(listH);

        col_SymbolO.setCellValueFactory(new PropertyValueFactory<Order,String>("symbol"));
        col_PriceO.setCellValueFactory(new PropertyValueFactory<Order,Double>("price"));
        col_QuantityO.setCellValueFactory(new PropertyValueFactory<Order,Integer>("quantity"));
        col_TypeO.setCellValueFactory(new PropertyValueFactory<Order,String>("type"));
        listO=dbh.displayOrders(user.getUsername());
        ordersTable.setItems(listO);


    //    List<TradeHistory> list=new ArrayList<>(tradeHistory());
    //    //here insert to get balance,points and position
    //    for(int i=0;i<list.size();i++){
    //     FXMLLoader fxmlLoader=new FXMLLoader(); //create an fxml object which direct to TradeHistory.fxml
    //     fxmlLoader.setLocation(getClass().getResource("TradeHistory.fxml"));
    //     try{
    //         HBox hBox=fxmlLoader.load();        //we are loadng Hbox into the list
    //         TradeHistoryController thc=fxmlLoader.getController();  //the controller we use for loading List for TradeHistory 
    //         thc.setData(list.get(i));
    //         tradeHistoryLayout.getChildren().add(hBox); //add to the VBox in the ScrollPane;
    //     }catch(IOException e){
    //         e.printStackTrace();
    //     }
        
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
     void clickedTrading(MouseEvent event) {
        App.setRoot("Trading.fxml");
    }
    @FXML
    void clickedHelpSupport(MouseEvent event) {
        App.setRoot("HelpSupport.fxml");
    }

//     private List<TradeHistory> tradeHistory(){      //use this to set the field other dont care;
//         List<TradeHistory> list=new ArrayList<>();
//         TradeHistory tradehistory=new TradeHistory();

//         tradehistory.setOrderNo(1);
//         tradehistory.setStockName("Google");

//         tradehistory.setShares(100);
//         tradehistory.setPrice(500);
//         tradehistory.setTime();

//         list.add(tradehistory);
        
//         tradehistory.setOrderNo(1);
//         tradehistory.setStockName("Google");

//         tradehistory.setShares(100);
//         tradehistory.setPrice(500);
//         tradehistory.setTime();

//         list.add(tradehistory);
        
//         tradehistory.setOrderNo(1);
//         tradehistory.setStockName("Google");

//         tradehistory.setShares(100);
//         tradehistory.setPrice(500);
//         tradehistory.setTime();

//         list.add(tradehistory);
        
//         tradehistory.setOrderNo(1);
//         tradehistory.setStockName("Google");

//         tradehistory.setShares(100);
//         tradehistory.setPrice(500);
//         tradehistory.setTime();

//         list.add(tradehistory);
        
//         tradehistory.setOrderNo(1);
//         tradehistory.setStockName("Google");

//         tradehistory.setShares(100);
//         tradehistory.setPrice(500);
//         tradehistory.setTime();

//         list.add(tradehistory);
//         tradehistory.setOrderNo(1);
//         tradehistory.setStockName("Google");

//         tradehistory.setShares(100);
//         tradehistory.setPrice(500);
//         tradehistory.setTime();

//         list.add(tradehistory);
        
//         tradehistory.setOrderNo(1);
//         tradehistory.setStockName("Google");

//         tradehistory.setShares(100);
//         tradehistory.setPrice(500);
//         tradehistory.setTime();

//         list.add(tradehistory);
        
//         tradehistory.setOrderNo(1);
//         tradehistory.setStockName("Google");

//         tradehistory.setShares(100);
//         tradehistory.setPrice(500);
//         tradehistory.setTime();

//         list.add(tradehistory);
        
//         tradehistory.setOrderNo(1);
//         tradehistory.setStockName("Google");

//         tradehistory.setShares(100);
//         tradehistory.setPrice(500);
//         tradehistory.setTime();

//         list.add(tradehistory);
        
//         tradehistory.setOrderNo(1);
//         tradehistory.setStockName("Google");

//         tradehistory.setShares(100);
//         tradehistory.setPrice(500);
//         tradehistory.setTime();

//         list.add(tradehistory);

//         return list;
// }
public int getPosition() {
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
