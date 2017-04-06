package Games.Controllers.AI;

import Games.Controllers.TabControllers.TicTacToeController;
import Games.Models.Boards.TicTacToeBoard;

import java.util.ArrayList;

/**
 * Created by rik on 3-4-17.
 */
public class TicTacToeAI {
	private TicTacToeBoard playBoard;
	private char isPlaying;
	private char isNotPlaying;
	private TicTacToeController controller;

	public TicTacToeAI(TicTacToeBoard board, TicTacToeController controller, char isPlaying) {
		playBoard = board;
		this.isPlaying = isPlaying;
		this.controller = controller;
		isNotPlaying = isPlaying == 'X' ? 'O' : 'X';
	}

	public void doNextSet(){
		End end = new End();
		int[][] sets = getAvailableSets(isPlaying, playBoard.board.clone(), end);
		if (end.getEnd()) return;

		ArrayList<Integer> values = new ArrayList<>();
		for (int[] set : sets){
			char[][] b = playBoard.board.clone();
			b[set[0]][set[1]] = isPlaying;
			values.add(minValue(b));
		}
		int i = 0;
		int point = 0;
		int max = values.get(0);
		for (int number : values){
			if (number > max){
				max = number;
				point = i;
			}
			i++;
		}

        controller.set(controller.getButton(sets[point][0], sets[point][1]));

	}

	private int[][] getAvailableSets(char turn, char[][] board, End end){
		int[][] sets = new int[9][2];
		int pointer = 0;
		for (int col = 0; col < 3; col ++) {
			for (int row = 0; row < 3; row++) {
				if (isValid(board, col, row)) {
					sets[pointer][0] = col;
					sets[pointer][1] = row;
					pointer++;
					end.setEnd(false);
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
		int[][] sets = getAvailableSets(isPlaying, board, new End());
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
		ArrayList<Integer> values = new ArrayList<>();

		End end = new End();

		int[][] sets = getAvailableSets(isPlaying, board, end);


		if (playBoard.find3InARow(board)) {
			return 100;
		}else if (end.getEnd()){
			return 50;
		}

		for (int[] set : sets){
			char[][] b = board.clone();
			b[set[0]][set[1]] = isPlaying;
			values.add(minValue(b));
		}
		int max = values.get(0);
		for (int number : values){
			if (number > max){
				max = number;
			}
		}
		return max;
	}

	private int minValue(char[][] board){
		ArrayList<Integer> values = new ArrayList<>();

		End end = new End();

		int[][] sets = getAvailableSets(isNotPlaying, board, end);

		if (playBoard.find3InARow(board)) {
			return -100;
		}else if (end.getEnd()){
			return 50;
		}

		for (int[] set : sets){
			char[][] b = board.clone();
			b[set[0]][set[1]] = isNotPlaying;
			values.add(maxValue(b));
		}
		int min = values.get(0);
		for (int number : values){
			if (number < min){
				min = number;
			}
		}
		return min;
	}

	public int getYourMove(Integer opponentsMove) {
		End end = new End();
		int[][] sets = getAvailableSets(isPlaying, playBoard.board.clone(), end);


		ArrayList<Integer> values = new ArrayList<>();
		for (int[] set : sets){
			char[][] b = playBoard.board.clone();
			b[set[0]][set[1]] = isPlaying;
			values.add(minValue(b));
		}
		int i = 0;
		int point = 0;
		int max = values.get(0);
		for (int number : values){
			if (number > max){
				max = number;
				point = i;
			}
			i++;
		}

		return sets[point][0] * 8 + sets[point][1];
	}
}
