package com.home.englishnote.utils;

import android.os.Handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestThreadExecutor implements ThreadExecutor {

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void execute(Runnable runnable) {
        executorService.execute(runnable);
    }

    @Override
    public void executeUiThread(Runnable runnable) {
        executorService.execute(runnable);
    }
}
