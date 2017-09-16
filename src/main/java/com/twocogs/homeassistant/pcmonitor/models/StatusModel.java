package com.twocogs.homeassistant.pcmonitor.models;

/**
 * Model for basic status information.
 */
public class StatusModel {
    private double cpuUsage = 0;
    private double totalMemory = 0;
    private double usedMemory = 0;
    private String operatingSystem = "";

    public StatusModel(final double cpuUsage, final double totalMemory, final double usedMemory, String operatingSystem) {
        this.cpuUsage = cpuUsage;
        this.totalMemory = totalMemory;
        this.usedMemory = usedMemory;
        this.operatingSystem = operatingSystem;
    }

    public double getCpuUsage() {
        return cpuUsage;
    }

    public double getTotalMemory() {
        return totalMemory;
    }

    public double getUsedMemory() {
        return usedMemory;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }
}
