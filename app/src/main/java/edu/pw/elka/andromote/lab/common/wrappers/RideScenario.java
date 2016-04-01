package edu.pw.elka.andromote.lab.common.wrappers;

import java.util.LinkedList;
import java.util.Queue;

import edu.pw.elka.andromote.commons.Packet;


public abstract class RideScenario {
    private final int freezeTimeInSeconds;
    private Queue<Packet> movementSteps = new LinkedList<Packet>();

    protected RideScenario(int freezeTimeInSeconds) {
        this.freezeTimeInSeconds = freezeTimeInSeconds;
    }

    public abstract void configureMovement();

    protected void addStep(Packet packet) {
        movementSteps.add(packet);
    }

    public Queue<Packet> getMovementSteps() {
        return movementSteps;
    }

    public int getFreezeTimeInSeconds() {
        return freezeTimeInSeconds;
    }
}
