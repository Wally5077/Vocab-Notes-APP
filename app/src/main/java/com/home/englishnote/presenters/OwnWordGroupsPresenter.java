package com.home.englishnote.presenters;

import com.home.englishnote.models.entities.WordGroup;
import com.home.englishnote.models.repositories.WordGroupRepository;
import com.home.englishnote.utils.ThreadExecutor;

import java.util.List;

public class OwnWordGroupsPresenter {

    private OwnWordGroupsView ownWordGroupsView;
    private WordGroupRepository wordGroupRepository;
    private ThreadExecutor threadExecutor;

    public OwnWordGroupsPresenter(OwnWordGroupsView ownWordGroupsView,
                                  WordGroupRepository wordGroupRepository,
                                  ThreadExecutor threadExecutor) {
        this.ownWordGroupsView = ownWordGroupsView;
        this.wordGroupRepository = wordGroupRepository;
        this.threadExecutor = threadExecutor;
    }

    public void getWordGroups(int memberId, int offset, int limit) {
        threadExecutor.execute(() -> {
            try {
                List<WordGroup> groupList = wordGroupRepository
                        .getWordGroupsFromOwnDictionary(memberId, memberId, offset, limit);
                threadExecutor.executeUiThread(
                        () -> ownWordGroupsView
                                .onGetWordGroupsSuccessfully(groupList));
            } catch (RuntimeException err) {
                err.printStackTrace();
            }
        });
    }

    public interface OwnWordGroupsView {
        void onGetWordGroupsSuccessfully(List<WordGroup> wordGroupList);
    }
}
