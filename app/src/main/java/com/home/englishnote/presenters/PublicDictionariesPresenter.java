package com.home.englishnote.presenters;

import com.home.englishnote.models.entities.Dictionary;
import com.home.englishnote.models.repositories.DictionaryRepository;
import com.home.englishnote.utils.ThreadExecutor;

import java.util.List;

public class PublicDictionariesPresenter {

    private PublicDictionaryView publicDictionaryView;
    private DictionaryRepository dictionaryRepository;
    private ThreadExecutor threadExecutor;

    public PublicDictionariesPresenter(PublicDictionaryView publicDictionaryView,
                                       DictionaryRepository dictionaryRepository,
                                       ThreadExecutor threadExecutor) {
        this.publicDictionaryView = publicDictionaryView;
        this.dictionaryRepository = dictionaryRepository;
        this.threadExecutor = threadExecutor;
    }

    public void getDictionaries(int offset, int limit) {
        threadExecutor.execute(() -> {
            threadExecutor.executeUiThread(() -> {
                List<Dictionary> dictionaryList =
                        dictionaryRepository.getDictionaries(offset, limit);
                publicDictionaryView.onGetDictionariesSuccessfully(dictionaryList);
            });
        });
    }

    public interface PublicDictionaryView {
        void onGetDictionariesSuccessfully(List<Dictionary> dictionaryList);
    }
}
