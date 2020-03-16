package com.home.englishnote.presenters;

import com.home.englishnote.models.entities.Token;
import com.home.englishnote.models.repositories.MemberRepository;
import com.home.englishnote.utils.ThreadExecutor;

public class CreateOwnDictionaryPresenter {

    private CreateOwnDictionaryView createOwnDictionaryView;
    private MemberRepository memberRepository;
    private ThreadExecutor threadExecutor;

    public CreateOwnDictionaryPresenter(CreateOwnDictionaryView createOwnDictionaryView,
                                        MemberRepository memberRepository,
                                        ThreadExecutor threadExecutor) {
        this.createOwnDictionaryView = createOwnDictionaryView;
        this.memberRepository = memberRepository;
        this.threadExecutor = threadExecutor;
    }

    public void createOwnDictionary(Token token, int memberId, String title, String description) {
        threadExecutor.execute(() -> {
            try {
                memberRepository.createOwnDictionary(token, memberId, title, description);
                threadExecutor.executeUiThread(
                        () -> createOwnDictionaryView.createOwnDictionarySuccessfully());
            } catch (Exception err) {
                err.printStackTrace();
            }
        });
    }

    public interface CreateOwnDictionaryView {
        void createOwnDictionarySuccessfully();
    }
}
