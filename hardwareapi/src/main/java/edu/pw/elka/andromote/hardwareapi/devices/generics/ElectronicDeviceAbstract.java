package edu.pw.elka.andromote.hardwareapi.devices.generics;

import ioio.lib.api.IOIO;
import ioio.lib.api.PwmOutput;
import ioio.lib.api.exception.ConnectionLostException;

public abstract class ElectronicDeviceAbstract {
	private static final int LED_PIN = 0;
	private static final int LED_PWM_FREQ = 10000;
	
	protected double ledFreq = 0.01;

	protected PwmOutput led_;

	public void initIOIOPins(final IOIO ioio) throws ConnectionLostException {
		try {
			led_ = ioio.openPwmOutput(LED_PIN, LED_PWM_FREQ);
			led_.setDutyCycle((float) 0.001);
		} catch (ConnectionLostException e) {
			e.printStackTrace();
		}
	}
	
	public abstract void writeNewIoioPinValues(final IOIO ioio) throws ConnectionLostException;
	
	public abstract void readNewIOIOPinValues() throws InterruptedException, ConnectionLostException;
}
