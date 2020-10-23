package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.Helper;

// This is a primitive construct to input signals to a brain.
// The idea is that it's a panel of little light buttons, each with a number.
public class SensoryInputButtonPanel extends ClusterOfNeurons {

    public SensoryInputButtonPanel(Helper helper) {
        super(helper);
    }

    public SensoryInputButtonPanel(
            Helper helper,
            String label) {
        super(helper, label);
    }
}
