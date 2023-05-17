import java.io.IOException;

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
      root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
      Scene scene = new Scene(root);
      
      primaryStage.initStyle(StageStyle.UNDECORATED);
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
}
