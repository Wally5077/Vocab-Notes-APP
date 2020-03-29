package com.home.englishnote.views.fragments.secondary.profile;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.home.englishnote.presenters.MemberProfileModifyPresenter;
import com.home.englishnote.utils.Global;
import com.home.englishnote.utils.VocabularyNoteKeyword;
import com.home.englishnote.views.fragments.BaseFragment;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.home.englishnote.presenters.MemberProfileModifyPresenter.*;
import static com.home.englishnote.utils.ViewEnableUtil.setViewsFocusable;
import static com.home.englishnote.utils.ViewEnableUtil.setViewsVisible;

public class MemberProfileModifyFragment extends BaseFragment implements MemberProfileModifyView {

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
    private MemberProfileModifyPresenter memberProfileModifyPresenter;
    private ImageView memberModifyPhoto;

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
        memberModifyPhoto = view.findViewById(R.id.memberModifyPhoto);
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
        memberProfileModifyPresenter = new MemberProfileModifyPresenter(
                this, Global.memberRepository(), Global.threadExecutor());
        setMember();
        setProfileEnable(false);
        setEmailAddressEnable(false);
        setPasswordEnable(false);
        setAgeSpinner();
        setMemberPhoto();
        setModifyButtons();
    }

    private void setMember() {
        Glide.with(this)
                .asBitmap()
                .load(user.getImageURL())
                .circleCrop()
                .error(R.drawable.small_user_pic)
                .into(memberInfoModifyMemberPhoto);
        String memberName = user.getFirstName();
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

    private void setMemberPhoto() {
        memberModifyPhoto.setOnClickListener(v -> {
            if (isProfileModifyButtonClick) {
                uploadPhoto();
            }
        });
    }

    private void uploadPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //這裡的intent是跳轉到指定的Android內建功能，讓使用者在相片總管選照片。
        //SELECT_CAR_PHOTO_REQUEST_CODE之常數，則是為了執行onActivityResult時判斷是誰發出。
        startActivityForResult(intent, VocabularyNoteKeyword.SELECT_CAR_PHOTO_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //執行此函式，會將SELECT_CAR_PHOTO_REQUEST_CODE傳入requestCode
        //並自動把取得的資料內容傳入data、執行結果傳入resultCode
        if (resultCode == RESULT_OK) {
            if (requestCode == VocabularyNoteKeyword.SELECT_CAR_PHOTO_REQUEST_CODE) {
                //把data中相片的uri取出。
                if (data != null) {
                    Uri uri = data.getData();
                    if (uri != null) {
                        try {
                            ContentResolver contentResolver = getActivity().getContentResolver();
                            if (contentResolver != null) {
                                //開啟檔案路徑, 讀取檔案, 轉成inputStream(輸入串流)型態
//                            Glide.get(getContext()).clearMemory();
//                            Thread thread = new Thread(() -> Glide.get(getContext()).clearDiskCache());
//                            thread.start();
//                            try {
//                                thread.join();
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
                                photoBitmap = BitmapFactory
                                        .decodeStream(contentResolver.openInputStream(uri));
                                if (!isProfileModifyButtonClick) {
                                    memberProfileModifyPresenter
                                            .uploadPhoto(token, user.getId(), photoBitmap);
                                }
                            }
                        } catch (FileNotFoundException err) {
                            Log.e("FileNotFoundException", err.getMessage(), err);
                        }
                    }
                }
            }
        }
    }

    private boolean isProfileModifyButtonClick = false;
    private boolean isEmailAddressModifyButtonClick = false;
    private boolean isPasswordModifyButtonClick = false;
    private Bitmap photoBitmap;

    private void setModifyButtons() {
        setMemberInfoEditable(isProfileModifyButtonClick, profileModifyButton);
        setMemberInfoEditable(isEmailAddressModifyButtonClick, emailAddressModifyButton);
        setMemberInfoEditable(isPasswordModifyButtonClick, passwordModifyButton);

        profileModifyButton.setOnClickListener(v -> {
            isProfileModifyButtonClick = !isProfileModifyButtonClick;
            setMemberInfoEditable(isProfileModifyButtonClick, profileModifyButton);
            setProfileEnable(isProfileModifyButtonClick);
            if (!isProfileModifyButtonClick) {
                if (photoBitmap != null) {
                    memberProfileModifyPresenter
                            .uploadPhoto(token, user.getId(), photoBitmap);
                }
            }
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

    @Override
    public void onUploadMemberPhotoSuccessfully(Bitmap photoBitmap) {
        Glide.with(this)
                .asBitmap()
                .load(photoBitmap)
                .error(R.drawable.small_user_pic)
                .circleCrop()
                .into(memberInfoModifyMemberPhoto);
        Glide.with(this)
                .asBitmap()
                .load(photoBitmap)
                .error(R.drawable.profile_photo)
                .circleCrop()
                .into(memberModifyPhoto);
    }
}
