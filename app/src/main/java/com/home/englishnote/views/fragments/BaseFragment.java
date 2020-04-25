package com.home.englishnote.views.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.home.englishnote.models.entities.Token;
import com.home.englishnote.models.entities.User;
import com.home.englishnote.views.activities.DictionaryHomePageActivity;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public abstract class BaseFragment extends Fragment implements Cloneable {

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

    protected void switchFragment(int fragmentId, Serializable... serializableArray) {
        dictionaryHomePageActivity.switchFragment(fragmentId, serializableArray);
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
