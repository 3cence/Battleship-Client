package Battleship.UI;

import javax.swing.*;
public class WelcomeScreen {
    private JButton playButton;
    private JPanel welcomeScreen;
    private JLabel titleLabel;
    private JLabel statusLabel;

    public WelcomeScreen() {
        playButton.addActionListener(MainWindow.getMainWindow().switchScreenListener(MainWindow.ROOM_SELECT));
    }
    public JPanel getWelcomeScreen() {
        return welcomeScreen;
    }
}
