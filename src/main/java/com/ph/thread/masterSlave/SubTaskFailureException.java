package com.ph.thread.masterSlave;

public class SubTaskFailureException extends Exception {
    public final RetryInfo retryInfo;


    public SubTaskFailureException(RetryInfo retryInfo, Exception e) {
        super(e);
        this.retryInfo = retryInfo;
    }
}
