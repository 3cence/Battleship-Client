package Battleship.Game;

import Battleship.UI.GameElements.TileButton;
import Battleship.UI.GameScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Tile {
    private TileButton button;
    private ShipType shipType;
    private Board parentBoard;
    private int x, y;
    private int shipX, shipY;
    private boolean isVertical, isProjection;

    public Tile(Board parent, int x, int y, Color c) {
        this.x = x;
        this.y = y;
        parentBoard = parent;
        button = new TileButton(c);
        isProjection = true;
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("Clilcky clicky click " + isProjection);
                if (SwingUtilities.isRightMouseButton(e)) {
                    parentBoard.removeShip(x, y, true);
                    parentBoard.toggleRotation();
                    parentBoard.putShip(parentBoard.getSelectedType(), x, y, true);
                }
                else if (SwingUtilities.isLeftMouseButton(e)) {
                    parentBoard.removeShip(x, y, true);
                    if (parentBoard.getBoard()[y][x].isProjection() && !isShip()) {
                        System.out.println("Putting");
                        parentBoard.putShip(parentBoard.getSelectedType(), x, y, false);
                    }
                    else {
                        System.out.println("Removing");
                        parentBoard.removeShip(x, y, false);
                    }
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                parentBoard.putShip(parentBoard.getSelectedType(), x, y, true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                parentBoard.removeShip(x, y, true);
            }
        });
    }
    public TileButton getButton() {
        return button;
    }
    public ShipType getShipType() {
        return shipType;
    }
    public boolean isShip() {
        return shipType != null;
    }
    public boolean isShip(int x, int y) {
        return shipType != null && shipX == x && shipY == y;
    }
    public boolean isProjection() {
        return isProjection;
    }
    public boolean isVertical() {
        return isVertical;
    }
    public void makeShip(ShipType s, int x, int y, boolean isVertical, boolean isProjection) {
        shipType = s;
        shipX = x;
        shipY = y;
        this.isVertical = isVertical;
        this.isProjection = isProjection;
        button.showShip(s, isVertical, isProjection);
    }
    public void unship() {
        shipType = null;
        button.hideShip();
        isProjection = true;
    }
}
