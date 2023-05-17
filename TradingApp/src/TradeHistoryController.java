import Model.TradeHistory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class TradeHistoryController {
    @FXML
    private Label orderNo;

    @FXML
    private Label price;

    @FXML
    private Label shares;

    @FXML
    private Label stockName;

    @FXML
    private Label time;

    public void setData(TradeHistory tradeHistory){
        orderNo.setText(String.valueOf(tradeHistory.getOrderNo()));
        price.setText(String.valueOf(tradeHistory.getPrice()));
        shares.setText(String.valueOf(tradeHistory.getShares()));
        stockName.setText(tradeHistory.getStockName());
        time.setText(String.valueOf(tradeHistory.getTime()));
    }
  

}
