package com.home.englishnote.models;

import com.home.englishnote.utils.Dictionary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RetrofitDictionaryRepository implements DictionaryRepository {

    private DictionaryApi dictionaryApi;

    public RetrofitDictionaryRepository(Retrofit retrofit) {
        dictionaryApi = retrofit.create(DictionaryApi.class);
    }

    private interface DictionaryApi {
        @GET("/api/public/dictionaries/{id}")
        Call<Dictionary> getDictionary(@Path("id") int dictionaryId);

        @GET("/api/public/dictionaries")
        Call<List<Dictionary>> getDictionaries(
                @Query("offset") int offset,
                @Query("limit") int limit);
    }

    @Override
    public Dictionary getDictionary(int dictionaryId) {
        Call<Dictionary> dictionaryCall = dictionaryApi.getDictionary(dictionaryId);
        Dictionary dictionary = null;
        try {
            Response<Dictionary> dictionaryResponse = dictionaryCall.execute();
            dictionary = dictionaryResponse.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dictionary;
    }

    @Override
    public List<Dictionary> getDictionaries(int offset, int limit) {
        Call<List<Dictionary>> dictionaryCall = dictionaryApi.getDictionaries(offset, limit);
        List<Dictionary> dictionaryList = new ArrayList<>(limit);
        try {
            Response<List<Dictionary>> dictionaryResponse = dictionaryCall.execute();
            dictionaryList = dictionaryResponse.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dictionaryList;
    }
}
