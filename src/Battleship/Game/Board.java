package Battleship.Game;

import java.awt.*;

public class Board {
    private Tile[][] board;

    public Board() {
        board = new Tile[10][10];
        Color c1 = new Color(61,143,187);
        Color c2 = new Color(51,114,187);
        Color color = c1;
        for (Tile[] row: board) {
            for (int i = 0; i < row.length; i++) {
                row[i] = new Tile(color);
                color = (color == c1) ? c2 : c1;
            }
            color = (color == c1) ? c2 : c1;
        }
    }
    public Tile[][] getBoard() {
        return board;
    }
}
