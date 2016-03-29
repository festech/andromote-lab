package edu.pw.elka.andromote.devices.andromote_v1;


import java.util.ArrayList;
import java.util.List;

import edu.pw.elka.andromote.commons.hardware.devices.ElectronicDevice;
import edu.pw.elka.andromote.commons.hardware.devices.ElectronicDeviceFactory;

public class AndroMote1DeviceFactory implements ElectronicDeviceFactory {
	@Override
	public ElectronicDevice createRobotPlatform() {
		return new NewBrightPlatform();
	}

	@Override
	public List<ElectronicDevice> createDevices() {
		return new ArrayList<ElectronicDevice>();
	}

}
