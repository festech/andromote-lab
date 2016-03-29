package edu.pw.elka.andromote.devices.andromote_v2;


import edu.pw.elka.andromote.commons.Packet;
import edu.pw.elka.andromote.commons.PacketType;
import edu.pw.elka.andromote.commons.hardware.devices.DeviceSettings;
import edu.pw.elka.andromote.hardwareapi.devices.generics.RobotHardwareAbstract;

public class Rover5Platform extends RobotHardwareAbstract {
	private static final String TAG = Rover5Platform.class.getName();
	private AndroMote2Settings settings;
	
	public Rover5Platform() {
		super(new RNVNH2Driver());
		settings = new AndroMote2Settings();
	}
	
	@Override
	public void stop() {
		logger.debug(TAG, "STOP!");
		getDevice().setM1_PWM_DutyCycle_value(0.0f);
		getDevice().setM2_PWM_DutyCycle_value(0.0f);
	}
	
	@Override
	public void interpretPacket(Packet inputPacket) {
		float speed = (float) inputPacket.getSpeed();
		float speedB = (float) inputPacket.getSpeedB();
		PacketType.Motion packetType = (PacketType.Motion) inputPacket.getPacketType();
		switch(packetType) {
		case MOVE_CATERPILLAR : 
			moveCaterpillar(speed, speedB);
			break;
		case MOVE_LEFT_FORWARD : 
			moveForwardDifferSpeed(speed, speed * 0.3f);
			break;
		case MOVE_FORWARD : 
			moveForward(speed);
			break;
		case MOVE_FORWARD_DIFFER_SPEED : 
			moveForwardDifferSpeed(speed, speedB);
			break;
		case MOVE_RIGHT_FORWARD : 
			moveForwardDifferSpeed(speed * 0.3f, speed);
			break;
		case MOVE_LEFT : 
			steerLeft(speed);
			break;
		case STOP : 
			stop();
			break;
		case MOVE_RIGHT : 
			steerRight(speed);
			break;
		case MOVE_LEFT_BACKWARD : 
			moveBackwardDifferSpeed(speed, speed * 0.3f);
			break;
		case MOVE_BACKWARD : 
			moveBackward(speed);
			break;
		case MOVE_BACKWARD_DIFFER_SPEED : 
			moveBackwardDifferSpeed(speed, speedB);
			break;
		case MOVE_RIGHT_BACKWARD : 
			moveBackwardDifferSpeed(speed * 0.3f, speed);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void setValuesForSimpleStep(PacketType.Motion packetType) {
		float speed = (float)settings.getSpeed();
		float speedB = (float)settings.getSpeed_B();
		switch(packetType) {
		case MOVE_LEFT_FORWARD : 
			moveForwardDifferSpeed(speed, speed * 0.3f);
			break;
		case MOVE_FORWARD : 
			moveForward(speed);
			break;
		case MOVE_FORWARD_DIFFER_SPEED : 
			moveForwardDifferSpeed(speed, speedB);
			break;
		case MOVE_RIGHT_FORWARD : 
			moveForwardDifferSpeed(speed * 0.3f, speed);
			break;
		case MOVE_LEFT : 
			steerLeft(speed);
			break;
		case STOP : 
			stop();
			break;
		case MOVE_RIGHT : 
			steerRight(speed);
			break;
		case MOVE_LEFT_BACKWARD : 
			moveBackwardDifferSpeed(speed, speed * 0.3f);
			break;
		case MOVE_BACKWARD : 
			moveBackward(speed);
			break;
		case MOVE_BACKWARD_DIFFER_SPEED : 
			moveBackwardDifferSpeed(speed, speedB);
			break;
		case MOVE_RIGHT_BACKWARD : 
			moveBackwardDifferSpeed(speed * 0.3f, speed);
			break;
		default:
			break;
		}
	}
	
	@Override
	public DeviceSettings getSettings() {
		return settings;
	}

	private void moveCaterpillar(float speed, float speedB) {
		if(speed >= 0) {
			getDevice().setM1_inA_value(false);
			getDevice().setM1_inB_value(true);
		} else {
			getDevice().setM1_inA_value(true);
			getDevice().setM1_inB_value(false);
		}
		if(speedB >= 0) {
			getDevice().setM2_inA_value(false);
			getDevice().setM2_inB_value(true);
		} else {
			getDevice().setM2_inA_value(true);
			getDevice().setM2_inB_value(false);
		}
		getDevice().setM1_PWM_DutyCycle_value(Math.abs(speed));
		getDevice().setM2_PWM_DutyCycle_value(Math.abs(speedB));
	}

	private void steerLeft(float speed) {
		getDevice().setM1_inA_value(false);
		getDevice().setM1_inB_value(true);
		getDevice().setM2_inA_value(true);
		getDevice().setM2_inB_value(false);
		
		getDevice().setM1_PWM_DutyCycle_value(Math.abs(speed));
		getDevice().setM2_PWM_DutyCycle_value(Math.abs(speed));
	}

	private void steerRight(float speed) {
		getDevice().setM1_inA_value(true);
		getDevice().setM1_inB_value(false);
		getDevice().setM2_inA_value(false);
		getDevice().setM2_inB_value(true);
		
		getDevice().setM1_PWM_DutyCycle_value(Math.abs(speed));
		getDevice().setM2_PWM_DutyCycle_value(Math.abs(speed));

	}

	private void moveForward(float speed) {
		getDevice().setM1_inA_value(false);
		getDevice().setM1_inB_value(true);
		getDevice().setM2_inA_value(false);
		getDevice().setM2_inB_value(true);
		
		getDevice().setM1_PWM_DutyCycle_value(Math.abs(speed));
		getDevice().setM2_PWM_DutyCycle_value(Math.abs(speed));
	}
	
	private void moveForwardDifferSpeed(float speed, float speedB) {
		logger.debug(TAG, "moveForward -> inA: true");
		getDevice().setM1_inA_value(false);
		getDevice().setM1_inB_value(true);
		getDevice().setM2_inA_value(false);
		getDevice().setM2_inB_value(true);
		
		getDevice().setM1_PWM_DutyCycle_value(Math.abs(speed));
		getDevice().setM2_PWM_DutyCycle_value(Math.abs(speedB));
	}

	private void moveBackward(float speed) {
		logger.debug(TAG, "moveBackward -> inA: false");
		getDevice().setM1_inA_value(true);
		getDevice().setM1_inB_value(false);
		getDevice().setM2_inA_value(true);
		getDevice().setM2_inB_value(false);
		getDevice().setM1_PWM_DutyCycle_value(Math.abs(speed));
		getDevice().setM2_PWM_DutyCycle_value(Math.abs(speed));
	}

	private void moveBackwardDifferSpeed(float speed, float speedB) {
		logger.debug(TAG, "moveBackward -> inA: false");
		getDevice().setM1_inA_value(true);
		getDevice().setM1_inB_value(false);
		getDevice().setM2_inA_value(true);
		getDevice().setM2_inB_value(false);
		getDevice().setM1_PWM_DutyCycle_value(Math.abs(speed));
		getDevice().setM2_PWM_DutyCycle_value(Math.abs(speedB));
	}
	
	private RNVNH2Driver getDevice() {
		return (RNVNH2Driver)device;
	}
}
