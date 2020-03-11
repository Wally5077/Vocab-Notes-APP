package com.home.englishnote.presenters;

import com.home.englishnote.models.repositories.MemberRepository;
import com.home.englishnote.utils.ThreadExecutor;

public class PublicDictionaryPagePresenter {

    private PublicDictionaryPageView publicDictionaryPageView;
    private MemberRepository memberRepository;
    private ThreadExecutor threadExecutor;

    public PublicDictionaryPagePresenter(PublicDictionaryPageView publicDictionaryPageView,
                                         MemberRepository memberRepository,
                                         ThreadExecutor threadExecutor) {
        this.publicDictionaryPageView = publicDictionaryPageView;
        this.memberRepository = memberRepository;
        this.threadExecutor = threadExecutor;
    }


    public interface PublicDictionaryPageView {
    }
}
