package org.example;

public class Host {
    private String ip;
    private double packetLoss;
    private double avgRTT;
    private String location; // optional extra info
    private boolean reachable;

    public Host(String ip) {
        this.ip = ip;
        this.packetLoss = 100;
        this.avgRTT = Double.POSITIVE_INFINITY;
        this.reachable = false;
        this.location = "Unknown";
    }

    // Getters and setters
    public String getIp() { return ip; }
    public double getPacketLoss() { return packetLoss; }
    public void setPacketLoss(double packetLoss) {
        this.packetLoss = packetLoss;
        this.reachable = packetLoss < 100;
    }
    public double getAvgRTT() { return avgRTT; }
    public void setAvgRTT(double avgRTT) { this.avgRTT = avgRTT; }
    public boolean isReachable() { return reachable; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
