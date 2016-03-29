package edu.pw.elka.andromote.devices.andromote_v1;


import edu.pw.elka.andromote.hardwareapi.devices.generics.ElectronicDeviceAbstract;
import ioio.lib.api.DigitalOutput;
import ioio.lib.api.IOIO;
import ioio.lib.api.PwmOutput;
import ioio.lib.api.exception.ConnectionLostException;

/**
 * Dwukanałowy sterownik max 1A
 * MAX_STEP_DURATION = 2000
 * MIN_SPEED = 0.6
 * @author Sebastian Łuczak
 *
 */
public class PololuTwoEngines extends ElectronicDeviceAbstract {
	private static final int AIN1_PIN = 44;
	private static final int AIN2_PIN = 45;
	private static final int PWMA_PIN = 46;
	private static final int PWMB_PIN = 40;
	private static final int BIN2_PIN = 41;
	private static final int BIN1_PIN = 42;
	private static final int STBY_PIN = 43;

	private static final int PWM_FREQ = 100;

	private DigitalOutput ain1_;
	private DigitalOutput ain2_;
	private PwmOutput pwma_;
	private PwmOutput pwmb_;
	private DigitalOutput bin2_;
	private DigitalOutput bin1_;
	private DigitalOutput stby_;

	// steering values
	private boolean servoLeft = false;
	private boolean servoRight = false;
	private int servoVoltage = 0;
	private boolean engineGearForward = true;
	private boolean engineGearBackward = false;
	private double engineFreq = 0;
	private boolean stby = true;

	@Override
	public void initIOIOPins(final IOIO ioio) throws ConnectionLostException {
		ain1_ = ioio.openDigitalOutput(AIN1_PIN);
		ain2_ = ioio.openDigitalOutput(AIN2_PIN);

		bin2_ = ioio.openDigitalOutput(BIN2_PIN);
		bin1_ = ioio.openDigitalOutput(BIN1_PIN);

		pwma_ = ioio.openPwmOutput(PWMA_PIN, PWM_FREQ);
		pwmb_ = ioio.openPwmOutput(PWMB_PIN, PWM_FREQ);

		stby_ = ioio.openDigitalOutput(STBY_PIN);
		stby_.write(true);
		super.initIOIOPins(ioio);
	}

	@Override
	public void writeNewIoioPinValues(final IOIO ioio) throws ConnectionLostException {
		ioio.beginBatch();
		try {
			pwmb_.setDutyCycle((float) servoVoltage);
			bin1_.write(servoLeft);
			bin2_.write(servoRight);

			pwma_.setDutyCycle((float) engineFreq);
			ain1_.write(engineGearBackward);
			ain2_.write(engineGearForward);

			stby_.write(stby);
		} finally {
			ioio.endBatch();
		}
	}

	public void setServoLeft(boolean servoLeft) {
		this.servoLeft = servoLeft;
	}

	public void setServoRight(boolean servoRight) {
		this.servoRight = servoRight;
	}

	public void setServoVoltage(int servoVoltage) {
		this.servoVoltage = servoVoltage;
	}

	public void setEngineGearForward(boolean engineGearForward) {
		this.engineGearForward = engineGearForward;
	}

	public void setEngineGearBackward(boolean engineGearBackward) {
		this.engineGearBackward = engineGearBackward;
	}

	public void setEngineFreq(double engineFreq) {
		this.engineFreq = engineFreq;
	}

	public void setStby(boolean stby) {
		this.stby = stby;
	}

	@Override
	public void readNewIOIOPinValues() {
		//Nothing to read		
	}
}
