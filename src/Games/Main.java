package Games;

import Games.Controllers.ServerConnection.Connection;
import Games.Controllers.ServerConnection.ReceiveListener;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Views/MainLayout.fxml"));
        primaryStage.setTitle("Reverse AI Games");
        Scene scene = new Scene(root, 720, 480);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        new Thread(new Connection(new ReceiveListener())).start();
        launch(args);
    }
}
