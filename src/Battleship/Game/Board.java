package Battleship.Game;

import EmNet.Event;

import java.awt.*;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class Board {
    public static int INACTIVE = 0;
    public static int TARGET_ACTIVE = 1;
    public static int PLAYER_PLACEMENT = 2;

    private Tile[][] board;
    private int boardState;
    private ShipType selectedType;
    private Event<ShipType> colorLink;
    /**
     * true = verticle
     */
    private boolean placementMode = true;
    private record PlacementEntry(ShipType type, int x, int y) {
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof PlacementEntry)
                return ((PlacementEntry)obj).type() == type();
            return false;
        }
    }
    private ArrayList<PlacementEntry> shipPlacements;
    public Board() {
        board = new Tile[10][10];
        shipPlacements = new ArrayList<>();
        Color c1 = new Color(61, 143, 187);
        Color c2 = new Color(51, 114, 187);
        Color color = c1;
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                board[y][x] = new Tile(this, x, y, color);
                color = (color == c1) ? c2 : c1;
            }
            color = (color == c1) ? c2 : c1;
        }
    }
    public Tile[][] getBoard() {
        return board;
    }
    public void setMode(int m) {
        boardState = m;
    }
    public void setColorLink(Event<ShipType> s) {
        colorLink = s;
    }
    public void setSelectedType(ShipType s) {
        selectedType = s;
    }
    public ShipType getSelectedType() {
        return selectedType;
    }
    public ArrayList<ShipType> getShipPlacements() {
        ArrayList<ShipType> list = new ArrayList<>();
        for (PlacementEntry p: shipPlacements) {
            list.add(p.type);
        }
        return list;
    }
    public void toggleRotation() {
        placementMode = !placementMode;
    }
    public void putShip(ShipType type, int x, int y, boolean isProjection) {
        PlacementEntry placementEntry = new PlacementEntry(type, x, y);
        if (boardState == Board.PLAYER_PLACEMENT && selectedType != null && !shipPlacements.contains(placementEntry)) {
            System.out.println("Starting placement");
            if (placementMode && y + type.holes() > 10)
                return;
            else if (!placementMode && x + type.holes() > 10) {
                return;
            }
            int tx = x, ty = y;
            for (int i = 0; i < type.holes(); i++) {
                if ((isProjection && board[ty][tx].isShip()) || (!isProjection && !board[ty][tx].isProjection())) {
                    return;
                }
                if (placementMode)
                    ty++;
                else
                    tx++;
            }
            tx = x;
            ty = y;
            for (int i = 0; i < type.holes(); i++) {
                board[ty][tx].makeShip(type, x, y, placementMode, isProjection);
                if (placementMode)
                    ty++;
                else
                    tx++;
            }
            if (!isProjection) {
                System.out.println("Non projection placement");
                shipPlacements.add(placementEntry);
                colorLink.trigger(type);
            }
        }
//        else {
//            System.out.printf("%b, %b, %b\n", boardState == Board.PLAYER_PLACEMENT, selectedType != null, !shipPlacements.contains(placementEntry));
//        }
    }
    public void removeShip(int x, int y, boolean isProjection) {
        if (!board[y][x].isShip(x, y))
            return;
        placementMode = board[y][x].isVertical();
        ShipType type = board[y][x].getShipType();
        int tx = x, ty = y;
        for (int i = 0; i < type.holes(); i++) {
            if (board[ty][tx].isShip(x, y) && board[ty][tx].isProjection() == isProjection)
                board[ty][tx].unship();
            if (placementMode)
                ty++;
            else
                tx++;
        }
        if (!isProjection) {
            System.out.println("REMOVING");
            shipPlacements.remove(new PlacementEntry(type, x, y));
            colorLink.trigger(type);
        }
    }
}
