package Battleship.UI;

import javax.swing.*;
public class WelcomeScreen {
    private JButton playButton;
    private JPanel welcomeScreen;
    private JLabel titleLabel;

    public WelcomeScreen() {
        playButton.addActionListener(MainWindow.getMainWindow().switchScreenListener(MainWindow.ROOM_SELECT));
    }
    public JPanel getWelcomeScreen() {
        return welcomeScreen;
    }
}
