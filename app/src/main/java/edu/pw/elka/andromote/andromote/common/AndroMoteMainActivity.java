package edu.pw.elka.andromote.andromote.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import org.apache.log4j.BasicConfigurator;

import andromote.hello.world.R;
import edu.pw.elka.andromote.andromotelogger.AndroMoteLogger;
import edu.pw.elka.andromote.commons.IntentsIdentifiers;
import edu.pw.elka.andromote.commons.Packet;
import edu.pw.elka.andromote.commons.PacketType.Engine;
import edu.pw.elka.andromote.commons.PacketType.Motion;
import edu.pw.elka.andromote.commons.hardware.devices.ElectronicDeviceFactory;
import edu.pw.elka.andromote.devices.andromote_v2.AndroMote2DeviceFactory;
import edu.pw.elka.andromote.hardwareapi.ElectronicsController;

/**
 * Aplikacja HelloWorld dla AndroMote.
 *
 * @author Maciej Gzik
 * @author Sebastian Luczak (modyfikacje)
 *
 * -----> Do podstawowych dzialan nie trzeba modyfikowac tego pliku <-----
 *
 */
public class AndroMoteMainActivity extends Activity {
	AndroMoteLogger logger = new AndroMoteLogger(AndroMoteMainActivity.class);
	private String TAG = this.getClass().getSimpleName();
	private Button startButton = null;
	private Button stopButton = null;
	private MovementScenarioExecutor scenarioExecutor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		BasicConfigurator.configure();
		initEngineService();
		initStartButton();
		initStopButton();
	}

	@Override
	protected void onDestroy() {
		Log.d(TAG, "onDestroy");
		stopEngineService();
		super.onDestroy();
	}


	/**
	 * Wyslanie pakietu do serwisu silnikow.
	 * 
	 * @param packet
	 *            Wysylany pakiet {@link Packet}
	 */
	public void sendPacketToEngineService(Packet packet) {
		ElectronicsController.INSTANCE.execute(packet);
	}

	/**
	 * Inicjalizacja przycisku start
	 */
	private void initStartButton() {
		this.startButton = (Button) findViewById(R.id.startButton);
		this.startButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				scenarioExecutor = new MovementScenarioExecutor(AndroMoteMainActivity.this);
				scenarioExecutor.execute();
			}
		});
	}
	
	/**
	 * Inicjalizacja przycisku stop
	 */
	private void initStopButton() {
		this.stopButton = (Button) findViewById(R.id.stopButton);
		this.stopButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sendPacketToEngineService(new Packet(Motion.STOP));
				stopEngineService();
				initEngineService();
			}
		});
	}

	/**
	 * Wstepna konfiguracja ustawien sterownika silnikow. Tryb ruchu - krokowy.
	 * Czas trwania jednego kroku - 500 ms. Predkosc tylnego silnika - 0,8.
	 */
	private void initEngineService() {
		ElectronicDeviceFactory factory = new AndroMote2DeviceFactory();
		ElectronicsController.INSTANCE.init(getApplication(), factory);
		sendPacketToEngineService(new Packet(Engine.SET_STEPPER_MODE));
		// zmiana czasu trwania jednego kroku
		Packet stepDurationPacket = new Packet(Engine.SET_STEP_DURATION);
		stepDurationPacket.setStepDuration(500);
		sendPacketToEngineService(stepDurationPacket);
	}

	/**
	 * Zatrzymanie serwisu kontroli silnikow. Po zakonczeniu dzialania aplikacji
	 * konieczne jest zatrzymanie sterownika silnikow - najlepiej w metodzie
	 * onDestroy aktywnosci. Wynika to z koniecznosci zamkniecia polaczenia z
	 * mikrokontrolerem przed kolejna proba polaczenia.
	 */
	private void stopEngineService() {
		Intent closeService = new Intent(IntentsIdentifiers.ACTION_ENGINES_CONTROLLER);
		stopService(closeService);
	}
}
