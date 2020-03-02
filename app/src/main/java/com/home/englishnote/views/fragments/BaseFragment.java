package com.home.englishnote.views.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.home.englishnote.models.entities.Member;
import com.home.englishnote.views.activities.MainPageActivity;

public class BaseFragment extends Fragment {

    protected MainPageActivity mainPageActivity;
    protected Member member;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainPageActivity = (MainPageActivity) getActivity();
//        member = (Member) mainPageActivity.getIntent().getSerializableExtra("member");
        member = new Member("firstName", "lastName",
                25, "email", "password");
    }
}
