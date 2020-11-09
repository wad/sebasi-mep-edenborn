package org.sebasi.mep.tool.datastructure.v1;

public enum NeuralType {
    Standard("standard"),
    SensoryInput("sensory input"),
    MotorOutput("motor output");

    private final String neuralTypeName;

    NeuralType(String neuralTypeName) {
        this.neuralTypeName = neuralTypeName;
    }

    public String getNeuralTypeName() {
        return neuralTypeName;
    }
}
