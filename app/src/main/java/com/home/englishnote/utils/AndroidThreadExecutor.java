package com.home.englishnote.utils;

import android.os.Handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AndroidThreadExecutor implements ThreadExecutor {

    private static ExecutorService executorService = Executors.newScheduledThreadPool(7);
    private Handler handler = new Handler();
    private int delayTime = 1500;

    public void execute(Runnable runnable) {
        executorService.execute(runnable);
    }

    public void executeUiThread(Runnable runnable) {
        handler.post(runnable);
    }

    public void delayExecuteUiThreadDelay(Runnable runnable) {
        handler.postDelayed(runnable, delayTime);
    }

    public static void shutDownThreadPool() {
        executorService.shutdown();
    }
}
