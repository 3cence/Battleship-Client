package Battleship.Game;

import Battleship.UI.GameElements.TileButton;

import java.awt.*;

public class Tile {
    private TileButton button;
    private ShipType shipType;
    private boolean isVertical;

    public Tile(Color c) {
        button = new TileButton(c);
    }
    public TileButton getButton() {
        return button;
    }
    public boolean isShip() {
        return shipType != null;
    }
    public void makeShip(ShipType s, boolean isVertical) {
        shipType = s;
        this.isVertical = isVertical;
        button.showShip(s, isVertical);
    }
    public void unship() {
        shipType = null;
        button.hideShip();
    }
}
