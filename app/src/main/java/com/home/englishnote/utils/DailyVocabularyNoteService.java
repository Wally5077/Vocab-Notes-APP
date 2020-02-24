package com.home.englishnote.utils;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

public class DailyVocabularyNoteService extends IntentService {
    public DailyVocabularyNoteService() {
        super(DailyVocabularyNoteService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
