package com.home.englishnote.presenters;

import com.home.englishnote.models.entities.Dictionary;
import com.home.englishnote.models.repositories.DictionaryRepository;
import com.home.englishnote.utils.ThreadExecutor;

import java.util.List;

public class MainPagePresenter {

    private MainPageView mainPageView;
    private DictionaryRepository dictionaryRepository;
    private ThreadExecutor threadExecutor;

    public MainPagePresenter(MainPageView mainPageView,
                             DictionaryRepository dictionaryRepository,
                             ThreadExecutor threadExecutor) {
        this.mainPageView = mainPageView;
        this.dictionaryRepository = dictionaryRepository;
        this.threadExecutor = threadExecutor;
    }

    public void getDictionaries() {
        threadExecutor.execute(() -> {
            threadExecutor.executeUiThread(() -> {
                List<Dictionary> dictionaryList =
                        dictionaryRepository.getDictionaries(0, 3);
                mainPageView.onDictionariesSuccessfully(dictionaryList);
            });
        });
    }

    public interface MainPageView {
        void onDictionariesSuccessfully(List<Dictionary> dictionaryList);
    }
}
