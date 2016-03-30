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
import edu.pw.elka.andromote.andromote.common.asynctasks.RemoteControlAsyncTask;
import edu.pw.elka.andromote.andromote.common.asynctasks.RideAsyncTask;
import edu.pw.elka.andromote.andromote.common.asynctasks.SensorAsyncTask;
import edu.pw.elka.andromote.andromote.common.wrappers.TtsProcessor;
import edu.pw.elka.andromote.andromote.tasks.task3.TaskThree;
import edu.pw.elka.andromote.andromotelogger.AndroMoteLogger;
import edu.pw.elka.andromote.commons.IntentsIdentifiers;
import edu.pw.elka.andromote.commons.Packet;
import edu.pw.elka.andromote.commons.PacketType;
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
    private TaskThree taskThree = new TaskThree(this);

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
        RemoteControlAsyncTask rcAsyncTask = new RemoteControlAsyncTask(AndroMoteMainActivity.this, taskThree);
		rideAsyncTask.executeOnExecutor(executorService);
		sensorAsyncTask.executeOnExecutor(executorService);
        rcAsyncTask.executeOnExecutor(executorService);
	}

	@Override
	protected void onDestroy() {
		Log.d(TAG, "onDestroy");
        ttsProcessor.cleanup();
        taskThree.stop();
		super.onDestroy();
	}

	private void initStopButton() {
		this.stopButton = (Button) findViewById(R.id.stopButton);
		this.stopButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				executorService.shutdownNow();
                ElectronicsController.INSTANCE.execute(new Packet(PacketType.Motion.STOP));
			}
		});
	}

	private void initEngineService() {
		ElectronicDeviceFactory factory = new AndroMote2DeviceFactory();
		ElectronicsController.INSTANCE.init(getApplication(), factory);
        ElectronicsController.INSTANCE.execute(new Packet(Engine.SET_STEPPER_MODE));
		Packet stepDurationPacket = new Packet(Engine.SET_STEP_DURATION);
		stepDurationPacket.setStepDuration(500);
        ElectronicsController.INSTANCE.execute(stepDurationPacket);
	}

	private void stopEngineService() {
		Intent closeService = new Intent(IntentsIdentifiers.ACTION_ENGINES_CONTROLLER);
		stopService(closeService);
	}
}
