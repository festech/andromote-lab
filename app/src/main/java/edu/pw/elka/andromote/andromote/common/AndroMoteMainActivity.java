package edu.pw.elka.andromote.andromote.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import org.apache.log4j.BasicConfigurator;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import andromote.hello.world.R;
import edu.pw.elka.andromote.andromote.common.asynctasks.RideAsyncTask;
import edu.pw.elka.andromote.andromote.common.asynctasks.SensorAsyncTask;
import edu.pw.elka.andromote.andromote.common.wrappers.TtsProcessor;
import edu.pw.elka.andromote.andromotelogger.AndroMoteLogger;
import edu.pw.elka.andromote.commons.IntentsIdentifiers;
import edu.pw.elka.andromote.commons.Packet;
import edu.pw.elka.andromote.commons.PacketType.Engine;
import edu.pw.elka.andromote.commons.hardware.devices.ElectronicDeviceFactory;
import edu.pw.elka.andromote.devices.andromote_v2.AndroMote2DeviceFactory;
import edu.pw.elka.andromote.hardwareapi.ElectronicsController;

/*
 * -----> Do podstawowych dzialan nie trzeba modyfikowac tego pliku <-----
 */
public class AndroMoteMainActivity extends Activity {
	AndroMoteLogger logger = new AndroMoteLogger(AndroMoteMainActivity.class);
	private String TAG = this.getClass().getSimpleName();
	private Button stopButton = null;
    private TtsProcessor ttsProcessor;

	private final BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
	private final ExecutorService executorService = new ThreadPoolExecutor(1, 1, 200, TimeUnit.MILLISECONDS, taskQueue);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		BasicConfigurator.configure();
		initEngineService();
		initStopButton();
	}

	@Override
	protected void onResume() {
		super.onResume();
        ttsProcessor = new TtsProcessor(this);
        startScenario();
	}

	private void startScenario() {
		RideAsyncTask rideAsyncTask = new RideAsyncTask(AndroMoteMainActivity.this);
		SensorAsyncTask sensorAsyncTask = new SensorAsyncTask(AndroMoteMainActivity.this, ttsProcessor);
		rideAsyncTask.executeOnExecutor(executorService);
		sensorAsyncTask.executeOnExecutor(executorService);
	}

	@Override
	protected void onDestroy() {
		Log.d(TAG, "onDestroy");
        ttsProcessor.cleanup();
//		stopEngineService();
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

	private void initStopButton() {
		this.stopButton = (Button) findViewById(R.id.stopButton);
		this.stopButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				executorService.shutdownNow();
			}
		});
	}

	private void initEngineService() {
		ElectronicDeviceFactory factory = new AndroMote2DeviceFactory();
		ElectronicsController.INSTANCE.init(getApplication(), factory);
		sendPacketToEngineService(new Packet(Engine.SET_STEPPER_MODE));
		// zmiana czasu trwania jednego kroku
		Packet stepDurationPacket = new Packet(Engine.SET_STEP_DURATION);
		stepDurationPacket.setStepDuration(500);
		sendPacketToEngineService(stepDurationPacket);
	}

	private void stopEngineService() {
		Intent closeService = new Intent(IntentsIdentifiers.ACTION_ENGINES_CONTROLLER);
		stopService(closeService);
	}
}
