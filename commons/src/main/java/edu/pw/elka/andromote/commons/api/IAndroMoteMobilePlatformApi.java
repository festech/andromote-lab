package edu.pw.elka.andromote.commons.api;

import edu.pw.elka.andromote.commons.MotionMode;
import edu.pw.elka.andromote.commons.api.exceptions.MobilePlatformException;

/**
 * Interfejs opisujący przyjęte funkcjonalności jakie powinna posiadać
 * implementacja API dla połączenia telefonu z platformą mobilną jaką jest np.
 * realizowany projekt AndroMote.
 * 
 * @author Maciej Gzik
 * @author Sebastian Łuczak
 * 
 */
public interface IAndroMoteMobilePlatformApi {
	public boolean setMotionMode(MotionMode motionMode) throws MobilePlatformException, UnsupportedOperationException;

	public boolean setStepDuration(int stepDuration) throws MobilePlatformException, UnsupportedOperationException;

	public boolean stopMobilePlatform() throws MobilePlatformException, UnsupportedOperationException;
}
