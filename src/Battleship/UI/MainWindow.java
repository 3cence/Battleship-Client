package Battleship.UI;

import Battleship.Game.Main;
import EmNet.Event;
import EmNet.Packet;
import Network.NetworkHandler;
import Network.PacketData;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

public class MainWindow extends JFrame {
    public final static int WELCOME_SCREEN = 0;
    public final static int ROOM_SELECT = 1;
    public final static int GAME_SCREEN = 2;
    private static MainWindow kMainWindow;
    private WelcomeScreen welcomeScreen;
    private RoomSelection roomSelection;
    private GameScreen gameScreen;
    public static MainWindow getMainWindow() {
        if (kMainWindow == null) {
            kMainWindow = new MainWindow();
            kMainWindow.init();
        }
        return kMainWindow;
    }
    private MainWindow() {
        super();
    }
    private void init() {
        welcomeScreen = new WelcomeScreen();
        roomSelection = new RoomSelection();
        gameScreen = new GameScreen();
        setTitle("Battleship Client");
        setContentPane(welcomeScreen.getWelcomeScreen());
        setSize(welcomeScreen.getWelcomeScreen().getPreferredSize());
        setPreferredSize(welcomeScreen.getWelcomeScreen().getPreferredSize());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public void setScreen(int screen) {
        switch (screen) {
            case MainWindow.WELCOME_SCREEN:
                setContentPane(welcomeScreen.getWelcomeScreen());
                break;
            case MainWindow.ROOM_SELECT:
                setContentPane(roomSelection.getRoomSelection());
                break;
            case MainWindow.GAME_SCREEN:
                setContentPane(gameScreen.getGameScreen());
                break;
        }
        revalidate();
    }
    public ActionListener switchScreenListener(int screen) {
        return e -> {
            setScreen(screen);
        };
    }
    public synchronized Event<Packet> getOnReceiveNewPacket() {
        return p -> SwingUtilities.invokeLater(() -> {
            System.out.println(p.getData());
            List<PacketData> pd = NetworkHandler.extractPacketData(p);
            if (pd.get(0).type().equals("room_list")) {
                roomSelection.updateRoomList(pd);
            }
            gameScreen.processPacket(pd);
        });
    }
}
