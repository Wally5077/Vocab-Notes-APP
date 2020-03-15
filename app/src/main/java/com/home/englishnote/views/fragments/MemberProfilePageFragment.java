package com.home.englishnote.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.home.englishnote.R;

public class MemberProfilePageFragment extends BaseFragment {

    private ImageView memberProfilePagePhoto;
    private TextView memberProfilePageName;
    private Button memberProfilePageEditProfileButton;
    private Button memberProfilePageCreateDictionaryButton;
    private DrawerLayout memberProfilePageDrawer;
    private ImageView memberProfilePageDrawerButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater
                .inflate(R.layout.fragment_member_profile_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        init();
    }

    private void findViews(View view) {
        memberProfilePageDrawer = view.findViewById(R.id.memberProfilePageDrawer);
        memberProfilePageDrawerButton = view.findViewById(R.id.memberProfilePageDrawerButton);
        memberProfilePagePhoto = view.findViewById(R.id.memberProfilePagePhoto);
        memberProfilePageName = view.findViewById(R.id.memberProfilePageName);
        memberProfilePageEditProfileButton = view.findViewById(R.id.memberProfilePageEditProfileButton);
        memberProfilePageCreateDictionaryButton = view.findViewById(R.id.memberProfilePageCreateDictionaryButton);
    }

    private void init() {
        setToolBar();
        setMemberPhoto();
        setMemberName();
        setMemberProfileEditProfileButtonClick();
        setMemberProfileCreateDictionaryButtonClick();
        setDefaultPage();
    }

    private void setToolBar() {
        memberProfilePageDrawerButton.setOnClickListener(
                v -> memberProfilePageDrawer.openDrawer(GravityCompat.START));
    }

    private void setMemberPhoto() {
        Glide.with(getContext())
                .asBitmap()
                .load(member.getImageURL())
                .fitCenter()
                .error(R.drawable.big_user_pic)
                .into(memberProfilePagePhoto);
    }

    private void setMemberName() {
        memberProfilePageName.setText(member.getFirstName());
    }

    private void setMemberProfileEditProfileButtonClick() {
        memberProfilePageEditProfileButton.setOnClickListener(
                v -> switchFragment(
                        R.layout.fragment_member_profile_modify, PUBLIC_DICTIONARY_CONTAINER));
    }

    private void setMemberProfileCreateDictionaryButtonClick() {
        memberProfilePageCreateDictionaryButton.setOnClickListener(
                v -> switchFragment(
                        R.layout.fragment_create_own_dictionary, MEMBER_PROFILE_CONTAINER));
    }

    private void setDefaultPage() {
        switchFragment(R.layout.fragment_own_dictionaries, MEMBER_PROFILE_CONTAINER);
    }
}
