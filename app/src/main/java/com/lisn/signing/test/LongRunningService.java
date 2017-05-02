package com.lisn.signing.test;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;


public class LongRunningService extends Service {

    private String TAG = "-------------ooooo";
    private ThreadFlag thread;

    public LongRunningService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        thread = new ThreadFlag();
        thread.start();

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        int anHour=60*60*1000;
        int anHour = 3 * 1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, LongRunningService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);

        return super.onStartCommand(intent, flags, startId);
    }

    class ThreadFlag extends Thread {
        public  boolean exit ;
        public void setExit(boolean b){
            exit = b;
        }
        public void run() {
            if (!exit){
                Log.e(TAG, "run:---------exit- "+exit);
            } ;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ------");
//        thread.stop();
        try {
            thread.setExit(true);  // 终止线程thread
//            thread.join();
            thread.stop();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "onDestroy: -----e-"+e.toString());
        }
    }
}
