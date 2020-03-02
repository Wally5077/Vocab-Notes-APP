package com.home.englishnote.presenters;

import com.home.englishnote.models.entities.Dictionary;
import com.home.englishnote.models.repositories.DictionaryRepository;
import com.home.englishnote.utils.ThreadExecutor;

import java.util.List;

public class DictionaryFragmentPresenter {

    private DictionaryFragmentView dictionaryFragmentView;
    private DictionaryRepository dictionaryRepository;
    private ThreadExecutor threadExecutor;

    public DictionaryFragmentPresenter(DictionaryFragmentView dictionaryFragmentView,
                                       DictionaryRepository dictionaryRepository,
                                       ThreadExecutor threadExecutor) {
        this.dictionaryFragmentView = dictionaryFragmentView;
        this.dictionaryRepository = dictionaryRepository;
        this.threadExecutor = threadExecutor;
    }

    public void getDictionaries() {
        threadExecutor.execute(() -> {
            threadExecutor.executeUiThread(() -> {
                List<Dictionary> dictionaryList =
                        dictionaryRepository.getDictionaries(0, 3);
                dictionaryFragmentView.onDictionariesSuccessfully(dictionaryList);
            });
        });
    }

    public interface DictionaryFragmentView {
        void onDictionariesSuccessfully(List<Dictionary> dictionaryList);
    }
}
