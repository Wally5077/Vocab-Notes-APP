package com.home.englishnote.views.activities;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.home.englishnote.R;
import com.home.englishnote.presenters.MainPresenter;
import com.home.englishnote.utils.Global;

import static com.home.englishnote.utils.VocabularyNoteKeyword.REQUEST_CODE_CAMERA;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
            } else {
                init();
            }
        }
    }

    private void init() {
        MainPresenter mainPresenter = new MainPresenter(this);
//        Todo DailyVocabNotification
//        if (sharedPreferences
//                .getBoolean(VocabularyNoteKeyword.NOTIFICATION_SWITCH, true)) {
//            mainPresenter.setDailyVocabularyNotification();
//            sharedPreferences.edit()
//                    .putBoolean(VocabularyNoteKeyword.NOTIFICATION_SWITCH, false).apply();
//        }
    }

    public void onSignInButtonClick(View view) {
        startActivity(new Intent(this, SignInActivity.class));
    }

    public void onSignUpButtonClick(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    public void onGuestSignInClick(View view) {
        // Todo Needs a Guest Object which is a Member
        startActivity(new Intent(this, HomePageActivity.class)
                .putExtra("user", Global.user()));
    }
}
