package edu.pw.elka.andromote.commons.hardware.devices;

import edu.pw.elka.andromote.commons.Packet;
import edu.pw.elka.andromote.commons.PacketType;
import edu.pw.elka.andromote.commons.hardware.devices.generics.ElectronicDeviceHardware;
import edu.pw.elka.andromote.commons.stepper.Step;

public interface ElectronicDevice extends ElectronicDeviceHardware {
	public abstract void takeStep(Step step);
	public abstract void interpretPacket(Packet inputPacket);
	public abstract void setValuesForSimpleStep(PacketType.Motion stepType);
	public abstract void stop();
	public abstract DeviceSettings getSettings();
}