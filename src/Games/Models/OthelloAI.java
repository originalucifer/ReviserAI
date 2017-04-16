package Games.Models;

import Games.Models.Boards.Othello.OthelloBoard;
import Games.Models.Boards.Othello.OthelloBoardFunctions;
import Games.Models.Boards.Othello.OthelloItem;

import java.util.ArrayList;

/**
 * Class OthelloAI
 *
 * @author koen
 * @version 0.1 (4/11/17)
 */
public class OthelloAI {

    private char[] AIboard;
    private int movesToDo;
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
        updateMovesToDo();
        char player = OthelloBoard.activePlayer == OthelloBoard.black ? black : white;
        OthelloItem bestMove = null;
        int bestValue = -9999999;
        ArrayList<OthelloItem> validMoves = OthelloBoard.getValidMoves();
        for (OthelloItem item : validMoves){
            int index = getIndex(item.getColumn(),item.getRow());
            int v = getValue(index, getOpponent(player), AIboard, 0);
            if (v > bestValue){
                bestMove = item;
                bestValue = v;
            }
        }
        bestMove.clicked();
    }

    private int getValue(int move, char player, char[] board, int depth){
        char[] newBoard = doMove(board, move, player);
        if (movesToDo + depth == 60) return directValue(newBoard);
        if (depth >= 5) return calculateBoardPosition(newBoard);
        ArrayList<Integer> newMoves = getPossibleMoves(newBoard, player);
        char opponent = getOpponent(player);
        int bestValue = -999999;
        int side = player == white ? 1 : -1;
        for (int m : newMoves){
            int v = getValue(m, opponent, newBoard, depth++);
            if (side * v > bestValue){
                bestValue = v;
            }
        }
        return bestValue;
    }

    private void updateMovesToDo(){
        movesToDo = 0;
        for (int i = 0; i < 64; i++){
            if (AIboard[i] == nothing){
                movesToDo++;
            }
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

    private int calculateBoardPosition(char[] board){
        return calculateBoardPositionPlayer(board, white) - calculateBoardPositionPlayer(board, black);
    }

    private int calculateBoardPositionPlayer(char[] board, char player){
        int value = 0;
        for (int field = 0; field < 64; field++){
            if (board[field] == player){
                value += boardValues[field];
            }
        }
        return value;
    }

    /**
    * return the amount of fields white has more than black
     */
    private int directValue(char[] board){
        return directValuePlayer(board, white) - directValuePlayer(board, black);
    }
    /**
    * Returns the number of field the player has in possession.
     */
    private int directValuePlayer(char[] board, char player){
        int value = 0;
        for (int i = 0; i < 64; i++){
            if (board[i] == player){
                value++;
            }
        }
        return value;
    }

    private char[] doMove(char[] oldBoard, int move, char player){
        char[] board = oldBoard.clone();
        char opp = getOpponent(player);
        board[move] = player;
        int check;
        check = OthelloBoardFunctions.getTopLeftNeighbour(move);
        if (check != -1 && board[check] == opp){
            int e = OthelloBoardFunctions.searchForPlayerTopLeft(board, player, move);
            if (e != -1){
                for (int i = move; i != e; i -= 9){
                    board[i] = player;
                }
            }
        }
        check = OthelloBoardFunctions.getTopNeighbour(move);
        if (check != -1 && board[check] == opp){
            int e = OthelloBoardFunctions.searchForPlayerTop(board, player, move);
            if (e != -1){
                for (int i = move; i != e; i -= 8){
                    board[i] = player;
                }
            }
        }
        check = OthelloBoardFunctions.getTopRightNeighbour(move);
        if (check != -1 && board[check] == opp){
            int e = OthelloBoardFunctions.searchForPlayerTopRight(board, player, move);
            if (e != -1){
                for (int i = move; i != e; i -= 7){
                    board[i] = player;
                }
            }
        }
        check = OthelloBoardFunctions.getLeftNeighbour(move);
        if (check != -1 && board[check] == opp){
            int e = OthelloBoardFunctions.searchForPlayerLeft(board, player, move);
            if (e != -1){
                for (int i = move; i != e; i -= 1){
                    board[i] = player;
                }
            }
        }
        check = OthelloBoardFunctions.getRightNeighbour(move);
        if (check != -1 && board[check] == opp){
            int e = OthelloBoardFunctions.searchForPlayerRight(board, player, move);
            if (e != -1){
                for (int i = move; i != e; i += 1){
                    board[i] = player;
                }
            }
        }
        check = OthelloBoardFunctions.getBottomLeftNeighbour(move);
        if (check != -1 && board[check] == opp){
            int e = OthelloBoardFunctions.searchForPlayerBottomLeft(board, player, move);
            if (e != -1){
                for (int i = move; i != e; i += 7){
                    board[i] = player;
                }
            }
        }
        check = OthelloBoardFunctions.getBottomNeighbour(move);
        if (check != -1 && board[check] == opp){
            int e = OthelloBoardFunctions.searchForPlayerBottom(board, player, move);
            if (e != -1){
                for (int i = move; i != e; i += 8){
                    board[i] = player;
                }
            }
        }
        check = OthelloBoardFunctions.getBottomRightNeighbour(move);
        if (check != -1 && board[check] == opp){
            int e = OthelloBoardFunctions.searchForPlayerBottomRight(board, player, move);
            if (e != -1){
                for (int i = move; i != e; i += 9){
                    board[i] = player;
                }
            }
        }
        return board;
    }

    private ArrayList<Integer> getPossibleMoves(char[] board, char player){
        char opponent = getOpponent(player);

        ArrayList<Integer> moves = new ArrayList<>();
        for (int i = 0; i < 64; i++){
            int position;
            if (board[i] == nothing){
                position = OthelloBoardFunctions.getTopLeftNeighbour(i);
                if (position != -1 && board[position] == opponent){
                    int r = OthelloBoardFunctions.searchForPlayerTopLeft(board, player, i);
                    if (r != -1){
                        moves.add(i);
                        continue;
                    }
                }
                position = OthelloBoardFunctions.getTopNeighbour(i);
                if (position != -1 && board[position] == opponent){
                    int r = OthelloBoardFunctions.searchForPlayerTop(board, player, i);
                    if (r != -1){
                        moves.add(i);
                        continue;
                    }
                }
                position = OthelloBoardFunctions.getTopRightNeighbour(i);
                if (position != -1 && board[position] == opponent){
                    int r = OthelloBoardFunctions.searchForPlayerTopRight(board, player, i);
                    if (r != -1){
                        moves.add(i);
                        continue;
                    }
                }
                position = OthelloBoardFunctions.getLeftNeighbour(i);
                if (position != -1 && board[position] == opponent){
                    int r = OthelloBoardFunctions.searchForPlayerLeft(board, player, i);
                    if (r != -1){
                        moves.add(i);
                        continue;
                    }
                }
                position = OthelloBoardFunctions.getRightNeighbour(i);
                if (position != -1 && board[position] == opponent){
                    int r = OthelloBoardFunctions.searchForPlayerRight(board, player, i);
                    if (r != -1){
                        moves.add(i);
                        continue;
                    }
                }
                position = OthelloBoardFunctions.getBottomLeftNeighbour(i);
                if (position != -1 && board[position] == opponent){
                    int r = OthelloBoardFunctions.searchForPlayerBottomLeft(board, player, i);
                    if (r != -1){
                        moves.add(i);
                        continue;
                    }
                }
                position = OthelloBoardFunctions.getBottomNeighbour(i);
                if (position != -1 && board[position] == opponent){
                    int r = OthelloBoardFunctions.searchForPlayerBottom(board, player, i);
                    if (r != -1){
                        moves.add(i);
                        continue;
                    }
                }
                position = OthelloBoardFunctions.getBottomRightNeighbour(i);
                if (position != -1 && board[position] == opponent){
                    int r = OthelloBoardFunctions.searchForPlayerBottomRight(board, player, i);
                    if (r != -1){
                        moves.add(i);
                        continue;
                    }
                }
            }
        }
        return moves;
    }


    private void printBoard(char[] board){
        for (int r = 0; r < 8; r++){
            for (int c = 0; c < 8; c++){
                System.out.print(" " +OthelloBoardFunctions.getIndex(c,r)+":"+ board[OthelloBoardFunctions.getIndex(c,r)]);
            }
            System.out.println();
        }
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
