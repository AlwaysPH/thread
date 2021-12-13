package com.ph.thread.activeObject.resue;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class SampleObjectImpl implements SampleObject {
    @Override
    public Future<String> doSayHello() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "123";
            }
        };
        FutureTask<String> task = new FutureTask<String>(callable);
        new Thread(task).start();
        return task;
    }
}
