package Games.Controllers.TabControllers;

import Games.Main;
import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory;
import com.sun.javafx.application.HostServicesDelegate;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;

/**
 * Class WelcomeController
 *
 * @author Koen Hendriks
 * @version 0.1 (28-03-2017)
 */
public class WelcomeController {

    @FXML
    public Hyperlink githubLink;

    public void initialize(){

    }

    @FXML
    private void openGithub(){
        HostServicesDelegate hostServices = HostServicesFactory.getInstance(new Main());
        hostServices.showDocument("https://github.com/TeamReviser/ReviserAI");
    }
}
