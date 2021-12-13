package com.ph.thread.producerComsumer.workSteal;

import java.util.concurrent.BlockingDeque;

/***
 * 一个通道对应多个队列实例，防止出现其中的消费者线程处理完自己队列的任务，
 * 导致消费者线程闲置，窃取其他队列任务任务，从而减轻其他消费者线程负担
 * @param <T>
 */
public class WorkStealingChannel<T> implements WorkStealingEnableChannel<T> {

    private final BlockingDeque<T>[] manageQueue;

    public WorkStealingChannel(BlockingDeque<T>[] manageQueue) {
        this.manageQueue = manageQueue;
    }

    @Override
    public T take(BlockingDeque<T> queue) throws InterruptedException {
        BlockingDeque<T> targetQueue = queue;
        T product = null;
        //从指定队列中获取“产品”
        if(null != targetQueue){
            product = targetQueue.take();
        }
        int queueIndex = -1;
        while(null == product){
            queueIndex = (queueIndex + 1) % manageQueue.length;
            targetQueue = manageQueue[queueIndex];
            //从其他队列里获取队尾“产品”
            product = targetQueue.pollLast();
            if(queue == targetQueue){
                break;
            }
        }

        if(null == product){
            //随机从某个队列中获取“产品”
            queueIndex = (int) (System.currentTimeMillis() % manageQueue.length);
            targetQueue = manageQueue[queueIndex];
            product = targetQueue.takeLast();
        }
        return product;
    }

    @Override
    public T take() throws InterruptedException {
        return take(null);
    }

    @Override
    public void put(T product) throws InterruptedException {
        int targetIndex = product.hashCode() % manageQueue.length;
        BlockingDeque<T> targetDeque = manageQueue[targetIndex];
        targetDeque.put(product);
    }
}
