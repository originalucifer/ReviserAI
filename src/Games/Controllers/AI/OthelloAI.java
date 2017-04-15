package Games.Controllers.AI;

import Games.Models.Boards.Othello.OthelloBoard;
import Games.Models.Boards.Othello.OthelloItem;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class OthelloAI
 *
 * @author koen
 * @version 0.1 (4/11/17)
 */
public class OthelloAI {

    public void makeMove(){
        Random randomGenerator = new Random();
        ArrayList<OthelloItem> validMoves = OthelloBoard.getValidMoves();
        int index = randomGenerator.nextInt(validMoves.size());
        OthelloItem item = validMoves.get(index);
        item.clicked(false,"AI MOVED");
    }
}
