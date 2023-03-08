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
        button.addActionListener(e -> {
            if (isShip())
                parentBoard.removeShip(parentBoard.getSelectedType(), x, y, false);
            else
                parentBoard.putShip(parentBoard.getSelectedType(), x, y, false);
        });
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (SwingUtilities.isRightMouseButton(e)) {
                    parentBoard.removeShip(parentBoard.getSelectedType(), x, y, true);
                    parentBoard.toggleRotation();
                    parentBoard.putShip(parentBoard.getSelectedType(), x, y, true);
                }
                else if (SwingUtilities.isLeftMouseButton(e)) {
                    parentBoard.putShip(parentBoard.getSelectedType(), x, y, false);
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                parentBoard.putShip(parentBoard.getSelectedType(), x, y, true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                parentBoard.removeShip(parentBoard.getSelectedType(), x, y, true);
            }
        });
    }
    public TileButton getButton() {
        return button;
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
    public void makeShip(ShipType s, int x, int y, boolean isVertical, boolean isProjection) {
        shipType = s;
        shipX = x;
        shipY = y;
        this.isVertical = isVertical;
        this.isProjection = isProjection;
        button.showShip(s, isVertical);
    }
    public void unship() {
        shipType = null;
        button.hideShip();
    }
}
