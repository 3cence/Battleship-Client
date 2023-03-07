package Battleship.UI;

import Battleship.Game.Board;
import Battleship.UI.GameElements.BoardDisplay;
import Network.PacketData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

public class GameScreen {
    private JPanel gameScreen;
    private JLabel roomInfo, opponentShipsLeft, yourShipsLeft;
    private JButton carrierBtn, battleshipBtn, cruiserBtn, submarineBtn, destroyerBtn;
    private BoardDisplay yourBoard, targetBoard;

    public GameScreen() {
        gameScreen = new JPanel();
        roomInfo = new JLabel();
        gameScreen.setForeground(new Color(61,143,187));
        gameScreen.setBackground(new Color(61,143,187));
        gameScreen.setLayout(new GridBagLayout());
        opponentShipsLeft = new JLabel();
        yourShipsLeft = new JLabel();
        carrierBtn = new JButton();
        battleshipBtn = new JButton();
        cruiserBtn = new JButton();
        submarineBtn = new JButton();
        destroyerBtn = new JButton();
        yourBoard = new BoardDisplay(new Board());
        targetBoard = new BoardDisplay(new Board());
        setupScreen();
    }
    private GridBagConstraints getConstraints(int x, int y, int w, int h, double weightx, double weighty) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridheight = h;
        gbc.gridwidth = w;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.fill = GridBagConstraints.BOTH;
        return gbc;
    }
    private GridBagConstraints getConstraints(int x, int y, double weightx, double weighty) {
        return getConstraints(x, y, 1, 1, weightx, weighty);
    }
    private void setupScreen() {
        gameScreen.add(opponentShipsLeft, getConstraints(0, 0,.4,.1));
        gameScreen.add(yourShipsLeft, getConstraints(1, 0,.4,.1));
        gameScreen.add(new JLabel(), getConstraints(2, 0,.2,.1));

        double bwx = 1, bwy = 1;
        GridBagConstraints gbc = getConstraints(0, 1, 1, 5, bwx, bwy);
//        gbc.fill = GridBagConstraints.HORIZONTAL;
        gameScreen.add(targetBoard, gbc);
        gbc = getConstraints(1, 1, 1, 5, bwx, bwy);
//        gbc.fill = GridBagConstraints.HORIZONTAL;
        gameScreen.add(yourBoard, gbc);

        double wx = .2, wy = .2;
        gameScreen.add(carrierBtn, getConstraints(2,1,1,1, wx, wy));
        gameScreen.add(battleshipBtn, getConstraints(2,2,1,1, wx, wy));
        gameScreen.add(cruiserBtn, getConstraints(2,3,1,1, wx, wy));
        gameScreen.add(submarineBtn, getConstraints(2,4,1,1, wx, wy));
        gameScreen.add(destroyerBtn, getConstraints(2,5,1,1, wx, wy));
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
