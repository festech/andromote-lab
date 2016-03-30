package edu.pw.elka.andromote.andromote.common.asynctasks;

import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Queue;

import edu.pw.elka.andromote.andromote.common.AndroMoteMainActivity;
import edu.pw.elka.andromote.andromote.tasks.task1.TaskOne;
import edu.pw.elka.andromote.commons.Packet;
import edu.pw.elka.andromote.commons.PacketType.Motion;

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
	private edu.pw.elka.andromote.andromote.tasks.task1.TaskOne taskOne;

	public RideAsyncTask(AndroMoteMainActivity mainActivity) {
		super();
		this.andromoteActivity = mainActivity;
		this.taskOne = new edu.pw.elka.andromote.andromote.tasks.task1.TaskOne();
		taskOne.configureMovement();
	}

	@Override
	protected Boolean doInBackground(Void... arg) {
		Queue<Packet> movementSteps = taskOne.getMovementSteps();
		try {
			final int freezeTimeInSeconds = taskOne.getFreezeTimeInSeconds();
			andromoteActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(andromoteActivity, "Freeze time: " + freezeTimeInSeconds + " sec", Toast.LENGTH_SHORT).show();
				}
			});
			Thread.sleep(freezeTimeInSeconds * 1000);

			for(Packet packet : movementSteps) {
				andromoteActivity.sendPacketToEngineService(packet);
				Thread.sleep(packet.getStepDuration());
				andromoteActivity.sendPacketToEngineService(new Packet(Motion.STOP));
			}
		} catch (InterruptedException e) {
			andromoteActivity.sendPacketToEngineService(new Packet(Motion.STOP));
			return false;
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
