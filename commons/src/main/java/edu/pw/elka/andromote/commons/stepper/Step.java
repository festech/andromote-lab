package edu.pw.elka.andromote.commons.stepper;

import edu.pw.elka.andromote.commons.PacketType.Motion;

public class Step implements IStep {

	/**
	 * Czas trwania kroku
	 */
	private long stepDuration;

	private int degrees;

	private Motion stepType;

	public Step() {
	}

	public Step(Motion stepType) {
		this.stepType = stepType;
	}

	public Motion getReverseDirection() {
		if (this.stepType == Motion.MOVE_FORWARD) {
			return Motion.MOVE_BACKWARD;
		} else if (this.stepType == Motion.MOVE_LEFT_FORWARD) {
			return Motion.MOVE_LEFT_BACKWARD;
		} else if (this.stepType == Motion.MOVE_RIGHT_FORWARD) {
			return Motion.MOVE_RIGHT_BACKWARD;
		} else if (this.stepType == Motion.MOVE_BACKWARD) {
			return Motion.MOVE_FORWARD;
		} else if (this.stepType == Motion.MOVE_LEFT_BACKWARD) {
			return Motion.MOVE_LEFT_FORWARD;
		} else if (this.stepType == Motion.MOVE_RIGHT_BACKWARD) {
			return Motion.MOVE_RIGHT_FORWARD;
		} else {
			return this.stepType;
		}
	}

//	/**
//	 * Funkcja zwraca nazwę kroku, który został wykonany (bez sufiksu _REQUEST)
//	 *
//	 * @param stepRequest
//	 *            Nazwa zlecenia wykonania kroku.
//	 * @return Nazwa wykonanego kroku
//	 */
//	public static Motion getTakenStep(Motion stepRequest) {
//		Motion returnStepType = null;
//		switch (stepRequest) {
//		case MOVE_LEFT_FORWARD:
//			returnStepType = Motion.MOVE_LEFT_FORWARD_RESPONSE;
//			break;
//		case MOVE_FORWARD:
//			returnStepType = Motion.MOVE_FORWARD_RESPONSE;
//			break;
//		case MOVE_RIGHT_FORWARD:
//			returnStepType = Motion.MOVE_RIGHT_FORWARD_RESPONSE;
//			break;
//		case MOVE_LEFT:
//			returnStepType = Motion.MOVE_LEFT_RESPONSE;
//			break;
//		case STOP:
//			returnStepType = Motion.STOP_RESPONSE;
//			break;
//		case MOVE_RIGHT:
//			returnStepType = Motion.MOVE_RIGHT_RESPONSE;
//			break;
//		case MOVE_LEFT_BACKWARD:
//			returnStepType = Motion.MOVE_LEFT_BACKWARD_RESPONSE;
//			break;
//		case MOVE_BACKWARD:
//			returnStepType = Motion.MOVE_BACKWARD_RESPONSE;
//			break;
//		case MOVE_RIGHT_BACKWARD:
//			returnStepType = Motion.MOVE_RIGHT_BACKWARD_RESPONSE;
//			break;
//		default:
//			break;
//		}
//		return returnStepType;
//	}

	public long getStepDuration() {
		return stepDuration;
	}

	public void setStepDuration(long stepDuration) {
		this.stepDuration = stepDuration;
	}

	public int getDegrees() {
		return degrees;
	}

	public void setDegrees(int degrees) {
		this.degrees = degrees;
	}

	public Motion getStepType() {
		return stepType;
	}

	public void setStepType(Motion stepType) {
		this.stepType = stepType;
	}
}
