package com.ph.thread.activeObject.resue;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SampleTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SampleObject object = ActiveObjectProxy.newInstance(SampleObject.class, new SampleObjectImpl(), Executors.newCachedThreadPool());
        Future<String> future = object.doSayHello();
        System.out.println(future.get());
    }
}
