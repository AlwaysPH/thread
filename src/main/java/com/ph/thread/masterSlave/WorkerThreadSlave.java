package com.ph.thread.masterSlave;

import com.ph.thread.twoPhaseTermination.AbstractTerminatableThread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/***
 * 工作者线程的slave参与者通用实现
 * @param <T> 子任务类型
 * @param <V> 子任务处理结果类型
 */
public abstract class WorkerThreadSlave<T, V> extends AbstractTerminatableThread implements SlaveSpec<T, V> {
    private final BlockingQueue<Runnable> taskQueue;

    public WorkerThreadSlave(BlockingQueue<Runnable> taskQueue) {
        this.taskQueue = taskQueue;
    }

    public Future<V> submit(final T task) throws InterruptedException{
        FutureTask<V> ft = new FutureTask<V>(new Callable<V>() {
            @Override
            public V call() throws Exception {
                V result = null;
                try {
                    result = doProcess(task);
                }catch (Exception e){
                    SubTaskFailureException stfe = newSubTaskFailureException(task, e);
                    throw stfe;
                }
                return result;
            }
        });
        taskQueue.put(ft);
        terminationToken.reservations.incrementAndGet();
        return ft;
    }

    private SubTaskFailureException newSubTaskFailureException(final T subTaks, Exception cause){
        RetryInfo<T, V> retryInfo = new RetryInfo<T, V>(subTaks, new Callable<V>() {
            @Override
            public V call() throws Exception {
                return doProcess(subTaks);
            }
        });
        return new SubTaskFailureException(retryInfo, cause);
    }

    /***
     * 留给子类实现。用于实现子任务的处理逻辑
     * @param task
     * @return
     * @throws Exception
     */
    protected abstract V doProcess(T task) throws Exception;

    @Override
    protected void doRun() throws Exception{
        try {
            Runnable task = taskQueue.take();
            task.run();
        }finally {
            terminationToken.reservations.decrementAndGet();
        }
    }

    @Override
    public void init(){
        start();
    }

    @Override
    public void shutDown(){
        terminate(true);
    }
}
