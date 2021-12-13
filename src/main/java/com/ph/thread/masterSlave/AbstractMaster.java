package com.ph.thread.masterSlave;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Future;

/***
 * Master-slave模式Master参与者的可复用实现
 * @param <T> 子任务对象类型
 * @param <V> 子任务处理结果类型
 * @param <R> 原始任务处理结果类型
 */
public abstract class AbstractMaster<T, V, R> {

    protected volatile Set<? extends SlaveSpec<T, V>> slaves;

    //子任务派发算法策略
    private volatile SubTaskDispatchStrategy<T, V> dispatchStrategy;

    public AbstractMaster() {
    }

    protected void init(){
        slaves = createSlaves();
        dispatchStrategy = newSubTaskDispatchStrategy();
        for(SlaveSpec<T, V> slave : slaves){
            slave.init();
        }
    }

    /***
     * 对子类暴露的服务方法。该类的子类需要定义一个比该方法命名更为具体的服务方法
     * @param params 客户端代码传递的参数
     * @return
     * @throws Exception
     */
    protected R service(Object... params)throws Exception{
        final TaskDivideStrategy<T> taskDivideStrategy = newTaskDivideStrategy(params);

        //对原始任务进行分解，并将分解得来的子任务派发给slave参与者实例
        Iterator<Future<V>> subResult = dispatchStrategy.dispatch(slaves, taskDivideStrategy);
        for(SlaveSpec<T, V> slave : slaves){
            slave.shutDown();
        }
        //合并子任务的处理结果
        R result = combineResult(subResult);
        return result;
    }

    /***
     * 留给子类实现。用于创建原始任务分解算法策略
     * @param params 客户端代码传递的参数
     * @return
     */
    protected abstract TaskDivideStrategy<T> newTaskDivideStrategy(Object[] params);

    /***
     * 用于创建子任务派发算法策略。默认使用（Round-Robin轮询算法）
     * @return 子任务派发算法策略
     */
    protected SubTaskDispatchStrategy<T,V> newSubTaskDispatchStrategy(){
        return new RoundRobinSubTaskDispatchStrategy<T, V>();
    }

    /***
     * 留个子类实现。用于创建slave参与者实例
     * @return
     */
    protected abstract Set<? extends SlaveSpec<T,V>> createSlaves();

    /***
     * 留个子类实现。用于合并子任务的处理结果
     * @param subResult 子任务处理结果
     * @return  原始任务处理结果
     */
    protected abstract R combineResult(Iterator<Future<V>> subResult);
}
