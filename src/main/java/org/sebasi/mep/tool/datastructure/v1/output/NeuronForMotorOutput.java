package org.sebasi.mep.tool.datastructure.v1.output;

import org.sebasi.mep.tool.datastructure.v1.AxonForMotorOutput;
import org.sebasi.mep.tool.datastructure.v1.FiringComputer;
import org.sebasi.mep.tool.datastructure.v1.Neuron;
import org.sebasi.mep.tool.datastructure.v1.highperf.DendriticTreeSize;
import org.sebasi.mep.tool.datastructure.v1.util.Helper;
import org.sebasi.mep.tool.datastructure.v1.util.TickPriority;

public abstract class NeuronForMotorOutput extends Neuron {

    public NeuronForMotorOutput(
            FiringComputer firingComputer,
            DendriticTreeSize dendriticTreeSize,
            Helper helper) {
        this(
                firingComputer,
                helper,
                null);
    }

    public NeuronForMotorOutput(
            FiringComputer firingComputer,
            Helper helper,
            String label) {
        super(
                firingComputer,
                TickPriority.third,
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
