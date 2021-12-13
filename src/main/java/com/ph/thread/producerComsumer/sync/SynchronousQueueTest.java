package com.ph.thread.producerComsumer.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueTest {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        final SynchronousQueue<Object> queue = new SynchronousQueue();

        Runnable producer = new Runnable() {
            @Override
            public void run() {
                Object object = new Object();
                try {
                    queue.put(object);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("producer : " + object.toString());
            }
        };

        Runnable consumer = new Runnable() {
            @Override
            public void run() {
                try {
                    Object o = queue.take();
                    System.out.println("consumer : " + o.toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        executorService.submit(producer);
        executorService.submit(consumer);
        executorService.shutdown();
    }
}
