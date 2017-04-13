package Games.Controllers;

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

    @FXML public TextArea serverOutput;
    @FXML public TextArea gameListOutput;
    @FXML public TextArea playerListOutput;
    @FXML public TextField loginTf;
    @FXML public TextField challengeTf;
    @FXML public TextField acceptChallengeTf;
    @FXML public TextField portNumber;
    @FXML public TextField hostNumber;


    protected ConnectionHandler connectionHandler = new ConnectionHandler(this);
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
        String buttonLabel = clickedButton.getId();
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
    public void getConnection(){
            try {
                connectionHandler.connect();
                Thread.sleep(1000);
                serverOutput.appendText("\nConnection made");
                connectionHandler.getGameList();
                connectionHandler.getPlayerList();
            }catch (Exception e){
                System.out.println(e);
                serverOutput.appendText("\nConnection could not be made");
            }
    }

    /**
     * requests gameList from gameServer
     */
    private void getGameList(){
        if (connectionHandler.isConnected()){
            gameListOutput.setText("");
            connectionHandler.getGameList();
        } else {
            serverOutput.appendText("\nWarning: You are not connected");
        }
    }

    /**
     * requests playerList from gameServer
     */
    private void getPlayerList(){
        if (connectionHandler.isConnected()){
            playerListOutput.setText("");
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
     * Subscribe, should be overridden in the subclass
     */
    public void subscribe(){}

    /**
     * Subscribes user to specified Game
     * @param game name of the game to subscribe to
     */
    protected void subscribeForGame(String game){
        if (connectionHandler.isConnected() && loggedIn){
            connectionHandler.subscribe(game);
            serverOutput.appendText("\nSubscribed for game: \""+game+"\"");
        } else {
            serverOutput.appendText("\nWarning: You must first connect and log in");
        }
    }

    /**
     * Challenge, should be overridden in subclass.
     */
    public void challenge(){
    }

    /**
     * Challenge another player for the specified game.
     * @param game Name of current game.
     */
    protected void challengeForGame(String game){
        if (connectionHandler.isConnected() && loggedIn){
            String challenge = challengeTf.getText();
            challenge = challenge.replace("\\s+","");
            System.out.println(challenge+"Challenged!!!!");
            if(!challenge.equals("")){
                connectionHandler.challenge(challenge,game);
                serverOutput.appendText("\nChallenged: \""+challenge+"\" for a game of: \""+game+"\"");
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

    public void setHost(ActionEvent actionEvent) {
        if (!connectionHandler.isConnected()){
            String host = hostNumber.getText();
            String port = portNumber.getText();
            if (!host.equals("") && !port.equals("")){
                connectionHandler.setHost(host,Integer.parseInt(port));
            } else {
                serverOutput.setText("\nWarning: Specify a valid address");
            }
        } else {
            serverOutput.setText("\nWarning: You are already connected");
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
     * update the playerListOutput textArea with all players
     * @param playerName name of the players
     */
    public void updatePlayerListOutput(String playerName) { playerListOutput.appendText(playerName+"\n");
    }

    public void updateGameListOutput(String gameName){ gameListOutput.appendText(gameName+"\n");}
}
