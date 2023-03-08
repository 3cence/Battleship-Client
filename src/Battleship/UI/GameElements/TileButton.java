package Battleship.UI.GameElements;

import Battleship.Game.ShipType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TileButton extends JButton {
    private Color color;
    private volatile boolean mouseOver = false, isShip = false, isVertical;
    private ShipType shipType;

    public TileButton(Color c) {
        super();
        setTileColor(c);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                mouseOver = true;
//                System.out.println("mouse enter");
//                System.out.println(getWidth()+" "+getHeight());
                getParent().repaint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                mouseOver = false;
//                System.out.println("mouse exit");
                getParent().repaint();
            }
        });
        repaint();
    }
    public void setTileColor(Color c) {
        color = c;
    }
    public void showShip(ShipType s, boolean isVertical) {
        this.isVertical = isVertical;
        this.isShip = true;
        shipType = s;
        repaint();
    }
    public void hideShip() {
        isShip = false;
        shipType = null;
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
        g.setColor(color);
        Rectangle b = getBounds();
        int offset = 0;
        g.fillRect(offset,offset, b.width + offset, b.height + offset);
//        System.out.printf("X: %d, Y: %d, Width:%d, Height: %d, %b\n", b.x, b.y, b.width, b.height, drawCross);
        if (isShip) {
            g.setColor(Color.BLACK);
            if (isVertical)
                g.fillRect(10 + offset, offset, b.width - 20 + offset, b.height + offset);
            else
                g.fillRect(offset, 10 + offset, b.width + offset, b.height - 20 + offset);
        }
        if (mouseOver) {
            int stroke = 3;
            ((Graphics2D)g).setStroke(new BasicStroke(stroke));
            g.setColor(Color.RED);
            g.drawOval(stroke + offset, stroke + offset, b.width - stroke*2 + offset, b.height - stroke*2 + offset);
            g.drawLine(b.width / 5 + offset, b.height / 5 + offset, b.width - (b.width / 5) + offset, b.height - (b.height / 5) + offset);
            g.drawLine(b.width - (b.width / 5) + offset, b.height / 5 + offset, b.width / 5 + offset, b.height - (b.height / 5) + offset);
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
