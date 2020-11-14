package org.sebasi.mep.tool.datastructure.v1.input;

public enum ButtonPressStrength {
    ReallyHard(100),
    Hard(50),
    Standard(30),
    Soft(15),
    ReallySoft(5),
    JustATouch(1);

    private int strengthValue;

    ButtonPressStrength(int strengthValue) {
        this.strengthValue = strengthValue;
    }

    public int getStrengthValue() {
        return strengthValue;
    }
}
