package com.home.englishnote.presenters;

import com.home.englishnote.models.entities.Dictionary;
import com.home.englishnote.models.repositories.MemberRepository;
import com.home.englishnote.utils.ThreadExecutor;

import java.util.List;

public class FavoriteDictionariesPresenter {

    private FavoriteDictionariesView favoriteDictionariesView;
    private MemberRepository memberRepository;
    private ThreadExecutor threadExecutor;

    public FavoriteDictionariesPresenter(FavoriteDictionariesView favoriteDictionariesView,
                                         MemberRepository memberRepository,
                                         ThreadExecutor threadExecutor) {
        this.favoriteDictionariesView = favoriteDictionariesView;
        this.memberRepository = memberRepository;
        this.threadExecutor = threadExecutor;
    }

    public void getOwnDictionaries(int memberId, int offset, int limit) {
        threadExecutor.execute(() -> {
            try {
                threadExecutor.executeUiThread(() -> {
                    List<Dictionary> dictionaryList =
                            memberRepository.getFavoriteDictionaries(memberId, offset, limit);
                    favoriteDictionariesView.onGetDictionariesSuccessfully(dictionaryList);
                });
            } catch (Exception err) {

            }
        });
    }

    public interface FavoriteDictionariesView {
        void onGetDictionariesSuccessfully(List<Dictionary> dictionaryList);
    }
}
