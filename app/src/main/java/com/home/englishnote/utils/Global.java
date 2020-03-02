package com.home.englishnote.utils;

import com.home.englishnote.models.repositories.*;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class Global {

    private static Retrofit vocabularyNoteRetrofit = new Retrofit
            .Builder().baseUrl("https://vocab-notes-v2.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create()).build();

    private static MemberRepository memberRepository;

    public static MemberRepository memberRepository() {
//        return memberRepository = (memberRepository != null) ? memberRepository :
//                new RetrofitMemberRepository(vocabularyNoteRetrofit);
        return new StubMemberRepository();
    }

    private static DictionaryRepository dictionaryRepository;

    public static DictionaryRepository dictionaryRepository() {
//        return dictionaryRepository = (dictionaryRepository != null) ? dictionaryRepository :
//                new RetrofitDictionaryRepository(vocabularyNoteRetrofit);
        return new StubDictionaryRepository();
    }

    private static WordGroupRepository wordGroupRepository;


    public static WordGroupRepository wordGroupRepository() {
//        return wordGroupRepository = (wordGroupRepository != null) ? wordGroupRepository :
//                new RetrofitWordGroupRepository(vocabularyNoteRetrofit);
        return new StubWordGroupRepository();
    }

    private static WordRepository wordRepository;

    public static WordRepository wordRepository() {
//        return wordRepository = (wordRepository != null) ? wordRepository :
//                new RetrofitWordRepository(vocabularyNoteRetrofit);
        return new StubWordRepository();
    }

    private static ThreadExecutor threadExecutor;

    public static ThreadExecutor threadExecutor() {
        return threadExecutor = (threadExecutor == null) ?
                new AndroidThreadExecutor() : threadExecutor;
    }
}
