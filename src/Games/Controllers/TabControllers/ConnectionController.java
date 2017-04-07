package Games.Controllers.TabControllers;

import ServerConnection.ConnectionHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


/**
 * Controller for the ConnectionView
 *
 * Created by robin on 3-4-17.
 */
public class ConnectionController {

    @FXML TextArea serverOutput;
    @FXML private TextField subscribeTf;
    @FXML private TextField loginTf;
    @FXML private TextField challengeTf;
    @FXML private TextField acceptChallengeTf;

    ConnectionHandler connectionHandler = new ConnectionHandler(this);
    private boolean loggedIn = false;

    public ConnectionController(){
    }


    /**
     * Handle the pressed buttons
     *
     * @param actionEvent pressed button
     */
    public void buttonEventHandler(ActionEvent actionEvent){
        Button clickedButton = (Button) actionEvent.getTarget();
        String buttonLabel = clickedButton.getText();
        switch (buttonLabel){
            case "Connect" :
                this.getConnection();break;
            case "Login" :
                this.login();break;
            case "Logout" :
                this.logout();break;
            case "Subscribe":
                this.subscribe();break;
            case "Challenge":
                this.challenge();break;
            case "Accept Challenge":
                this.acceptChallenge();break;
            case "Close connection":
                this.closeConnection();break;
            case "Get gamelist":
                this.getGameList();break;
            case "Get playerlist":
                this.getPlayerList();break;
        }
    }

    /**
     * Connects to the server.
     *
     * If it fails it shutdown entire application
     */
    void getConnection(){
            try {
                connectionHandler.connect();
                Thread.sleep(1000);
                serverOutput.appendText("\nConnection made");
            }catch (Exception e){
                System.out.println(e);
                serverOutput.appendText("\nConnection could not be made");
            }
    }

    /**
     * requests gamelist from gameserver
     */
    private void getGameList(){
        if (connectionHandler.isConnected()){
            connectionHandler.getGameList();
        } else {
            serverOutput.appendText("\nWarning: You are not connected");
        }
    }

    /**
     * requests gamelist from gameserver
     */
    private void getPlayerList(){
        if (connectionHandler.isConnected()){
            connectionHandler.getPlayerList();
        } else {
            serverOutput.appendText("\nWarning: You are not connected");
        }
    }

    /**
     * Logs user in to server with specified name
     */
    private void login(){
        if(!loggedIn){
            if (connectionHandler.isConnected()){
                String name = loginTf.getText();
                if(!name.equals("")){
                    connectionHandler.login(name);
                    loggedIn = true;
                    serverOutput.appendText("\nLogged in with name: \"" +name+"\"");
                }else{
                    serverOutput.appendText("\nWarning: Enter a valid name");
                }
            } else {
                serverOutput.appendText("\nWarning: You must first connect");
            }
        }
    }

    /**
     * logs out the user
     */
    private void logout(){
        if (loggedIn){
           connectionHandler.logout();
           loggedIn = false;
           serverOutput.appendText("\nLogged out");
        } else {
            serverOutput.appendText("\nWarning: You are not logged in yet");
        }
    }

    /**
     * Subscribes user to specified Game on the Server
     */
    private void subscribe(){
        if (connectionHandler.isConnected() && loggedIn){
            String game = subscribeTf.getText();
            if(!game.equals("")){
                game = correctCase(game);
                connectionHandler.subscribe(game);
                serverOutput.appendText("\nSubscribed for game: \"" +game+"\"");
            }else{
                serverOutput.appendText("\nWarning: Enter a valid game");
            }
        } else {
            serverOutput.appendText("\nWarning: You must first connect and log in");
        }
    }

    /**
     * Challenges another player for a specified game
     */
    private void challenge(){
        if (connectionHandler.isConnected() && loggedIn){
            String challenge = challengeTf.getText();
            String[] split = challenge.split("\\s+");
            if(split.length == 2 && !split[0].equals("") && !split[1].equals("")){
                split[1] = correctCase(split[1]);
                connectionHandler.challenge(split);
                serverOutput.appendText("\nChallenged: \""+split[0]+"\" for a game of: \"" +split[1]+ "\"");
            }else{
                serverOutput.appendText("\nWarning: Enter a valid name and game for the challenge");
            }
        } else {
            serverOutput.appendText("\nWarning: You must first connect and log in");
        }
    }

    /**
     * Accept challenge belonging to specified challenge ID
     */
    private void acceptChallenge(){
        if (connectionHandler.isConnected() && loggedIn){
            String challengeID = acceptChallengeTf.getText();
            if(!challengeID.equals("")){
                connectionHandler.acceptChallenge(challengeID);
                serverOutput.appendText("\nChallenge \"" +challengeID+ "\" accepted");
            }else{
                serverOutput.appendText("\nWarning: Enter a valid challenge id");
            }
        } else {
            serverOutput.appendText("\nWarning: You must first connect and log in");
        }
    }


    /**
     * Closes connection with server and logs out if logged in
     */
    private void closeConnection(){
        if (connectionHandler.isConnected()) {
            if(loggedIn){
                connectionHandler.logout();
                loggedIn = false;
            }
            connectionHandler.quitConnection();
            serverOutput.appendText("\nConnection closed");
        } else {
            serverOutput.appendText("\nWarning: You are not connected");
        }
    }

    /**
     * updates the serverOutput textarea.
     * @param serverMessage response from server
     */
    public void updateServerOutput(String serverMessage){
        serverOutput.appendText("\n"+serverMessage);
    }


    /**
     * Returns the entered string with a uppercase first character and all other characters lowercase
     *
     * @param inputVal entered string
     * @return corrected String
     */
    private String correctCase (String inputVal) {
        return inputVal.substring(0,1).toUpperCase() + inputVal.substring(1).toLowerCase();
    }
}
