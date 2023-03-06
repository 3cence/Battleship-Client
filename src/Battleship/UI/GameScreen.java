package Battleship.UI;

import Battleship.UI.GameElements.TileButton;
import Network.PacketData;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameScreen {
    private JPanel gameScreen;
    private JLabel roomInfo;

    public GameScreen() {
        gameScreen = new JPanel();
        roomInfo = new JLabel();
        gameScreen.setForeground(new Color(61,143,187));
        gameScreen.setBackground(new Color(61,143,187));
        gameScreen.setLayout(new GridLayout(10, 10));
        for (int i = 0; i < 50; i++) {
            gameScreen.add(new TileButton(Color.BLUE));
            gameScreen.add(new TileButton(Color.BLUE));
        }
    }
    public JPanel getGameScreen() {
        return gameScreen;
    }
    public void processPacket(List<PacketData> pd) {
        StringBuilder s = new StringBuilder();
        for (PacketData p: pd) {
            s.append(p.type()).append(": ").append(p.data()).append(", \n");
        }
        roomInfo.setText(s.toString());
    }
}
