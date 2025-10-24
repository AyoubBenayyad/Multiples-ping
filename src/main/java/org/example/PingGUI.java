package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class PingGUI {
    public void display(List<Host> hosts) {
        JFrame frame = new JFrame("Ping Results");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] columns = {"IP", "Packet Received %", "Avg RTT (ms)", "Reachable", "Location"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Host host : hosts) {
            Object[] row = {
                    host.getIp(),
                    100 - host.getPacketLoss(),
                    host.getAvgRTT() == Double.POSITIVE_INFINITY ? "+∞" : host.getAvgRTT(),
                    host.isReachable() ? "Yes" : "No",
                    host.getLocation()
            };
            model.addRow(row);
        }

        JTable table = new JTable(model);
        frame.add(new JScrollPane(table));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
