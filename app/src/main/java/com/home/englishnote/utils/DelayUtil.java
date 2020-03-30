package com.home.englishnote.utils;

public class DelayUtil {

    public static void delayExecuteThread(int delayTime) {
        try {
            Thread.sleep(delayTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
