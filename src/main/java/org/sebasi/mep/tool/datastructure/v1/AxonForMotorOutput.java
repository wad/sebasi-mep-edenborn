package org.sebasi.mep.tool.datastructure.v1;

public class AxonForMotorOutput extends Axon {

    boolean hasFired;

    public AxonForMotorOutput() {
        reset();
    }

    @Override
    public void fire() {
    }

    public boolean getHasFired() {
        return hasFired;
    }

    public void reset() {
        hasFired = false;
    }
}
