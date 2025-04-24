package Abstraction;

interface Configurable {
    boolean setProperty(String property, String value);

    String getStatusString();
}
