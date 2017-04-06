package Games.Models.Boards;

import java.util.Arrays;

/**
 * Created by rik on 3/31/17.
 */
public abstract class Game implements InGameActions {

    private int boardSize;
    char[][] board;
    private String player;
    private int[] playerMove;

	public Game(int boardSize){
	    this.boardSize = boardSize;
        board = new char[boardSize][boardSize];
    }

    @Override
    public void win() {
    }

    @Override
    public void loss() {
    }

    @Override
    public void draw() {
        System.out.println("Its a draw");
    }

    @Override
    public void yourTurn() {
//        int[] move = new int[2];
        switch (player){
            case "AIPlayer": {
//                move = getAIMove();
                System.out.println("AI should make a move");
                break;
            }
            case "GUIPlayer": {
                System.out.println("GuiPlayer should make a move");
                getGuiMove();
                break;
            }
        }
    }

    public void matchStart(){}

    /**
     * gets move from user. should be overridden in subclass
     * @return array with move
     */
    abstract void getGuiMove();

    /**
     * Sets the type of the player AI or GUI
     * @param playerType
     */
    public void setPlayerType(String playerType){
        this.player = playerType;
    }

    /**
     * should be overridden in subclasses
     */
    abstract void updateBoard(String move);

    @Override
    public void move(String move) {
        updateBoard(move);
    }

    /**
     * for debugging
     * TODO remove this method
     */
    public void showBoard() {
        for (char[] chars : board) {
            for (char c : chars) {
                System.out.print(c);
            }
            System.out.print("\n");
        }
    }
}
