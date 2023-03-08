package Battleship.Game;

import java.awt.*;

public class Board {
    public static int INACTIVE = 0;
    public static int TARGET_ACTIVE = 1;
    public static int PLAYER_PLACEMENT = 2;

    private Tile[][] board;
    private int boardState;
    /**
     * true = verticle
     */
    private boolean placementMode = true;
    public Board() {
        board = new Tile[10][10];
        Color c1 = new Color(61, 143, 187);
        Color c2 = new Color(51, 114, 187);
        Color color = c1;
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                board[y][x] = new Tile(x, y, color);
                color = (color == c1) ? c2 : c1;
            }
            color = (color == c1) ? c2 : c1;
        }
    }
    public Tile[][] getBoard() {
        return board;
    }
    public void putShip(ShipType type, int x, int y) {
        int tx = x, ty = y;
        for (int i = 0; i < type.holes(); i++) {
            board[ty][tx].makeShip(type, x, y, placementMode);
            if (placementMode)
                ty++;
            else
                x++;
        }
    }
}
