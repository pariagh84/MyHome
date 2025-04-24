import Abstraction.Device;
import Abstraction.Rule;
import Devices.Light;
import Devices.Thermostat;

import java.util.*;

class SmartHomeSystem {
    private final List<Device> devices;
    private final List<Rule> rules;

    public SmartHomeSystem() {
        devices = new ArrayList<>();
        rules = new ArrayList<>();
    }

    public void addDevice(String type, String name, String protocol) {
        if (!type.equals("light") && !type.equals("thermostat")) {
            System.out.println("invalid input");
            return;
        }
        if (!protocol.equalsIgnoreCase("WiFi") && !protocol.equalsIgnoreCase("Bluetooth")) {
            System.out.println("invalid input");
            return;
        }
        for (Device device : devices) {
            if (device.getName().equals(name)) {
                System.out.println("Duplicate device name");
                return;
            }
        }

        Device newDevice;
        if (type.equals("light")) {
            newDevice = new Light(name, protocol);
        } else {
            newDevice = new Thermostat(name, protocol);
        }
        devices.add(newDevice);
        System.out.println("device added successfully");
    }

    public void setDevice(String name, String property, String value) {
        Device targetDevice = null;
        for (Device device : devices) {
            if (device.getName().equals(name)) {
                targetDevice = device;
                break;
            }
        }
        if (targetDevice == null) {
            System.out.println("device not found");
            return;
        }
        if (targetDevice.setProperty(property, value)) {
            System.out.println("device updated successfully");
        }
    }

    public void removeDevice(String name) {
        Device targetDevice = null;
        for (Device device : devices) {
            if (device.getName().equals(name)) {
                targetDevice = device;
                break;
            }
        }
        if (targetDevice == null) {
            System.out.println("device not found");
            return;
        }
        devices.remove(targetDevice);
        rules.removeIf(rule -> rule.deviceName().equals(name));
        System.out.println("device removed successfully");
    }

    public void listDevices() {
        if (devices.isEmpty()) {
            System.out.println();
            return;
        }
        for (Device device : devices) {
            System.out.println(device.getStatusString());
        }
    }

    public void addRule(String deviceName, String time, String action) {
        Device targetDevice = null;
        for (Device device : devices) {
            if (device.getName().equals(deviceName)) {
                targetDevice = device;
                break;
            }
        }
        if (targetDevice == null) {
            System.out.println("device not found");
            return;
        }
        if (isValidTime(time)) {
            System.out.println("invalid time");
            return;
        }
        if (!action.equals("on") && !action.equals("off")) {
            System.out.println("invalid action");
            return;
        }
        for (Rule rule : rules) {
            if (rule.deviceName().equals(deviceName) && rule.time().equals(time)) {
                System.out.println("duplicate rule");
                return;
            }
        }
        rules.add(new Rule(deviceName, time, action));
        System.out.println("rule added successfully");
    }

    public void checkRules(String time) {
        if (isValidTime(time)) {
            System.out.println("invalid time");
            return;
        }
        for (Rule rule : rules) {
            if (rule.time().equals(time)) {
                for (Device device : devices) {
                    if (device.getName().equals(rule.deviceName())) {
                        device.setStatus(rule.action().equals("on"));
                        break;
                    }
                }
            }
        }
        System.out.println("rules checked");
    }

    public void listRules() {
        if (rules.isEmpty()) {
            System.out.println();
            return;
        }
        for (Rule rule : rules) {
            System.out.println(rule.toString());
        }
    }

    private boolean isValidTime(String time) {
        if (!time.matches("\\d{2}:\\d{2}")) {
            return true;
        }
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours < 0 || hours > 23 || minutes < 0 || minutes > 59;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SmartHomeSystem system = new SmartHomeSystem();

        int q = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < q; i++) {
            String[] command = scanner.nextLine().split(" ");
            switch (command[0]) {
                case "add_device":
                    system.addDevice(command[1], command[2], command[3]);
                    break;
                case "set_device":
                    system.setDevice(command[1], command[2], command[3]);
                    break;
                case "remove_device":
                    system.removeDevice(command[1]);
                    break;
                case "list_devices":
                    system.listDevices();
                    break;
                case "add_rule":
                    system.addRule(command[1], command[2], command[3]);
                    break;
                case "check_rules":
                    system.checkRules(command[1]);
                    break;
                case "list_rules":
                    system.listRules();
                    break;
            }
        }
    }
}