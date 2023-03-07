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
    private JLabel roomInfoLbl, opponentShipsLeftLbl, yourShipsLeftLbl, boardSpacerLabel;
    private JButton carrierBtn, battleshipBtn, cruiserBtn, submarineBtn, destroyerBtn;
    private BoardDisplay yourBoard, targetBoard;

    public GameScreen() {
        gameScreen = new JPanel();
        gameScreen.setForeground(new Color(61,143,187));
        gameScreen.setBackground(new Color(61,143,187));
        gameScreen.setLayout(new GridBagLayout());
        roomInfoLbl = new JLabel();
        boardSpacerLabel = new JLabel();
        Font f = new Font("Copperplate Gothic Bold", Font.BOLD,30);
        opponentShipsLeftLbl = new JLabel("Guest: 5/5");
        opponentShipsLeftLbl.setFont(f);
        opponentShipsLeftLbl.setHorizontalAlignment(SwingConstants.CENTER);
        yourShipsLeftLbl = new JLabel("You: 5/5");
        yourShipsLeftLbl.setFont(f);
        yourShipsLeftLbl.setHorizontalAlignment(SwingConstants.CENTER);
        carrierBtn = new JButton();
        battleshipBtn = new JButton();
        cruiserBtn = new JButton();
        submarineBtn = new JButton();
        destroyerBtn = new JButton();
        yourBoard = new BoardDisplay(new Board());
        targetBoard = new BoardDisplay(new Board());
        gameScreen.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setShipLabelPolicy();
                super.componentResized(e);
            }
        });
        setupScreen();
        setShipLabelPolicy();
    }
    private void setShipLabelPolicy() {
        int minx = (int) (MainWindow.getMainWindow().getWidth() * .4);
        int miny = (int) (MainWindow.getMainWindow().getHeight() * .05);
        yourShipsLeftLbl.setMaximumSize(new Dimension(minx, miny));
        opponentShipsLeftLbl.setMaximumSize(new Dimension(minx, miny));
        yourShipsLeftLbl.setMinimumSize(new Dimension(minx, miny));
        opponentShipsLeftLbl.setMinimumSize(new Dimension(minx, miny));
        yourShipsLeftLbl.setPreferredSize(new Dimension(minx, miny));
        opponentShipsLeftLbl.setPreferredSize(new Dimension(minx, miny));

        minx = (int) (MainWindow.getMainWindow().getWidth() * .2);
        miny = (int) (MainWindow.getMainWindow().getHeight() * .05);
        roomInfoLbl.setMaximumSize(new Dimension(minx, miny));
        roomInfoLbl.setMinimumSize(new Dimension(minx, miny));
        roomInfoLbl.setPreferredSize(new Dimension(minx, miny));

        minx = (int) (MainWindow.getMainWindow().getWidth() * .01);
        miny = roomInfoLbl.getHeight();
        boardSpacerLabel.setMaximumSize(new Dimension(minx, miny));
        boardSpacerLabel.setMinimumSize(new Dimension(minx, miny));
        boardSpacerLabel.setPreferredSize(new Dimension(minx, miny));
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
        GridBagConstraints gbc = getConstraints(x, y, 1, 1, weightx, weighty);
        gbc.fill = GridBagConstraints.NONE;
        return gbc;
    }
    private void setupScreen() {
        gameScreen.add(opponentShipsLeftLbl, getConstraints(0, 0,.4,.1));
        gameScreen.add(yourShipsLeftLbl, getConstraints(2, 0,.4,.1));
        gameScreen.add(roomInfoLbl, getConstraints(3, 0,.2,.1));

        double bwx = .6, bwy = .6;
        GridBagConstraints gbc = getConstraints(0, 1, 1, 5, bwx, bwy);
        gameScreen.add(targetBoard, gbc);
        gameScreen.add(boardSpacerLabel, getConstraints(1,1,1,5,.05,.05));
        gbc = getConstraints(2, 1, 1, 5, bwx, bwy);
        gameScreen.add(yourBoard, gbc);

        double wx = .2, wy = .2;
        gameScreen.add(carrierBtn, getConstraints(3,1,1,1, wx, wy));
        gameScreen.add(battleshipBtn, getConstraints(3,2,1,1, wx, wy));
        gameScreen.add(cruiserBtn, getConstraints(3,3,1,1, wx, wy));
        gameScreen.add(submarineBtn, getConstraints(3,4,1,1, wx, wy));
        gameScreen.add(destroyerBtn, getConstraints(3,5,1,1, wx, wy));
    }
    public JPanel getGameScreen() {
        return gameScreen;
    }
    public void processPacket(List<PacketData> pd) {
        StringBuilder s = new StringBuilder();
        for (PacketData p: pd) {
            s.append(p.type()).append(": ").append(p.data()).append(", \n");
        }
//        roomInfoLbl.setText(s.toString());
    }
}
