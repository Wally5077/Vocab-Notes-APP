package com.home.englishnote.models;

import com.home.englishnote.utils.WordGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RetrofitWordGroupRepository implements WordGroupRepository {

    private WordGroupApi wordGroupApi;

    public RetrofitWordGroupRepository(Retrofit retrofit) {
        wordGroupApi = retrofit.create(WordGroupApi.class);
    }

    private interface WordGroupApi {
        @GET("/api/public/wordgroups/{id}")
        Call<WordGroup> getWordGroup(@Path("id") int wordGroupId);

        @GET("/api/public/dictionaries/{id}/wordgroups")
        Call<List<WordGroup>> getWordGroupsFromDictionary(
                @Path("id") int dictionaryId,
                @Query("offset") int offset,
                @Query("limit") int limit);
    }

    @Override
    public WordGroup getWordGroup(int wordGroupId) {
        Call<WordGroup> wordGroupCall = wordGroupApi.getWordGroup(wordGroupId);
        WordGroup wordGroup = null;
        try {
            Response<WordGroup> wordGroupResponse = wordGroupCall.execute();
            wordGroup = wordGroupResponse.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordGroup;
    }

    @Override
    public List<WordGroup> getWordGroupsFromDictionary(int dictionaryId, int offset, int limit) {
        Call<List<WordGroup>> wordGroupCall =
                wordGroupApi.getWordGroupsFromDictionary(dictionaryId, offset, limit);
        List<WordGroup> wordGroupList = new ArrayList<>(limit);
        try {
            Response<List<WordGroup>> wordGroupResponse = wordGroupCall.execute();
            wordGroupList = wordGroupResponse.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordGroupList;
    }
}
