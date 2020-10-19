package org.sebasi.mep.tool.datastructure.v1;

// This class holds some global settings that are passed around.
// There should only be one of these objects, but I don't want to make it a singleton, because I wants
// test code to confidently use it without weird state being persisted.
public class Helper {

    // todo: This can hold the randomizer

    private OperationMode operationMode;

    public Helper() {
        operationMode = OperationMode.SAFE;
    }

    public OperationMode getOperationMode() {
        return operationMode;
    }

    public void setOperationMode(OperationMode operationMode) {
        this.operationMode = operationMode;
    }
}
