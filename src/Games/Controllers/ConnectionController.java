package Games.Controllers;

import ServerConnection.ConnectionHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;

/**
 * Created by robin on 3-4-17.
 */
public class ConnectionController {

    @FXML private TextArea serverOutput;
    @FXML private TextField subscribeTf;
    @FXML private TextField loginTf;
    @FXML private TextField challengeTf;
    @FXML private TextField acceptChallengeTf;

    private ConnectionHandler connectionHandler = new ConnectionHandler();
    private boolean loggedIn = false;

    public ConnectionController(){}


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
            case "Subscribe":
                this.subscribe();break;
            case "Challenge":
                this.challenge();break;
            case "Accept Challenge":
                this.acceptChallenge();break;
            case "Close connection":
                this.closeConnection();break;
        }
        try{
            Thread.sleep(1000);
            updateServerOutput();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Connects to the server.
     *
     * If it fails it shutdown entire application
     */
    private void getConnection(){
        if (!connectionHandler.isConnected()){
            try {
                connectionHandler.connect();
                serverOutput.appendText("\nConnection made");
            }catch (Exception e){
                System.out.println(e);
            }
        } else {
            serverOutput.appendText("\nWarning: You are already connected");
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
     * Subscribes user to specified Game on the Server
     */
    private void subscribe(){
        if (connectionHandler.isConnected() && loggedIn){
            String game = subscribeTf.getText();
            if(!game.equals("")){
                connectionHandler.subscribe(correctCase(game));
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
     * TODO does not work yet, should look at command
     */
    private void challenge(){
        if (connectionHandler.isConnected() && loggedIn){
            String challenge = challengeTf.getText();
            if(!challenge.equals("")){
                String[] split = challenge.split("\\s+");
                split[1] = correctCase(split[1]);
                connectionHandler.challenge(split);
                serverOutput.appendText("\n\n\""+split[0]+"\" challenged, for a game of: \"" +split[1]+ "\"");
            }else{
                serverOutput.appendText("\nWarning: Enter a valid name and game for the challenge");
            }
        } else {
            serverOutput.appendText("\nWarning: You must first connect and log in");
        }
    }

    /**
     * Accept challenge belonging to specified challenge ID
     * TODO does not work yet, should look at command
     */
    private void acceptChallenge(){
        if (connectionHandler.isConnected() && loggedIn){
            String challengeID = acceptChallengeTf.getText();
            if(!challengeID.equals("")){
                connectionHandler.acceptChallenge(challengeID);
                serverOutput.appendText("\n\nChallenge \"" +challengeID+ "\" accepted");
            }else{
                serverOutput.appendText("\nWarning: Enter a valid challenge id");
            }
        } else {
            serverOutput.appendText("\nWarning: You must first connect and log in");
        }
    }


    /**
     * Closes connection with server
     */
    private void closeConnection(){
        if (connectionHandler.isConnected()) {
            connectionHandler.quitConnection();
            loggedIn = false;
            serverOutput.appendText("\nConnection closed");
        } else {
            serverOutput.appendText("\nWarning: You are not connected");
        }
    }

    private void updateServerOutput(){
        ArrayList<String> serverResponse = connectionHandler.updateOutput();
        for (String s : serverResponse){
            serverOutput.appendText("\n"+s);
        }
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
