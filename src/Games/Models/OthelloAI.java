package Games.Models;

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

    private char[] AIboard;
    private char nothing = 'n';
    private char black = 'b';
    private char white = 'w';
    //othellomaster.com/OM/Report/HTML/img13.png
    private int[] boardValues = {
            10000,-3000,1000,800,800,-3000,10000,
            -3000,-5000,-450,-500,-500,-450,-5000,-3000,
            1000,-450,30,10,10,30,-450,1000,
            800,-500,10,50,50,10,-500,800,
            800,-500,10,50,50,10,-500,800,
            1000,-450,30,10,10,30,-450,1000,
            -3000,-5000,-450,-500,-500,-450,-5000,-3000,
            10000,-3000,1000,800,800,-3000,10000
    };

    public OthelloAI(){
        AIboard = new char[64];
        for (int i = 0; i < 64; i++){
            AIboard[i] = nothing;
        }
    }

    public void makeMove(){
        updateBoard();
        char player = OthelloBoard.activePlayer == OthelloBoard.black ? black : white;
        for (int i : getAvailableMoves(AIboard, player)){
            System.out.print(i);
        }
        System.out.println();
        Random randomGenerator = new Random();
        ArrayList<OthelloItem> validMoves = OthelloBoard.getValidMoves();
        int index = randomGenerator.nextInt(validMoves.size());
        OthelloItem item = validMoves.get(index);
        item.clicked();
    }

    private void updateBoard(){
        for (OthelloItem item : OthelloBoard.blackItems){
            AIboard[getIndex(item.getColumn(), item.getRow())] = item.getPlayer().getColor().equals("black") ? black : white;
        }
    }

    private int calculateBoardPosition(char[] board, char player){
        int value = 0;
        for (int field = 0; field < 64; field++){
            if (board[field] == player){
                value += boardValues[field];
            }
        }
        return value;
    }


    private ArrayList<Integer> getAvailableMoves(char[] board, char player){
        char opp = player == white ? black : white;
        ArrayList<Integer> moves = new ArrayList<>();
        for (int i = 0; i < 64; i++){
            //looking for opponents place
            if (board[i] == opp){
                int row = getRow(i);
                int col = getCol(i);

                if (i - 9 >= 0 && board[i - 9] == nothing) {
                    int search = i + 9;
                    while(true){
                        if (search < 0 || search > 63) break;
                        if (getCol(search) < col) break;
                        if (board[search] == player){
                            moves.add(i-9);
                            break;
                        }
                    search += 9;
                    }
                }
                if (i + 9 <= 63 && board[i + 9] == nothing) {
                    int search = i - 9;
                    while(true){
                        if (search < 0 || search > 63) break;
                        if (getCol(search) > col) break;
                        if (board[search] == player){
                            moves.add(i+9);
                            break;
                        }
                        search -= 9;
                    }
                }

                if (i + 8 <= 63 && board[i + 8] == nothing) {
                    int search = i - 8;
                    while(true){
                        if (search < 0 || search > 63) break;
                        if (board[search] == player){
                            moves.add(i+8);
                            break;
                        }
                        search -= 8;
                    }
                }
                if (i - 8 >= 0 && board[i - 8] == nothing) {
                    int search = i + 8;
                    while(true){
                        if (search < 0 || search > 63) break;
                        if (board[search] == player){
                            moves.add(i-8);
                            break;
                        }
                        search += 8;
                    }
                }

                if (i - 7 >= 0 && board[i - 7] == nothing) {
                    int search = i + 7;
                    while(true){
                        if (search < 0 || search > 63) break;
                        if (getCol(search) > col) break;
                        if (board[search] == player){
                            moves.add(i-7);
                            break;
                        }
                        search += 7;
                    }
                }
                if (i + 7 <= 63 && board[i + 7] == nothing) {
                    int search = i - 7;
                    while(true){
                        if (search < 0 || search > 63) break;
                        if (getCol(search) < col) break;
                        if (board[search] == player){
                            moves.add(i+7);
                            break;
                        }
                        search -= 7;
                    }
                }
                if (i + 1 <= 63 && board[i + 1] == nothing) {
                    int search = i - 1;
                    while(true){
                        if (search < 0 || search > 63) break;
                        if (getRow(search) > row) break;
                        if (board[search] == player){
                            moves.add(i+1);
                            break;
                        }
                        search -= 1;
                    }
                }
                if (i - 1 >= 0 && board[i - 1] == nothing) {
                    int search = i + 1;
                    while(true){
                        if (search < 0 || search > 63) break;
                        if (getRow(search) < row) break;
                        if (board[search] == player){
                            moves.add(i-1);
                            break;
                        }
                        search += 1;
                    }
                }
            }
        }
        return moves;
    }

    private int getRow(int index){
        return index / 8;
    }

    private int getCol(int index){
        return index % 8;
    }


    private int getIndex(int column, int row){
        return row * 8 + column;
    }
}