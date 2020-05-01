package com.home.englishnote.views.fragments.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.home.englishnote.R;
import com.home.englishnote.views.fragments.BaseFragment;

public class MemberProfilePageFragment extends BaseFragment {

    private ImageView memberProfilePagePhoto;
    private TextView memberProfilePageName;
    private Button memberProfilePageEditProfileButton;
    private Button memberProfilePageCreateDictionaryButton;

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
    }

    private void findViews(View view) {
        memberProfilePagePhoto = view.findViewById(R.id.memberProfilePagePhoto);
        memberProfilePageName = view.findViewById(R.id.memberProfilePageName);
        memberProfilePageEditProfileButton = view.findViewById(R.id.memberProfilePageEditProfileButton);
        memberProfilePageCreateDictionaryButton = view.findViewById(R.id.memberProfilePageCreateDictionaryButton);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        setUpMemberPhoto();
        setUpMemberName();
        setUpMemberProfileEditProfileButtonClick();
        setUpMemberProfileCreateDictionaryButtonClick();
    }

    private void setUpMemberPhoto() {
        Glide.with(getContext())
                .asBitmap()
                .load(user.getImageURL())
                .fitCenter()
                .error(R.drawable.big_user_pic)
                .into(memberProfilePagePhoto);
    }

    private void setUpMemberName() {
        memberProfilePageName.setText(user.getFirstName());
    }

    private void setUpMemberProfileEditProfileButtonClick() {
        memberProfilePageEditProfileButton.setOnClickListener(v -> {
            Fragment mainFragment = switchMainFragment(R.layout.fragment_public_dictionary_page);
            switchSecondaryFragment(mainFragment, R.layout.fragment_member_profile_modify);
        });
    }

    private void setUpMemberProfileCreateDictionaryButtonClick() {
        memberProfilePageCreateDictionaryButton.setOnClickListener(
                v -> switchSecondaryFragment(null,
                        R.layout.fragment_create_own_dictionary));
    }

}
