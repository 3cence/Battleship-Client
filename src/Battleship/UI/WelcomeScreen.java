package Battleship.UI;

import Battleship.Game.Main;
import Network.NetworkHandler;

import javax.swing.*;
public class WelcomeScreen {
    private JButton playButton;
    private JPanel welcomeScreen;
    private JLabel titleLabel;
    private JLabel statusLabel;

    public WelcomeScreen() {
        playButton.addActionListener(MainWindow.getMainWindow().switchScreenListener(MainWindow.ROOM_SELECT));
        playButton.addActionListener(e -> Main.getClient().sendPacket(NetworkHandler.generatePacketData("refresh_rooms")));
    }
    public JPanel getWelcomeScreen() {
        return welcomeScreen;
    }
}
