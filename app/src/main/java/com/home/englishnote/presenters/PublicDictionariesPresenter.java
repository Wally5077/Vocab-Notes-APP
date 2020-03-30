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
            try {
                List<Dictionary> dictionaryList =
                        dictionaryRepository.getDictionaries(offset, limit);
                threadExecutor.executeUiThread(
                        () -> publicDictionaryView.onGetDictionariesSuccessfully(dictionaryList));
            } catch (Exception err) {

            }
        });
    }

    public interface PublicDictionaryView {
        void onGetDictionariesSuccessfully(List<Dictionary> dictionaryList);
    }
}
