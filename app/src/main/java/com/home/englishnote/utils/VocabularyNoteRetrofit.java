package com.home.englishnote.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VocabularyNoteRetrofit {
    private static Retrofit vocabularyNoteRetrofit = null;

    public static Retrofit vocabularyNoteRetrofit() {
        return (vocabularyNoteRetrofit == null) ?
                new Retrofit.Builder()
                        .baseUrl("https://vocab-notes-v2.herokuapp.com")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build() : vocabularyNoteRetrofit;
    }
}
