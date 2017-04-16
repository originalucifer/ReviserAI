package Games.Models.Boards.Othello;

/**
 * Created by rik on 4/16/17.
 */
public class OthelloBoardFunctions {
    public static int getTopLeftNeighbour(int root){
    if (getCol(root) == 0 || getRow(root) == 0) return -1;
    return root -9;
}

    public static int getTopRightNeighbour(int root){
        if (getCol(root) == 7 || getRow(root) == 0) return -1;
        return root -7;
    }

    public static int getTopNeighbour(int root){
        if (getRow(root) == 0) return -1;
        return root -8;
    }

    public static int getLeftNeighbour(int root){
        if (getCol(root) == 0) return -1;
        return root -1;
    }

    public static int getRightNeighbour(int root){
        if (getCol(root) == 7) return -1;
        return root + 1;
    }

    public static int getBottomLeftNeighbour(int root){
        if (getCol(root) == 0 || getRow(root) == 7) return -1;
        return root + 7;
    }

    public static int getBottomNeighbour(int root){
        if (getCol(root) == 0 || getRow(root) == 7) return -1;
        return root + 7;
    }

    public static int getBottomRightNeighbour(int root){
        if (getCol(root) == 0 || getRow(root) == 7) return -1;
        return root + 7;
    }

    public static int searchForPlayerTopLeft(char[] board, char player, int base){
        int i = base;
        while (true){
            i = getTopLeftNeighbour(i);
            if (i == -1) return -1;
            if (board[i] == player) return i;
        }
    }

    public static int searchForPlayerTop(char[] board, char player, int base){
        int i = base;
        while (true){
            i = getTopNeighbour(i);
            if (i == -1) return -1;
            if (board[i] == player) return i;
        }
    }

    public static int searchForPlayerTopRight(char[] board, char player, int base){
        int i = base;
        while (true){
            i = getTopRightNeighbour(i);
            if (i == -1) return -1;
            if (board[i] == player) return i;
        }
    }

    public static int searchForPlayerLeft(char[] board, char player, int base){
        int i = base;
        while (true){
            i = getLeftNeighbour(i);
            if (i == -1) return -1;
            if (board[i] == player) return i;
        }
    }

    public static int getRow(int index){
        return index / 8;
    }

    public static int getCol(int index){
        return index % 8;
    }

    public static int getIndex(int column, int row){
        return row * 8 + column;
    }
}
