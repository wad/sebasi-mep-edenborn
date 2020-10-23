package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.Helper;

// This is a primitive construct to read the output.
// The idea is that it's a panel of little light bulbs, each with a number and a reset button by it.
public class MotorOutputLampPanel extends ClusterOfNeurons {

    public MotorOutputLampPanel(Helper helper) {
        super(helper);
    }

    public MotorOutputLampPanel(
            Helper helper,
            String label) {
        super(helper, label);
    }
}
