package Battleship.UI;

import Battleship.Game.Main;
import Network.NetworkHandler;
import Network.PacketData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

public class RoomSelection {
    private JList roomList;
    private JPanel roomSelection;
    private JButton refreshButton;
    private JButton joinRoomButton;
    private JButton makeRoomBtn;
    private JTextField nameInput;
    private List<PacketData> roomPacketData;

    public RoomSelection() {
        makeRoomBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter room name:");
            if (name != null && !name.equals(""))
                Main.getClient().sendPacket(NetworkHandler.generatePacketData("make_room", name));
        });
        refreshButton.addActionListener(e -> {
            Main.getClient().sendPacket(NetworkHandler.generatePacketData("refresh_rooms"));
        });
        joinRoomButton.addActionListener(e -> {
            Main.getClient().sendPacket(NetworkHandler.generatePacketData("join_room", roomPacketData.get(roomList.getSelectedIndex()).data().split(",")[0]));
            MainWindow.getMainWindow().setScreen(MainWindow.GAME_SCREEN);
        });
        nameInput.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                Main.getClient().sendPacket(NetworkHandler.generatePacketData("name", nameInput.getText()));
            }
        });
    }

    public JPanel getRoomSelection() {
        return roomSelection;
    }
    public void updateRoomList(List<PacketData> pd) {
        roomPacketData = pd;
        DefaultListModel<String> model = new DefaultListModel<>();
        pd.remove(0);
        for (PacketData p: pd) {
            model.addElement(p.type() + ": " + p.data().split(",")[1] + "/2 Players");
        }
        roomList.setModel(model);
        // this really shouldn't be here but needs to update the name of the user, and this guarantees it
        Main.getClient().sendPacket(NetworkHandler.generatePacketData("name", nameInput.getText()));
    }
}
