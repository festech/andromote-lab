package edu.pw.elka.andromote.commons.hardware.devices;


import edu.pw.elka.andromote.commons.MotionMode;

public interface DeviceSettings {

	public abstract MotionMode getMotionMode();

	public abstract void setMotionMode(MotionMode motionMode);

	public abstract long getStepDuration();

	public abstract void setStepDuration(long stepDuration);

	public abstract double getSpeed();

	public abstract void setSpeed(double speed);

	public abstract double getSpeed_B();

	public abstract void setSpeedB(double speed);

	public abstract double getMIN_SPEED();

	public abstract long getPauseTimeBetweenSteps();

}