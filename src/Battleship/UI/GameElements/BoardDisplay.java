package Battleship.UI.GameElements;

import Battleship.Game.Board;
import Battleship.Game.ShipType;
import Battleship.Game.Tile;
import EmNet.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardDisplay extends JPanel {
    private Board board;
    public BoardDisplay(Board board) {
        super();
        setLayout(new GridLayout(10, 10));
        setSize(new Dimension(100, 100));
        setPreferredSize(new Dimension(100, 100));
        setForeground(new Color(61,143,187));
        setBackground(new Color(61,143,187));
        this.board = board;
        for (Tile[] tl: board.getBoard()) {
            for (Tile t: tl)
                add(t.getButton());
        }
    }
    @Override
    protected void paintChildren(Graphics g) {
        super.paintChildren(g);
        g.setColor(Color.blue);
        ((Graphics2D)g).setStroke(new BasicStroke(5));
        g.drawRect(0,0,getWidth(),getHeight());
    }

    public Board getBoard() {
        return board;
    }
    public void setMode(int m) {
        board.setMode(m);
    }
    public void setColorLink(Event<ShipType> e) {
        board.setColorLink(e);
    }
}
