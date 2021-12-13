package com.ph.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {

    private static final Lock lock = new ReentrantLock();;

    private static final Condition condition = lock.newCondition();;

    private static volatile Boolean flag = false;

    public static void main(String[] args) {
        Thread waiter = new Thread(new Waiter());
        waiter.start();

        Thread signaler = new Thread(new Signaler());
        signaler.start();
    }

    static class Waiter implements Runnable{
        public void run() {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "不满足条件，等待......");
                while (!flag){
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + "接收到通知");
            }finally {
                lock.unlock();
            }
        }
    }

    static class Signaler implements Runnable{

        public void run() {
            lock.lock();
            try {
                flag = true;
                condition.signalAll();
            }finally {
                lock.unlock();
            }
        }
    }
}
