package com.home.englishnote.presenters;

import com.home.englishnote.models.repositories.MemberRepository;
import com.home.englishnote.utils.ThreadExecutor;

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

    public interface OwnDictionariesView {

    }
}
