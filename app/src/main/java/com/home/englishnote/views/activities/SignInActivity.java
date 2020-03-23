package com.home.englishnote.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;
import com.home.englishnote.R;
import com.home.englishnote.models.entities.Member;
import com.home.englishnote.presenters.SignInPresenter;
import com.home.englishnote.presenters.SignInPresenter.SignInView;
import com.home.englishnote.utils.Global;

public class SignInActivity extends BaseActivity implements SignInView {

    private TextInputLayout signInEmailLayout;
    private TextInputLayout signInPasswordLayout;
    private SignInPresenter signInPresenter;
    private String passwordSalt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        findViews();
        init();
    }

    private void findViews() {
        signInEmailLayout = findViewById(R.id.signInEmailLayout);
        signInPasswordLayout = findViewById(R.id.signInPasswordLayout);
    }

    private void init() {
        signInPresenter = new SignInPresenter(
                this, Global.memberRepository(), Global.threadExecutor());
    }

    public void onSignUpLinkClick(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    public void onSignInButtonClick(View view) {
        // Todo Layout - > TIL
        String email = signInEmailLayout.getEditText().getText().toString().trim();
        String password = signInPasswordLayout.getEditText().getText().toString().trim();
        passwordSalt = getSalt();
        signInPresenter.signIn(email, password, passwordSalt);
    }

    @Override
    public void onSignInSuccessfully(Member member) {
        startActivity(new Intent(this, DictionaryHomePageActivity.class)
                .putExtra("member", member));
        saveSalt(passwordSalt);
        finish();
    }

    @Override
    public void onUserInputEmpty() {
        showErrorMessage(getString(R.string.inputEmpty),
                signInEmailLayout, signInPasswordLayout);
    }

    @Override
    public void onEmailFormatInvalid() {
        showErrorMessage(getString(R.string.emailFormatInvalid), signInEmailLayout);
    }

    @Override
    public void onPasswordFormatInvalid() {
        showErrorMessage(getString(R.string.passwordFormatInvalid), signInPasswordLayout);
    }

    @Override
    public void onInvalidCredentials() {
        showErrorMessage(getString(R.string.invalidCredentials), signInEmailLayout, signInPasswordLayout);
    }

}
