package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.Helper;
import org.sebasi.mep.tool.datastructure.v1.util.TickPriority;

public class NeuronForMotorOutput extends NeuronWithoutMemory {

    public NeuronForMotorOutput(
            FiringComputer firingComputer,
            DendriticTreeSize dendriticTreeSize,
            Helper helper) {
        this(
                firingComputer,
                dendriticTreeSize,
                helper,
                null);
    }

    public NeuronForMotorOutput(
            FiringComputer firingComputer,
            DendriticTreeSize dendriticTreeSize,
            Helper helper,
            String label) {
        super(
                firingComputer,
                TickPriority.third,
                dendriticTreeSize,
                helper,
                label);
    }

    @Override
    protected void initializeAxon() {
        axon = new AxonForMotorOutput();
    }

    public boolean isLampOn() {
        return ((AxonForMotorOutput) axon).getHasFired();
    }

    public void resetLamp() {
        ((AxonForMotorOutput) axon).reset();
    }
}
