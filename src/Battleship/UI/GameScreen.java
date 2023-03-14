package Battleship.UI;

import Battleship.Game.Board;
import Battleship.Game.Main;
import Battleship.Game.PlacementEntry;
import Battleship.Game.ShipType;
import Battleship.UI.GameElements.BannerPanel;
import Battleship.UI.GameElements.BoardDisplay;
import Battleship.UI.GameElements.ShipButton;
import Battleship.UI.GameElements.TileButton;
import Network.NetworkHandler;
import Network.PacketData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameScreen {
    private BannerPanel gameScreen;
    private JLabel roomInfoLbl, opponentShipsLeftLbl, yourShipsLeftLbl, boardSpacerLabel;
    private ShipButton carrierBtn, battleshipBtn, cruiserBtn, submarineBtn, destroyerBtn;
    private BoardDisplay yourBoard, targetBoard;
    private boolean isDonePlacing, gameOver;
    private String gameOverState;
    private String opponentName;

    public GameScreen() {
        gameScreen = new BannerPanel();
        gameScreen.setForeground(new Color(61,143,187));
        gameScreen.setBackground(new Color(61,143,187));
        gameScreen.setLayout(new GridBagLayout());
        roomInfoLbl = new JLabel();
        boardSpacerLabel = new JLabel();
        opponentName = "Opponent";
        Font f = new Font("Copperplate Gothic Bold", Font.BOLD,30);
        opponentShipsLeftLbl = new JLabel("Guest: 5/5");
        opponentShipsLeftLbl.setFont(f);
        opponentShipsLeftLbl.setHorizontalAlignment(SwingConstants.CENTER);
        yourShipsLeftLbl = new JLabel("You: 5/5");
        yourShipsLeftLbl.setFont(f);
        yourShipsLeftLbl.setHorizontalAlignment(SwingConstants.CENTER);
        carrierBtn = new ShipButton(ShipType.CARRIER);
        carrierBtn.addActionListener(e -> yourBoard.getBoard().setSelectedType(carrierBtn.getShipType()));
        battleshipBtn = new ShipButton(ShipType.BATTLESHIP);
        battleshipBtn.addActionListener(e -> yourBoard.getBoard().setSelectedType(battleshipBtn.getShipType()));
        cruiserBtn = new ShipButton(ShipType.CRUISER);
        cruiserBtn.addActionListener(e -> yourBoard.getBoard().setSelectedType(cruiserBtn.getShipType()));
        submarineBtn = new ShipButton(ShipType.SUBMARINE);
        submarineBtn.addActionListener(e -> yourBoard.getBoard().setSelectedType(submarineBtn.getShipType()));
        destroyerBtn = new ShipButton(ShipType.DESTROYER);
        destroyerBtn.addActionListener(e -> yourBoard.getBoard().setSelectedType(destroyerBtn.getShipType()));
        yourBoard = new BoardDisplay(new Board());
        yourBoard.setMode(Board.PLAYER_PLACEMENT);
        targetBoard = new BoardDisplay(new Board());
        targetBoard.setMode(Board.INACTIVE);
        yourBoard.setColorLink(type -> {
//            for (PlacementEntry s: yourBoard.getBoard().getShipPlacements())
//                System.out.println(s.type());
            if (yourBoard.getBoard().getShipPlacements().size() == 5)
                isDonePlacing = true;
            if (type.equals(ShipType.CARRIER))
                carrierBtn.setEnabled(!carrierBtn.isEnabled());
            if (type.equals(ShipType.BATTLESHIP))
                battleshipBtn.setEnabled(!battleshipBtn.isEnabled());
            if (type.equals(ShipType.CRUISER))
                cruiserBtn.setEnabled(!cruiserBtn.isEnabled());
            if (type.equals(ShipType.SUBMARINE))
                submarineBtn.setEnabled(!submarineBtn.isEnabled());
            if (type.equals(ShipType.DESTROYER))
                destroyerBtn.setEnabled(!destroyerBtn.isEnabled());
        });
        gameScreen.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setShipLabelPolicy();
                super.componentResized(e);
            }
        });
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER && e.getID() == KeyEvent.KEY_RELEASED) {
                if (yourBoard.getBoard().getShipPlacements().size() == 5)
                    isDonePlacing = true;
                if (isDonePlacing) {
                    ArrayList<PacketData> packet = new ArrayList<>();
                    packet.add(new PacketData("ship_placement","5"));
                    for (PlacementEntry p: yourBoard.getBoard().getShipPlacements()) {
                        packet.add(new PacketData(p.type().type(), p.x() + "," + p.y() + "," + p.isVertical()));
                    }
                    Main.getClient().sendPacket(NetworkHandler.generatePacketData(packet));
                    yourBoard.getBoard().setMode(Board.INACTIVE);
                }
                if (gameOver) {
                    MainWindow.getMainWindow().setScreen(MainWindow.WELCOME_SCREEN);
                    ((WelcomeScreen)MainWindow.getMainWindow().getScreen(MainWindow.WELCOME_SCREEN)).setWelcomeText("You " + gameOverState + "!");
                    gameOver = false;
                }
            }
            // Return false to allow the event to be dispatched to its intended recipient
            return false;
        }});
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
    private void opponentAttacked(List<PacketData> pd) {
        String[] data = pd.get(0).data().split(",");
        int x = Integer.parseInt(data[0]);
        int y = Integer.parseInt(data[1]);
        String hitmiss = data[2];
        int shipsLeft = Integer.parseInt(data[3]);
        if (hitmiss.equals("hit"))
            yourBoard.getBoard().getBoard()[y][x].getButton().setTileIcon(TileButton.ICON_HIT);
        else
            yourBoard.getBoard().getBoard()[y][x].getButton().setTileIcon(TileButton.ICON_MISS);
        yourShipsLeftLbl.setText("You: " + shipsLeft + "/5");
        gameScreen.repaint();
    }
    private void attackResults(List<PacketData> pd) {
        String[] data = pd.get(0).data().split(",");
        int x = Integer.parseInt(data[0]);
        int y = Integer.parseInt(data[1]);
        String hitmiss = data[2];
        int shipsLeft = Integer.parseInt(data[3]);
        if (hitmiss.equals("hit"))
            targetBoard.getBoard().getBoard()[y][x].getButton().setTileIcon(TileButton.ICON_HIT);
        else
            targetBoard.getBoard().getBoard()[y][x].getButton().setTileIcon(TileButton.ICON_MISS);
        opponentShipsLeftLbl.setText(opponentName + ": " + shipsLeft + "/5");
        gameScreen.repaint();
    }
    public void processPacket(List<PacketData> pd) {
        switch (pd.get(0).type()) {
            case "opponent_attacked":
                opponentAttacked(pd);
                break;
            case "attack_results":
                attackResults(pd);
                break;
            case "your_turn", "invalid_attack":
                targetBoard.setMode(Board.TARGET_ACTIVE);
                System.out.println("Your Turn!");
                break;
            case "game_start":
                opponentName = pd.get(0).data();
                break;
            case "game_over":
                gameOver = true;
                gameOverState = pd.get(0).data();
                targetBoard.setMode(Board.INACTIVE);
                break;
        }
//        roomInfoLbl.setText(s.toString());
    }
}
