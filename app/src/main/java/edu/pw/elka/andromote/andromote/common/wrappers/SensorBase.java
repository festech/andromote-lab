package edu.pw.elka.andromote.andromote.common.wrappers;

public abstract class SensorBase {
    protected int value;
    protected String greetingText = "Witaj! Przypisz wartość zwracaną przez sensor do zmiennej value. Obecna wartość to ";

    public abstract void register();

    public abstract void unregister();

    public String getGreetingText() {
        return greetingText;
    }

    public int getValue() {
        return value;
    }
}
