import Abstraction.*;
import Devices.*;
import exceptions.*;

import java.util.*;

class SmartHomeSystem {
    private final List<Device> devices;
    private final List<Rule> rules;

    public SmartHomeSystem() {
        devices = new ArrayList<>();
        rules = new ArrayList<>();
    }

    public void addDevice(String type, String name, String protocol) {
        if (!type.equalsIgnoreCase("light") && !type.equalsIgnoreCase("thermostat")) {
            throw new InvalidInputException("invalid input");
        }
        if (!protocol.equalsIgnoreCase("WiFi") && !protocol.equalsIgnoreCase("Bluetooth")) {
            throw new InvalidInputException("invalid input");
        }
        for (Device device : devices) {
            if (device.getName().equals(name)) {
                throw new DuplicateException("Duplicate device name");
            }
        }

        Device newDevice;
        if (type.equalsIgnoreCase("light")) {
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
            throw new DeviceNotFoundException("device not found");
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
            throw new DeviceNotFoundException("device not found");
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
            throw new DeviceNotFoundException("device not found");
        }
        if (isValidTime(time)) {
            throw new InvalidInputException("invalid time");
        }
        if (!action.equals("on") && !action.equals("off")) {
            throw new InvalidInputException("invalid action");
        }
        for (Rule rule : rules) {
            if (rule.deviceName().equals(deviceName) && rule.time().equals(time)) {
                throw new DuplicateException("duplicate rule");

            }
        }
        rules.add(new Rule(deviceName, time, action));
        System.out.println("rule added successfully");
    }

    public void checkRules(String time) {
        if (isValidTime(time)) {
            throw new InvalidInputException("invalid time");
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
                    try {
                        system.addDevice(command[1], command[2], command[3]);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "set_device":
                    try {
                        system.setDevice(command[1], command[2], command[3]);
                    } catch (DeviceNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "remove_device":
                    try {
                        system.removeDevice(command[1]);
                    } catch (DeviceNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "list_devices":
                    system.listDevices();
                    break;
                case "add_rule":
                    try {
                        system.addRule(command[1], command[2], command[3]);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "check_rules":
                    try {
                        system.checkRules(command[1]);
                    } catch (InvalidInputException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "list_rules":
                    system.listRules();
                    break;
            }
        }
    }
}
