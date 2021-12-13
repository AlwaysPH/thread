package com.ph.thread.threadPool;

import java.util.concurrent.*;

public class SmsCodeSender {

    private static final ExecutorService EXECUTOR = new ThreadPoolExecutor(1, Runtime.getRuntime().availableProcessors(),
            60, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "smsSender");
            t.setDaemon(true);
            return t;
        }
    }, new ThreadPoolExecutor.DiscardPolicy());

    public void sendSmsCode(final String msisdn){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                //doSomething
            }
        };
        EXECUTOR.submit(task);
    }


}
