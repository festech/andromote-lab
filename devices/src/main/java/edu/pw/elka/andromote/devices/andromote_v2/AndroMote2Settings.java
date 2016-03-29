package edu.pw.elka.andromote.devices.andromote_v2;


import edu.pw.elka.andromote.andromotelogger.AndroMoteLogger;
import edu.pw.elka.andromote.commons.MotionMode;
import edu.pw.elka.andromote.commons.hardware.devices.DeviceSettings;

public class AndroMote2Settings implements DeviceSettings {
	private static final String TAG = AndroMote2Settings.class.getName();
		/**
		 * Maksymalny czas trwania kroku w trybie MOTION_MODE_STEPPER
		 */
		private final int MAX_STEP_DURATION = 2000;
		/**
		 * Minimalna prędkość silników. Zabezpieczenie przed małą mocą.
		 */
		private final double MIN_SPEED = -1.0;
		/**
		 * Czas trwania pojedynczego kroku
		 */
		public long stepDuration;
	private AndroMoteLogger log = new AndroMoteLogger(AndroMote2Settings.class);
		/**
		 * Aktualna prędkość silnika.
		 */
		private double currentSpeed;
		/**
		 * Aktualna prędkość drugiego silnika.
		 */
		private double currentSpeed_B;
		/**
		 * Czas oczekiwania pomiędzy kolejnymi krokami.
		 */
		private long pauseTimeBetweenSteps;
		/**
		 * Tryb ruchu pojazdu.
		 * 
		 * @see IOIOLooperManagerService.MOVING_MODE_CONTINUOUS
		 * @see IOIOLooperManagerService.MOVING_MODE_STEPPER
		 */
		private MotionMode motionMode = MotionMode.MOTION_MODE_CONTINUOUS;

		
		@Override
		public MotionMode getMotionMode() {
			return motionMode;
		}
		
		@Override
		public void setMotionMode(MotionMode motionMode) {
			this.motionMode = motionMode;

		}
		
		@Override
		public long getStepDuration() {
			return stepDuration;
		}

		@Override
		public void setStepDuration(long stepDuration) {
			if (stepDuration > MAX_STEP_DURATION) {
				log.debug(
						TAG,
						"EnginesControllerService: próba ustawienia większego niż dopuszczalny czasu trwania jednego kroku. Ustawiona wartość: "
								+ MAX_STEP_DURATION);
				this.stepDuration = MAX_STEP_DURATION;
			} else {
				log.debug(TAG, "EnginesControllerService: zmiana czasu trwania kroku. Nowa wartość [ms]: "
						+ stepDuration);
				this.stepDuration = stepDuration;
			}
		}
		
		@Override
		public long getPauseTimeBetweenSteps() {
			return pauseTimeBetweenSteps;
		}

		@Override
		public double getSpeed() {
			return currentSpeed;
		}
		
		@Override
		public void setSpeed(double speed) {
			if (speed > 1 || speed < MIN_SPEED) {
				log.debug(TAG, "EnginesControllerService: niedopuszczalna nowa wartość prędkości silników: " + speed
						+ ". Zmiana nie została dokonana!.");
			} else {
				log.debug(TAG, "EnginesControllerService: zmiana prędkości silników na: " + speed);
				currentSpeed = speed;
			}
		}
		
		@Override
		public double getSpeed_B() {
			return currentSpeed_B;
		}

		@Override
		public void setSpeedB(double speed) {
			if (speed > 1 || speed < MIN_SPEED) {
				log.debug(TAG, "EnginesControllerService: niedopuszczalna nowa wartość prędkości silników: " + speed
						+ ". Zmiana nie została dokonana!.");
			} else {
				log.debug(TAG, "EnginesControllerService: zmiana prędkości silników na: " + speed);
				currentSpeed_B = speed;
			}
		}

		@Override
		public double getMIN_SPEED() {
			return MIN_SPEED;
		}

		public int getMAX_STEP_DURATION() {
			return MAX_STEP_DURATION;
		}
}