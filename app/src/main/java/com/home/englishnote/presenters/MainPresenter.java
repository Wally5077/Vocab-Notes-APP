package com.home.englishnote.presenters;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.home.englishnote.receivers.DailyVocabularyBroadcastReceiver;

import java.util.Calendar;
import java.util.TimeZone;

import static android.content.Context.ALARM_SERVICE;

public class MainPresenter {
    private Context context;

    public MainPresenter(Context context) {
        this.context = context;
    }

    public void setDailyVocabularyNotification() {
        AlarmManager manager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(
                context, 0, new Intent(context, DailyVocabularyBroadcastReceiver.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        if (manager != null) {
            manager.setInexactRepeating(
                    AlarmManager.ELAPSED_REALTIME, getWakeUpTime(),
                    AlarmManager.INTERVAL_DAY, sender);
        }
    }

    private long getWakeUpTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

}
