package Games;

import ServerConnection.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * Main class which starts the application.
 */
public class Main extends Application {

    /**
     * Start the javaFX application
     *
     * @param primaryStage main stage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        // Create main stage for the application
        Parent root = FXMLLoader.load(getClass().getResource("Views/MainLayout.fxml"));
        primaryStage.setTitle("Reviser AI Games");
        Scene scene = new Scene(root, 720, 480);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(WindowEvent -> Platform.exit());
        primaryStage.show();

        // Create stage for the connection handling.
        Stage connectionStage = createConnectionStage();
        connectionStage.setOnCloseRequest(WindowEvent -> Platform.exit());
        connectionStage.show();
    }


    /**
     * Get connection with the GameServer and launch the javaFX application
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    private Stage createConnectionStage() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Views/ConnectionView.fxml"));
        Scene scene = new Scene(root, 250, 480);
        stage.setScene(scene);
        return stage;
    }
}
