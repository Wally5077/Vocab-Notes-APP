package com.home.englishnote.presenters;

import com.home.englishnote.models.entities.Dictionary;
import com.home.englishnote.models.repositories.MemberRepository;
import com.home.englishnote.utils.ThreadExecutor;

import java.util.List;

public class OwnDictionariesPresenter {

    private OwnDictionariesView ownDictionariesView;
    private MemberRepository memberRepository;
    private ThreadExecutor threadExecutor;

    public OwnDictionariesPresenter(OwnDictionariesView ownDictionariesView,
                                    MemberRepository memberRepository,
                                    ThreadExecutor threadExecutor) {
        this.ownDictionariesView = ownDictionariesView;
        this.memberRepository = memberRepository;
        this.threadExecutor = threadExecutor;
    }

    public void getOwnDictionaries(int memberId, int offset, int limit) {
        threadExecutor.execute(() -> {
            try {
                threadExecutor.executeUiThread(() -> {
                    List<Dictionary> dictionaryList =
                            memberRepository.getOwnDictionaries(memberId, offset, limit);
                    ownDictionariesView.onGetDictionariesSuccessfully(dictionaryList);
                });
            } catch (Exception err) {

            }
        });
    }

    public interface OwnDictionariesView {
        void onGetDictionariesSuccessfully(List<Dictionary> dictionaryList);
    }
}
