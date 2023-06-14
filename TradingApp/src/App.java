import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Model.DatabaseHandler;
import Model.TradingRestrictions;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
 
public class App extends Application {
      private static Stage stage;
    @Override
    public void start(Stage primaryStage) {
     stage=primaryStage;
    Parent root;
    try {
      setScheduler();
      root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
      Scene scene = new Scene(root);
      
      primaryStage.initStyle(StageStyle.UNDECORATED);
      root.setOnMousePressed(pressEvent -> {
        root.setOnMouseDragged(dragEvent -> {
            primaryStage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
            primaryStage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
        });
    });
      primaryStage.setScene(scene);
      primaryStage.setTitle("Trading App");
        primaryStage.show();
    }catch (IOException e) {
     }
    }
 
 public static void main(String[] args) {
        launch(args);
    }
    public static void setRoot(String filename){
      try {
        stage.getScene().setRoot(FXMLLoader.load(App.class.getResource(filename)));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    public static void setScheduler(){
       TradingRestrictions tr=new TradingRestrictions();
        String strStartDate = "2023-06-08";
        System.out.println(strStartDate);
        String strEndDate = "2023-06-20";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(strStartDate, dateFormatter);
        LocalDate endDate = LocalDate.parse(strEndDate, dateFormatter);


        tr.setTradingPeriod(startDate, endDate, 3);
        DatabaseHandler.scheduleLotPoolReplenishment();
        DatabaseHandler.schedulePLPointsUpdate();
        DatabaseHandler.scheduleUserBalanceCheck();
    }
}
