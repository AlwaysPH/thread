package com.ph.thread.producerComsumer.workSteal;

import com.ph.thread.twoPhaseTermination.AbstractTerminatableThread;
import com.ph.thread.twoPhaseTermination.TerminationToken;

import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class WorkSteallingExample {

    private final WorkStealingEnableChannel<String> channel;

    private final TerminationToken terminationToken = new TerminationToken();

    public WorkSteallingExample(){
        int nCpu = Runtime.getRuntime().availableProcessors();
        int consumerCpu = nCpu / 2 + 1;

        BlockingDeque<String>[] deques = new LinkedBlockingDeque[consumerCpu];
        channel = new WorkStealingChannel<String>(deques);

        Consumer[] consumers = new Consumer[consumerCpu];
        for (int i = 0; i < consumerCpu; i++) {
            deques[i] = new LinkedBlockingDeque<String>();
            consumers[i] = new Consumer(terminationToken, deques[i]);
        }

        for (int i = 0; i < nCpu; i++) {
            Product product = new Product();
            product.start();
//            product.interrupt();
        }

        for (int i = 0; i < nCpu; i++) {
            consumers[i].start();
//            consumers[i].terminate();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        WorkSteallingExample ws = new WorkSteallingExample();
        Thread.sleep(5000);
    }

    private class Product extends AbstractTerminatableThread{

        private int i = 0;
        @Override
        protected void doRun() throws Exception {
            channel.put(String.valueOf(i++));
            terminationToken.reservations.incrementAndGet();
        }

        @Override
        protected void doTerminate(){
            System.out.println("producer is shutDown");
        }
    }

    private class Consumer extends AbstractTerminatableThread{

        private final BlockingDeque<String> blockingDeque;

        private Consumer(TerminationToken token, BlockingDeque<String> blockingDeque) {
            super(token);
            this.blockingDeque = blockingDeque;
        }

        @Override
        protected void doRun() throws Exception {
            String product = channel.take(blockingDeque);
            System.out.println("consumer : " + product);
            //模拟消费产品消耗时间
            try {
                Thread.sleep(new Random().nextInt(50));
            }finally {
                terminationToken.reservations.decrementAndGet();
            }
        }

        @Override
        protected void doTerminate(){
            System.out.println("Consumer is shutDown");
        }
    }
}
