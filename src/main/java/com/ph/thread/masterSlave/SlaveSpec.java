package com.ph.thread.masterSlave;

import java.util.concurrent.Future;

/***
 * 对master-slave模式slave参与者抽象
 * @param <T> 子任务类型
 * @param <V> 子任务结果类型
 */
public interface SlaveSpec<T, V> {

    /***
     * 用于Master向其提交一个子任务
     * @param task 子任务
     * @return 子任务处理结果
     * @throws InterruptedException
     */
    Future<V> submit(final T task) throws InterruptedException;

    /***
     * 初始化slave实例方法
     */
    void init();

    /***
     * 停止slave实例方法
     */
    void shutDown();
}
