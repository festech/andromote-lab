package edu.pw.elka.andromote.hardwareapi.ioio_service;

import edu.pw.elka.andromote.andromotelogger.AndroMoteLogger;
import edu.pw.elka.andromote.commons.MotionMode;
import edu.pw.elka.andromote.commons.Packet;
import edu.pw.elka.andromote.commons.api.exceptions.UnknownDeviceException;
import edu.pw.elka.andromote.commons.hardware.devices.ElectronicDevice;
import edu.pw.elka.andromote.commons.stepper.Step;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;

//Stworzyć interfejs controllerlooper'a i klasę abstrakcyjną -  stworzyć współzależność modelu i loopera
public class AndroMoteIOIOLooper extends BaseIOIOLooper {
	private static final String TAG = AndroMoteIOIOLooper.class.getName();
	private final IOIOLooperManagerService parentControllerService;
	private final ElectronicDevice hardware;
	private AndroMoteLogger logger = new AndroMoteLogger(AndroMoteIOIOLooper.class);
	private Step currentStep = null;
	private boolean isDeviceConnected = false;

	public AndroMoteIOIOLooper(IOIOLooperManagerService enginesControllerService, ElectronicDevice hardware) throws ConnectionLostException,
	InterruptedException, UnknownDeviceException {
		super();
//		FIXME Moved to main application
//		AndroMoteLogger.ConfigureLogger("AndroMoteClient.log");
		this.parentControllerService = enginesControllerService;
		this.hardware = hardware;
		logger.debug(TAG, "setup ioio engine controller");
	}

	@Override
	public void setup() throws ConnectionLostException, InterruptedException {
		logger.debug(TAG, "EngineControllerLooper: initIOIOPins");
		try {
			hardware.initIOIOPins(ioio_);
			isDeviceConnected = true;
		} catch (ConnectionLostException e) {
			logger.error(TAG, e);
		} 
	}

	@Override
	public void loop() throws ConnectionLostException, InterruptedException {
		if (hardware.getSettings().getMotionMode().equals(MotionMode.MOTION_MODE_STEPPER)) {
//			logger.debug(TAG, "stepper mode loop start");
			// blokada przed pobieraniem kolejnych kroków w trakcie wykonywanej akcji
			if (!IOIOLooperManagerService.isOperationExecuted) {
				currentStep = parentControllerService.getNextStep();
				if (currentStep != null) {
					logger.debug(TAG,
							"EngineControllerLooper; stepperMode: step pobrany z kolejki: " + currentStep.getStepType());
					hardware.takeStep(currentStep);
				}
			}

			Thread.sleep(50);
			writeNewIOIOPinValues();
			hardware.readNewPinValues();

		} else if (hardware.getSettings().getMotionMode().equals(
				MotionMode.MOTION_MODE_CONTINUOUS)) {
			writeNewIOIOPinValues();
			hardware.readNewPinValues();
			Thread.sleep(50);
		}

	}

	private void writeNewIOIOPinValues() throws ConnectionLostException {
		ioio_.beginBatch();
		try {
			hardware.writeNewIOIOPinValues(ioio_);
		} finally {
			ioio_.endBatch();
		}
	}

	@Override
	public void disconnected() {
		logger.debug(TAG, "ioio disconnected");
		isDeviceConnected = false;
	}

	@Override
	public void incompatible() {
		logger.debug(TAG, "ioio incompatibile");
	}

	public void executePacket(Packet inputPacket) {
		hardware.interpretPacket(inputPacket);
	}

	public boolean isDeviceConnected() {
		return isDeviceConnected;
	}
}
