package com.home.englishnote.presenters;

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
    }
}
