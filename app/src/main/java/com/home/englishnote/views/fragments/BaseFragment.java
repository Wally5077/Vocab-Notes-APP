package com.home.englishnote.views.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.home.englishnote.R;
import com.home.englishnote.models.entities.Member;
import com.home.englishnote.models.entities.Token;
import com.home.englishnote.utils.VocabularyNoteKeyword;
import com.home.englishnote.views.activities.DictionaryHomePageActivity;

public class BaseFragment extends Fragment {

    protected DictionaryHomePageActivity mainPageActivity;
    protected FragmentManager fragmentManager;
    protected Member member;
    protected Token token;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainPageActivity = (DictionaryHomePageActivity) getActivity();
        // Todo
//        member = (Member) mainPageActivity.getIntent().getSerializableExtra("member");
//        token = (Token) mainPageActivity.getIntent().getSerializableExtra("token");
        fragmentManager = mainPageActivity.getSupportFragmentManager();
        member = new Member("firstName", "lastName",
                25, "email", "password");
    }

    protected void onChangeProfilePage(View v) {
        mainPageActivity.switchFragment(
                R.layout.fragment_member_profile_page, VocabularyNoteKeyword.DICTIONARY_HOME_PAGE_CONTAINER);
    }

    protected void onBackButtonClick(View v) {
        mainPageActivity.switchFragment(
                R.layout.fragment_public_dictionaries, VocabularyNoteKeyword.DICTIONARY_HOME_PAGE_CONTAINER);
    }
}
