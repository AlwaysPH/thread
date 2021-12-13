package com.ph.thread.masterSlave;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Future;

/***
 * 对子任务派发算法策略抽象类
 * @param <T> 子任务类型
 * @param <V> 子任务处理结果类型
 */
public interface SubTaskDispatchStrategy<T, V> {

    /***
     * 根据指定的原始任务分解策略，将分解得来的各个子任务派发给一组slave参与者实例
     * @param slaves  可以接受子任务的一组slave参与者实例
     * @param taskDivideStrategy  原始任务分解策略
     * @return 遍历iterator可得到用于湖区子任务处理结果
     * @throws InterruptedException
     */
    Iterator<Future<V>> dispatch(Set<? extends SlaveSpec<T, V>> slaves,
                                 TaskDivideStrategy<T> taskDivideStrategy) throws InterruptedException;
}
