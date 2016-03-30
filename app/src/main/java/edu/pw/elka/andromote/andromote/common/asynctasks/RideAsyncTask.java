package edu.pw.elka.andromote.andromote.common.asynctasks;

import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Queue;

import edu.pw.elka.andromote.andromote.common.AndroMoteMainActivity;
import edu.pw.elka.andromote.andromote.common.wrappers.RideScenario;
import edu.pw.elka.andromote.andromote.tasks.task1.TaskOne;
import edu.pw.elka.andromote.commons.Packet;
import edu.pw.elka.andromote.commons.PacketType.Motion;
import edu.pw.elka.andromote.hardwareapi.ElectronicsController;

/**
 *
 * @author Sebastian Luczak
 *
 * Watek asynchroniczny odpowiadajacy za uruchomienie scenariusza
 *
 * -----> Do podstawowych dzialan nie trzeba modyfikowac tego pliku <-----
 *
 */

public class RideAsyncTask extends AsyncTask<Void, Integer, Boolean> {
	private final AndroMoteMainActivity andromoteActivity;
	private RideScenario rideScenario;

	public RideAsyncTask(AndroMoteMainActivity mainActivity) {
		super();
		this.andromoteActivity = mainActivity;
		this.rideScenario = new TaskOne();
		rideScenario.configureMovement();
	}

	@Override
	protected Boolean doInBackground(Void... arg) {
		Queue<Packet> movementSteps = rideScenario.getMovementSteps();
		try {
			final int freezeTimeInSeconds = rideScenario.getFreezeTimeInSeconds();
			andromoteActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(andromoteActivity, "Freeze time: " + freezeTimeInSeconds + " sec", Toast.LENGTH_SHORT).show();
				}
			});
			Thread.sleep(freezeTimeInSeconds * 1000);

			for(Packet packet : movementSteps) {
				ElectronicsController.INSTANCE.execute(packet);
				Thread.sleep(packet.getStepDuration());
				ElectronicsController.INSTANCE.execute(new Packet(Motion.STOP));
			}
		} catch (InterruptedException e) {
			return false;
		} finally {
			ElectronicsController.INSTANCE.execute(new Packet(Motion.STOP));
		}
		return true;
	}

	@Override
	protected void onPostExecute(Boolean wasCompleted) {
		if(!wasCompleted) {
			System.out.println("Execution interrupted");
			Toast.makeText(andromoteActivity, "Task one - stopped", Toast.LENGTH_SHORT).show();
		}
	}
}
