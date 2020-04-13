package com.home.englishnote.presenters;

import com.home.englishnote.models.entities.WordGroup;
import com.home.englishnote.models.repositories.WordGroupRepository;
import com.home.englishnote.utils.ThreadExecutor;

import java.util.List;

public class FavoriteWordGroupsPresenter {

    private FavoriteWordGroupsView favoriteWordGroupsView;
    private WordGroupRepository wordGroupRepository;
    private ThreadExecutor threadExecutor;

    public FavoriteWordGroupsPresenter(FavoriteWordGroupsView favoriteWordGroupsView,
                                       WordGroupRepository wordGroupRepository,
                                       ThreadExecutor threadExecutor) {
        this.favoriteWordGroupsView = favoriteWordGroupsView;
        this.wordGroupRepository = wordGroupRepository;
        this.threadExecutor = threadExecutor;
    }

    public void getWordGroups(int memberId, int offset, int limit) {
        threadExecutor.execute(() -> {
            try {
                List<WordGroup> groupList = wordGroupRepository
                        .getWordGroupsFromFavoriteDictionary(memberId, offset, limit);
                threadExecutor.executeUiThread(
                        () -> favoriteWordGroupsView
                                .onGetWordGroupsSuccessfully(groupList));
            } catch (RuntimeException err) {
                err.printStackTrace();
            }
        });
    }

    public interface FavoriteWordGroupsView {
        void onGetWordGroupsSuccessfully(List<WordGroup> wordGroupList);
    }
}
