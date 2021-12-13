package com.ph.thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class AQS implements Lock, Serializable {

    private static final long serialVersionUID = -5575439727392767380L;

    private static class Sync extends AbstractQueuedSynchronizer{

        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        protected boolean tryAcquire(int arg) {
            assert arg == 1;
            if(compareAndSetState(0, 1)){
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        protected boolean tryRelease(int releases) {
            assert releases == 1;
            if (getState() == 0){
                setExclusiveOwnerThread(null);
                setState(0);
            }
            return true;
        }

        Condition newCondition(){
            return new ConditionObject();
        }

        private void readObject(ObjectInputStream s)
                throws IOException, ClassNotFoundException {
            s.defaultReadObject();
            setState(0); // reset to unlocked state
        }

    }

    private final Sync sync = new Sync();
    public void lock() {
        sync.acquire(1);
    }

    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    public boolean tryLock() {
        return sync.isHeldExclusively();
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    public void unlock() {
        sync.release(1);
    }

    public Condition newCondition() {
        return sync.newCondition();
    }
}
