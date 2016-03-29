package edu.pw.elka.andromote.commons;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import edu.pw.elka.andromote.commons.api.IPacket;

/**
 * Kontener danych wymienianych pomiędzy modułami aplikacji, węzłami AndroMote
 * oraz serwerem. Aby obiekt Packet mógł być transportowany w obeiektach Intent
 * wymagana jest implementacja interfejsu Parcelable. Dla transmisji poprzez
 * obiekty Socket kalsa implementuje interfejs Seriazlizable. W zależności od
 * przesyłanego rodzaju pakietu należy uzupełnić odpowiednie pola, które będą
 * następnie odczytywane w punkcie docelowym (odbiorczym). To, które pola
 * powinny zostać uzupelnione zostało zdefiniowane w klasie {@link PacketType} w
 * opisach poszczególnych typów pakietów.
 * 
 * 
 * @author Maciej Gzik
 * 
 */
public class Packet implements Serializable, Parcelable, IPacket {

	public static final Creator<Packet> CREATOR = new Creator<Packet>() {

		public Packet createFromParcel(Parcel pc) {
			return new Packet(pc);
		}

		public Packet[] newArray(int size) {
			return new Packet[size];
		}

	};
	private static final long serialVersionUID = 1L;
	private PacketType.IPacketType packetType = null;
	/**
	 * Przyjmuje wartości od 0 do 1.
	 */
	private double speed = 0.0;
	private double speedB = 0.0;
	private PacketType.IPacketType stepDirection = null;
	private long stepDuration = 600;
	private Packet packet = null;
//	private ArrayList<String> devicesList = null;
	private int nodeStatus = 0;
	private int oldState = 0;
	private int newState = 0;
	private int bearing = 0;

	public Packet(PacketType.IPacketType packetType) {
		this.packetType = packetType;
	}

	@SuppressWarnings("unchecked")
	public Packet(Parcel pc) {
		this.packetType = (PacketType.IPacketType) pc.readSerializable();
		this.speed = pc.readDouble();
		this.speedB = pc.readDouble();
//		this.stepDirection = (PacketType.IPacketType) pc.readSerializable();
		this.stepDuration = pc.readLong();
		this.packet = (Packet) pc.readSerializable();
//		this.devicesList = (ArrayList<String>) pc.readArrayList(null);
		this.nodeStatus = pc.readInt();
		this.oldState = pc.readInt();
		this.newState = pc.readInt();
		this.bearing = pc.readInt();
	}

	public static Packet deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
		ByteArrayInputStream b = new ByteArrayInputStream(bytes);
		ObjectInputStream o = new ObjectInputStream(b);
		return (Packet) o.readObject();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeSerializable((Serializable) packetType);
		dest.writeDouble(speed);
		dest.writeDouble(speedB);
//		dest.writeSerializable((Serializable) stepDirection);
		dest.writeLong(stepDuration);
		dest.writeSerializable(packet);
//		dest.writeList(devicesList);
		dest.writeInt(nodeStatus);
		dest.writeInt(oldState);
		dest.writeInt(newState);
		dest.writeInt(bearing);

	}

	public byte[] serialize() throws IOException {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		ObjectOutputStream o = new ObjectOutputStream(b);
		o.writeObject(this);
		return b.toByteArray();
	}

//	public ArrayList<String> getDevicesList() {
//		return devicesList;
//	}
//
//	public void setDevicesList(ArrayList<String> pairedDevices) {
//		this.devicesList = pairedDevices;
//	}

//	public IPacketType getStepDirection() {
//		return stepDirection;
//	}

//	public void setStepDirection(IPacketType stepDirection) {
//		this.stepDirection = stepDirection;
//	}

	public int getNodeStatus() {
		return nodeStatus;
	}

	public void setNodeStatus(int nodeStatus) {
		this.nodeStatus = nodeStatus;
	}

	public int getOldState() {
		return oldState;
	}

	public void setOldState(int oldState) {
		this.oldState = oldState;
	}

	public int getNewState() {
		return newState;
	}

	public void setNewState(int newState) {
		this.newState = newState;
	}

	public PacketType.IPacketType getPacketType() {
		return packetType;
	}

	public void setPacketType(PacketType.IPacketType packetType) {
		this.packetType = packetType;
	}

	public double getSpeed() {
		return speed;
	}

	/**
	 * Zmiana prędkości silników w przesyłanym pakiecie.
	 * 
	 * @param speed
	 *            Prędkość w zakresie: 0 - 1.
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getSpeedB() {
		return speedB;
	}

	public void setSpeedB(double speedB) {
		this.speedB = speedB;
	}

	public long getStepDuration() {
		return stepDuration;
	}

	/**
	 * Zmiana czasu trwania kroku w ms.
	 * 
	 * @param stepDuration
	 */
	public void setStepDuration(long stepDuration) {
		this.stepDuration = stepDuration;
	}

	public Packet getPacket() {
		return packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}

	public int getBearing() {
		return bearing;
	}

	public void setBearing(int bearing) {
		this.bearing = bearing;
	}

}
