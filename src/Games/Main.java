package Games;

import Config.config;
import ServerConnection.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Properties;

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
        Properties c = config.getConfig("config");
        System.out.println(c.getProperty("port"));
//        new Initialize();
//        launch(args);
    }
}
