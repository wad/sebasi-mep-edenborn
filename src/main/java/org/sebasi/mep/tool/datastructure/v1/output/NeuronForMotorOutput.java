package org.sebasi.mep.tool.datastructure.v1.output;

import org.sebasi.mep.tool.datastructure.v1.AxonForMotorOutput;
import org.sebasi.mep.tool.datastructure.v1.FiringComputer;
import org.sebasi.mep.tool.datastructure.v1.NeuralType;
import org.sebasi.mep.tool.datastructure.v1.NeuronWithDendriticTreeStandard;
import org.sebasi.mep.tool.datastructure.v1.util.Helper;
import org.sebasi.mep.tool.datastructure.v1.util.TickPriority;

public class NeuronForMotorOutput extends NeuronWithDendriticTreeStandard {

    public NeuronForMotorOutput(
            FiringComputer firingComputer,
            Helper helper) {
        this(
                firingComputer,
                helper,
                null);
    }

    @Override
    public NeuralType getNeuralType() {
        return NeuralType.MotorOutput;
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
