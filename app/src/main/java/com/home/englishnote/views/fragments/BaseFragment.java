package com.home.englishnote.views.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.home.englishnote.models.entities.Token;
import com.home.englishnote.models.entities.User;
import com.home.englishnote.views.activities.HomePageActivity;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public abstract class BaseFragment extends Fragment implements Cloneable {

    protected HomePageActivity homePageActivity;
    protected User user;
    protected Token token;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homePageActivity = (HomePageActivity) getActivity();
        user = homePageActivity.getUser();
        token = homePageActivity.getToken();
    }

    protected void onBackPressed() {
        homePageActivity.onBackPressed();
    }

    protected Fragment switchMainFragment(int fragmentId, Serializable... serializableArray) {
        return homePageActivity.switchMainFragment(fragmentId, serializableArray);
    }

    protected void switchSecondaryFragment(Fragment mainFragment, int fragmentId, Serializable... serializableArray) {
        homePageActivity.switchSecondaryFragment(mainFragment, fragmentId, serializableArray);
    }

    @NotNull
    public BaseFragment clone() {
        BaseFragment baseFragment = null;
        try {
            baseFragment = (BaseFragment) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return baseFragment;
    }
}
