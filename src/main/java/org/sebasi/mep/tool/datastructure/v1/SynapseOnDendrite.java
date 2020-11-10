package org.sebasi.mep.tool.datastructure.v1;

public class SynapseOnDendrite {

    static final int MIN_SYNAPTIC_STRENGTH_VALUE = -100;
    static final int MAX_SYNAPTIC_STRENGTH_VALUE = 10000;

    int synapticStrengthValue;

    public SynapseOnDendrite(int initialSynapticStrength) {
        this.synapticStrengthValue = initialSynapticStrength;
    }

    public int getSynapticStrengthValue() {
        return synapticStrengthValue;
    }

    public void setSynapticStrengthValue(int synapticStrengthValue) {
        this.synapticStrengthValue = synapticStrengthValue;
        ensureSynapticStrengthValueInAllowedRange();
    }

    public void modifySynapticStrength(int delta) {
        synapticStrengthValue += delta;
        ensureSynapticStrengthValueInAllowedRange();
    }

    void ensureSynapticStrengthValueInAllowedRange() {
        if (synapticStrengthValue > MAX_SYNAPTIC_STRENGTH_VALUE) {
            synapticStrengthValue = MAX_SYNAPTIC_STRENGTH_VALUE;
        }
        if (synapticStrengthValue < MIN_SYNAPTIC_STRENGTH_VALUE) {
            synapticStrengthValue = MIN_SYNAPTIC_STRENGTH_VALUE;
        }
    }
}
