package edu.pw.elka.andromote.commons.api;


import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import edu.pw.elka.andromote.andromotelogger.AndroMoteLogger;
import edu.pw.elka.andromote.commons.IntentsFieldsIdentifiers;
import edu.pw.elka.andromote.commons.IntentsIdentifiers;
import edu.pw.elka.andromote.commons.Packet;
import edu.pw.elka.andromote.commons.api.exceptions.BroadcastReceiverClientNotSetException;
import edu.pw.elka.andromote.commons.api.exceptions.MobilePlatformException;

/**
 * Podstawowa implementacja interfejsu {@link IMessagesFromDeviceReceiver}
 * pozwalająca na odbieranie wiadomości z urzadzenia zewnętrznego w projekcie
 * AndroMote. W przypadku chęci dalszego rozwoju należy dodać kolejne
 * identyfikatory akcji Intencji przy rejestracji filtrów BroadcastReceivera.
 * 
 * @author Maciej Gzik
 * 
 */
public class MessagesFromDeviceReceiver extends BroadcastReceiver implements IMessagesFromDeviceReceiver {
	private static final String TAG = MessagesFromDeviceReceiver.class.getName().toString();
	private final IAndroMoteDeviceReceiverClient client;
	private final Application application;
	AndroMoteLogger logger = new AndroMoteLogger(MessagesFromDeviceReceiver.class);

	public MessagesFromDeviceReceiver(IAndroMoteDeviceReceiverClient client, Application application) {
		this.application = application;
		this.client = client;
	}

	@Override
	public boolean startMessagesListener() throws BroadcastReceiverClientNotSetException {
		if (this.client == null || this.application == null)
			throw new BroadcastReceiverClientNotSetException();
		else {
			IntentFilter filter = new IntentFilter(IntentsIdentifiers.ACTION_ENGINE_STEP);
			filter.addAction(IntentsIdentifiers.ACTION_MESSAGE_FROM_DEVICE);
			LocalBroadcastManager.getInstance(application).registerReceiver(this, filter);
			return true;
		}
	}

	@Override
	public boolean stopMessagesListener() throws BroadcastReceiverClientNotSetException {
		if (this.application == null)
			throw new BroadcastReceiverClientNotSetException();
		else {
			LocalBroadcastManager.getInstance(application).unregisterReceiver(this);
			return true;
		}
	}

	@Override
	public void mobilePlatformMessageReceived(Packet pack) throws BroadcastReceiverClientNotSetException {
		if (client == null) {
			throw new BroadcastReceiverClientNotSetException();
		}
		else {
			try {
				this.client.deviceMessageReceived(pack);
			} catch (MobilePlatformException e) {
				logger.error(TAG, e);
			}
		}
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			if (this.client != null) {
				Packet packet = (Packet) intent.getSerializableExtra(IntentsFieldsIdentifiers.EXTRA_PACKET);
				mobilePlatformMessageReceived(packet);
			} else {
				throw new BroadcastReceiverClientNotSetException();
			}
		} catch (BroadcastReceiverClientNotSetException e) {
			logger.error(TAG, e);
		}
	}
}
