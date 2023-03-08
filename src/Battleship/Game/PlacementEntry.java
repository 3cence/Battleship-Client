package Battleship.Game;

public record PlacementEntry(ShipType type, int x, int y, boolean isVertical) {
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PlacementEntry)
            return ((PlacementEntry)obj).type() == type();
        return false;
    }
}
