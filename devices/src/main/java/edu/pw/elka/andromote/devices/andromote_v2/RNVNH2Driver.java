package edu.pw.elka.andromote.devices.andromote_v2;


import edu.pw.elka.andromote.andromotelogger.AndroMoteLogger;
import edu.pw.elka.andromote.commons.IntentsIdentifiers;
import edu.pw.elka.andromote.commons.Packet;
import edu.pw.elka.andromote.commons.api.LocalBroadcastDispatcher;
import edu.pw.elka.andromote.hardwareapi.devices.generics.ElectronicDeviceAbstract;
import edu.pw.elka.andromote.hardwareapi.platform_controller.AdditionalPacketTypes;
import ioio.lib.api.AnalogInput;
import ioio.lib.api.DigitalInput;
import ioio.lib.api.DigitalOutput;
import ioio.lib.api.IOIO;
import ioio.lib.api.PwmOutput;
import ioio.lib.api.exception.ConnectionLostException;

public class RNVNH2Driver extends ElectronicDeviceAbstract {
	private static final String TAG = RNVNH2Driver.class.getSimpleName();
	private static final int M1_INA_PIN = 17;
	private static final int M1_INB_PIN = 19;
	private static final int M2_INA_PIN = 18;
	private static final int M2_INB_PIN = 16;
	private static final int M1_PWM_PIN = 3;
	private static final int M2_PWM_PIN = 4;
	private static final int M1_CURRENT_MEASURE_PIN = 33;
	private static final int M2_CURRENT_MEASURE_PIN = 34;
	private static final int TEMPERATURE_PIN = 46;
	private static final int M1_ENCODER_A_PIN = 5;
	private static final int M1_ENCODER_B_PIN = 6;
	private static final int M2_ENCODER_A_PIN = 10;
	private static final int M2_ENCODER_B_PIN = 11;
	private static final int DEFAULT_PWM_FREQ = 1500;
	AndroMoteLogger log = new AndroMoteLogger(getClass());
	private DigitalOutput m1_InA_Output;
	private DigitalOutput m1_InB_Output;
	private DigitalOutput m2_InA_Output;
	private DigitalOutput m2_InB_Output;
	
	private PwmOutput m1_Pwm_Output;
	private PwmOutput m2_Pwm_Output;
	
	private AnalogInput m1_Current_Measure_Input;
	private AnalogInput m2_Current_Measure_Input;
	private AnalogInput rnvn2_temperature_Input;
	
	private DigitalInput m1_Encoder_A_Input;
	private DigitalInput m1_Encoder_B_Input;
	private DigitalInput m2_Encoder_A_Input;
	private DigitalInput m2_Encoder_B_Input;

	private boolean m1_inA_value = true;
	private boolean m1_inB_value = false;
	private boolean m2_inA_value = true;
	private boolean m2_inB_value = false;
	
	private float m1_PWM_DutyCycle_value = 0;
	private float m2_PWM_DutyCycle_value = 0;
	

