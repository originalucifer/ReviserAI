package Games.Controllers;

import Games.Controllers.TabControllers.GameControls;
import Games.Controllers.TabControllers.GameStatusView;
import Games.Models.Factories.PlayerFactory;
import Games.Models.Players.Player;
import Games.Models.Boards.TicTacToeBoard;
import Games.Models.Factories.TicTacToePlayerFactory;
import javafx.application.Platform;

/**
 * Created by rik on 4/5/17.
 */
public class GameController implements Runnable{
    private PlayerFactory factory;
    private Player player0;
    private Player player1;
    private TicTacToeBoard board;
    private boolean finished = false;
    private Integer lastMove = null;
    private boolean playerTurn = false; //false = 0 & true = 1
    private Boolean winner = null;
    private GameStatusView gui;

    public GameController(String playerOne, String playerTwo, TicTacToeBoard board, PlayerFactory factory, GameStatusView gui){
        this.factory = factory;
        player0 = factory.getPlayer(playerOne);
        player1 = factory.getPlayer(playerTwo);
        this.board = board;
        this.gui = gui;
    }

    private void endGame(){
        finished = true;
    }

    @Override
    public void run() {

        while (!finished){

           nextMove();
           if (lastMove != null) {
               board.receiveMove(lastMove, playerTurn);
           }
           playerTurn = !playerTurn;
            checkWinSituation();
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void checkWinSituation() {
        if (winner == null){
            winner = board.getWinner();
        }
        if (winner != null){
            endGame();
            Platform.runLater(() -> gui.gameWon(!playerTurn));

        }
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public void forfeit(Player you){
        if (you == player0){
            winner = false;
        }else if (you == player1){
            winner = true;
        }
    }

    private void nextMove(){
        if (!playerTurn){
            lastMove = player0.getYourMove(lastMove);
        }else {
            lastMove = player1.getYourMove(lastMove);
        }
    }

}
