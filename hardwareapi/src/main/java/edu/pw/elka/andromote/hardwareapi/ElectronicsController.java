package edu.pw.elka.andromote.hardwareapi;

import android.app.Application;

import edu.pw.elka.andromote.andromotelogger.AndroMoteLogger;
import edu.pw.elka.andromote.commons.Packet;
import edu.pw.elka.andromote.commons.PacketType.Engine;
import edu.pw.elka.andromote.commons.PacketType.Motion;
import edu.pw.elka.andromote.commons.api.exceptions.MobilePlatformException;
import edu.pw.elka.andromote.commons.hardware.devices.ElectronicDeviceFactory;
import edu.pw.elka.andromote.hardwareapi.platform_controller.HardwareApi;

public enum ElectronicsController {
	INSTANCE;
	private static final String TAG = ElectronicsController.class.getSimpleName();
	private final AndroMoteLogger log = new AndroMoteLogger(getClass());
	private HardwareApi hardwareApi = null;
	private ElectronicDeviceFactory factory;


	public void init(Application application, ElectronicDeviceFactory factory) {
		this.hardwareApi = new HardwareApi(application);
		this.factory = factory;
		try {
			hardwareApi.startCommunicationWithDevice();
		} catch (MobilePlatformException e) {
			e.printStackTrace();
		}
		startAndromoteControllerService();
	}

	public void destroy() {
		stopAndromoteControllerService();
	}

	public boolean execute(Packet packet) {
		boolean result;
		try {	
			hardwareApi.sendMessageToDevice(packet);
			result = true;
		} catch (MobilePlatformException e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	public void stopRide() {
		Packet packet = new Packet(Motion.STOP);
		try {
			hardwareApi.sendMessageToDevice(packet);
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (MobilePlatformException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Start i ustawienia początkowe steronika silników.
	 */
	private void startAndromoteControllerService() {
		// ustawienia silników w nowym wątku z powodu asynchronicznego wywołania
		// aktywacji usługi
		Thread t = new Thread() {
			public void run() {
				try {
					Thread.sleep(2000);

					initAndromoteControllerService();
				} catch (InterruptedException e) {
					log.error(TAG, e.getMessage());
				}
			}
		};
		t.start();
	}

	/**
	 * Stop usługi silnikowej
	 */
	private void stopAndromoteControllerService() {
		try {
			hardwareApi.stopCommunicationWithDevice();
		} catch (MobilePlatformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Wstępna konfiguracja ustawień sterownika silników. 
	 */
	private void initAndromoteControllerService() {
		// zmiana czasu trwaniajednego węzła
		Packet motionMode = new Packet(Engine.SET_CONTINUOUS_MODE);
		Packet stepDurationPacket = new Packet(Engine.SET_STEP_DURATION);
		stepDurationPacket.setStepDuration(500);
		Packet speedPacket = new Packet(Engine.SET_SPEED);
		speedPacket.setSpeed(0.8);
		try {
			hardwareApi.sendMessageToDevice(motionMode);
			hardwareApi.sendMessageToDevice(stepDurationPacket);
			hardwareApi.sendMessageToDevice(speedPacket);
		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MobilePlatformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isRideAvailable() {
		return hardwareApi.checkIfConnectionIsActive();
	}

	public ElectronicDeviceFactory getFactory() {
		return factory;
	}
}
