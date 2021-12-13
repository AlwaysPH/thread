package com.ph.thread.threadSpecificStorage;

import java.text.DecimalFormat;
import java.util.concurrent.*;

public class SmsVerficationConsender {

    private static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(1, Runtime.getRuntime().availableProcessors(), 60,
            TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "smsVerfication");
            t.setDaemon(true);
            return t;
        }
    }, new ThreadPoolExecutor.DiscardPolicy());

    public static void main(String[] args) {
        SmsVerficationConsender client = new SmsVerficationConsender();
        client.send("18812345677");
        client.send("15007363306");

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void send(final String phone){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                int num = ThreadSpecificSecureRandom.INSTANCE.nextInt(999999);
                DecimalFormat format = new DecimalFormat("000000");
                String code = format.format(num);
                sendCode(phone, code);
            }
        };

        EXECUTOR_SERVICE.submit(task);
    }

    private void sendCode(String phone, String code) {
        System.out.println(phone + "~" + code);
    }
}
