import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import org.controlsfx.control.textfield.TextFields;
import javafx.fxml.FXML;
import Model.DatabaseHandler;
import Model.OrderHandler;
import Model.StockSearch;
import Model.StockWrapper;
import Model.TradingRestrictions;
import Model.User;
import Model.emailService;
import javafx.animation.TranslateTransition;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;


public class TradingController {
   
     @FXML
    private TextField action;

    @FXML
    private TextField companyName;

    @FXML
    private HBox input;

    @FXML
    private TextField quantity;

    @FXML
    private HBox title;

    @FXML
    private Label username;
    
    @FXML
    private Label symbol;
   
    @FXML
    private Label priceStock;
    
    @FXML
    private TextField price;

    @FXML
    private Label notValidCompany;

    @FXML
    private HBox buttons;

    @FXML
    private Label notValidPrice;

    @FXML
    private Label notValidQuantity;

    @FXML
    private Label notValidAction;

    @FXML
    private Label submitOrder;

    @FXML
    private Label suggestedBuyPrice;

    @FXML
    private Label suggestedSellPrice;
    
    @FXML
    private Label stockSearchButton;

    
    @FXML
    private Label cancelOrder;

    @FXML
    private Label disqualifiedBody;

    @FXML
    private Label disqualifiedTitle;

    

     DatabaseHandler dbh = new DatabaseHandler();
    
     List<String> companyNames = dbh.getCompanyNames();
    
     String[] actions={"BUY","SELL"};

     StockWrapper stockSearched;

    private User user=dbh.getUser();

    TradingRestrictions tr;

     OrderHandler oh=new OrderHandler();

     emailService email=new emailService();
     TradingDashboardController TD=new TradingDashboardController();
    @FXML
    void initialize() {
             username.setText(user.getUsername());
            companyNames.remove("AGES-PA");
            TextFields.bindAutoCompletion(companyName, companyNames);
            // // Disable the action text field initially
            price.setDisable(true);
            action.setDisable(true);
            quantity.setDisable(true);
            submitOrder.setDisable(true);
            TextFields.bindAutoCompletion(action,actions);
            if(!user.isQualified()){
                stockSearchButton.setDisable(true);
                companyName.setDisable(true);
                cancelOrder.setDisable(true);
                disqualifiedTitle.setText("You are disqualified from the competition");
                disqualifiedBody.setText(" Contact Us Through Help & Support If You Feel This is Wrong");
            }else{
                disqualifiedTitle.setText("");
                disqualifiedBody.setText("");
            }

    }
    
   
    
    @FXML
    void clickedStockSearch(MouseEvent event) {

         stockSearched = StockSearch.getStockData(companyName.getText());
        if(stockSearched!=null){
            notValidCompany.setText("");
         TranslateTransition titleAnimation=new TranslateTransition(Duration.millis(500), title);
        TranslateTransition inputAnimation = new TranslateTransition(Duration.millis(500), input);
        TranslateTransition buttonsAnimation = new TranslateTransition(Duration.millis(500), buttons);
        titleAnimation.setFromY(0);
        titleAnimation.setToY(150);
        // Start the animation
        inputAnimation.setFromY(0);
        inputAnimation.setToY(150);
        buttonsAnimation.setFromY(0);
        buttonsAnimation.setToY(150);
        
        // Start the animation
        inputAnimation.play();
        titleAnimation.play();
        buttonsAnimation.play();
        symbol.setText("Stock Symbol: "+stockSearched.getSymbol());
        priceStock.setText("Market Price: "+stockSearched.getPrice());
        double buyPrice = stockSearched.getPrice() * 0.99;
        String suggestedBuyPriceFormatted = String.format("%.2f", buyPrice);
        double sellPrice = stockSearched.getPrice() * 1.01;
        String suggestedSellPriceFormatted = String.format("%.2f", sellPrice);
        suggestedBuyPrice.setText("Suggested Buy Price: "+suggestedBuyPriceFormatted);
        suggestedSellPrice.setText("Suggested Sell Price: "+suggestedSellPriceFormatted);
        action.setDisable(false);
        price.setDisable(false);
        quantity.setDisable(false);
        submitOrder.setDisable(false);
        
        }else{
            notValidCompany.setText("Invalid Company Name");
        }
        

    }

