package com.ph.thread.serialThreadConfinement.reuse;

/***
 * 对任务处理的类型
 * @param <T> 任务类型
 * @param <V> 处理结果
 */
public interface TaskProcessor<T, V> {
    /***
     * 对指定任务处理方法
     * @param task
     * @return 处理结果
     * @throws Exception
     */
    V doProcess(T task) throws Exception;
}
