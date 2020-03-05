package com.home.englishnote.presenters;

import com.home.englishnote.models.entities.WordGroup;
import com.home.englishnote.models.repositories.WordGroupRepository;
import com.home.englishnote.utils.ThreadExecutor;

import java.util.List;

public class WordGroupsPresenter {

    private WordGroupsView wordGroupsView;
    private WordGroupRepository wordGroupRepository;
    private ThreadExecutor threadExecutor;

    public WordGroupsPresenter(WordGroupsView
                                       wordGroupsView,
                               WordGroupRepository wordGroupRepository,
                               ThreadExecutor threadExecutor) {
        this.wordGroupsView = wordGroupsView;
        this.wordGroupRepository = wordGroupRepository;
        this.threadExecutor = threadExecutor;
    }

    public void getWordGroups(int dictionaryId, int offset, int limit) {
        threadExecutor.execute(() -> {
            try {
                List<WordGroup> groupList = wordGroupRepository
                        .getWordGroupsFromDictionary(dictionaryId, offset, limit);
                threadExecutor.executeUiThread(
                        () -> wordGroupsView
                                .onGetWordGroupsSuccessfully(groupList));
            } catch (RuntimeException err) {
                err.printStackTrace();
            }
        });
    }

    public interface WordGroupsView {
        void onGetWordGroupsSuccessfully(List<WordGroup> wordGroupList);
    }
}
