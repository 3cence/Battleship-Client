package Battleship.UI.GameElements;

import Battleship.Game.Board;
import Battleship.Game.ShipType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TileButton extends JButton {
    public static final int ICON_NONE = 0;
    public static final int ICON_MISS = 1;
    public static final int ICON_HIT = 2;
    private Color color;
    private volatile boolean mouseOver = false, isShip = false, isVertical, isProjection;
    private ShipType shipType;
    private Board parentBoard;
    private int icon;

    public TileButton(Board parentBoard, Color c) {
        super();
        this.parentBoard = parentBoard;
        icon = ICON_NONE;
        setTileColor(c);
        isProjection = true;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                mouseOver = true;
                getParent().repaint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                mouseOver = false;
                getParent().repaint();
            }
        });
        repaint();
    }
    public void setTileColor(Color c) {
        color = c;
    }
    public void showShip(ShipType s, boolean isVertical, boolean isProjection) {
        this.isVertical = isVertical;
        this.isShip = true;
        this.isProjection = isProjection;
        shipType = s;
        getParent().repaint();
    }
    public void hideShip() {
        isShip = false;
        shipType = null;
        isProjection = true;
        getParent().repaint();
    }
    public void setTileIcon(int i) {
        icon = i;
    }
    public int getTileIcon() {
        return icon;
    }
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(color);
        Rectangle b = getBounds();
        int offset = 0;
        g.fillRect(offset,offset, b.width + offset, b.height + offset);
        if (isShip) {
            g.setColor(Color.BLACK);
            if (isVertical)
                g.fillRect(10 + offset, offset, b.width - 20 + offset, b.height + offset);
            else
                g.fillRect(offset, 10 + offset, b.width + offset, b.height - 20 + offset);
        }
        if (icon == TileButton.ICON_HIT) {
            g.setColor(Color.red);
            ((Graphics2D)g).setStroke(new BasicStroke(8));
            g.drawLine(b.width / 5 + offset, b.height / 5 + offset, b.width - (b.width / 5) + offset, b.height - (b.height / 5) + offset);
            g.drawLine(b.width - (b.width / 5) + offset, b.height / 5 + offset, b.width / 5 + offset, b.height - (b.height / 5) + offset);
        }
        if (icon == TileButton.ICON_MISS) {
            int stroke = 8;
            g.setColor(Color.white);
            ((Graphics2D)g).setStroke(new BasicStroke(stroke));
            g.drawOval(stroke + offset, stroke + offset, b.width - stroke * 2 + offset, b.height - stroke * 2 + offset);
        }
        if (mouseOver) {
            if (parentBoard.getMode() == Board.TARGET_ACTIVE) {
                int stroke = 3;
                ((Graphics2D) g).setStroke(new BasicStroke(stroke));
                g.setColor(Color.RED);
                g.drawOval(stroke + offset, stroke + offset, b.width - stroke * 2 + offset, b.height - stroke * 2 + offset);
                g.drawLine(b.width / 5 + offset, b.height / 5 + offset, b.width - (b.width / 5) + offset, b.height - (b.height / 5) + offset);
                g.drawLine(b.width - (b.width / 5) + offset, b.height / 5 + offset, b.width / 5 + offset, b.height - (b.height / 5) + offset);
            }
        }
    }
    @Override
    protected void paintBorder(Graphics g) {
//        super.paintBorder(g);
    }
    @Override
    protected void paintChildren(Graphics g) {
//        super.paintChildren(g);
    }
}
