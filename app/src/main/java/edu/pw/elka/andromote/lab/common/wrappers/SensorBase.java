package edu.pw.elka.andromote.lab.common.wrappers;

public abstract class SensorBase {
    protected int value;
    protected String unit = "To obecna wartość";

    public abstract void register();

    public abstract void unregister();

    public String getUnit() {
        return unit;
    }

    public int getValue() {
        return value;
    }
}
