package org.sebasi.mep.tool.datastructure.v1.util;

// This class holds some global settings that are passed around.
// There should only be one of these objects, but I don't want to make it a singleton, because I wants
// test code to confidently use it without weird state being persisted.
public class Helper {

    boolean shouldShowMessages;
    OperationMode operationMode;
    RandomUtil randomUtil;
    Tickers tickers;
    MessageDisplay messageDisplay;

    public Helper() {
        shouldShowMessages = true;
        operationMode = OperationMode.SAFE;
        randomUtil = new RandomUtil();
        tickers = new Tickers(this);
        messageDisplay = new MessageDisplay();
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

    public Tickers getTickers() {
        return tickers;
    }

    // Put any message string construction inside an if statement that checks this first,
    // for performance reasons.
    public boolean shouldShowLogMessages() {
        return shouldShowMessages;
    }

    public void setShouldShowLogMessages(boolean shouldShowLogMessages) {
        this.shouldShowMessages = shouldShowLogMessages;
    }

    public MessageDisplay getMessageDisplay() {
        return messageDisplay;
    }
}
