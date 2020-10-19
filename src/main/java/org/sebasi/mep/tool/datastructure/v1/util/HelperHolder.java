package org.sebasi.mep.tool.datastructure.v1.util;

public abstract class HelperHolder {

    private final Helper helper;

    public HelperHolder(Helper helper) {
        this.helper = helper;
    }

    public Helper getHelper() {
        return helper;
    }
}
