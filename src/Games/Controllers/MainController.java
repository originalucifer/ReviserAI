package Games.Controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;


public class MainController extends VBox{

    @FXML
    private TabPane gameTabPane;

    public void initialize() {
        this.createTabs();
    }

    /**
     * Create the tabs for each sorting algorithm and the about tab
     */
    private void createTabs() {

        ObservableList<Tab> tabs = this.gameTabPane.getTabs();
        try {
            // Create an array with the file names and add the FXML for each file.
            File[] tabViews = new File("./src/Games/Views/Tabs").listFiles();
            if (tabViews != null) {
                for(File tabView : tabViews){
                    tabs.add(FXMLLoader.load(getClass().getResource("../Views/Tabs/" +tabView.getName())));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
