package edu.pw.elka.andromote.commons.hardware.devices.generics;

import ioio.lib.api.IOIO;
import ioio.lib.api.exception.ConnectionLostException;

public interface ElectronicDeviceHardware {
	public void initIOIOPins(final IOIO ioio) throws ConnectionLostException;
	public void writeNewIOIOPinValues(final IOIO ioio) throws ConnectionLostException;
	public void readNewPinValues() throws InterruptedException, ConnectionLostException;
}
