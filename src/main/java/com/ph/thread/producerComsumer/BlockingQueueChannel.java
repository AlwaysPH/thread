package com.ph.thread.producerComsumer;

import java.util.concurrent.BlockingQueue;

/***
 * 基于阻塞队列的通道实现
 * @param <P>
 */
public class BlockingQueueChannel<P> implements Channel<P>  {
    private final BlockingQueue<P> queue;

    public BlockingQueueChannel(BlockingQueue<P> queue) {
        this.queue = queue;
    }

    @Override
    public P take() throws InterruptedException {
        return queue.take();
    }

    @Override
    public void put(P product) throws InterruptedException {
        queue.put(product);
    }
}
