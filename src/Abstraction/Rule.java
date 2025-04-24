package Abstraction;

public record Rule(String deviceName, String time, String action) {

    public String toString() {
        return deviceName + " " + time + " " + action;
    }
}
