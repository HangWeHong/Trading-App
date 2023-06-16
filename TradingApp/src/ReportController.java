

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import java.io.File;
import java.util.ArrayList;

import Model.DatabaseHandler;
import Model.Transaction;
import Model.User;

public class ReportController {

    @FXML
    private Text Email;

    @FXML
    private ImageView ImageViewer;

    @FXML
    private Text Name;

    @FXML
    private TableColumn<Transaction, String> OrderTypeColumn;

    @FXML
    private Text PhoneNumber;

    @FXML
    private TableColumn<Transaction, Double> PriceColumn;

    @FXML
    private TableColumn<Transaction, String> StatusColumn;

    @FXML
    private TableColumn<Transaction, String> SymbolColumn;

    @FXML
    private TableColumn<Transaction, String> dateTimeColumn;

    @FXML
    private TableColumn<Transaction, Integer> quantityColumn;

    @FXML
    private TableView<Transaction> transactionTable;

    ArrayList<Transaction> list = new ArrayList<>();

    DatabaseHandler dbh=new DatabaseHandler();

    User user=dbh.getUser();

    public void initialize() {
        transactionTable.getItems().clear();

        // Set up column mappings
        dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        SymbolColumn.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        OrderTypeColumn.setCellValueFactory(new PropertyValueFactory<>("Order_Type"));
        StatusColumn.setCellValueFactory(new PropertyValueFactory<>("Status"));

        // Load data from the database and populate the TableView
        loadTransactionData(user.getUsername());
        // Set Image
        setImage();

        Name.setText(user.getUsername());
        PhoneNumber.setText(user.getPhoneNum());
        Email.setText(user.getEmail());

    }

    private void loadTransactionData(String username) {

        list = DatabaseHandler.loadTransactionData(username);
        for (Transaction transaction : list) {
            transactionTable.getItems().add(transaction);
        }
    }

    private void setImage() {

        File backendImageFile = new File("D:/TradingApp/TradingApp/line_chart.png");
        Image image = new Image(backendImageFile.toURI().toString());
        ImageViewer.setImage(image);

    }
}

