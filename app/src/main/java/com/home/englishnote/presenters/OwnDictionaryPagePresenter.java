package com.home.englishnote.presenters;

import com.home.englishnote.models.entities.WordGroup;
import com.home.englishnote.models.repositories.WordGroupRepository;
import com.home.englishnote.utils.ThreadExecutor;

import java.util.List;

public class OwnDictionaryPagePresenter {

    private OwnDictionaryPageView ownDictionaryPageView;
    private WordGroupRepository wordGroupRepository;
    private ThreadExecutor threadExecutor;

    public OwnDictionaryPagePresenter(OwnDictionaryPageView ownDictionaryPageView,
                                      WordGroupRepository wordGroupRepository,
                                      ThreadExecutor threadExecutor) {
        this.ownDictionaryPageView = ownDictionaryPageView;
        this.wordGroupRepository = wordGroupRepository;
        this.threadExecutor = threadExecutor;
    }

    public void getWordGroups(int memberId, int offset, int limit) {
        threadExecutor.execute(() -> {
            try {
                List<WordGroup> groupList = wordGroupRepository
                        .getWordGroupsFromOwnDictionary(memberId, offset, limit);
                threadExecutor.executeUiThread(
                        () -> ownDictionaryPageView
                                .onGetWordGroupsSuccessfully(groupList));
            } catch (RuntimeException err) {
                err.printStackTrace();
            }
        });
    }

    public interface OwnDictionaryPageView {
        void onGetWordGroupsSuccessfully(List<WordGroup> wordGroupList);
    }
}
