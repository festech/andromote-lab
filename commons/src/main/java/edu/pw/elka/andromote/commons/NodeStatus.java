package edu.pw.elka.andromote.commons;

/**
 * Klasa zawiera opisy stanów jakie może przyjmować urządzenie AndroMote.
 * 
 * @author Maciej Gzik
 * 
 */
public class NodeStatus {
	/**
	 * Tryb powrotu do punktu startowego. Wszystkie komendy są ignorowane.
	 */
	public static final int CONTROLL_MODE_BACK_TO_START_POINT = 0;

	/**
	 * Tryb konroli poprzez serwer.
	 */
	public static final int CONTROLL_MODE_PC_CONTROLL = 1;

	/**
	 * tryb bt klienta - komendy ruchu są wydawane poprzez serwer BT.
	 */
	public static final int CONTROLL_MODE_BT_CLIENT = 2;

	/**
	 * Tryb serwera BT. Wszystkie komendy otrzymane z serwera pc są przesyłane
	 * do klientów.
	 */
	public static final int CONTROLL_MODE_BT_SERVER = 3;

	/**
	 * Węzeł w trybie roota WIFI. Możliwe jest sterowanie innymi urządzeniami -
	 * info o powiązaniach znajduje się w serwerze.
	 */
	public static final int CONTROLL_MODE_ANDRO_ANDRO_ROOT = 4;

	/**
	 * Węzeł jest sterowany poprzez inny AndroMote.
	 */
	public static final int CONTROLL_MODE_ANDRO_ANDRO_CHILD = 5;

	/**
	 * Połączenie z innym AndroMote jest właśnie zestawiane.
	 */
	public static final int CONTROLL_MODE_ANDRO_ANDRO_CONNECTING = 6;

	/**
	 * Funkcja zamienia wartość statusu na jego nazwę.
	 * @param status
	 * @return
	 */
	public static String toString(int status) {
		if (status == CONTROLL_MODE_BACK_TO_START_POINT)
			return "CONTROLL_MODE_BACK_TO_START_POINT";
		else if (status == CONTROLL_MODE_PC_CONTROLL)
			return "CONTROLL_MODE_PC_CONTROLL";
		else if (status == CONTROLL_MODE_BT_CLIENT)
			return "CONTROLL_MODE_BT_CLIENT";
		else if (status == CONTROLL_MODE_BT_SERVER)
			return "CONTROLL_MODE_BT_SERVER";
		else if (status == CONTROLL_MODE_ANDRO_ANDRO_ROOT)
			return "CONTROLL_MODE_ANDRO_ANDRO_ROOT";
		else if (status == CONTROLL_MODE_ANDRO_ANDRO_CHILD)
			return "CONTROLL_MODE_ANDRO_ANDRO_CHILD";
		else if (status == CONTROLL_MODE_ANDRO_ANDRO_CONNECTING)
			return "CONTROLL_MODE_ANDRO_ANDRO_CONNECTING";
		else
			return "";
	}

}
