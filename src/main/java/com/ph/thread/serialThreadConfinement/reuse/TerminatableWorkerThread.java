package com.ph.thread.serialThreadConfinement.reuse;

import com.ph.thread.twoPhaseTermination.AbstractTerminatableThread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/***
 * 工作者线程
 * @param <T> Serializer向workerThread工作线程所提交的任务类型
 * @param <V> service方法返回值类型
 */
public class TerminatableWorkerThread<T, V> extends AbstractTerminatableThread {

    private final BlockingQueue<Runnable> workQueue;

    //负责真正执行任务的对象
    private final TaskProcessor<T, V> taskProcessor;

    public TerminatableWorkerThread(BlockingQueue<Runnable> workQueue, TaskProcessor<T, V> taskProcessor) {
        this.workQueue = workQueue;
        this.taskProcessor = taskProcessor;
    }

    /***
     * 接受并执行任务，并将任务串行化
     * @param task
     * @return
     * @throws InterruptedException
     */
    public Future<V> submit(final T task) throws InterruptedException{
        Callable<V> callable = new Callable<V>() {
            @Override
            public V call() throws Exception {
                return taskProcessor.doProcess(task);
            }
        };
        FutureTask<V> ft = new FutureTask<V>(callable);
        workQueue.put(ft);
        terminationToken.reservations.incrementAndGet();
        return ft;
    }

    /***
     * 任务执行的处理逻辑
     * @throws Exception
     */
    @Override
    protected void doRun() throws Exception {
        Runnable rt = workQueue.take();
        try {
            rt.run();
        }finally {
            terminationToken.reservations.decrementAndGet();
        }
    }
}
