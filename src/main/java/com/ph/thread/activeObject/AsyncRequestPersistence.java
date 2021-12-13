package com.ph.thread.activeObject;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/***
 * 角色模式：ActiveObject.proxy
 */
public class AsyncRequestPersistence implements RequestPersistence {

    private static final Logger log = LoggerFactory.getLogger(AsyncRequestPersistence.class);

    private final AtomicLong taskConsumerPerInterval = new AtomicLong(0);

    private final AtomicInteger requestSubmitPerInterval = new AtomicInteger(0);

    private final DiskBaseRequestPersistence delegate = new DiskBaseRequestPersistence();

    private final ThreadPoolExecutor scheduler;

    private static class InstanceHolder{
        final static AsyncRequestPersistence INSTANCE = new AsyncRequestPersistence();
    }

    public static AsyncRequestPersistence getInstance(){
        return InstanceHolder.INSTANCE;

    }
    public AsyncRequestPersistence() {
        scheduler = new ThreadPoolExecutor(1, 3, 60 * 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(200), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r, "AsyncRequestPersistence");
                return t;
            }
        });

        scheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        //启动队列监控任务
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                taskConsumerPerInterval.set(0);
                requestSubmitPerInterval.set(0);
            }
        }, 0, 60 * 1000);
    }

    @Override
    public void store(ActiveObject activeObject) {

    }
}
