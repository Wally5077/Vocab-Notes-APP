package com.home.englishnote.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.home.englishnote.R;
import com.home.englishnote.models.entities.Member;
import com.home.englishnote.presenters.MainPresenter;
import com.home.englishnote.utils.VocabularyNoteKeyword;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        MainPresenter mainPresenter = new MainPresenter(this);
        SharedPreferences sharedPreferences =
                getSharedPreferences(VocabularyNoteKeyword.SP_NAME, MODE_PRIVATE);
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
        startActivity(new Intent(this, MainPageActivity.class)
                .putExtra("member",
                        new Member("firstName", "lastName",
                                25, "email", "password")));
    }
}
