package com.home.englishnote.views.fragments.profile;

import android.graphics.drawable.Drawable;
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

import com.bumptech.glide.Glide;
import com.home.englishnote.R;
import com.home.englishnote.views.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import static com.home.englishnote.utils.ViewEnableUtil.setViewsFocusable;
import static com.home.englishnote.utils.ViewEnableUtil.setViewsVisible;

public class MemberProfileModifyFragment extends BaseFragment {

    private Spinner ageModifySpinner;
    private TextView profileModifyButton, emailAddressModifyButton, passwordModifyButton;
    private TextView newEmailAddress, passwordModifyPrompt, newPassword, confirmNewPassword;
    private TextView ageContent;
    private EditText firstNameContent, lastNameContent;
    private EditText newEmailAddressContent, newPasswordContent, newConfirmPasswordContent;
    private View memberInfoModifyVocabSearch;
    private View memberInfoModifyDictionarySearch;
    private ImageView memberInfoModifyMemberPhoto;
    private TextView memberInfoModifyMemberName;
    private TextView userModifyPageCurrentEmailAddressContent;

    // Todo data setting hasn't finished

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater
                .inflate(R.layout.fragment_member_profile_modify, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        init();
    }

    private void findViews(View view) {
        // Toolbar
        memberInfoModifyVocabSearch = view.findViewById(R.id.memberInfoModifyVocabSearch);
        memberInfoModifyDictionarySearch = view.findViewById(R.id.memberInfoModifyDictionarySearch);
        memberInfoModifyMemberPhoto = view.findViewById(R.id.memberInfoModifyMemberPhoto);
        memberInfoModifyMemberName = view.findViewById(R.id.memberInfoModifyMemberName);

        // MyProfile
        firstNameContent = view.findViewById(R.id.userModifyPageFirstNameContent);
        lastNameContent = view.findViewById(R.id.userModifyPageLastNameContent);
        ageContent = view.findViewById(R.id.userModifyPageAgeContent);
        ageModifySpinner = view.findViewById(R.id.userModifyPageAgeSpinner);
        profileModifyButton = view.findViewById(R.id.profileModifyButton);

        // EmailAddress
        userModifyPageCurrentEmailAddressContent = view.findViewById(R.id.userModifyPageCurrentEmailAddressContent);
        newEmailAddress = view.findViewById(R.id.userModifyPageNewEmailAddress);
        newEmailAddressContent = view.findViewById(R.id.userModifyPageNewEmailAddressContent);
        emailAddressModifyButton = view.findViewById(R.id.emailAddressModifyButton);

        // Password
        passwordModifyPrompt = view.findViewById(R.id.userModifyPagePasswordModifyPrompt);
        newPassword = view.findViewById(R.id.userModifyPageNewPassword);
        newPasswordContent = view.findViewById(R.id.userModifyPageNewPasswordContent);
        confirmNewPassword = view.findViewById(R.id.userModifyPageConfirmNewPassword);
        newConfirmPasswordContent = view.findViewById(R.id.userModifyPageNewConfirmPasswordContent);
        passwordModifyButton = view.findViewById(R.id.passwordModifyButton);
    }

    private void init() {
        setMember();
        setProfileEnable(false);
        setEmailAddressEnable(false);
        setPasswordEnable(false);
        setAgeSpinner();
        setModifyButtons();
    }

    private void setMember() {
        Glide.with(this)
                .asBitmap()
                .load(member.getImageURL())
                .fitCenter()
                .error(R.drawable.small_user_pic)
                .into(memberInfoModifyMemberPhoto);
        String memberName = member.getFirstName();
        memberInfoModifyMemberName.setText((memberName.isEmpty()) ? "memberName" : memberName);
    }

    private void setProfileEnable(boolean enable) {
        setViewsVisible(enable, ageModifySpinner);
        setViewsFocusable(enable, firstNameContent, lastNameContent, ageModifySpinner);
        setViewBackground(enable, firstNameContent, lastNameContent);
    }

    private void setEmailAddressEnable(boolean enable) {
        setViewsVisible(enable, newEmailAddress, newEmailAddressContent);
        setViewsFocusable(enable, newEmailAddressContent);
        setViewBackground(enable, newEmailAddressContent);
    }

    private void setPasswordEnable(boolean enable) {
        setViewsVisible(enable, newPassword, newPasswordContent,
                confirmNewPassword, newConfirmPasswordContent);
        setViewsVisible(!enable, passwordModifyPrompt);
        setViewsFocusable(enable, newPasswordContent, newConfirmPasswordContent);
        setViewBackground(enable, newPasswordContent, newConfirmPasswordContent);
    }

    private void setViewBackground(boolean enable, View... views) {
        for (View view : views) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackground(enable ?
                        getResources().getDrawable(R.drawable.modify_border) :
                        getResources().getDrawable(R.color.mainWhite));
            }
        }
    }

    private void setAgeSpinner() {
        ageModifySpinner.setAdapter(new ArrayAdapter<>(dictionaryHomePageActivity,
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

    private boolean isProfileModifyButtonClick = false;
    private boolean isEmailAddressModifyButtonClick = false;
    private boolean isPasswordModifyButtonClick = false;

    private void setModifyButtons() {
        setMemberInfoEditable(isProfileModifyButtonClick, profileModifyButton);
        setMemberInfoEditable(isEmailAddressModifyButtonClick, emailAddressModifyButton);
        setMemberInfoEditable(isPasswordModifyButtonClick, passwordModifyButton);

        profileModifyButton.setOnClickListener(v -> {
            isProfileModifyButtonClick = !isProfileModifyButtonClick;
            setMemberInfoEditable(isProfileModifyButtonClick, profileModifyButton);
            setProfileEnable(isProfileModifyButtonClick);
        });
        emailAddressModifyButton.setOnClickListener(v -> {
            isEmailAddressModifyButtonClick = !isEmailAddressModifyButtonClick;
            setMemberInfoEditable(isEmailAddressModifyButtonClick, emailAddressModifyButton);
            setEmailAddressEnable(isEmailAddressModifyButtonClick);
        });
        passwordModifyButton.setOnClickListener(v -> {
            isPasswordModifyButtonClick = !isPasswordModifyButtonClick;
            setMemberInfoEditable(isPasswordModifyButtonClick, passwordModifyButton);
            setPasswordEnable(isPasswordModifyButtonClick);
        });
    }

    private void setMemberInfoEditable(boolean editable, TextView modifyButton) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            modifyButton.setBackground(editable ?
                    getResources().getDrawable(R.drawable.bg_modify_grey_round_button) :
                    getResources().getDrawable(R.drawable.bg_modify_green_round_button));
        }
        Drawable img = (editable) ? null : getResources().getDrawable(R.drawable.modify_pen);
        modifyButton.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        modifyButton.setText(editable ?
                getString(R.string.modifyButtonSaveText) : getString(R.string.modifyButtonChangeText));
    }
}
