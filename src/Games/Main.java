package Games;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class which starts the application.
 */
public class Main extends Application {

    /**
     * Start the javaFX application
     *
     * @param primaryStage main stage
     * @throws Exception exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create main stage for the application
        Parent root = FXMLLoader.load(getClass().getResource("Views/MainLayout.fxml"));
        primaryStage.setTitle("Reviser (AI) Games");
        Scene scene = new Scene(root, 1400, 800);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(WindowEvent -> Platform.exit());
        primaryStage.show();
    }

    /**
     * Get connection with the GameServer and launch the javaFX application
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
