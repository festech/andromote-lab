package edu.pw.elka.andromote.devices.andromote_v1;


import edu.pw.elka.andromote.commons.Packet;
import edu.pw.elka.andromote.commons.PacketType.Motion;
import edu.pw.elka.andromote.commons.hardware.devices.DeviceSettings;
import edu.pw.elka.andromote.hardwareapi.devices.generics.RobotHardwareAbstract;

public class NewBrightPlatform extends RobotHardwareAbstract {
	private static final String TAG = NewBrightPlatform.class.getName();
	private NewBrightSettings settings;
	
	public NewBrightPlatform() {
		super(new PololuTwoEngines());
		settings = new NewBrightSettings();
	}
	
	@Override
	public void interpretPacket(Packet inputPacket) {
		double speed = inputPacket.getSpeed();
		Motion packetType = (Motion) inputPacket.getPacketType();
		switch(packetType) {
		case MOVE_LEFT_FORWARD : 
			moveForward(speed);
			steerLeft();
			break;
		case MOVE_FORWARD : 
			moveForward(speed);
			steerCenter();
			break;
		case MOVE_RIGHT_FORWARD : 
			moveForward(speed);
			steerRight();
			break;
		case MOVE_LEFT : 
			moveForward(0.0);
			steerLeft();
			break;
		case STOP : 
			stop();
			break;
		case MOVE_RIGHT : 
			stop();
			steerRight();
			break;
		case MOVE_LEFT_BACKWARD : 
			moveBackward(speed);
			steerLeft();
			break;
		case MOVE_BACKWARD : 
			moveBackward(speed);
			steerCenter();
			break;
		case MOVE_RIGHT_BACKWARD : 
			moveBackward(speed);
			steerRight();
			break;
		default:
			break;
		}
	}
	
	private void steerLeft() {
		getDevice().setServoVoltage(1);
		getDevice().setServoLeft(false);
		getDevice().setServoRight(true);
	}

	private void steerRight() {
		getDevice().setServoVoltage(1);
		getDevice().setServoLeft(true);
		getDevice().setServoRight(false);
	}

	private void steerCenter() {
		getDevice().setServoVoltage(0);
		getDevice().setServoLeft(false);
		getDevice().setServoRight(true);
	}

	private void moveForward(double speed) {
		getDevice().setEngineGearBackward(false);
		getDevice().setEngineGearForward(true);
		getDevice().setEngineFreq(speed);

	}

	private void moveBackward(double speed) {
		getDevice().setEngineGearBackward(true);
		getDevice().setEngineGearForward(false);
		getDevice().setEngineFreq(speed);
	}

	/**
	 * 
	 * @param packetType
	 */
	public void setValuesForSimpleStep(Motion packetType) {
		double speed = settings.getSpeed();
		if (packetType == Motion.MOVE_LEFT_FORWARD) {
			moveForward(speed);
			steerLeft();
		} else if (packetType == Motion.MOVE_FORWARD) {
			moveForward(speed);
			steerCenter();
		} else if (packetType == Motion.MOVE_RIGHT_FORWARD) {
			moveForward(speed);
			steerRight();
		} else if (packetType == Motion.MOVE_LEFT) {
			moveForward(0.0);
			steerLeft();
		} else if (packetType == Motion.MOVE_RIGHT) {
			moveForward(0.0);
			steerRight();
		} else if (packetType == Motion.STOP) {
			stop();
		} else if (packetType == Motion.MOVE_LEFT_BACKWARD) {
			moveBackward(speed);
			steerLeft();
		} else if (packetType == Motion.MOVE_BACKWARD) {
			moveBackward(speed);
			steerCenter();
		} else if (packetType == Motion.MOVE_RIGHT_BACKWARD) {
			moveBackward(speed);
			steerRight();
		}
	}

	@Override
	public void stop() {
		getDevice().setEngineFreq(0);
		getDevice().setEngineGearBackward(false);
		getDevice().setEngineGearForward(false);
		this.steerCenter();
	}

	@Override
	public DeviceSettings getSettings() {
		return settings;
	}	
	
	private PololuTwoEngines getDevice() {
		return (PololuTwoEngines)device;
	}
}
