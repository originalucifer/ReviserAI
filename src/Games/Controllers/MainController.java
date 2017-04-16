package Games.Controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * MainController which loads the tabs for each game
 */
public class MainController extends VBox{

    @FXML
    private TabPane gameTabPane;

    public void initialize() {
        this.loadCustomTabs();
        this.createTabsfromFxml();
    }

    /**
     *  Sometimes we have our own rules for certain tabs
     *  FXML files can be added here by name to make sure
     *  they won't be loaded dynamically
     */
    private final ArrayList<String> tabException = new ArrayList<String>() {{
        add("Welcome.fxml");
    }};

    /**
     * Load tabs manually to make sure they are in a wishfull order.
     */
    private void loadCustomTabs(){
        ObservableList<Tab> tabs = this.gameTabPane.getTabs();
        try {
            tabs.add(FXMLLoader.load(getClass().getResource("../Views/Tabs/Welcome.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the tabs for the games dynamically for the FXML files we have.
     */
    private void createTabsfromFxml() {

        ObservableList<Tab> tabs = this.gameTabPane.getTabs();
        try {
            // Create an array with the file names and add the FXML for each file.
            File[] tabViews = new File("./src/Games/Views/Tabs").listFiles();
            if (tabViews != null) {
                for(File tabView : tabViews){
                    if(!tabException.contains(tabView.getName())){
                        tabs.add(FXMLLoader.load(getClass().getResource("../Views/Tabs/" +tabView.getName())));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
