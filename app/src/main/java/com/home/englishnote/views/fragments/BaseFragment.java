package com.home.englishnote.views.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.home.englishnote.R;
import com.home.englishnote.models.entities.Member;
import com.home.englishnote.models.entities.Role;
import com.home.englishnote.models.entities.Token;
import com.home.englishnote.models.entities.User;
import com.home.englishnote.utils.RandomVacabProducer;
import com.home.englishnote.views.activities.DictionaryHomePageActivity;

import java.io.Serializable;

public class BaseFragment extends Fragment {

    protected final static int DICTIONARY_HOME_PAGE_CONTAINER = R.id.dictionaryHomePageContainer;
    protected final static int PUBLIC_DICTIONARY_CONTAINER = R.id.publicDictionaryPageContainer;
    protected final static int MEMBER_PROFILE_CONTAINER = R.id.memberProfilePageContainer;

    protected DictionaryHomePageActivity dictionaryHomePageActivity;
    protected User user;
    protected Token token;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dictionaryHomePageActivity = (DictionaryHomePageActivity) getActivity();
        // Todo
        user = dictionaryHomePageActivity.getUser();
        token = dictionaryHomePageActivity.getToken();
    }

    protected void backLastFragment() {
        dictionaryHomePageActivity.onBackPressed();
    }

    protected void switchFragment(int fragmentId, int containerId,
                                  Serializable... serializableArray) {
        dictionaryHomePageActivity.switchFragment(fragmentId, containerId, serializableArray);
    }

}
