package com.home.englishnote.receivers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.google.gson.Gson;
import com.home.englishnote.R;
import com.home.englishnote.models.entities.Word;
import com.home.englishnote.models.entities.WordGroup;
import com.home.englishnote.models.repositories.WordGroupRepository;
import com.home.englishnote.utils.Global;
import com.home.englishnote.utils.VocabularyNoteKeyword;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class DailyVocabularyBroadcastReceiver extends BroadcastReceiver {

    // Todo
    private WordGroupRepository wordGroupRepository;
    private SharedPreferences sp;
    private Random random;
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        init(context);
        downloadVocab(intent);
    }

    private void init(Context context) {
        this.context = context;
        sp = context.getSharedPreferences(
                VocabularyNoteKeyword.SP_NAME, Context.MODE_PRIVATE);
        wordGroupRepository = Global.wordGroupRepository();
        random = new Random();
    }

    private void downloadVocab(final Intent intent) {
        Global.threadExecutor().execute(() -> {
            int dictionaryId = sp.getInt(VocabularyNoteKeyword.DICTIONARY_ID, 1);
            int offset = sp.getInt(VocabularyNoteKeyword.OFFSET, 0);
            int limit = VocabularyNoteKeyword.READ_DICTIONARY_LIMIT;
            List<WordGroup> wordGroupList = wordGroupRepository
                    .getWordGroupsFromDictionary(dictionaryId, offset, limit);
            setVocabToLocalVocabStorage(dictionaryId, wordGroupList);
            createNotificationChannel();
            sendDailyVocabularyNotification(intent);
        });
    }

    private void set(String spKeyword, int spValue, List vocabList) {
        int currentVocabIndex = sp.getInt(spKeyword, 0);
        int vocabListEnd = vocabList.size();
        if (currentVocabIndex < vocabListEnd) {
            Editor editor = sp.edit();
            Object object = vocabList.get(currentVocabIndex);
            if (object instanceof List) {

            }
            currentVocabIndex++;
            if (currentVocabIndex == vocabListEnd) {

            } else {

            }
        }
    }

    private void setVocabToLocalVocabStorage(int dictionaryId, List<WordGroup> wordGroupList) {
        int currentWordGroupIndex = sp.getInt(VocabularyNoteKeyword.CURRENT_WORD_GROUP, 0);
        int wordGroupListEnd = wordGroupList.size();
        if (currentWordGroupIndex < wordGroupListEnd) {
            Editor editor = sp.edit();
            WordGroup wordGroup = wordGroupList.get(currentWordGroupIndex);
            List<Word> wordList = wordGroup.getWords();
            int currentWordIndex = sp.getInt(VocabularyNoteKeyword.CURRENT_WORD, 0);
            int wordListEnd = wordList.size();
            if (currentWordIndex < wordListEnd) {
                Word word = wordList.get(currentWordIndex);
//                String wordJson = new Gson().toJson(word);
                currentWordIndex++;
                if (currentWordIndex == wordListEnd) {
                    editor.putInt(VocabularyNoteKeyword.CURRENT_WORD, 0);
                    currentWordGroupIndex++;
                    if (currentWordGroupIndex == wordGroupListEnd) {
                        dictionaryId++;
                        editor.putInt(VocabularyNoteKeyword.DICTIONARY_ID, dictionaryId)
                                .putInt(VocabularyNoteKeyword.CURRENT_WORD_GROUP, 0);
                    } else {
                        editor.putInt(VocabularyNoteKeyword.CURRENT_WORD_GROUP, currentWordGroupIndex);
                    }
                } else {
                    editor.putInt(VocabularyNoteKeyword.CURRENT_WORD, currentWordIndex);
                }
            }
            editor.apply();
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(
                    context.getString(R.string.CHANNEL_ID), name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void sendDailyVocabularyNotification(Intent intent) {
        try {
            NotificationCompat.Builder builder = new NotificationCompat
                    .Builder(context, context.getString(R.string.CHANNEL_ID))
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle(intent.getStringExtra("vocab"))
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(intent.getStringExtra("vocabDescription")))
                    .setLargeIcon(Glide
                            .with(context)
                            .asBitmap()
                            .load(new GlideUrl(intent.getStringExtra("VocabImageURL")))
                            .error(R.drawable.person_holding_orange_pen_1925536)
                            .fitCenter()
                            .submit()
                            .get());
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(1, builder.build());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
