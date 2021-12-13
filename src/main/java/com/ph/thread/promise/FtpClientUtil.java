package com.ph.thread.promise;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class FtpClientUtil {

    private final FtpClientUtil ftpClientUtil = new FtpClientUtil();

    private final Map<String, Boolean> dirCreateMap = new HashMap<String, Boolean>();

    public FtpClientUtil() {
    }

    public static Future<FtpClientUtil> getInstance(final String ftpServer, final String userName, final String password){
        Callable<FtpClientUtil> callable = new Callable<FtpClientUtil>() {
            @Override
            public FtpClientUtil call() throws Exception {
                FtpClientUtil clientUtil = new FtpClientUtil();
                clientUtil.init(ftpServer, userName, password);
                return clientUtil;
            }
        };
        final FutureTask<FtpClientUtil> task = new FutureTask<FtpClientUtil>(callable);
        new Thread(task).start();
        return task;
    }

    private void init(String ftpServer, String userName, String password) {
        /***
         * ftp客户端初始化
         */
    }
}
