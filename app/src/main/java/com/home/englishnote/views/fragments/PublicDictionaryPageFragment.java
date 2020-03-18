package com.home.englishnote.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.home.englishnote.R;

public class PublicDictionaryPageFragment extends BaseFragment {

    private TextView memberName;
    private ImageView memberPhoto;
    private View vocabSearch;
    private View dictionarySearch;
    private View memberProfile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater
                .inflate(R.layout.fragment_public_dictionary_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        init();
    }

    private void findViews(View view) {
        vocabSearch = view.findViewById(R.id.publicDictionaryPageVocabSearch);
        dictionarySearch = view.findViewById(R.id.publicDictionaryPageDictionarySearch);
        memberProfile = view.findViewById(R.id.publicDictionaryPageMemberProfile);
        memberName = view.findViewById(R.id.publicDictionaryPageMemberName);
        memberPhoto = view.findViewById(R.id.publicDictionaryPageMemberPhoto);
    }

    private void init() {
        setDefaultPage();
        setOnVocabSearchClick();
        setOnDictionarySearchClick();
        setOnMemberProfileClick();
        setMemberPhoto();
        setMemberName();
    }

    private void setOnVocabSearchClick() {
//        Todo
    }

    private void setOnDictionarySearchClick() {
//        Todo
    }

    private void setOnMemberProfileClick() {
        memberProfile.setOnClickListener(this::onSwitchProfilePage);
    }

    private void setMemberPhoto() {
        Glide.with(this)
                .asBitmap()
                .load(member.getImageURL())
                .fitCenter()
                .error(R.drawable.small_user_pic)
                .into(memberPhoto);
    }

    private void setMemberName() {
        String memberName = member.getFirstName();
        this.memberName.setText((memberName.isEmpty()) ? "memberName" : memberName);
    }

    private void setDefaultPage() {
        switchFragment(R.layout.fragment_public_dictionaries, PUBLIC_DICTIONARY_CONTAINER);
    }

}
