package com.ph.thread.producerComsumer;

import com.ph.thread.twoPhaseTermination.AbstractTerminatableThread;

import java.io.File;
import java.io.InputStream;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;

public class AttachmentProcessor {
    private final String BASE_URL = "/attachment";

    private final Channel<File> syncChannel = new BlockingQueueChannel<File>(new SynchronousQueue<File>());

    /***
     * producer 基于有界阻塞队列
     */
    private final Channel<File> channel = new BlockingQueueChannel<File>(new ArrayBlockingQueue<File>(200));

    /***
     * producer 基于流量限制阻塞队列
     */
    private final Channel<File> fileChannel = new SemaphoreBaseChannel<File>(new LinkedBlockingDeque<File>(200), 10);

    //consumer
    private final AbstractTerminatableThread thread = new AbstractTerminatableThread() {
        @Override
        protected void doRun() throws Exception {
            File file = channel.take();
            try {
                doIndexTypeFile(file);
            }catch (Exception e){

            }finally {
                terminationToken.reservations.decrementAndGet();
            }
        }

        private void doIndexTypeFile(File file) {
            //模拟执行逻辑消耗的时间
            Random random = new Random();
            try {
                Thread.sleep(random.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public void init(){
        thread.start();
    }

    public void shutdown(){
        thread.terminate();
    }

    public void saveAttachment(InputStream inputStream, String documentId, String originalFileName){
        File file = saveFile(inputStream, documentId, originalFileName);
        try {
            channel.put(file);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.terminationToken.reservations.incrementAndGet();
    }

    //保存文件及其他操作
    private File saveFile(InputStream inputStream, String documentId, String originalFileName) {
        return null;
    }


}
