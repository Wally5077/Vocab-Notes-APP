package com.home.englishnote.presenters;

import com.home.englishnote.models.entities.WordGroup;
import com.home.englishnote.models.repositories.MemberRepository;
import com.home.englishnote.models.repositories.WordGroupRepository;
import com.home.englishnote.utils.ThreadExecutor;

import java.util.List;

public class PublicWordGroupsPresenter {

    private PublicWordGroupsView publicWordGroupsView;
    private MemberRepository memberRepository;
    private WordGroupRepository wordGroupRepository;
    private ThreadExecutor threadExecutor;

    public PublicWordGroupsPresenter(PublicWordGroupsView publicWordGroupsView,
                                     MemberRepository memberRepository,
                                     WordGroupRepository wordGroupRepository,
                                     ThreadExecutor threadExecutor) {
        this.publicWordGroupsView = publicWordGroupsView;
        this.memberRepository = memberRepository;
        this.wordGroupRepository = wordGroupRepository;
        this.threadExecutor = threadExecutor;
    }

    public void getWordGroups(int dictionaryId, int offset, int limit) {
        threadExecutor.execute(() -> {
            try {
                List<WordGroup> groupList = wordGroupRepository
                        .getWordGroupsFromPublicDictionary(dictionaryId, offset, limit);
                threadExecutor.executeUiThread(
                        () -> publicWordGroupsView
                                .onGetWordGroupsSuccessfully(groupList));
            } catch (RuntimeException err) {
                err.printStackTrace();
            }
        });
    }

    public void addFavoriteDictionaryList(int dictionaryId, int memberId) {
        threadExecutor.execute(() -> {
            try {
                memberRepository.addFavoriteDictionary(dictionaryId, memberId);
                threadExecutor.executeUiThread(
                        () -> publicWordGroupsView.onAddFavoriteDictionaryListSuccessfully());
            } catch (Exception err) {
                threadExecutor.executeUiThread(
                        () -> publicWordGroupsView.onAddFavoriteDictionaryListFailed());
            }

        });
    }

    public interface PublicWordGroupsView {
        void onGetWordGroupsSuccessfully(List<WordGroup> wordGroupList);

        void onAddFavoriteDictionaryListSuccessfully();

        void onAddFavoriteDictionaryListFailed();
    }
}
