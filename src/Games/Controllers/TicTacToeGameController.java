package Games.Controllers;

import Games.Models.Boards.TicTacToe.TicTacToeBoard;
import Games.Models.Factories.PlayerFactory;
import Games.Models.Players.Player;


/**
 * TicTacToeGameController handles the playing of the game.
 *
 * Created by rik on 4/5/17.
 */
public class TicTacToeGameController implements Runnable{
    private Player player0;
    private Player player1;
    private TicTacToeBoard board;
    private boolean finished = false;
    private Integer lastMove = null;
    private boolean playerTurn = false; //false = 0 & true = 1

    public TicTacToeGameController(String playerOne, String playerTwo, TicTacToeBoard board, PlayerFactory factory){
        player0 = factory.getPlayer(playerOne);
        player1 = factory.getPlayer(playerTwo);
        this.board = board;
    }

    private void endGame(){
        finished = true;
    }

    @Override
    public void run() {

        while (!finished){

           nextMove();
           if (lastMove != null) {
               board.receiveMove(lastMove, !playerTurn);
           }
           playerTurn = !playerTurn;
           checkEndSituation();
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void checkEndSituation() {
        Boolean ended = board.getEnded();
        if (ended){
            endGame();
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
