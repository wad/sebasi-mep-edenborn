package org.sebasi.mep.tool.datastructure.v1.util;

public class NeuronConnectionException extends RuntimeException {
    String neuronLabel;

    public NeuronConnectionException(String neuronLabel) {
        this.neuronLabel = neuronLabel;
    }

    public NeuronConnectionException(String message, String neuronLabel) {
        super(message);
        this.neuronLabel = neuronLabel;
    }

    public NeuronConnectionException(String message, Throwable cause, String neuronLabel) {
        super(message, cause);
        this.neuronLabel = neuronLabel;
    }

    public NeuronConnectionException(Throwable cause, String neuronLabel) {
        super(cause);
        this.neuronLabel = neuronLabel;
    }

    public NeuronConnectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String neuronLabel) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.neuronLabel = neuronLabel;
    }

    @Override
    public String getMessage() {
        return "[Neuron label='" + neuronLabel + "'] " + super.getMessage();
    }
}
