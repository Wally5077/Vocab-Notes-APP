package com.home.englishnote.presenters;

import android.graphics.Bitmap;

import com.home.englishnote.models.entities.Token;
import com.home.englishnote.models.repositories.MemberRepository;
import com.home.englishnote.utils.ThreadExecutor;

public class MemberProfileModifyPresenter {

    private MemberProfileModifyView memberProfileModifyView;
    private MemberRepository memberRepository;
    private ThreadExecutor threadExecutor;

    public MemberProfileModifyPresenter(MemberProfileModifyView memberProfileModifyView,
                                        MemberRepository memberRepository,
                                        ThreadExecutor threadExecutor) {
        this.memberProfileModifyView = memberProfileModifyView;
        this.memberRepository = memberRepository;
        this.threadExecutor = threadExecutor;
    }

    public void uploadPhoto(Token token, int memberId, Bitmap photoBitmap) {
        threadExecutor.execute(() -> {
            try {
                threadExecutor.executeUiThread(
                        () -> memberProfileModifyView.onUploadMemberPhotoSuccessfully(photoBitmap));
            } catch (Exception err) {

            }
        });
    }

    public interface MemberProfileModifyView {
        void onUploadMemberPhotoSuccessfully(Bitmap photoBitmap);
    }
}
