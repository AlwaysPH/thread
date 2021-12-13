package com.ph.thread.masterSlave;

/***
 * 对原始任务分解算法策略抽象类
 * @param <T>
 */
public interface TaskDivideStrategy<T> {

    /***
     * 返回下一个子任务。若为null，则无下个子任务
     * @return 下一个子任务
     */
    T nextChunk();
}
