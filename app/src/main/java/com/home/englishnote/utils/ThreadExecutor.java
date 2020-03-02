package com.home.englishnote.utils;

public interface ThreadExecutor {
    void execute(Runnable runnable);

    void executeUiThread(Runnable runnable);

    void delayExecuteUiThreadDelay(Runnable runnable);
}
