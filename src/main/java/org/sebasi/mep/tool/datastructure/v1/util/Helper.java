package org.sebasi.mep.tool.datastructure.v1.util;

// This class holds some global settings that are passed around.
// There should only be one of these objects, but I don't want to make it a singleton, because I wants
// test code to confidently use it without weird state being persisted.
public class Helper {

    OperationMode operationMode;
    RandomUtil randomUtil;

    public Helper() {
        operationMode = OperationMode.SAFE;
        randomUtil = new RandomUtil();
    }

    public OperationMode getOperationMode() {
        return operationMode;
    }

    public void setOperationMode(OperationMode operationMode) {
        this.operationMode = operationMode;
    }

    public RandomUtil getRandomUtil() {
        return randomUtil;
    }

    public void setRandomUtil(RandomUtil randomUtil) {
        this.randomUtil = randomUtil;
    }
}
