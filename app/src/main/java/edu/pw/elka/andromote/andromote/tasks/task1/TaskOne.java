package edu.pw.elka.andromote.andromote.tasks.task1;

import edu.pw.elka.andromote.andromote.common.wrappers.PacketBuilder;
import edu.pw.elka.andromote.andromote.common.wrappers.RideScenario;
import edu.pw.elka.andromote.commons.PacketType.Motion;

/**
 * Wyjedz platforma z punktu startowego i dojedz do lokalizacji pomiarowej
 */
public class TaskOne extends RideScenario {
	// Wartosc okreslajaca po jakim czasie od uruchomienia aplikacji rozpocznie sie jazda platformy
	public static final int FREEZE_TIME_IN_SECONDS = 5;

	public TaskOne() {
		super(FREEZE_TIME_IN_SECONDS);
	}

	/**
	 * W tej metodzie zaprogramuj przejazd platformy z miejsca poczatkowego az do punktu pojazdu
	 * Pamietaj, zeby nie przejezdzac po liniach miejsca startowego!
	 */
    @Override
	public void configureMovement() {
		/**
		 * Przykladowe komendy przemieszczania pojazdu
		 * Mozesz je zastapic wlasnymi wywolaniami
		 */
		addStep(PacketBuilder.newPacket(Motion.MOVE_FORWARD)
                .withSpeed(0.5f)
                .withStepDuration(2000)
				.create());
        addStep(PacketBuilder.newPacket(Motion.MOVE_BACKWARD)
				.create());
        addStep(PacketBuilder.newPacket(Motion.MOVE_LEFT)
				.create());
		addStep(PacketBuilder.newPacket(Motion.MOVE_RIGHT)
				.create());
	}
}
