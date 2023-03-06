package Battleship.UI;

import Network.PacketData;

import javax.swing.*;
import java.util.List;

public class GameScreen {
    private JPanel gameScreen;
    private JLabel roomInfo;

    public GameScreen() {

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
