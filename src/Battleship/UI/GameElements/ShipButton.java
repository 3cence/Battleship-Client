package Battleship.UI.GameElements;

import Battleship.Game.ShipType;

import javax.swing.*;
import java.awt.*;

public class ShipButton extends JButton {
    private ShipType shipType;
    public ShipButton(ShipType type) {
        shipType = type;
        setBackground(new Color(61,143,187));
        setText(type.type() + ": " + type.holes() + " Holes");
    }
    public ShipType getShipType() {
        return shipType;
    }
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0,0,getWidth(),getHeight());
        if (isEnabled())
            g.setColor(Color.GREEN);
        else
            g.setColor(getForeground());
        FontMetrics fm = getFontMetrics(g.getFont());
        g.setFont(new Font("Copperplate Gothic Bold", Font.BOLD,20));
        g.drawString(getText(), (getWidth()/2) - (int)(fm.stringWidth(getText())),(getHeight() + fm.getHeight())/2);
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
