package Abstraction;

public abstract class Device implements Configurable {
    protected String name;
    protected String protocol;
    protected boolean status;

    public Device(String name, String protocol) {
        this.name = name;
        this.protocol = protocol;
        this.status = false;
    }

    public String getName() {
        return name;
    }

    public String getProtocol() {
        return protocol;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public abstract String getStatusString();
}
