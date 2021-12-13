package com.ph.thread.twoPhaseTermination;

import java.lang.ref.WeakReference;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class TerminationToken {

    protected volatile boolean shutdown = false;

    public final AtomicInteger reservations = new AtomicInteger(0);

    private final Queue<WeakReference<Terminatable>> coordinatedThreads;

    /**
     * 在多个可停止线程实例共享一个TerminationToken实例的情况下，该队列用于记录那些共享TerminationToken实例
     * 的可停止线程，以便尽可能减少锁的使用情况下，实现这些线程的停止
     */
    public TerminationToken() {
        coordinatedThreads = new ConcurrentLinkedQueue<WeakReference<Terminatable>>();
    }

    public Boolean isToShutdown(){
        return shutdown;
    }

    protected void setShutdown(Boolean toShutdown){
        this.shutdown = toShutdown;
    }

    protected void register(Terminatable thread){
        coordinatedThreads.add(new WeakReference<Terminatable>(thread));
    }

    /**
     * 通知TerminationToken实例，共享该实例的所有可停止线程中的一个线程停止了，以便其停止其他为被停止的线程
     * @param thread
     * 已停止的线程
     */
    protected void notifyTreadTermination(Terminatable thread){
        WeakReference<Terminatable> wrThread;
        Terminatable otherTread;
        while (null !=(wrThread = coordinatedThreads.poll())){
            otherTread = wrThread.get();
            if(null != otherTread && otherTread != thread){
                otherTread.terminate();
            }
        }
    }
}
