package Games.Controllers.AI;

import Games.Models.Boards.TicTacToeBoard;

import java.util.LinkedList;

/**
 * Created by rik on 3-4-17.
 */
public class TicTacToeAI {
	private TicTacToeBoard playBoard;
	private char isPlaying;
	private char isNotPlaying;

	public TicTacToeAI(TicTacToeBoard board, char isPlaying) {
		playBoard = board;
		this.isPlaying = isPlaying;
		isNotPlaying = isPlaying == 'X' ? 'O' : 'X';
	}

	private void doNextSet(){
		getMaxSet(playBoard.board.clone());
	}

	private int[][] getAvailableSets(char turn, char[][] board){
		int[][] sets = new int[9][2];
		int pointer = 0;
		for (int col = 0; col < 3; col ++) {
			for (int row = 0; row < 3; row++) {
				if (isValid(board, col, row)) {
					sets[pointer][0] = col;
					sets[pointer][1] = row;
					pointer++;
				}
			}
		}
		return sets;
	}

	private boolean isValid(char[][] board, int col, int row){
		if (board[col][row] == 'X' || board[col][row] == 'O') {
			return false;
		}
		return true;
	}

	private int getMaxValue(char[][] board, int[] set){
return 0;
	}

	private int[] getMaxSet(char[][] board) {
		int[][] sets = getAvailableSets(isPlaying, board);
		int [] values = new int[9];
		int pointer = 0;
		for (int[] set : sets){
			values[pointer] = getMaxValue(board, set);
			pointer++;
		}
		int i = 0;
		int max = values[0];
		int maxPoint = 0;
		for (int value : values) {
			if (value > max) {
				maxPoint = i;
				max = value;
			}
			i++;
		}
		return sets[maxPoint];
	}

	private int maxValue(char[][] board){
		int[][] sets = getAvailableSets(isPlaying, board);
		if (playBoard.find3InARow(board)) {
			return 100;
		}


	}

	private int minValue(char[][] board){
		int[][] sets = getAvailableSets(isNotPlaying, board);
		if (playBoard.find3InARow(board)) {
			return -100;
		}


	}

}
