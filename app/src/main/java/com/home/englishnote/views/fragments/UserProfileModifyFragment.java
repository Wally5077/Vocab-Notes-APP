package com.home.englishnote.views.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.home.englishnote.R;

import java.util.ArrayList;
import java.util.List;

public class UserProfileModifyFragment extends BaseFragment {

    private View memberInfoBackButton;
    private Spinner ageModifySpinner;
    private View memberInfoModifyButton, emailAddressModifyButton, passwordModifyButton;
    private TextView newEmailAddress, passwordModifyPrompt, newPassword, confirmNewPassword;
    private TextView memberInfoModifyButtonText, emailAddressModifyButtonText, passwordModifyButtonText;
    private TextView ageContent;
    private EditText firstNameContent, lastNameContent;
    private EditText newEmailAddressContent, newPasswordContent, newConfirmPasswordContent;
    private ImageView memberInfoModifyButtonImage, emailAddressModifyButtonImage, passwordModifyButtonImage;

    // Todo data setting hasn't finished

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_profile_modify, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        init();
    }

    private void findViews(View view) {
        memberInfoBackButton = view.findViewById(R.id.memberInfoBackButton);
        ageModifySpinner = view.findViewById(R.id.profileAgeSpinner);
        ageContent = view.findViewById(R.id.ageContent);

        memberInfoModifyButton = view.findViewById(R.id.memberInfoModifyButton);
        memberInfoModifyButtonText = view.findViewById(R.id.memberInfoModifyButtonText);
        memberInfoModifyButtonImage = view.findViewById(R.id.memberInfoModifyButtonImage);

        emailAddressModifyButton = view.findViewById(R.id.emailAddressModifyButton);
        emailAddressModifyButtonText = view.findViewById(R.id.emailAddressModifyButtonText);
        emailAddressModifyButtonImage = view.findViewById(R.id.emailAddressModifyButtonImage);

        passwordModifyButton = view.findViewById(R.id.passwordModifyButton);
        passwordModifyButtonText = view.findViewById(R.id.passwordModifyButtonText);
        passwordModifyButtonImage = view.findViewById(R.id.passwordModifyButtonImage);

        firstNameContent = view.findViewById(R.id.firstNameContent);
        lastNameContent = view.findViewById(R.id.lastNameContent);

        newEmailAddress = view.findViewById(R.id.newEmailAddress);
        newEmailAddressContent = view.findViewById(R.id.newEmailAddressContent);

        passwordModifyPrompt = view.findViewById(R.id.passwordModifyPrompt);

        newPassword = view.findViewById(R.id.newPassword);
        newPasswordContent = view.findViewById(R.id.newPasswordContent);

        confirmNewPassword = view.findViewById(R.id.confirmNewPassword);
        newConfirmPasswordContent = view.findViewById(R.id.newConfirmPasswordContent);
    }

    private void init() {
        setBackButton(memberInfoBackButton);
        setMemberInfoEnable(false);
        setEmailAddressEnable(false);
        setPasswordEnable(false);
        setAgeSpinner();
        setModifyButtons();
    }

    private void setMemberInfoEnable(boolean enable) {
        setViewsVisible(enable, ageModifySpinner);
        setViewsFocusable(enable, firstNameContent, lastNameContent, ageModifySpinner);
        setModifyBorder(enable, firstNameContent, lastNameContent);
    }

    private void setModifyBorder(boolean enable, View... views) {
        for (View view : views) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackground(enable ?
                        getResources().getDrawable(R.drawable.modify_border) :
                        getResources().getDrawable(R.color.paleWhite));
            }
        }
    }

    private void setEmailAddressEnable(boolean enable) {
        setViewsVisible(enable, newEmailAddress, newEmailAddressContent);
        setViewsFocusable(enable, newEmailAddressContent);
    }

    private void setPasswordEnable(boolean enable) {
        setViewsVisible(enable, newPassword, newPasswordContent,
                confirmNewPassword, newConfirmPasswordContent);
        setViewsVisible(!enable, passwordModifyPrompt);
        setViewsFocusable(enable, newPasswordContent, newConfirmPasswordContent);
    }

    private void setViewsVisible(boolean visible, View... views) {
        for (View view : views) {
            view.setVisibility((visible) ? View.VISIBLE : View.INVISIBLE);
        }
    }

    private void setViewsFocusable(boolean enable, View... views) {
        for (View view : views) {
            view.setFocusable(enable);
            view.setFocusableInTouchMode(enable);
            view.setClickable(enable);
            view.setLongClickable(enable);
        }
    }

    private void setAgeSpinner() {
        ageModifySpinner.setAdapter(new ArrayAdapter<>(mainPageActivity,
                android.R.layout.simple_spinner_dropdown_item,
                configAgeRange(150)));
    }

    private List<Integer> configAgeRange(int ageRange) {
        List<Integer> ageList = new ArrayList<>(ageRange);
        for (int age = 0; age <= ageRange; age++) {
            ageList.add(age);
        }
        return ageList;
    }

    private void setModifyButtons() {
        memberInfoModifyButton.setOnClickListener(this::onModifyButtonClick);
        emailAddressModifyButton.setOnClickListener(this::onModifyButtonClick);
        passwordModifyButton.setOnClickListener(this::onModifyButtonClick);
    }

    private void onModifyButtonClick(View v) {
        switch (v.getId()) {
            case R.id.memberInfoModifyButton:
                if (isModifyState(memberInfoModifyButtonText)) {
                    setMemberInfoOnModitfyState(true);
                } else {
                    setMemberInfoOnModitfyState(false);
                }
                break;
            case R.id.emailAddressModifyButton:
                if (isModifyState(emailAddressModifyButtonText)) {
                    setEmailAddressOnModitfyState(true);
                } else {
                    setEmailAddressOnModitfyState(false);
                }
                break;
            case R.id.passwordModifyButton:
                if (isModifyState(passwordModifyButtonText)) {
                    setPasswordOnModitfyState(true);
                } else {
                    // Todo upload new userInfo while user has modified
                    setPasswordOnModitfyState(false);
                }
                break;
            default:
                throw new IllegalArgumentException("哈啦");
        }
    }

    private boolean isModifyState(TextView textView) {
        return !getString(R.string.modifyButtonChangeText).equals(textView.getText().toString());
    }

    private void setMemberInfoOnModitfyState(boolean stateChange) {
        setMemberInfoEnable(stateChange);
        setModifyButtonState(stateChange, memberInfoModifyButton,
                memberInfoModifyButtonText, memberInfoModifyButtonImage);
    }

    private void setEmailAddressOnModitfyState(boolean stateChange) {
        setEmailAddressEnable(stateChange);
        setModifyButtonState(stateChange, emailAddressModifyButton,
                emailAddressModifyButtonText, emailAddressModifyButtonImage);
    }

    private void setPasswordOnModitfyState(boolean stateChange) {
        setPasswordEnable(stateChange);
        setModifyButtonState(stateChange, passwordModifyButton,
                passwordModifyButtonText, passwordModifyButtonImage);
    }

    private void setModifyButtonState(boolean stateChange, View modifyButton, TextView buttonText, ImageView buttonImage) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            modifyButton.setBackground(stateChange ?
                    getResources().getDrawable(R.drawable.bg_modify_grey_round_button) :
                    getResources().getDrawable(R.drawable.bg_modify_green_round_button));
        }
        buttonText.setText(stateChange ?
                getString(R.string.modifyButtonSaveText) : getString(R.string.modifyButtonChangeText));
        setViewsVisible(!stateChange, buttonImage);
    }
}
