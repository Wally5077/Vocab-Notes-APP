package com.home.englishnote.views.fragments.profile;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.home.englishnote.R;
import com.home.englishnote.views.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class MemberProfileModifyFragment extends BaseFragment {

    private Spinner ageModifySpinner;
    private Button memberInfoModifyButton, emailAddressModifyButton, passwordModifyButton;
    private TextView newEmailAddress, passwordModifyPrompt, newPassword, confirmNewPassword;
    private TextView ageContent;
    private EditText firstNameContent, lastNameContent;
    private EditText newEmailAddressContent, newPasswordContent, newConfirmPasswordContent;
    private ImageView memberInfoModifyDrawerButton;
    private DrawerLayout memberInfoModifyDrawer;

    // Todo data setting hasn't finished

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_member_profile_modify, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        init();
    }

    private void findViews(View view) {

        memberInfoModifyDrawer = view.findViewById(R.id.memberInfoModifyDrawer);
        memberInfoModifyDrawerButton = view.findViewById(R.id.memberInfoModifyDrawerButton);

        ageModifySpinner = view.findViewById(R.id.userModifyPageAgeSpinner);
        ageContent = view.findViewById(R.id.userModifyPageAgeContent);

        memberInfoModifyButton = view.findViewById(R.id.memberInfoModifyButton);

        emailAddressModifyButton = view.findViewById(R.id.emailAddressModifyButton);

        passwordModifyButton = view.findViewById(R.id.passwordModifyButton);

        firstNameContent = view.findViewById(R.id.userModifyPageFirstNameContent);
        lastNameContent = view.findViewById(R.id.userModifyPageLastNameContent);

        newEmailAddress = view.findViewById(R.id.userModifyPageNewEmailAddress);
        newEmailAddressContent = view.findViewById(R.id.userModifyPageNewEmailAddressContent);

        passwordModifyPrompt = view.findViewById(R.id.userModifyPagePasswordModifyPrompt);

        newPassword = view.findViewById(R.id.userModifyPageNewPassword);
        newPasswordContent = view.findViewById(R.id.userModifyPageNewPasswordContent);

        confirmNewPassword = view.findViewById(R.id.userModifyPageConfirmNewPassword);
        newConfirmPasswordContent = view.findViewById(R.id.userModifyPageNewConfirmPasswordContent);
    }

    private void init() {
        setToolBar();
        setMemberInfoEnable(false);
        setEmailAddressEnable(false);
        setPasswordEnable(false);
        setAgeSpinner();
//        setModifyButtons();
    }

    private void setToolBar() {
        memberInfoModifyDrawerButton.setOnClickListener(
                v -> memberInfoModifyDrawer.openDrawer(GravityCompat.START));
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
                        getResources().getDrawable(R.color.mainWhite));
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

//    private void setModifyButtons() {
//        memberInfoModifyButton.setOnClickListener(this::onModifyButtonClick);
//        emailAddressModifyButton.setOnClickListener(this::onModifyButtonClick);
//        passwordModifyButton.setOnClickListener(this::onModifyButtonClick);
//    }
//
//    private void onModifyButtonClick(View v) {
//        switch (v.getId()) {
//            case R.id.memberInfoModifyButton:
//                if (isModifyState(memberInfoModifyButtonText)) {
//                    setMemberInfoOnModifyState(true);
//                } else {
//                    setMemberInfoOnModifyState(false);
//                }
//                break;
//            case R.id.emailAddressModifyButton:
//                if (isModifyState(emailAddressModifyButtonText)) {
//                    setEmailAddressOnModifyState(true);
//                } else {
//                    setEmailAddressOnModifyState(false);
//                }
//                break;
//            case R.id.passwordModifyButton:
//                if (isModifyState(passwordModifyButtonText)) {
//                    setPasswordOnModifyState(true);
//                } else {
//                    // Todo upload new userInfo while user has modified
//                    setPasswordOnModifyState(false);
//                }
//                break;
//            default:
//                throw new IllegalArgumentException("哈啦");
//        }
//    }
//
//    private boolean isModifyState(TextView textView) {
//        return !getString(R.string.modifyButtonChangeText).equals(textView.getText().toString());
//    }
//
//    private void setMemberInfoOnModifyState(boolean stateChange) {
//        setMemberInfoEnable(stateChange);
//        setModifyButtonState(stateChange, memberInfoModifyButton,
//                memberInfoModifyButtonText, memberInfoModifyButtonImage);
//    }
//
//    private void setEmailAddressOnModifyState(boolean stateChange) {
//        setEmailAddressEnable(stateChange);
//        setModifyButtonState(stateChange, emailAddressModifyButton,
//                emailAddressModifyButtonText, emailAddressModifyButtonImage);
//    }
//
//    private void setPasswordOnModifyState(boolean stateChange) {
//        setPasswordEnable(stateChange);
//        setModifyButtonState(stateChange, passwordModifyButton,
//                passwordModifyButtonText, passwordModifyButtonImage);
//    }

    private void setModifyButtonState(boolean stateChange, View modifyButton,
                                      TextView buttonText, ImageView buttonImage) {
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
