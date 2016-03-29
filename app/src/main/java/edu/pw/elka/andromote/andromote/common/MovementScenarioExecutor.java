package edu.pw.elka.andromote.andromote.common;

import android.os.AsyncTask;

import java.util.Queue;

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

public class MovementScenarioExecutor extends AsyncTask<Void, Void, Void> {
	private final AndroMoteMainActivity andromoteActivity;	
	private TaskOne moveToMeasurementsArea;
	
	public MovementScenarioExecutor(AndroMoteMainActivity mainActivity) {
		super();
		this.andromoteActivity = mainActivity;
		this.moveToMeasurementsArea = new TaskOne();
		moveToMeasurementsArea.configureMovement();
	}
	
	@Override
	protected Void doInBackground(Void... arg) {
		Queue<Packet> movementSteps = moveToMeasurementsArea.getMovementSteps();
		for(Packet packet : movementSteps) {
			andromoteActivity.sendPacketToEngineService(packet);
			try {
				Thread.sleep(packet.getStepDuration());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			andromoteActivity.sendPacketToEngineService(new Packet(Motion.STOP));
		}
		return null;
	}
}
