package com.ph.thread.producerComsumer;

public class SynchronousQueue<P> implements Channel<P> {
    private final SynchronousQueue<P> synchronousQueue;

    public SynchronousQueue(SynchronousQueue<P> synchronousQueue) {
        this.synchronousQueue = synchronousQueue;
    }

    @Override
    public P take() throws InterruptedException {
        return synchronousQueue.take();
    }

    @Override
    public void put(P product) throws InterruptedException {
        synchronousQueue.put(product);
    }
}
