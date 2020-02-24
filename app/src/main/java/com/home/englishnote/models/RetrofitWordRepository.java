package com.home.englishnote.models;

import com.home.englishnote.utils.Word;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class RetrofitWordRepository implements WordRepository {

    private WordApi wordApi;

    public RetrofitWordRepository(Retrofit retrofit) {
        wordApi = retrofit.create(WordApi.class);
    }

    private interface WordApi {
        @GET("/api/public/word/{name}")
        Call<Word> getWord(@Path("name") String name);
    }

    @Override
    public Word getWord(String name) {
        Call<Word> wordCall = wordApi.getWord(name);
        Word word = null;
        try {
            Response<Word> wordResponse = wordCall.execute();
            word = wordResponse.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return word;
    }
}
