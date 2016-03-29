package edu.pw.elka.andromote.andromote.common.wrappers;

import edu.pw.elka.andromote.commons.Packet;
import edu.pw.elka.andromote.commons.PacketType.Motion;

public class PacketBuilder {
    private static final int DEFAULT_STEP_DURATION = 500;
    private static final float DEFAULT_SPEED = 0.8f;
    private  Motion motionType;
    private float speed;
    private int stepDuration;

    public PacketBuilder(Motion motionType, float speed, int stepDuration) {
        this.motionType = motionType;
        this.speed = speed;
        this.stepDuration = stepDuration;
    }

    public static PacketBuilder newPacket(Motion motionType) {
        return new PacketBuilder(motionType, DEFAULT_SPEED, DEFAULT_STEP_DURATION);
    }

    public PacketBuilder withSpeed(float speed) {
        this.speed = speed;
        return this;
    }

    public PacketBuilder withStepDuration(int duration) {
        this.stepDuration = duration;
        return this;
    }

    public Packet create() {
       Packet packet = new Packet(motionType);
        packet.setSpeed(speed);
        packet.setStepDuration(stepDuration);
        return packet;
    }
}
