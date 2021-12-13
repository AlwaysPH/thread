package com.ph.thread.guardedSuspension;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

/***
 * 负责连接警告服务，并发送警告信息给警告服务器
 */
public class AlarmAgent {

    //记录是否连接上警告服务器
    private volatile Boolean connectFlag = false;

    private final Predicate predicate = new Predicate() {
        public Boolean evaluate() {
            return connectFlag;
        }
    };

    private final Blocker blocker = new ConditionVarBlocker();

    private final Timer heartbeatTimer = new Timer(true);

    /***
     * 发送警告信息
     * @param alarmInfo
     */
    public Boolean sendAlarmInfo(final AlarmInfo alarmInfo) throws Exception {
        GuardedAction<Boolean> guardedAction = new GuardedAction<Boolean>(predicate) {
            public Boolean call() throws Exception {
                doSendAlarm(alarmInfo);
                return true;
            }
        };
        return blocker.callWithGuarded(guardedAction);
    }

    private void doSendAlarm(AlarmInfo alarmInfo) {
        try {
            System.out.println("Sending alarm...");
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void init() {

        System.out.println("initing...");

        Thread connectingThread = new Thread(new ConnectingTask());

        connectingThread.start();

        heartbeatTimer.schedule(new HeartbeatTask(), 60000, 2000);
    }

    public void disconnect() {
        connectFlag = false;
    }

    protected void onConnected() {
        try {
            blocker.signalAfter(new Callable<Boolean>() {
                public Boolean call() throws Exception {
                    connectFlag = true;
                    return Boolean.TRUE;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onDisconnected() {
        connectFlag = false;
    }

    private class ConnectingTask implements Runnable {

        public void run() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            onConnected();
        }
    }

    /***
     * 心跳检测：定时检测与警告服务器连接是否正常
     */
    private class HeartbeatTask extends TimerTask {

        @Override
        public void run() {
            if (!testConnection()) {
                onDisconnected();
                reconnect();
            }
        }

        private boolean testConnection() {
            return true;
        }

        private void reconnect() {
            ConnectingTask connectingTask = new ConnectingTask();
            connectingTask.run();
        }
    }
}
