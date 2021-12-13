package com.ph.thread.promise;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class DataSyncTask implements Runnable {
    private final Map<String, String> taskParameters;

    public DataSyncTask(Map<String, String> taskParameters) {
        this.taskParameters = taskParameters;
    }

    @Override
    public void run() {
        String ftpServer = taskParameters.get("server");
        String ftpUserName = taskParameters.get("userName");
        String password = taskParameters.get("password");

        //实例化FTP客户端
        Future<FtpClientUtil> future = FtpClientUtil.getInstance(ftpServer, ftpUserName, password);
        /***
         * do something
         */


        //若FTP客户端未实例化完成，调用get方法会被阻塞
        FtpClientUtil ftpClientUtil = null;
        try {
            ftpClientUtil = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
