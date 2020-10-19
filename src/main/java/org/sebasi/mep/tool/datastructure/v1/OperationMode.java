package org.sebasi.mep.tool.datastructure.v1;

public enum OperationMode {

    SAFE(true),
    FAST(false);

    private final boolean shouldValidateDendriteAttachments;

    OperationMode(boolean shouldValidateDendriteAttachments) {
        this.shouldValidateDendriteAttachments = shouldValidateDendriteAttachments;
    }

    public boolean shouldValidateDendriteAttachments() {
        return shouldValidateDendriteAttachments;
    }
}
