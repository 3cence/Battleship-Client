package Battleship.UI.GameElements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TileButton extends JButton {
    private Color color;
    private volatile boolean mouseOver = false;
    public TileButton(Color c) {
        super();
        setTileColor(c);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                mouseOver = true;
//                System.out.println("mouse enter");
                repaint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                mouseOver = false;
//                System.out.println("mouse exit");
                repaint();
            }
        });
        repaint();
    }
    public void setTileColor(Color c) {
        color = c;
    }
    @Override
    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
        g.setColor(color);
        Rectangle b = getBounds();
        g.fillRect(0,0, b.width, b.height);
//        System.out.printf("X: %d, Y: %d, Width:%d, Height: %d, %b\n", b.x, b.y, b.width, b.height, drawCross);
        if (mouseOver) {
            ((Graphics2D)g).setStroke(new BasicStroke(5));
            g.setColor(Color.RED);
            g.draw
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
