package edu.pw.elka.andromote.commons.api;


import android.app.Application;

import edu.pw.elka.andromote.andromotelogger.AndroMoteLogger;
import edu.pw.elka.andromote.commons.MotionMode;
import edu.pw.elka.andromote.commons.Packet;
import edu.pw.elka.andromote.commons.api.exceptions.BroadcastReceiverClientNotSetException;
import edu.pw.elka.andromote.commons.api.exceptions.MobilePlatformException;
import edu.pw.elka.andromote.commons.hardware.devices.ElectronicDeviceFactory;

/**
 * Abstrakcyjna implementacja API sterowania urządzeniem zewnętrznym będącym
 * platformami mobilnymi. Do implementacji konkretnego rozwiązania należy
 * rozwinąć tę klasę w klasie implementującej. Klasa poza sterowaniem platformą
 * mobilną jest odpowiedzialna za odbieranie wiadomości wysyłanych przez
 * platformę mobilną poprzez imeplementację interfejsu
 * {@link IMessagesFromDeviceReceiver}.
 * 
 * @author Maciej Gzik
 * 
 */
public abstract class AndroMoteMobilePlatformApiAbstract implements IAndroMoteApi, IAndroMoteMobilePlatformApi,
IAndroMoteDeviceReceiverClient, IAndroMoteDeviceDataProvider {
	private static final String ANDROMOTE_API = "ANDROMOTE_API";
	protected final Application application;
	protected AndroMoteLogger logger = new AndroMoteLogger(AndroMoteMobilePlatformApiAbstract.class);
	protected MessagesFromDeviceReceiver mobilePlatformMessageReceiver = null;

	public AndroMoteMobilePlatformApiAbstract(Application application) {
		this.application = application;
		mobilePlatformMessageReceiver = new MessagesFromDeviceReceiver(this, application);
		try {
			mobilePlatformMessageReceiver.startMessagesListener();
		} catch (BroadcastReceiverClientNotSetException e) {
			logger.error(ANDROMOTE_API, e);
			e.printStackTrace();
		}
	}

	@Override
	public boolean stopCommunicationWithDevice() throws MobilePlatformException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean checkIfConnectionIsActive() throws MobilePlatformException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean setMotionMode(MotionMode motionMode) throws MobilePlatformException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean setStepDuration(int stepDuration) throws MobilePlatformException {
		throw new UnsupportedOperationException();
	}

	public Application getApplication() {
		return application;
	}

	@Override
	public void deviceMessageReceived(Packet pack) throws MobilePlatformException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean sendMessageToDevice(IPacket pack) throws MobilePlatformException, UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public IPacket getData(IPacket dataDescriptorPacket) {
		throw new UnsupportedOperationException();
	}

	public boolean startCommunicationWithDevice(ElectronicDeviceFactory deviceFactory)
			throws MobilePlatformException {
		return false;
	}
}
