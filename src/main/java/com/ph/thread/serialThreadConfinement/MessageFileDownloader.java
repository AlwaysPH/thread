package com.ph.thread.serialThreadConfinement;

import com.ph.thread.twoPhaseTermination.AbstractTerminatableThread;
import org.apache.commons.net.ftp.FTPClient;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.*;

public class MessageFileDownloader {
    //工作线程
    private final WorkerThread workerThread;

    public MessageFileDownloader(String outputDir, final String ftpServer,
                                 final String userName, final String password) {
        this.workerThread = new WorkerThread(outputDir, ftpServer, userName, password);
    }

    public void init(){
        workerThread.start();
    }

    public void shutDown(){
        workerThread.terminate();
    }

    /***
     * 自定义方法
     */
    public void downFile(String file){
        workerThread.downFile(file);
    }

    /***
     * 工作线程
     */
    private static class WorkerThread extends AbstractTerminatableThread{

        private final BlockingQueue<String> queue;

        private final Future<FTPClient> ftpClientFuture;

        private final String outputDir;

        public WorkerThread(String outputDir, final String ftpServer, final String userName, final String password) {
            this.queue = new ArrayBlockingQueue<String>(100);
            this.outputDir = outputDir + "/";
            this.ftpClientFuture = new FutureTask<FTPClient>(new Callable<FTPClient>() {
                @Override
                public FTPClient call() throws Exception {
                    return initFtpClient(ftpServer, userName, password);
                }
            });
            new Thread((Runnable) ftpClientFuture).start();
        }

        /***
         * 初始化ftp客户端
         * @param ftpServer
         * @param userName
         * @param password
         * @return
         */
        private FTPClient initFtpClient(String ftpServer, String userName, String password) {
            return null;
        }

        /***
         * 把任务放入串行队列
         * @param file
         */
        public void downFile(String file) {
            try {
                queue.put(file);
                terminationToken.reservations.incrementAndGet();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void doRun() throws Exception {
            String file = queue.take();
            OutputStream os = null;
            try {
                os = new BufferedOutputStream(new FileOutputStream(outputDir + file));
                ftpClientFuture.get().retrieveFile(file, os);
            }finally {
                if(null != os){
                    try {
                        os.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                terminationToken.reservations.decrementAndGet();
            }
        }

        @Override
        protected void doCleanup(Exception cause){
            try {
                ftpClientFuture.get().disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

    }

}
