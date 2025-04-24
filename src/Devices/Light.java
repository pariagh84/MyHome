package Devices;

import Abstraction.Device;

public class Light extends Device {
    private int brightness;

    public Light(String name, String protocol) {
        super(name, protocol);
        this.brightness = 50; // Default brightness
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
        } else if (property.equals("brightness")) {
            try {
                int brightnessValue = Integer.parseInt(value);
                if (brightnessValue >= 0 && brightnessValue <= 100) {
                    this.brightness = brightnessValue;
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
        return name + " " + (status ? "on" : "off") + " " + brightness + "% " + protocol;
    }
}
