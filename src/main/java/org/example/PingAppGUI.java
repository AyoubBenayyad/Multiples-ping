package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PingAppGUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PingAppGUI::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // Modern look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Network Ping Monitor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 550);
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(new Color(240, 242, 245));

        // --- Top Panel with Input ---
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel("Enter IPs or Domains:");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(new Color(60, 60, 60));

        JTextField inputField = new JTextField();
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        JButton pingButton = new JButton("Start Ping");
        pingButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        pingButton.setBackground(new Color(52, 168, 83));
        pingButton.setForeground(Color.WHITE);
        pingButton.setFocusPainted(false);
        pingButton.setBorderPainted(false);
        pingButton.setOpaque(true);
        pingButton.setPreferredSize(new Dimension(120, 40));
        pingButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        clearButton.setBackground(new Color(220, 220, 220));
        clearButton.setForeground(new Color(60, 60, 60));
        clearButton.setFocusPainted(false);
        clearButton.setBorderPainted(false);
        clearButton.setOpaque(true);
        clearButton.setPreferredSize(new Dimension(80, 40));
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel inputContainer = new JPanel(new BorderLayout(10, 5));
        inputContainer.setBackground(Color.WHITE);
        inputContainer.add(titleLabel, BorderLayout.NORTH);
        inputContainer.add(inputField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(clearButton);
        buttonPanel.add(pingButton);

        topPanel.add(inputContainer, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        frame.add(topPanel, BorderLayout.NORTH);

        // --- Progress Panel ---
        JPanel progressPanel = new JPanel(new BorderLayout(5, 5));
        progressPanel.setBackground(Color.WHITE);
        progressPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        progressPanel.setVisible(false);

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setPreferredSize(new Dimension(0, 25));
        progressBar.setForeground(new Color(52, 168, 83));

        JLabel progressLabel = new JLabel("Pinging hosts...");
        progressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        progressLabel.setForeground(new Color(100, 100, 100));

        progressPanel.add(progressLabel, BorderLayout.NORTH);
        progressPanel.add(progressBar, BorderLayout.CENTER);

        // --- Table for results ---
        String[] columns = {"IP", "Packet Received %", "Avg RTT (ms)", "Reachable"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(35);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(230, 240, 255));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(250, 250, 250));
        table.getTableHeader().setForeground(new Color(60, 60, 60));
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)));

        // Custom renderer for Reachable column
        table.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String reachable = value.toString();
                setHorizontalAlignment(CENTER);
                setFont(new Font("Segoe UI", Font.BOLD, 12));

                if (reachable.equals("Yes")) {
                    setForeground(new Color(15, 157, 88));
                } else {
                    setForeground(new Color(217, 48, 37));
                }

                if (!isSelected) {
                    setBackground(Color.WHITE);
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));

        // --- Center Panel ---
        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));
        centerPanel.setBackground(new Color(240, 242, 245));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        centerPanel.add(progressPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(centerPanel, BorderLayout.CENTER);

        // --- Ping action ---
        pingButton.addActionListener(e -> {
            String text = inputField.getText().trim();
            if (text.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Please enter at least one IP or domain.",
                        "Input Required",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String[] ips = text.split("\\s*,\\s*");

            // Show progress panel
            progressPanel.setVisible(true);
            progressBar.setValue(0);
            progressLabel.setText("Pinging " + ips.length + " host(s)...");

            pingButton.setEnabled(false);
            pingButton.setText("Pinging...");

            new Thread(() -> {
                for (int i = 0; i < ips.length; i++) {
                    String ip = ips[i];
                    final int currentIndex = i + 1;
                    final int total = ips.length;

                    SwingUtilities.invokeLater(() ->
                            progressLabel.setText("Pinging " + currentIndex + "/" + total + ": " + ip)
                    );

                    PingService service = new PingService();
                    Host host = service.ping(ip, (progress) -> {
                        // Update progress for this specific host
                        int overallProgress = ((currentIndex - 1) * 100 + progress) / total;
                        SwingUtilities.invokeLater(() -> {
                            progressBar.setValue(overallProgress);
                            progressBar.setString(overallProgress + "%");
                        });
                    });

                    final int finalProgress = (currentIndex * 100) / total;
                    SwingUtilities.invokeLater(() -> {
                        Object[] row = {
                                host.getIp(),
                                String.format("%.0f%%", 100 - host.getPacketLoss()),
                                host.getAvgRTT() == Double.POSITIVE_INFINITY ? "+∞" :
                                        String.format("%.2f ms", host.getAvgRTT()),
                                host.isReachable() ? "Yes" : "No"
                        };
                        model.addRow(row);

                        progressBar.setValue(finalProgress);
                        progressBar.setString(finalProgress + "%");

                        if (currentIndex == total) {
                            progressLabel.setText("Completed! " + total + " host(s) pinged.");
                            pingButton.setEnabled(true);
                            pingButton.setText("Start Ping");

                            Timer timer = new Timer(2000, evt -> progressPanel.setVisible(false));
                            timer.setRepeats(false);
                            timer.start();
                        }
                    });
                }
            }).start();
        });

        clearButton.addActionListener(e -> {
            model.setRowCount(0);
            inputField.setText("");
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}