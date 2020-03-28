package com.home.englishnote.views.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.home.englishnote.R;
import com.home.englishnote.models.entities.Member;
import com.home.englishnote.models.entities.Token;
import com.home.englishnote.views.activities.DictionaryHomePageActivity;

import java.io.Serializable;

@RequiresApi(api = Build.VERSION_CODES.N)
public class BaseFragment extends Fragment {

    protected final static int DICTIONARY_HOME_PAGE_CONTAINER = R.id.dictionaryHomePageContainer;
    protected final static int PUBLIC_DICTIONARY_CONTAINER = R.id.publicDictionaryPageContainer;
    protected final static int MEMBER_PROFILE_CONTAINER = R.id.memberProfilePageContainer;

    protected DictionaryHomePageActivity dictionaryHomePageActivity;
    protected Member member;
    protected Token token;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dictionaryHomePageActivity = (DictionaryHomePageActivity) getActivity();
        // Todo
//        member = (Member) dictionaryHomePageActivity.getIntent().getSerializableExtra("member");
//        token = (Token) dictionaryHomePageActivity.getIntent().getSerializableExtra("token");
        member = new Member("firstName", "lastName",
                25, "email", "password");
        token = new Token();
    }

    protected void onSwitchProfilePage(View v) {
        switchFragment(R.layout.fragment_member_profile_page, DICTIONARY_HOME_PAGE_CONTAINER);
        switchFragment(R.layout.fragment_own_dictionaries, MEMBER_PROFILE_CONTAINER);
    }

    protected void backLastFragment() {
        dictionaryHomePageActivity.onBackPressed();
    }

    protected void switchFragment(int fragmentId, int containerId,
                                  Serializable... serializableArray) {
        dictionaryHomePageActivity.switchFragment(fragmentId, containerId, serializableArray);
    }

}
