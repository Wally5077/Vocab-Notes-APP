package com.home.englishnote.presenters;

import com.home.englishnote.models.entities.WordGroup;
import com.home.englishnote.models.repositories.WordGroupRepository;
import com.home.englishnote.utils.ThreadExecutor;

import java.util.List;

public class PublicWordGroupsPresenter {

    private PublicWordGroupsView publicWordGroupsView;
    private WordGroupRepository wordGroupRepository;
    private ThreadExecutor threadExecutor;

    public PublicWordGroupsPresenter(PublicWordGroupsView
                                       publicWordGroupsView,
                                     WordGroupRepository wordGroupRepository,
                                     ThreadExecutor threadExecutor) {
        this.publicWordGroupsView = publicWordGroupsView;
        this.wordGroupRepository = wordGroupRepository;
        this.threadExecutor = threadExecutor;
    }

    public void getWordGroups(int dictionaryId, int offset, int limit) {
        threadExecutor.execute(() -> {
            try {
                List<WordGroup> groupList = wordGroupRepository
                        .getWordGroupsFromDictionary(dictionaryId, offset, limit);
                threadExecutor.executeUiThread(
                        () -> publicWordGroupsView
                                .onGetWordGroupsSuccessfully(groupList));
            } catch (RuntimeException err) {
                err.printStackTrace();
            }
        });
    }

    public interface PublicWordGroupsView {
        void onGetWordGroupsSuccessfully(List<WordGroup> wordGroupList);
    }
}