	@Override
	public void initIOIOPins(final IOIO ioio) {
		try {
			m1_InA_Output = ioio.openDigitalOutput(M1_INA_PIN);
			m1_InB_Output = ioio.openDigitalOutput(M1_INB_PIN);

			m2_InA_Output = ioio.openDigitalOutput(M2_INA_PIN);
			m2_InB_Output = ioio.openDigitalOutput(M2_INB_PIN);

			m1_Pwm_Output = ioio.openPwmOutput(M1_PWM_PIN, DEFAULT_PWM_FREQ);
			m2_Pwm_Output = ioio.openPwmOutput(M2_PWM_PIN, DEFAULT_PWM_FREQ);
			
			m1_Current_Measure_Input = ioio.openAnalogInput(M1_CURRENT_MEASURE_PIN);
			m2_Current_Measure_Input = ioio.openAnalogInput(M2_CURRENT_MEASURE_PIN);
			rnvn2_temperature_Input = ioio.openAnalogInput(TEMPERATURE_PIN);
			
			m1_Encoder_A_Input = ioio.openDigitalInput(M1_ENCODER_A_PIN, DigitalInput.Spec.Mode.PULL_DOWN);
			m1_Encoder_B_Input = ioio.openDigitalInput(M1_ENCODER_B_PIN, DigitalInput.Spec.Mode.PULL_DOWN);
			m2_Encoder_A_Input = ioio.openDigitalInput(M2_ENCODER_A_PIN, DigitalInput.Spec.Mode.PULL_DOWN);
			m2_Encoder_B_Input = ioio.openDigitalInput(M2_ENCODER_B_PIN, DigitalInput.Spec.Mode.PULL_DOWN);

			super.initIOIOPins(ioio);
		} catch (ConnectionLostException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void writeNewIoioPinValues(final IOIO ioio) throws ConnectionLostException {
			m1_Pwm_Output.setDutyCycle(m1_PWM_DutyCycle_value);
			m1_InA_Output.write(m1_inA_value);
			m1_InB_Output.write(m1_inB_value);

			m2_Pwm_Output.setDutyCycle(m2_PWM_DutyCycle_value);
			m2_InA_Output.write(m2_inA_value);
			m2_InB_Output.write(m2_inB_value);
	}
	
	@Override
	public void readNewIOIOPinValues() throws InterruptedException, ConnectionLostException {
		final boolean m1_encoder_A_state = m1_Encoder_A_Input.read();
		final boolean m1_encoder_B_state = m1_Encoder_B_Input.read();
		final boolean m2_encoder_A_state = m2_Encoder_A_Input.read();
		final boolean m2_encoder_B_state = m2_Encoder_B_Input.read();
		
		/*
		 * Realna wartość napięcia wynosi = wartość_odczytana * 7.58
		 */
		final float m1_current_value = m1_Current_Measure_Input.read();// * 7.58);
		final float m2_current_value = m2_Current_Measure_Input.read();// * 7.58);
		
		final float rnvn2_temperature_value = rnvn2_temperature_Input.read();
		
//		log.debug(TAG, "ENKODER_1_A: " + m1_encoder_A_state);
//		log.debug(TAG, "ENKODER_1_B: " + m1_encoder_B_state);
//		log.debug(TAG, "ENKODER_2_A: " + m2_encoder_A_state);
//		log.debug(TAG, "ENKODER_2_B: " + m2_encoder_B_state);
//		log.debug(TAG, "Prąd M1: " + m1_current_value);
//		log.debug(TAG, "Prad M2: " + m2_current_value);
//		log.debug(TAG, "Napięcie oznaczające temperaturę układu: " + rnvn2_temperature_value);
		if(rnvn2_temperature_value > 0.7) {
			Packet pack = new Packet(AdditionalPacketTypes.CHIP_TEMPERATURE_ALERT);
			LocalBroadcastDispatcher.INSTANCE.sendPacketViaLocalBroadcast(pack, IntentsIdentifiers.ACTION_MESSAGE_TO_CONTROLLER);
		}
	}

	public void setM1_inA_value(boolean m1_inA_value) {
		this.m1_inA_value = m1_inA_value;
	}
	public void setM1_inB_value(boolean m1_inB_value) {
		this.m1_inB_value = m1_inB_value;
	}
	public void setM2_inA_value(boolean m2_inA_value) {
		this.m2_inA_value = m2_inA_value;
	}
	public void setM2_inB_value(boolean m2_inB_value) {
		this.m2_inB_value = m2_inB_value;
	}
	public void setM1_PWM_DutyCycle_value(float m1_PWM_DutyCycle_value) {
		this.m1_PWM_DutyCycle_value = m1_PWM_DutyCycle_value;
	}
	public void setM2_PWM_DutyCycle_value(float m2_PWM_DutyCycle_value) {
		this.m2_PWM_DutyCycle_value = m2_PWM_DutyCycle_value;
	}	
}
