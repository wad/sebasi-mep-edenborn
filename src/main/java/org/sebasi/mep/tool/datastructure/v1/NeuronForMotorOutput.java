package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.Helper;

public class NeuronForMotorOutput extends NeuronWithoutMemory {

    public NeuronForMotorOutput(
            DendriticTreeSize dendriticTreeSize,
            Helper helper) {
        this(
                dendriticTreeSize,
                helper,
                null);
    }

    public NeuronForMotorOutput(
            DendriticTreeSize dendriticTreeSize,
            Helper helper,
            String label) {
        super(
                dendriticTreeSize,
                helper,
                label);
    }

    @Override
    protected void initializeAxon() {
        axon = new AxonForMotorOutput();
    }
}
