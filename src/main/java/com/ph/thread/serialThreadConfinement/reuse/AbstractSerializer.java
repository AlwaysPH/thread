package com.ph.thread.serialThreadConfinement.reuse;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;

/***
 * 串行线程封闭模式serializer参与者的实现
 * @param <T> Serializer向workerThread工作线程所提交的任务类型
 * @param <V> service方法返回值类型
 */
public abstract class AbstractSerializer<T, V> {

    //工作者线程
    private final TerminatableWorkerThread<T, V> workerThread;

    protected AbstractSerializer(BlockingQueue<Runnable> workQueue, TaskProcessor<T, V> taskProcessor) {
        this.workerThread = new TerminatableWorkerThread<T, V>(workQueue, taskProcessor);
    }

    /***
     * 留给子类实现。用于根据参数生成相应的任务实例
     * @param params
     * @return 任务实例。 用于提交给workerThread工作线程
     */
    protected abstract T makeTask(Object... params);

    /***
     * 对外暴露的方法。
     * @param params 客户端代码调用该方法时所传递的参数列表
     * @return
     * @throws InterruptedException
     */
    protected Future<V> service(Object... params) throws InterruptedException{
        T task = makeTask(params);
        Future<V> result = workerThread.submit(task);
        return result;
    }

    /***
     * 初始化该类对外暴露的方法
     */
    public void init(){
        workerThread.start();
    }

    /***
     * 停止该类对外暴露的方法
     */
    public void shutDown(){
        workerThread.terminate();
    }
}
