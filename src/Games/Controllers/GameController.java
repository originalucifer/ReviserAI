package Games.Controllers;

import Games.Models.Players.Player;
import Games.Models.Boards.TicTacToeBoard;
import Games.Models.Factories.TicTacToePlayerFactory;

/**
 * Created by rik on 4/5/17.
 */
public class GameController implements Runnable{
    private Player player0;
    private Player player1;
    private TicTacToeBoard board;
    private boolean finished = false;
    private Integer lastMove = null;
    private boolean playerTurn = false; //false = 0 & true = 1

    public GameController(String playerOne, String playerTwo, TicTacToeBoard board){
        TicTacToePlayerFactory factory = new TicTacToePlayerFactory();
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
               board.updateBoard(lastMove, playerTurn);
           }

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