        @FXML
    void clickedSubmitOrder(MouseEvent event) {
        if(!action.getText().equals("BUY") && !action.getText().equals("SELL") || !isNumeric(price.getText())|| !isNumeric(quantity.getText())|| !isPriceWithinRange(stockSearched.getPrice(),price.getText())|| !isInteger(quantity.getText())){
            if(!action.getText().equals("BUY") && !action.getText().equals("SELL")){
                notValidAction.setText("Invalid Action");
            }else{
                notValidAction.setText("");
            }
            if( !isNumeric(price.getText())){
                notValidPrice.setText("Invalid Price");
            }else{
                notValidPrice.setText("");
            }
            if(!isNumeric(quantity.getText())||!isInteger(quantity.getText())){
                notValidQuantity.setText("Invalid Quantity");
            }else{
                notValidQuantity.setText("");
            }
            if(!isPriceWithinRange(stockSearched.getPrice(),price.getText())){
                double lowestPrice = stockSearched.getPrice() * 0.99;
                String lowestPriceFormatted = String.format("%.2f", lowestPrice);
                double highestPrice = stockSearched.getPrice() * 1.01;
                String highestlPriceFormatted = String.format("%.2f", highestPrice);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Successful or nah");
                alert.setHeaderText("Warning:");
                alert.setContentText("Price Should be Between "+lowestPriceFormatted+" - "+highestlPriceFormatted);
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/Model/stylesheet.css").toExternalForm());
                alert.showAndWait();
            }
        }else{
            notValidAction.setText("");
            notValidPrice.setText("");
            notValidQuantity.setText("");
            tr=new TradingRestrictions();
            String strStartDate = "2023-06-08";
            String strEndDate = "2023-06-20";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(strStartDate, dateFormatter);
            LocalDate endDate = LocalDate.parse(strEndDate, dateFormatter);
            tr.setTradingPeriod(startDate, endDate, 3);
            // LocalDateTime currentDateTime = LocalDateTime.now();
            
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String strNow="2023-06-13 16:59:59";
            LocalDateTime currentDateTime = LocalDateTime.parse(strNow, dateTimeFormatter);
            
            if(!tr.isWithinTradingHours(currentDateTime)){
                  Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Successful or nah");
                alert.setHeaderText("Warning:");
                alert.setContentText("Market is Closed!");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/Model/stylesheet.css").toExternalForm());
                alert.showAndWait();
            }else{
              if(action.getText().equals("BUY")){
                    if(user.getBalance()<(Double.parseDouble(price.getText())*Integer.parseInt(quantity.getText()))){
                         Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Successful or nah");
                        alert.setHeaderText("Warning:");
                        alert.setContentText("You Do Not Have Enough Balance!");
                        DialogPane dialogPane = alert.getDialogPane();
                        dialogPane.getStylesheets().add(getClass().getResource("/Model/stylesheet.css").toExternalForm());
                        alert.showAndWait();
                    }else{
                        oh.handleBuyScenario(user.getUsername(), Double.parseDouble(price.getText()),Integer.parseInt(quantity.getText()), stockSearched);
                        try {
                            emailService.sendEmail(stockSearched, "Buy", Integer.parseInt(quantity.getText()),Double.parseDouble(price.getText()), "s2175788@siswa.um.edu.my", TD.getPosition());
                        } catch (NumberFormatException e) {
                            // TODO Auto-generated catch block
                            // e.printStackTrace();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            // e.printStackTrace();
                        }
                          Alert successfulAlert = new Alert(Alert.AlertType.INFORMATION);
                            successfulAlert.setTitle("Successful Order Completed");
                            successfulAlert.setHeaderText("Notification:");
                            successfulAlert.setContentText("Buy Order is Completed and The Corresponding Receipt is Sent To Your Email Address");
                            DialogPane successfulDialogPane = successfulAlert.getDialogPane();
                            successfulDialogPane.getStylesheets().add(getClass().getResource("/Model/stylesheet.css").toExternalForm());
                            successfulDialogPane.setPrefSize(450, 200);
                            successfulAlert.showAndWait();

                    }
                }else if(action.getText().equals("SELL")){
                    // Check if the user has the stock and enough quantity in holdings
                        if (!oh.checkHoldings(user.getUsername(),stockSearched.getSymbol() ,Integer.parseInt(quantity.getText()))) {
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Successful or nah");
                            alert.setHeaderText("Warning:");
                            alert.setContentText("You Do Not Have Enough/Any Lots for this Stock!");
                            DialogPane dialogPane = alert.getDialogPane();
                            dialogPane.getStylesheets().add(getClass().getResource("/Model/stylesheet.css").toExternalForm());
                            alert.showAndWait();
                        }else{
                            oh.handleSellScenario(user.getUsername(), Double.parseDouble(price.getText()),Integer.parseInt(quantity.getText()), stockSearched);
                            try {
                            emailService.sendEmail(stockSearched, "Sell", Integer.parseInt(quantity.getText()),Double.parseDouble(price.getText()), "s2175788@siswa.um.edu.my", TD.getPosition());
                            } catch (NumberFormatException e) {
                            // TODO Auto-generated catch block
                            // e.printStackTrace();
                            } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            // e.printStackTrace();
                            }
                             Alert successfulAlert = new Alert(Alert.AlertType.INFORMATION);
                            successfulAlert.setTitle("Successful Order Completed");
                            successfulAlert.setHeaderText("Notification:");
                            successfulAlert.setContentText("Sell Order is Completed and The Corresponding Receipt is Sent To Your Email Address");
                            DialogPane successfulDialogPane = successfulAlert.getDialogPane();
                            successfulDialogPane.getStylesheets().add(getClass().getResource("/Model/stylesheet.css").toExternalForm());
                            successfulDialogPane.setPrefSize(450, 200);
                            successfulAlert.showAndWait();
                        }           
                }
            }
            
        }
    }
 @FXML
    void clickedCancelOrder(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Order");
        alert.setHeaderText("Select Order Type to Cancel");
        alert.setContentText("Choose with Caution, Once it's Gone, it's Gone!");
        
        ButtonType buyButtonType = new ButtonType("Buy Order");
        ButtonType sellButtonType = new ButtonType("Sell Order");

        alert.getButtonTypes().setAll(buyButtonType, sellButtonType, ButtonType.CANCEL);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/Model/stylesheet.css").toExternalForm());
          dialogPane.setPrefSize(450, 200);
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == buyButtonType) {
                // Cancel Buy Order
                if(!oh.cancelOrder(user.getUsername(),"Buy")){
                            Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                            errorAlert.setTitle("Order Cancellation Failed");
                            errorAlert.setHeaderText("Warning:");
                            errorAlert.setContentText("You Do Not Have Any Buy Order!");
                            DialogPane errorDialogPane = errorAlert.getDialogPane();
                            errorDialogPane.getStylesheets().add(getClass().getResource("/Model/stylesheet.css").toExternalForm());
                            errorDialogPane.setPrefSize(450, 200);
                            errorAlert.showAndWait();
                }else{
                    Alert successfulAlert = new Alert(Alert.AlertType.INFORMATION);
                            successfulAlert.setTitle("Successful Order Cancellation");
                            successfulAlert.setHeaderText("Notification:");
                            successfulAlert.setContentText("Buy Order with the Highest Amount of Money is Removed!");
                            DialogPane successfulDialogPane = successfulAlert.getDialogPane();
                            successfulDialogPane.getStylesheets().add(getClass().getResource("/Model/stylesheet.css").toExternalForm());
                            successfulDialogPane.setPrefSize(450, 200);
                            successfulAlert.showAndWait();
                }
            } else if (buttonType == sellButtonType) {
                 if(!oh.cancelOrder(user.getUsername(),"Sell")){
                            Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                            errorAlert.setTitle("Order Cancellation Failed");
                            errorAlert.setHeaderText("Warning:");
                            errorAlert.setContentText("You Do Not Have Any Sell Order!");
                            DialogPane errorDialogPane = errorAlert.getDialogPane();
                            errorDialogPane.getStylesheets().add(getClass().getResource("/Model/stylesheet.css").toExternalForm());
                            errorDialogPane.setPrefSize(450, 200);
                            errorAlert.showAndWait();
                }else{
                    Alert successfulAlert = new Alert(Alert.AlertType.INFORMATION);
                            successfulAlert.setTitle("Successful Order Cancellation");
                            successfulAlert.setHeaderText("Notification:");
                            successfulAlert.setContentText("Sell Order with the Highest Amount of Money is Removed!");
                            DialogPane successfulDialogPane = successfulAlert.getDialogPane();
                            successfulDialogPane.getStylesheets().add(getClass().getResource("/Model/stylesheet.css").toExternalForm());
                            successfulDialogPane.setPrefSize(450, 200);
                            successfulAlert.showAndWait();
                }
            } else {
                // Cancel button or closed dialog
                System.out.println("Order cancellation cancelled.");
            }
        });
    }

   
     @FXML
    void clickedDashboard(MouseEvent event) {
        App.setRoot("TradingDashboard.fxml");
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
    void clickedNews(MouseEvent event) {
        App.setRoot("News.fxml");
    }

    @FXML
    void clickedProfileInfo(MouseEvent event) {
         App.setRoot("ProfileInfo.fxml");
    }

    public boolean isNumeric(String input) {
    
    return input.matches("-?\\d+(\\.\\d+)?");
}
public boolean isPriceWithinRange(double stockPrice, String inputPrice) {
    
        double priceEntered = Double.parseDouble(inputPrice);
        double lowerLimit = stockPrice * 0.99;
        double upperLimit = stockPrice * 1.01;
        lowerLimit = Double.parseDouble(String.format("%.2f", lowerLimit));
        upperLimit = Double.parseDouble(String.format("%.2f", upperLimit));

        return priceEntered >= lowerLimit && priceEntered <= upperLimit;
}
public boolean isInteger(String value) {
    try {
        int intValue = Integer.parseInt(value);
        return true;
    } catch (NumberFormatException e) {
        return false;
    }
}

}