package com.home.englishnote.views.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.home.englishnote.R;
import com.home.englishnote.models.entities.Member;
import com.home.englishnote.models.entities.Token;
import com.home.englishnote.views.activities.DictionaryHomePageActivity;

import java.io.Serializable;

public class BaseFragment extends Fragment {

    protected final static int DICTIONARY_HOME_PAGE_CONTAINER = R.id.DictionaryHomePageContainer;

    protected final static int PUBLIC_DICTIONARY_CONTAINER = R.id.PublicDictionaryPageContainer;
    protected final static int MEMBER_PROFILE_CONTAINER = R.id.memberProfilePageContainer;

    protected DictionaryHomePageActivity mainPageActivity;
    protected Member member;
    protected Token token;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainPageActivity = (DictionaryHomePageActivity) getActivity();
        // Todo
//        member = (Member) mainPageActivity.getIntent().getSerializableExtra("member");
//        token = (Token) mainPageActivity.getIntent().getSerializableExtra("token");
        member = new Member("firstName", "lastName",
                25, "email", "password");
    }

    protected void onSwitchProfilePage(View v) {
        switchFragment(R.layout.fragment_member_profile_page, DICTIONARY_HOME_PAGE_CONTAINER);
    }

    protected void switchDictionaryHomePage(Serializable... serializables) {
        switchFragment(R.layout.fragment_public_dictionary_page,
                R.id.DictionaryHomePageContainer, serializables);
    }

    protected void switchFragment(int fragmentId, int containerId,
                                  Serializable... serializableArray) {
        mainPageActivity.switchFragment(fragmentId, containerId, serializableArray);
    }

}
