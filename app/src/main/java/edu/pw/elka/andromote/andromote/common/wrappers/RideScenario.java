package edu.pw.elka.andromote.andromote.common.wrappers;

import java.util.LinkedList;
import java.util.Queue;

import edu.pw.elka.andromote.commons.Packet;


public class RideScenario {
    private Queue<Packet> movementSteps = new LinkedList<Packet>();

    protected void addStep(Packet packet) {
        movementSteps.add(packet);
    }

    public Queue<Packet> getMovementSteps() {
        return movementSteps;
    }
}
