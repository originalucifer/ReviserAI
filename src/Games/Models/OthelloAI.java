package Games.Models;

import Games.Controllers.AI.AI;
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
            10000,-3000,1000,800,800,1000,-3000,10000,
            -3000,-5000,-450,-500,-500,-450,-5000,-3000,
            1000,-450,30,10,10,30,-450,1000,
            800,-500,10,50,50,10,-500,800,
            800,-500,10,50,50,10,-500,800,
            1000,-450,30,10,10,30,-450,1000,
            -3000,-5000,-450,-500,-500,-450,-5000,-3000,
            10000,-3000,1000,800,800,1000,-3000,10000
    };

    public OthelloAI(){
        AIboard = new char[64];
        for (int i = 0; i < 64; i++){
            AIboard[i] = nothing;
        }
    }

    private char getOpponent(char player){
        return player == white ? black : white;
    }

    public void makeMove(){
        updateBoard();
        char player = OthelloBoard.activePlayer == OthelloBoard.black ? black : white;
        int bestMove = 0;
        int bestValue = -9999999;
        ArrayList<Integer> mo = getAvailableMoves(AIboard, player);
        for (int i : mo){
            AIboard[i] = player;
            int v = calculateBoardPosition(AIboard, player);
            System.out.print(i + " ");
            if (v > bestValue){
                bestMove = i;
                bestValue = v;
            }
            AIboard[i] = nothing;
        }
        System.out.println();
        int row = getRow(bestMove);
        int col = getCol(bestMove);
        ArrayList<OthelloItem> validMoves = OthelloBoard.getValidMoves();
        OthelloItem clicking = null;
        for (OthelloItem item : validMoves){
            if (item.getColumn() == col && item.getRow() == row){
                clicking = item;
                break;
            }
        }
        if (clicking == null){
            System.out.println("random");
            Random randomGenerator = new Random();
            int index = randomGenerator.nextInt(validMoves.size());
            OthelloItem item = validMoves.get(index);
            item.clicked();
        }else {
            System.out.println("smart");
            clicking.clicked();
        }

    }

    private void updateBoard(){
        for (OthelloItem item : OthelloBoard.blackItems){
            AIboard[getIndex(item.getColumn(), item.getRow())] = item.getPlayer().getColor().equals("black") ? black : white;
        }
        for (OthelloItem item : OthelloBoard.whiteItems){
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

    private void doMove(char[] board, int move, char player){
        char opp = getOpponent(player);
        board[move] = player;
        int row = getRow(move);
        int col = getCol(move);
        ArrayList<Integer> toFlip = new ArrayList<>();
        if (move - 9 >= 0 && board[move - 9] == nothing) {
            int search = move + 9;
            while(true){
                if (search < 0 || search > 63) break;
                if (getCol(search) < col) break;
                if (board[search] == nothing) break;
                if (board[search] == player){
                    for (int k = move + 9; k < search; k += 9){
                        board[k] = player;
                    }
                    break;
                }
                search += 9;
            }
        }
        if (move + 9 <= 63 && board[move + 9] == nothing) {
            int search =  - 9;
            while(true){
                if (search < 0 || search > 63) break;
                if (getCol(search) > col) break;
                if (board[search] == nothing) break;
                if (board[search] == player){
                    for (int k = move - 9; k > search; k -= 9){
                        board[k] = player;
                    }
                    break;
                }
                search -= 9;
            }
        }

        if (move + 8 <= 63 && board[move + 8] == nothing) {
            int search = move - 8;
            while(true){
                if (search < 0 || search > 63) break;
                if (board[search] == nothing) break;
                if (board[search] == player){
                    for (int k = move -8; k > search; k -= 8){
                        board[k] = player;
                    }
                    break;
                }
                search -= 8;
            }
        }
        if (move - 8 >= 0 && board[move - 8] == nothing) {
            int search = move + 8;
            while(true){
                if (search < 0 || search > 63) break;
                if (board[search] == nothing) break;
                if (board[search] == player){
                    for (int k = move + 8; k < search; k += 8){
                        board[k] = player;
                    }
                    break;
                }
                search += 8;
            }
        }

        if (move - 7 >= 0 && board[move - 7] == nothing) {
            int search = move + 7;
            while(true){
                if (search < 0 || search > 63) break;
                if (getCol(search) > col) break;
                if (board[search] == nothing) break;
                if (board[search] == player){
                    for (int k = move + 7; k < search; k += 7){
                        board[k] = player;
                    }
                    break;
                }
                search += 7;
            }
        }
        if (move + 7 <= 63 && board[move + 7] == nothing) {
            int search = move - 7;
            while(true){
                if (search < 0 || search > 63) break;
                if (getCol(search) < col) break;
                if (board[search] == nothing) break;
                if (board[search] == player){
                    for (int k = move - 7; k > search; k -= 7){
                        board[k] = player;
                    }
                    break;
                }
                search -= 7;
            }
        }
        if (move + 1 <= 63 && board[move + 1] == nothing) {
            int search = move - 1;
            while(true){
                if (search < 0 || search > 63) break;
                if (getRow(search) > row) break;
                if (board[search] == nothing) break;
                if (board[search] == player){
                    for (int k = move - 1; k > search; k -= 1){
                        board[k] = player;
                    }
                    break;
                }
                search -= 1;
            }
        }
        if (move - 1 >= 0 && board[move - 1] == nothing) {
            int search = move + 1;
            while(true){
                if (search < 0 || search > 63) break;
                if (getRow(search) < row) break;
                if (board[search] == nothing) break;
                if (board[search] == player){
                    for (int k = move + 1; k < search; k += 1){
                        board[k] = player;
                    }
                    break;
                }
                search += 1;
            }
        }
    }

    private ArrayList<Integer> getAvailableMoves(char[] board, char player){
        char opp = getOpponent(player);
        ArrayList<Integer> moves = new ArrayList<>();
        for (int i = 0; i < 64; i++){

            //looking for opponents place
            if (board[i] == opp){
                int row = getRow(i);
                int col = getCol(i);

                if (i - 9 >= 0 && getCol(i-9) < col && board[i - 9] == nothing) {
                    int search = i + 9;
                    while(true){
                        if (search < 0 || search > 63) break;
                        if (getCol(search) < col) break;
                        if (board[search] == nothing) break;
                        if (board[search] == player){
                            moves.add(i-9);
                            break;
                        }
                    search += 9;
                    }
                }
                if (i + 9 <= 63 && getCol(i+9) > col && board[i + 9] == nothing) {
                    int search = i - 9;
                    while(true){
                        if (search < 0 || search > 63) break;
                        if (getCol(search) > col) break;
                        if (board[search] == nothing) break;
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
                        if (board[search] == nothing) break;
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
                        if (board[search] == nothing) break;
                        if (board[search] == player){
                            moves.add(i-8);
                            break;
                        }
                        search += 8;
                    }
                }

                if (i - 7 >= 0 && getCol(i-7) > col && board[i - 7] == nothing) {
                    int search = i + 7;
                    while(true){
                        if (search < 0 || search > 63) break;
                        if (getCol(search) > col) break;
                        if (board[search] == nothing) break;
                        if (board[search] == player){
                            moves.add(i-7);
                            break;
                        }
                        search += 7;
                    }
                }
                if (i + 7 <= 63 && getCol(i+7) < col && board[i + 7] == nothing) {
                    int search = i - 7;
                    while(true){
                        if (search < 0 || search > 63) break;
                        if (getCol(search) < col) break;
                        if (board[search] == nothing) break;
                        if (board[search] == player){
                            moves.add(i+7);
                            break;
                        }
                        search -= 7;
                    }
                }
                if (i + 1 <= 63 && getRow(i+1) == row && board[i + 1] == nothing) {
                    int search = i - 1;
                    while(true){
                        if (search < 0 || search > 63) break;
                        if (getRow(search) > row) break;
                        if (board[search] == nothing) break;
                        if (board[search] == player){
                            moves.add(i+1);
                            break;
                        }
                        search -= 1;
                    }
                }
                if (i - 1 >= 0 && getRow(i-1) == row && board[i - 1] == nothing) {
                    int search = i + 1;
                    while(true){
                        if (search < 0 || search > 63) break;
                        if (getRow(search) < row) break;
                        if (board[search] == nothing) break;
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