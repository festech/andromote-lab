package edu.pw.elka.andromote.commons.hardware.devices;

import java.util.List;

public interface ElectronicDeviceFactory {
	public ElectronicDevice createRobotPlatform();
	public List<ElectronicDevice> createDevices();
}
