package com.ph.thread.guardedSuspension;

import java.util.concurrent.Callable;

public interface Blocker {

    /***
     * 在保护条件成立时执行目标方法；否则线程阻塞，直到保护条件成立
     * @param guardedAction
     * @param <V>
     * @return
     * @throws Exception
     */
    <V> V callWithGuarded(GuardedAction<V> guardedAction) throws Exception;

    /***
     * 执行stateOperation所指定的操作后，决定是否唤醒本Blocker所暂挂的所有线程中的一个线程
     * @param stateOperation
     * 在更改状态的操作，其call方法返回值为true时，本方法才会唤醒被暂挂的线程
     * @throws Exception
     */
    void signalAfter(Callable<Boolean> stateOperation) throws Exception;

    void signal() throws InterruptedException;

    /***
     * 执行stateOperation所指定的操作后，决定是否唤醒本Blocker所暂挂的所有线程
     * @param stateOperation
     * 在更改状态的操作，其call方法返回值为true时，本方法才会唤醒被暂挂的线程
     * @throws Exception
     */
    void broadcastAfter(Callable<Boolean> stateOperation) throws Exception;
}
