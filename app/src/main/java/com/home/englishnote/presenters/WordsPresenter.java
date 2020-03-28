package com.home.englishnote.presenters;

import com.home.englishnote.models.entities.Word;
import com.home.englishnote.models.repositories.WordRepository;
import com.home.englishnote.utils.ThreadExecutor;

public class WordsPresenter {

    private WordsView wordsView;
    private WordRepository wordRepository;
    private ThreadExecutor threadExecutor;

    public WordsPresenter(WordsView wordsView,
                          WordRepository wordRepository,
                          ThreadExecutor threadExecutor) {
        this.wordsView = wordsView;
        this.wordRepository = wordRepository;
        this.threadExecutor = threadExecutor;
    }


    public interface WordsView {
    }
}
