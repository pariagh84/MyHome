package Devices;

import Abstraction.Device;

public class Thermostat extends Device {
    private int temperature;

    public Thermostat(String name, String protocol) {
        super(name, protocol);
        this.temperature = 20; // Default temperature
    }

    @Override
    public boolean setProperty(String property, String value) {
        if (property.equals("status")) {
            if (value.equals("on")) {
                status = true;
                return true;
            } else if (value.equals("off")) {
                status = false;
                return true;
            } else {
                System.out.println("invalid value");
                return false;
            }
        } else if (property.equals("temperature")) {
            try {
                int tempValue = Integer.parseInt(value);
                if (tempValue >= 10 && tempValue <= 30) {
                    this.temperature = tempValue;
                    return true;
                } else {
                    System.out.println("invalid value");
                    return false;
                }
            } catch (NumberFormatException e) {
                System.out.println("invalid value");
                return false;
            }
        } else {
            System.out.println("invalid property");
            return false;
        }
    }

    @Override
    public String getStatusString() {
        return name + " " + (status ? "on" : "off") + " " + temperature + "C " + protocol;
    }
}
