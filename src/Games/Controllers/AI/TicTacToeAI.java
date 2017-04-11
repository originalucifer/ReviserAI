package Games.Controllers.AI;

import java.util.ArrayList;

/**
 * AI for playing TicTacToe.
 *
 * Created by rik on 3-4-17.
 */
public class TicTacToeAI implements AI{
	private char[] playingField;
	private char me = 'm';
	private char opponent = 'e';
	private char nothing = 'n';

    /**
     * Create a TicTacToeAI
     * With a new playingField in it
     */
	public TicTacToeAI() {
		playingField = new char[9];
		for (int i = 0; i < 9; i++){
		    playingField[i] = nothing;
        }
	}

    /**
     * Gets the best possible move after adding the opponentsMove to the playingField
     *
     * @param opponentsMove integer
     * @return integer bestMove
     */
	@Override
	public int getBestMove(Integer opponentsMove) {
	    if (opponentsMove != null){
	        playingField[opponentsMove] = opponent;
        }
	    ArrayList<Integer> moves = getAvailableMovesIndex(playingField);
	    int highValue = 0;
	    int highMove = 0;
	    for (int move : moves){
	        char[] copy = playingField.clone();
	        copy[move] = me;
	        int v = getBoardValue(copy, false);
	        if (v > highValue){
	            highValue = v;
	            highMove = move;
            }
        }
        playingField[highMove] = me;
		return highMove;
	}


	private int getBoardValue(char[] field, boolean max){
        ArrayList<Integer> moves = getAvailableMovesIndex(field);
        switch (getBoardState(field)){
            case 1: return 100;
            case 2: return -100;
            case 3: return 50;
        }

        int value;
        if (max){
            value = -100;
            for (int move : moves){
                char[] copy = field.clone();
                copy[move] = max ? me : opponent;
                int c = getBoardValue(copy, !max);
                if (c > value){
                    value = c;
                }
            }
        }else {
            value = 200;
            for (int move : moves){
                char[] copy = field.clone();
                copy[move] = max ? me : opponent;
                int c = getBoardValue(copy, !max);
                if (c < value){
                    value = c;
                }
            }
        }
        return value;
    }

    /*
    *states:
    * 0 = not ended
    * 1 = me won
    * 2 = opponent won
    * 3 = draw
    */
    private int getBoardState(char[] field){
        //column
	    for (int i = 0; i < 3; i++){
            if ((field[i] == field[i+3] && field[i] == field[i+6] && field[i] != nothing)) {
                return field[i] == me ? 1 : 2;
            }
        }
        //row
        for (int i = 0; i < 7; i += 3){
	        if (field[i] == field[i+1] && field[i] == field[i+2] && field[i] != nothing){
                return field[i] == me ? 1 : 2;
            }
        }
        //diagonal
        if (((field[0] == field[4] && field[0] == field[8])
                || (field[2] == field[4] && field[2] == field[6]))
                && field[4] != nothing ) {
            return field[4] == me ? 1 : 2;
        }
        for (int i = 0; i < 9; i++){
            if (field[i] == nothing){
                return 0;
            }
        }
        return 3;
    }

    /**
     * Gets the valid moves
     *
     * @param field playingField
     * @return arraylist with valid moves.
     */
    private ArrayList<Integer> getAvailableMovesIndex(char[] field){
        ArrayList<Integer> moves = new ArrayList<>();
        for (int i = 0; i < 9; i++){
            if (isMoveValid(i, field)){
                moves.add(i);
            }
        }
        return moves;
    }

    /**
     * Checks if the move is valid.
     * @param index move
     * @param field entire playingField
     * @return boolean valid.
     */
    private boolean isMoveValid(int index, char [] field){
        return field[index] == nothing;
    }

}
