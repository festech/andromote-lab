package edu.pw.elka.andromote.commons.api;


import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;

import edu.pw.elka.andromote.andromotelogger.AndroMoteLogger;
import edu.pw.elka.andromote.commons.IntentsFieldsIdentifiers;
import edu.pw.elka.andromote.commons.Packet;

public enum LocalBroadcastDispatcher {
	INSTANCE;

	private Context context;
	private String TAG = LocalBroadcastDispatcher.class.getSimpleName();
	private AndroMoteLogger log = new AndroMoteLogger(getClass());

	public void init(Context context) {
		this.context = context;
	}

	/**
	 * Pozwala na przesłanie pakietu zawierającego wiadomość do kontrolera
	 * @param pack
	 */
	public void sendPacketViaLocalBroadcast(Packet pack, String intentAction) {
		if(context == null) {
			log.error(TAG, "ReturnToControllerPacketDispatcher was not initialized!");
		} else {
			Intent intentBroadcast = new Intent(intentAction);
			intentBroadcast.putExtra(IntentsFieldsIdentifiers.EXTRA_PACKET, (Parcelable) pack);
			LocalBroadcastManager.getInstance(context).sendBroadcast(intentBroadcast);
		}
	}

//	/**
//	 * Broadcast pakietu zawierającego informacje o błędzie w trakcie wykonywania kroku.
//	 * 
//	 * @param responsePacket
//	 *            pakiet z informacjami o wykonanym kroku
//	 */
//	private void sendStepExecutionErrorBroadcast(Motion takenStep) {
//		log.debug(TAG, "engineController looper: broadcast informacji o błędzie w trakcie wykonywania kroku: " + takenStep);
//		Packet responsePacket = new Packet(Engine.MOTION_COMMAND_REFUSED_SENSOR_ERROR);
//		responsePacket.setStepDirection(takenStep);
//		sendPacketToController(responsePacket, IntentsIdentifiers.ACTION_ENGINE_STEP);
//	}
//	private void setSendStepExecutedPacket(boolean sendStepExecutedPacket) {
//		log.debug(TAG, "EnginesControllerService: set sendStepExecutedPacket. New value: " + sendStepExecutedPacket);
//		this.sendStepExecutedPacket = sendStepExecutedPacket;
//	}
//	private boolean isSendStepExecutedPacket() {
//		return sendStepExecutedPacket;
//	}

}

