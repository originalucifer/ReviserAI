package Games.Controllers.TabControllers;

import Games.Controllers.ConnectionController;
import Games.Models.Boards.Othello.OthelloBoard;
import Games.Models.Boards.Othello.OthelloItem;
import Games.Models.Players.OthelloPlayer;
import ServerConnection.ConnectionHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.util.Objects;

/**
 * Class OthelloController
 *
 * @author koen
 * @version 0.1 (4/3/17)
 */
public class OthelloController extends ConnectionController{

    @FXML
    public Label statusLabel;

    @FXML
    public GridPane boardView;

    @FXML
    public ListView<String> blackMoves;

    @FXML
    public ListView<String> whiteMoves;

    @FXML
    public CheckBox checkBlackAi;

    @FXML
    public CheckBox checkWhiteAi;

    @FXML
    public Button whitePlayerButton;

    @FXML
    public Button blackPlayerButton;

    private ObservableList<String> whiteMovesData;
    private ObservableList<String> blackMovesData;
    private ConnectionHandler connectionHandler;

    public void initialize(){
        // connect to the server
        super.getConnection();
        connectionHandler = super.connectionHandler;
        connectionHandler.setBoard(OthelloBoard.getInstance());

        whiteMovesData = FXCollections.observableArrayList();
        blackMovesData = FXCollections.observableArrayList();

        checkBoxListeners();

        OthelloBoard.initialize(this);
    }

    /**
     * Set the listeners for the AI checkboxes.
     */
    private void checkBoxListeners() {
        checkWhiteAi.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                OthelloBoard.getWhite().setAi(true);
                whitePlayerButton.setDisable(true);
            } else {
                OthelloBoard.getWhite().setAi(false);
                whitePlayerButton.setDisable(false);
            }

            checkBothAi();
        });

        checkBlackAi.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                OthelloBoard.getBlack().setAi(true);
                blackPlayerButton.setDisable(true);
            } else {
                OthelloBoard.getBlack().setAi(false);
                blackPlayerButton.setDisable(false);
            }

            checkBothAi();
        });
    }

    /**
     * Check if we need to start the game with AI vs AI
     */
    private void checkBothAi() {
        if(checkWhiteAi.isSelected() && checkBlackAi.isSelected()){
            startGame("white");
        }
    }

    public synchronized void addMove(OthelloPlayer player,OthelloItem item){
        Platform.runLater(() -> {
            if(Objects.equals(player.getColor(), "black")) {
                blackMovesData.add(item.getPositionString());
                blackMoves.setItems(blackMovesData);
            } else {
                whiteMovesData.add(item.getPositionString());
                whiteMoves.setItems(whiteMovesData);
            }
        });
    }

    public synchronized void resetMoveList(){
        Platform.runLater(() -> {
            whiteMovesData = FXCollections.observableArrayList();
            blackMovesData = FXCollections.observableArrayList();
            blackMoves.setItems(blackMovesData);
            whiteMoves.setItems(whiteMovesData);
        });
    }

    /**
     * Called when a color button is pressed.
     *
     * @param actionEvent of the button
     */
    public void pickColor(ActionEvent actionEvent) {
        if(OthelloBoard.hasStarted()){
            setStatus("Game is still running. "+OthelloBoard.getActivePlayer().getName()+" is next.");
        }else {
            Button colorPick = (Button) actionEvent.getSource();
            startGame(colorPick.getId());
        }
    }

    public void startGame(String activePlayer){
        OthelloBoard.startGame();
        OthelloBoard.setActivePlayer(activePlayer);

        disableButtons(true);
    }

    /**
     * Disable or Enable the player buttons
     *
     * @param disable boolean to disable to buttons or enable them
     */
    public void disableButtons(boolean disable) {
        blackPlayerButton.setDisable(disable);
        whitePlayerButton.setDisable(disable);
        checkBlackAi.setDisable(disable);
        checkWhiteAi.setDisable(disable);
    }

    /**
     * Get a OthelloItem from the GridPane
     *
     * @param row which row to get the OthelloItem from.
     * @param column which column to get the OthelloItem from.
     * @return OthelloItem on the given row and column.
     */
    public OthelloItem getOthelloItemByLocation (final int row, final int column) {
        OthelloItem result = null;
        ObservableList<Node> childrens = this.boardView.getChildren();

        for (Node node : childrens) {
            if(boardView.getRowIndex(node) == row && boardView.getColumnIndex(node) == column) {
                result = (OthelloItem) node;
                break;
            }
        }

        return result;
    }

    /**
     * Set the status in the GUI for the player.
     *
     * @param status String with current status of the game.
     */
    public synchronized void setStatus(String status){
        Platform.runLater(() -> statusLabel.setText(status));
    }

    /**
     * Resets the Othello game when the Reset button is pressed in the Gui.
     */
    public void resetGame(){
        OthelloBoard.reset();
    }

    /**
     * Remove a move from the Black ListView.
     *
     * @param item OthelloItem to remove
     */
    public synchronized void removeBlackList(OthelloItem item) {
        Platform.runLater(() -> {
            blackMovesData.remove(item.getPositionString());
        });
    }

    /**
     * Remove a move from the White ListView.
     *
     * @param item OthelloItem to remove
     */
    public synchronized void removeWhiteList(OthelloItem item) {
        Platform.runLater(() -> {
            whiteMovesData.remove(item.getPositionString());
        });
    }

    public void makeWhiteAi(ActionEvent actionEvent) {
    }

    public void makeBlackAi(ActionEvent actionEvent) {
    }


    /**
     * //TODO should setup connection with the server
     */
    public void getConnection() {
//        if (playerTypeChosen && playerChosen){
            if (!connectionHandler.isConnected()){
                super.getConnection();
//                connectionHandler.setBoard(OthelloBoard);
//                TODO somehow the othelloBoard should be set so that the methods yourTurn etc can be called.
            } else {
                serverOutput.appendText("\nWarning: You are already connected");
            }
//        } else {
//            serverOutput.appendText("\nYou must first choose AI or GUI and X or O");
//        }
    }

    /**
     * Get the connectionhandler from this controller
     *
     * @return ConnectionHandler
     */
    public ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }

    /**
     * Subscribes user to Reversi on the Server
     */
    public void subscribe(){
        super.subscribeForGame("Reversi");
    }

    /**
     * Challenges another player for a Reversi
     */
    public void challenge(){
        super.challengeForGame("Reversi");
    }

}
