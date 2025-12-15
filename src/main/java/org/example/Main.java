package org.example;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<String> ips = new ArrayList<>();

        // Read ips.txt
        try (BufferedReader br = new BufferedReader(new FileReader("ips.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    ips.add(line.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading ips.txt: " + e.getMessage());
            return;
        }

        // Ping each IP
        PingService service = new PingService();
        for (String ip : ips) {
            System.out.println("Pinging " + ip + "...");
            Host host = service.ping(ip, null);

            double received = 100 - host.getPacketLoss();
            String rtt = host.getAvgRTT() == Double.POSITIVE_INFINITY ?
                    "+∞" : String.format("%.3f", host.getAvgRTT());

            System.out.println(ip + " => " + (int)received + "%, " + rtt + " ms");
            System.out.println();
        }
    }
}
