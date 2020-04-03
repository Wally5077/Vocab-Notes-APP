package com.home.englishnote.views.fragments.secondary.profile;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.home.englishnote.R;
import com.home.englishnote.presenters.CreateOwnDictionaryPresenter;
import com.home.englishnote.utils.Global;
import com.home.englishnote.views.fragments.BaseFragment;

import static com.home.englishnote.presenters.CreateOwnDictionaryPresenter.*;

public class CreateOwnDictionaryFragment extends BaseFragment implements CreateOwnDictionaryView {

    private EditText ownDictionaryTitle;
    private EditText ownDictionaryDescription;
    private Button saveOwnDictionaryButton;
    private Button cancelOwnDictionaryButton;
    private CreateOwnDictionaryPresenter createOwnDictionaryPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater
                .inflate(R.layout.fragment_create_own_dictionary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        init();
    }

    @Override
    public void updateFragmentData() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        ownDictionaryTitle.setText("");
        ownDictionaryDescription.setText("");
    }

    private void findViews(View view) {
        ownDictionaryTitle = view.findViewById(R.id.ownDictionaryTitle);
        ownDictionaryDescription = view.findViewById(R.id.ownDictionaryDescription);
        saveOwnDictionaryButton = view.findViewById(R.id.saveOwnDictionaryButton);
        cancelOwnDictionaryButton = view.findViewById(R.id.cancelOwnDictionaryButton);
    }

    private void init() {
        createOwnDictionaryPresenter = new CreateOwnDictionaryPresenter(
                this, Global.memberRepository(), Global.threadExecutor());
        setOwnDictionaryTitle();
        setOwnDictionaryDescription();
        setSaveOwnDictionaryButton();
        setCancelOwnDictionaryButton();
    }

    private void setOwnDictionaryTitle() {
        ownDictionaryTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ownDictionaryTitle.getText().toString().contains("\n")) {
                    ownDictionaryTitle
                            .setText(ownDictionaryTitle.getText().toString()
                                    .replace("\n", ""));
                }
                if (s.toString().length() > 80) {
                    clearText(ownDictionaryTitle);
                }
            }
        });
    }

    private void setOwnDictionaryDescription() {
        ownDictionaryDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 80) {
                    clearText(ownDictionaryDescription);
                }
            }
        });
    }

    private void clearText(TextView... textViews) {
        for (TextView textView : textViews) {
            textView.setText("");
        }
    }

    private void setSaveOwnDictionaryButton() {
        saveOwnDictionaryButton.setOnClickListener(
                v -> createOwnDictionaryPresenter.createOwnDictionary(
                        token, user.getId(), ownDictionaryTitle.getText().toString(),
                        ownDictionaryDescription.getText().toString()));
    }

    private void setCancelOwnDictionaryButton() {
        cancelOwnDictionaryButton.setOnClickListener(
                v -> {
                    clearText(ownDictionaryTitle, ownDictionaryDescription);
                    backLastFragment();
                });
    }

    @Override
    public void createOwnDictionarySuccessfully() {
        clearText(ownDictionaryTitle, ownDictionaryDescription);
        backLastFragment();
    }
}
