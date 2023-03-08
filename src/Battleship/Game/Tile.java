package Battleship.Game;

import Battleship.UI.GameElements.TileButton;
import Battleship.UI.GameScreen;

import java.awt.*;

public class Tile {
    private TileButton button;
    private ShipType shipType;
    private Board parentBoard;
    private int x, y;
    private int shipX, shipY;
    private boolean isVertical;

    public Tile(int x, int y, Color c) {
        this.x = x;
        this.y = y;
        button = new TileButton(c);
//        button.addActionListener(e -> parentBoard.putShip(GameScreen.getSelectedType(), x, y));
    }
    public TileButton getButton() {
        return button;
    }
    public boolean isShip() {
        return shipType != null;
    }
    public void makeShip(ShipType s, int x, int y, boolean isVertical) {
        shipType = s;
        shipX = x;
        shipY = y;
        this.isVertical = isVertical;
        button.showShip(s, isVertical);
    }
    public void unship() {
        shipType = null;
        button.hideShip();
    }
}
