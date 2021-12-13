package com.ph.thread.producerComsumer.workSteal;

import com.ph.thread.producerComsumer.Channel;

import java.util.concurrent.BlockingDeque;

public interface WorkStealingEnableChannel<P> extends Channel<P> {
    P take(BlockingDeque<P> queue) throws InterruptedException;
}
