package com.home.englishnote.views.fragments.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.home.englishnote.R;
import com.home.englishnote.views.fragments.BaseFragment;

public class UserProfileFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_member_profile_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        init();
    }

    private void findViews(View view) {
//        ImageView userProfilePageBackButton = view.findViewById(R.id.userProfilePageBackButton);
        ImageView userProfilePagePhoto = view.findViewById(R.id.userProfilePagePhoto);
        TextView userProfilePageName = view.findViewById(R.id.userProfilePageName);
        View userModifyPageLogOutButton = view.findViewById(R.id.userModifyPageLogOutButton);
        View userModifyPageEditProfileButton = view.findViewById(R.id.userModifyPageEditProfileButton);
        View userModifyPageCreateDictionaryButton = view.findViewById(R.id.userProfilePageCreateDictionaryButton);
        View userModifyPageMyFavoriteButton = view.findViewById(R.id.userProfilePageMyFavoriteButton);
    }

    private void init() {

    }
}
