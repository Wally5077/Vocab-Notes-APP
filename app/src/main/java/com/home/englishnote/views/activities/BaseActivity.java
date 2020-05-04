package com.home.englishnote.views.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.home.englishnote.R;
import com.home.englishnote.utils.DelayUtil;
import com.home.englishnote.utils.Global;
import com.home.englishnote.utils.VocabularyNoteKeyword;

public class BaseActivity extends AppCompatActivity {

    protected SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = (sharedPreferences != null) ? sharedPreferences :
                getSharedPreferences(VocabularyNoteKeyword.SP_NAME, MODE_PRIVATE);
    }

}
