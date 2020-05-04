package com.home.englishnote.views.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.home.englishnote.R;
import com.home.englishnote.models.entities.Member;
import com.home.englishnote.models.entities.Token;
import com.home.englishnote.presenters.SignUpPresenter;
import com.home.englishnote.utils.Global;
import com.home.englishnote.utils.VocabularyNoteKeyword;


import java.util.ArrayList;
import java.util.List;

import static com.home.englishnote.presenters.SignUpPresenter.*;
import static com.home.englishnote.utils.UserInfoHandleUtil.*;

public class SignUpActivity extends BaseActivity implements SignUpView {

    private TextInputLayout signUpFirstNameLayout;
    private TextInputLayout signUpLastNameLayout;
    private TextInputLayout signUpEmailLayout;
    private TextInputLayout signUpPasswordLayout;
    private TextInputLayout signUpPasswordConfirmationLayout;
    private Spinner signUpAgeSpinner;
    private SignUpPresenter signUpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        findViews();
    }

    private void findViews() {
        signUpFirstNameLayout = findViewById(R.id.signUpFirstNameLayout);
        signUpLastNameLayout = findViewById(R.id.signUpLastNameLayout);
        signUpEmailLayout = findViewById(R.id.signUpEmailLayout);
        signUpPasswordLayout = findViewById(R.id.signUpPasswordLayout);
        signUpPasswordConfirmationLayout = findViewById(R.id.signUpPasswordConfirmationLayout);
        signUpAgeSpinner = findViewById(R.id.signUpAge);
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        signUpPresenter = new SignUpPresenter(
                this, Global.memberRepository(), Global.threadExecutor());
        setTextInputLayout(signUpFirstNameLayout, signUpLastNameLayout,
                signUpEmailLayout, signUpPasswordLayout, signUpPasswordConfirmationLayout);
        setUpAgeSpinner();

        signUpFirstNameLayout.getEditText().setText("wally");
        signUpLastNameLayout.getEditText().setText("chen");
        signUpEmailLayout.getEditText().setText("w@gmail.com");
        signUpPasswordLayout.getEditText().setText("w0000000");
        signUpPasswordConfirmationLayout.getEditText().setText("w0000000");
    }

    private void setUpAgeSpinner() {
        signUpAgeSpinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, configAgeRange(150)));
        signUpAgeSpinner.setSelection(0, true);
    }

    private List<String> configAgeRange(int ageRange) {
        List<String> ageList = new ArrayList<>(ageRange + 1);
        ageList.add(VocabularyNoteKeyword.DEFAULT_SPINNER_WORD);
        for (int age = 1; age <= ageRange; age++) {
            ageList.add(String.valueOf(age));
        }
        return ageList;
    }

    private void setTextInputLayout(TextInputLayout... textInputLayouts) {
        for (TextInputLayout textInputLayout : textInputLayouts) {
            TextView textView = textInputLayout.getEditText();
            textView.setOnTouchListener((v, event) -> {
                ((TextView) v).setText("");
                return true;
            });
            clearTextViewContent(textView);
        }
    }

    public void onSignInLinkClick(View view) {
        startActivity(new Intent(this, SignInActivity.class));
    }

    public void onSubmitButtonClick(View view) {
        String firstName = signUpFirstNameLayout.getEditText().getText().toString().trim();
        String lastName = signUpLastNameLayout.getEditText().getText().toString().trim();
        String email = signUpEmailLayout.getEditText().getText().toString().trim().trim();
        String password = signUpPasswordLayout.getEditText().getText().toString().trim();
        String passwordConfirmation =
                signUpPasswordConfirmationLayout.getEditText().getText().toString().trim();
        String age = signUpAgeSpinner.getSelectedItem().toString();
        signUpPresenter.signUp(firstName, lastName, age,
                email, password, passwordConfirmation);
    }

    @Override
    public void onSignUpSuccessfully(Member member, Token token) {
        startActivity(new Intent(this, HomePageActivity.class)
                .putExtra("user", member));
        finish();
    }

    @Override
    public void onUserInputEmpty() {
        showErrorMessage(
                getString(R.string.inputEmpty), signUpFirstNameLayout, signUpLastNameLayout,
                signUpEmailLayout, signUpPasswordLayout, signUpPasswordConfirmationLayout);
    }

    @Override
    public void onEmailFormatInvalid() {
        showErrorMessage(getString(R.string.emailFormatInvalid), signUpEmailLayout);
    }

    @Override
    public void onPasswordFormatInvalid() {
        showErrorMessage(
                getString(R.string.passwordFormatInvalid),
                signUpPasswordLayout, signUpPasswordConfirmationLayout);
    }

    @Override
    public void onPasswordConfirmationNotMatch() {
        showErrorMessage(
                getString(R.string.passwordConfirmationNotMatch),
                signUpPasswordLayout, signUpPasswordConfirmationLayout);
    }

    @Override
    public void onEmailDuplicated() {
        showErrorMessage(getString(R.string.signUpEmailExist), signUpEmailLayout);
    }

}
