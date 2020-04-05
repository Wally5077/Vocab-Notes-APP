package com.home.englishnote.presenters;

import com.home.englishnote.exceptions.WordNotFoundException;
import com.home.englishnote.models.entities.Dictionary;
import com.home.englishnote.models.entities.Word;
import com.home.englishnote.models.repositories.DictionaryRepository;
import com.home.englishnote.models.repositories.MemberRepository;
import com.home.englishnote.models.repositories.WordRepository;
import com.home.englishnote.utils.ThreadExecutor;

import java.util.List;

public class PublicDictionaryPagePresenter {

    private PublicDictionaryPageView publicDictionaryPageView;
    private DictionaryRepository dictionaryRepository;
    private WordRepository wordRepository;
    private MemberRepository memberRepository;
    private ThreadExecutor threadExecutor;

    public PublicDictionaryPagePresenter(PublicDictionaryPageView publicDictionaryPageView,
                                         DictionaryRepository dictionaryRepository,
                                         WordRepository wordRepository,
                                         MemberRepository memberRepository,
                                         ThreadExecutor threadExecutor) {
        this.publicDictionaryPageView = publicDictionaryPageView;
        this.dictionaryRepository = dictionaryRepository;
        this.wordRepository = wordRepository;
        this.memberRepository = memberRepository;
        this.threadExecutor = threadExecutor;
    }

    public void getDictionaryList() {
        threadExecutor.execute(() -> {
            try {
                List<Dictionary> dictionaryList =
                        dictionaryRepository.getDictionaries(-1, -1);
                threadExecutor.executeUiThread(() -> publicDictionaryPageView
                        .onGetDictionaryListSuccessfully(dictionaryList));
            } catch (Exception e) {

            }
        });
    }

    public void getWord(String wordName) {
        threadExecutor.execute(() -> {
            try {
                Word word = wordRepository.getWord(wordName);
                threadExecutor.executeUiThread(() ->
                        publicDictionaryPageView.onGetWordSuccessfully(word));
            } catch (WordNotFoundException e) {

            }
        });
    }

    public void getPossibleWord(String keyword) {
        threadExecutor.execute(() -> {
            try {
                List<Word> wordList = wordRepository.getPossibleWord(keyword);
                threadExecutor.executeUiThread(() ->
                        publicDictionaryPageView.onGetPossibleWordSuccessfully(wordList));
            } catch (Exception e) {

            }
        });
    }

    public interface PublicDictionaryPageView {

        void onGetPossibleWordSuccessfully(List<Word> wordList);

        void onGetWordSuccessfully(Word word);

        void onGetDictionaryListSuccessfully(List<Dictionary> dictionaryList);
    }
}
