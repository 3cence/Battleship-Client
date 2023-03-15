package Battleship.UI.GameElements;

import javax.swing.*;
import java.awt.*;

public class BannerPanel extends JPanel {
    public BannerPanel() {
        super();
    }
    @Override
    protected void paintChildren(Graphics g) {
        super.paintChildren(g);
//        g.setColor(Color.pink);
//        g.fillRect(10, 10, 1600, 400);
    }
}
