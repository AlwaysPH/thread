package com.ph.thread.producerComsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

/***
 * 基于Semaphore的支持流量控制的通道实现
 * @param <P>
 */
public class SemaphoreBaseChannel<P> implements Channel<P> {

    private final BlockingQueue<P> queue;

    private final Semaphore semaphore;

    /***
     *
     * @param queue
     * @param flowInt  流量限制数
     */
    public SemaphoreBaseChannel(BlockingQueue<P> queue, int flowInt) {
        this.queue = queue;
        this.semaphore = new Semaphore(flowInt);
    }

    @Override
    public P take() throws InterruptedException {
        return queue.take();
    }

    @Override
    public void put(P product) throws InterruptedException {
        semaphore.acquire();
        try {
            queue.put(product);
        }finally {
            semaphore.release();
        }

    }
}
