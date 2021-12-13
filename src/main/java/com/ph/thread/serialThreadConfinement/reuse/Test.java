package com.ph.thread.serialThreadConfinement.reuse;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Test {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        SomeService someService = new SomeService();
        someService.init();
        Future<String> future = someService.doSomething("come here", 1);
        Thread.sleep(50);
        System.out.println(future.get());
        someService.shutDown();
    }

    private static class Task{
        public final String message;

        public final int id;

        protected Task(String message, int id) {
            this.message = message;
            this.id = id;
        }
    }

    private static class SomeService extends AbstractSerializer<Task, String>{
        protected SomeService() {
            super(new ArrayBlockingQueue<Runnable>(200), new TaskProcessor<Task, String>() {
                @Override
                public String doProcess(Task task) throws Exception {
                    System.out.println(task.id + ":" + task.message);
                    return task.message + " accepted!";
                }
            });
        }

        @Override
        protected Task makeTask(Object... params) {
            String message = (String) params[0];
            int id = (Integer) params[1];
            return new Task(message, id);
        }

        public Future<String> doSomething(String message, int id) throws InterruptedException {
            Future<String> result = service(message, id);
            return result;
        }
    }
}
