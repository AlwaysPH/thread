package com.ph.thread.masterSlave;

import java.util.concurrent.Callable;

public class RetryInfo<T, V> {

    public final T subTask;

    public final Callable<V> redoCommand;

    public RetryInfo(T subTask, Callable<V> redoCommand) {
        this.subTask = subTask;
        this.redoCommand = redoCommand;
    }
}
