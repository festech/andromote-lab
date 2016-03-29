package edu.pw.elka.andromote.commons.api.exceptions;

public class BroadcastReceiverClientNotSetException extends Exception {

	private static final long serialVersionUID = 1L;

	public BroadcastReceiverClientNotSetException() {
		super("Client for Andro Mote Device Broadcast Receiver is null. Set client before after initializing Receiver!");
	}


}
