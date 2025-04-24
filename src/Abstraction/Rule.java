package Abstraction;

public record Rule(String deviceName, String time, String action) {
    @Override
    public String toString() {
        return deviceName + " " + time + " " + action;
    }
}
