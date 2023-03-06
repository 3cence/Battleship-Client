package Battleship.Game;

import Battleship.UI.MainWindow;
import EmNet.Client;

public class Main {
    private static Client client;
    public static Client getClient() {
        return client;
    }
    public static void main(String[] args) {
        String ip = "127.0.0.1";
        int port = 48863;
        client = new Client(ip, port);
        MainWindow mw = MainWindow.getMainWindow();
        client.onReceiveNewPacket(mw.getOnReceiveNewPacket());
        client.start();
    }
}
