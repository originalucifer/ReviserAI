package Games;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Main class which starts the application.
 */
public class Main extends Application {

    private Stage primaryStage;

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
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Reviser (AI) Games");
        Scene scene = new Scene(root, 800, 700);
        this.primaryStage.setScene(scene);
        this.primaryStage.setOnCloseRequest(WindowEvent -> Platform.exit());
        this.primaryStage.show();

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
        int stageWidth = 400;
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Views/ConnectionView.fxml"));
        Scene scene = new Scene(root, stageWidth, 700);
        stage.setX(primaryStage.getX() - stageWidth);
        stage.setY(primaryStage.getY());
        stage.setTitle("Reviser Connection Panel");
        stage.setScene(scene);
        return stage;
    }
}
