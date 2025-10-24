package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class PingService {

    @FunctionalInterface
    public interface ProgressCallback {
        void onProgress(int percent);
    }

    public Host ping(String ip, ProgressCallback callback) {
        Host host = new Host(ip);

        try {
            String os = System.getProperty("os.name").toLowerCase();
            int pingCount = 4;
            String command = os.contains("win") ? "ping -n " + pingCount + " " + ip : "ping -c " + pingCount + " " + ip;

            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            int linesRead = 0;
            int estimatedTotalLines = 10; // Approximate lines in ping output

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
                linesRead++;

                // Update progress based on lines read
                int progress = Math.min(95, (linesRead * 100) / estimatedTotalLines);
                if (callback != null) {
                    callback.onProgress(progress);
                }
            }

            process.waitFor();

            if (callback != null) {
                callback.onProgress(100);
            }

            parseOutput(host, output.toString());

        } catch (Exception e) {
            System.err.println("Error pinging " + ip + ": " + e.getMessage());
            host.setPacketLoss(100.0);
            host.setAvgRTT(Double.POSITIVE_INFINITY);
        }

        return host;
    }

    private void parseOutput(Host host, String output) {
        double packetLoss = extractPacketLoss(output);
        host.setPacketLoss(packetLoss);

        // Only extract RTT if host is reachable
        if (packetLoss < 100.0) {
            double avgRTT = extractAverageRTT(output);
            host.setAvgRTT(avgRTT);
        } else {
            host.setAvgRTT(Double.POSITIVE_INFINITY);
        }
    }

    private double extractPacketLoss(String output) {
        Pattern linuxPattern = Pattern.compile("(\\d+)% packet loss");
        Matcher linuxMatcher = linuxPattern.matcher(output);
        if (linuxMatcher.find()) return Double.parseDouble(linuxMatcher.group(1));

        Pattern windowsPattern = Pattern.compile("\\((\\d+)% loss\\)");
        Matcher windowsMatcher = windowsPattern.matcher(output);
        if (windowsMatcher.find()) return Double.parseDouble(windowsMatcher.group(1));

        return 100.0;
    }

    private double extractAverageRTT(String output) {
        Pattern linuxPattern = Pattern.compile("= [\\d.]+/([\\d.]+)/[\\d.]+");
        Matcher linuxMatcher = linuxPattern.matcher(output);
        if (linuxMatcher.find()) return Double.parseDouble(linuxMatcher.group(1));

        Pattern windowsPattern = Pattern.compile("Average = (\\d+)ms");
        Matcher windowsMatcher = windowsPattern.matcher(output);
        if (windowsMatcher.find()) return Double.parseDouble(windowsMatcher.group(1));

        return Double.POSITIVE_INFINITY;
    }
}