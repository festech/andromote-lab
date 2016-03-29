package edu.pw.elka.andromote.hardwareapi.ioio_service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.LinkedList;

import edu.pw.elka.andromote.andromotelogger.AndroMoteLogger;
import edu.pw.elka.andromote.commons.IntentsIdentifiers;
import edu.pw.elka.andromote.commons.MotionMode;
import edu.pw.elka.andromote.commons.Packet;
import edu.pw.elka.andromote.commons.PacketType.Connection;
import edu.pw.elka.andromote.commons.PacketType.Engine;
import edu.pw.elka.andromote.commons.PacketType.IPacketType;
import edu.pw.elka.andromote.commons.PacketType.Motion;
import edu.pw.elka.andromote.commons.api.LocalBroadcastDispatcher;
import edu.pw.elka.andromote.commons.api.exceptions.UnknownDeviceException;
import edu.pw.elka.andromote.commons.hardware.devices.DeviceSettings;
import edu.pw.elka.andromote.commons.hardware.devices.ElectronicDevice;
import edu.pw.elka.andromote.commons.stepper.Step;
import edu.pw.elka.andromote.hardwareapi.ElectronicsController;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOService;

public class IOIOLooperManagerService extends IOIOService {
	private static final String TAG = IOIOLooperManagerService.class.getName();
	/**
	 * Flaga informująca o tym czy wykonywana jest operacja np. skrętu o
	 * określony kąt, zawracania. Gdy flaga jest aktywna komendy sterujące
	 * odbierane przez serwis są ignorowane do momentu zakończenia wykoywanaego
	 * zadania - na każde zapytanie jest wysyłana intencja zwrotna.
	 */
	public static boolean isOperationExecuted = false;
	private final IBinder binder = new LocalBinder();
	AndroMoteIOIOLooper looper = null;
	private AndroMoteLogger log = new AndroMoteLogger(IOIOLooperManagerService.class);
	private LocalBroadcastDispatcher localBroadcastDispatcher;
	//	private Compass compass = null;
	/**
	 * Pojazd Andromote
	 */
	private DeviceSettings settings;
	private LinkedList<Step> stepsQueue;

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		log.error(TAG, "onStart(); is depreciated!");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
//		FIXME Moved to main application
//		AndroMoteLogger.ConfigureLogger("AndroMoteClient.log");
		log.debug(TAG, "engineService; onStartCommand(); startId=" + startId);
		localBroadcastDispatcher = LocalBroadcastDispatcher.INSTANCE;
		localBroadcastDispatcher.init(getApplicationContext());
		initStepsQueue();
		super.onStartCommand(intent, flags, startId);
		return Service.START_REDELIVER_INTENT;
	}

	@Override
	protected IOIOLooper createIOIOLooper() {
		log.debug(TAG, "EnginesControllerService: creating IOIOLooper");
		try {
			ElectronicDevice hardware = ElectronicsController.INSTANCE.getFactory().createRobotPlatform();
			settings = hardware.getSettings();
			looper = new AndroMoteIOIOLooper(this, hardware);
			log.debug(TAG, "looper created...");
			log.debug(TAG, "setting looper in service...");
		} catch (ConnectionLostException e) {
			log.error(TAG, e);
		} catch (UnknownDeviceException e) {
			log.error(TAG, e);
			this.stopEngineServiceOnError();
		} catch (InterruptedException e) {
			log.error(TAG, e);
		}
		return looper;
	}

	public boolean isConnectedToIOIO() {
		boolean result = false;
		if(looper != null) {
			result = looper.isDeviceConnected();
		}
		return result;
	}

	/**
	 * Receiver wiadomości dla sterownika silnikami.
	 *
	 * @author Maciej Gzik
	 * @author Sebastian Łuczak
	 */
	public void interpretPacket(Packet inputPacket) {
		log.debug(TAG, "engine service broadcast received: " + inputPacket.getPacketType());

		// odrzucenie pakietów Engine i Motion w przypadku wykonywania
		// zadania
		boolean isMotionOrEnginePacket = inputPacket.getPacketType() instanceof Motion || inputPacket.getPacketType() instanceof Engine;
		MotionMode motionMode = settings.getMotionMode();
		if (motionMode.equals(MotionMode.MOTION_MODE_CONTINUOUS) && isOperationExecuted && isMotionOrEnginePacket) {
			log.debug(TAG, "Engine Service: incoming packet instanceof: " + inputPacket.getPacketType()
					+ " rejected - operationIsBeingExecuted");
			Packet refusePacket = new Packet(Engine.MOTION_COMMAND_REFUSE_OTHER_ACTION_EXECUTED);
			refusePacket.setPacket(inputPacket);
			localBroadcastDispatcher.sendPacketViaLocalBroadcast(refusePacket, IntentsIdentifiers.ACTION_MESSAGE_TO_CONTROLLER);
			return;
		}

		// zmiany ustawień dotyczących ruchu
		// pakiety ruchu
		if (inputPacket.getPacketType() instanceof Motion) {
			if (motionMode.equals(MotionMode.MOTION_MODE_CONTINUOUS)) {
				interpretMotionPacketContinuous(inputPacket);
			} else if (motionMode.equals(MotionMode.MOTION_MODE_STEPPER)) {
				interpretMotionPacketStepper(inputPacket);
			}
		} else if (inputPacket.getPacketType() == Engine.SET_SPEED) {
			log.debug(TAG, "setting speed to value: " + inputPacket.getSpeed());
			settings.setSpeed(inputPacket.getSpeed());
		} else if (inputPacket.getPacketType() == Engine.SET_SPEED_B) {
			log.debug(TAG, "setting speed to value: " + inputPacket.getSpeedB());
			settings.setSpeedB(inputPacket.getSpeedB());

		} else if (inputPacket.getPacketType() == Engine.SET_STEPPER_MODE) {
			if (motionMode.equals(MotionMode.MOTION_MODE_CONTINUOUS)) {
				log.debug(TAG, "zmiana trybu ruchu na krokowy");
				setMotionMode(MotionMode.MOTION_MODE_STEPPER);
				// zerowanie kolejki ruchów
				if (isStepsQueueNotEmpty()) {
					stepsQueue.clear();
				}
			}
		} else if (inputPacket.getPacketType() == Engine.SET_CONTINUOUS_MODE) {
			if (motionMode.equals(MotionMode.MOTION_MODE_STEPPER)) {
				log.debug(TAG, "zmiana trybu ruchu na ciągły");
				setMotionMode(MotionMode.MOTION_MODE_CONTINUOUS);
				if (isStepsQueueNotEmpty()) {
					stepsQueue = null;
				}
			}
		} else if (inputPacket.getPacketType() == Engine.SET_STEP_DURATION) {
			log.debug(TAG,
					"EnginesControllerService: zmiana czasu trwania kroku na : " + inputPacket.getStepDuration());
			settings.setStepDuration(inputPacket.getStepDuration());
		} else if (inputPacket.getPacketType() == Connection.NODE_CONNECTION_STATUS_REQUEST) {
			log.debug(TAG, "engine service: sending motion mode: " + motionMode);
			Packet pack = null;
			if (motionMode.equals(MotionMode.MOTION_MODE_CONTINUOUS)) {
				pack = new Packet(Engine.CONTINUOUS_MODE_RESPONSE);
			} else if (motionMode.equals(MotionMode.MOTION_MODE_STEPPER)) {
				pack = new Packet(Engine.STEPPER_MODE_RESPONSE);
			}
			localBroadcastDispatcher.sendPacketViaLocalBroadcast(pack, IntentsIdentifiers.ACTION_MESSAGE_TO_CONTROLLER);
		}
	}

	/**
	 * Pobranie kolejnego kroku z kolejki.
	 */
	public synchronized Step getNextStep() {
		if (settings.getMotionMode().equals(MotionMode.MOTION_MODE_STEPPER) && stepsQueue != null
				&& stepsQueue.size() > 0) {
			Step step = stepsQueue.getFirst();
			stepsQueue.removeFirst();
			log.debug(TAG, "EnginesControllerService: pobrano krok z listy kroków:" + step.getStepType());
			return step;
		} else {
			return null;
		}
	}

	private boolean isStepsQueueNotEmpty() {
		return stepsQueue != null && stepsQueue.size() > 0;
	}

	/**
	 * inicjalizacja kolejki kroków
	 */
	private void initStepsQueue() {
		if (this.stepsQueue == null)
			this.stepsQueue = new LinkedList<Step>();
	}

	private void setMotionMode(MotionMode motionMode) {
		Packet pack = null;
		settings.setMotionMode(motionMode);
		if (motionMode.equals(MotionMode.MOTION_MODE_CONTINUOUS)) {
			pack = new Packet(Engine.CONTINUOUS_MODE_RESPONSE);
		} else if (motionMode.equals(MotionMode.MOTION_MODE_STEPPER)) {
			pack = new Packet(Engine.STEPPER_MODE_RESPONSE);
		}
		log.debug(TAG, "engineService: set motionMode=" + motionMode + "; with engine packet broadcast");
		localBroadcastDispatcher.sendPacketViaLocalBroadcast(pack, IntentsIdentifiers.ACTION_MESSAGE_TO_CONTROLLER);
	}

	private void interpretMotionPacketContinuous(Packet inputPacket) {
		if (inputPacket.getSpeed() >= settings.getMIN_SPEED()) {
			settings.setSpeed(inputPacket.getSpeed());
		}
		looper.executePacket(inputPacket);
		log.debug(TAG, "engine service received: " + inputPacket.getPacketType());

	}

	private void interpretMotionPacketStepper(Packet inputPacket) {
		IPacketType packetType = inputPacket.getPacketType();
		if(!(packetType instanceof Motion)) {
			log.error(TAG, packetType + " is not a motion packet!");
			return;
		}
		Motion motionPacketType = (Motion)packetType;
		log.debug(TAG, "EnginesControllerService; add packet to stepsList: " + motionPacketType);
		if (looper != null) {
			if (motionPacketType == Motion.MOVE_LEFT_FORWARD) {
				stepsQueue.addLast(new Step(motionPacketType));
			} else if (motionPacketType == Motion.MOVE_FORWARD) {
				stepsQueue.addLast(new Step(motionPacketType));
			} else if (motionPacketType == Motion.MOVE_RIGHT_FORWARD) {
				stepsQueue.addLast(new Step(motionPacketType));
			} else if (motionPacketType == Motion.MOVE_LEFT) {
				stepsQueue.addLast(new Step(motionPacketType));
			} else if (motionPacketType == Motion.STOP) {
				log.debug(TAG, "PacketType.STOP w trybie steppera nie jest dodawany do kolejki!!!");
			} else if (motionPacketType == Motion.MOVE_RIGHT) {
				stepsQueue.addLast(new Step(motionPacketType));
			} else if (motionPacketType == Motion.MOVE_LEFT_BACKWARD) {
				stepsQueue.addLast(new Step(motionPacketType));
			} else if (motionPacketType == Motion.MOVE_BACKWARD) {
				stepsQueue.addLast(new Step(motionPacketType));
			} else if (motionPacketType == Motion.MOVE_RIGHT_BACKWARD) {
				stepsQueue.addLast(new Step(motionPacketType));
			} else if (motionPacketType == Motion.HALT) {
				stepsQueue.clear();
			}
		}
	}

	/**
	 * Funkcja wywoływana w przypadku błędu inicjalizacji, błędu działania
	 * serwisu silników. kończy działanie serwisu i wysyła informację w pakiecie
	 * typu
	 */
	private void stopEngineServiceOnError() {
		Packet stopServicePacket = new Packet(Engine.ENGINE_SERVICE_STOP_ERROR);
		localBroadcastDispatcher.sendPacketViaLocalBroadcast(stopServicePacket, IntentsIdentifiers.ACTION_MESSAGE_TO_CONTROLLER);
		this.stopSelf();
	}

	/**
	 * Binder pozwalający na przypisanie usługi EnginesControllerService do innej usługi/aktywności
	 * wewnątrz aplikacji. Pozwala na bezpośrednie wywoływanie metod publicznych usługi
	 * @author Sebastian Łuczak
	 *
	 */
	public class LocalBinder extends Binder {
		public IOIOLooperManagerService getService() {
			return IOIOLooperManagerService.this;
		}
	}
}
