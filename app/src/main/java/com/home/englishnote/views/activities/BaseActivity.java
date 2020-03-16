package com.home.englishnote.views.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.home.englishnote.R;
import com.home.englishnote.utils.Global;
import com.home.englishnote.utils.HashUtil;
import com.home.englishnote.utils.VocabularyNoteKeyword;

public class BaseActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = (sharedPreferences != null) ? sharedPreferences :
                getSharedPreferences(VocabularyNoteKeyword.SP_NAME, MODE_PRIVATE);
    }

    // Todo refactor to utils class

    protected void showErrorMessage(String errMessage, TextInputLayout... textInputLayouts) {
        for (TextInputLayout textInputLayout : textInputLayouts) {
            textInputLayout.setErrorEnabled(true);
            TextView textView = textInputLayout.getEditText();
            boolean isTextContentEmpty = textView.getText().toString().isEmpty();
            if (isTextContentEmpty) {
                textInputLayout.setError(getString(R.string.inputEmpty));
            } else {
                clearTextViewContent(textView);
                textInputLayout.setError(errMessage);
            }
        }
        clearAllTextInputLayoutError(textInputLayouts);
    }

    protected void clearTextViewContent(TextView textView) {
        textView.setText("");
    }

    private void clearAllTextInputLayoutError(TextInputLayout[] textInputLayouts) {
        Global.threadExecutor().delayExecuteUiThreadDelay(() -> {
            for (TextInputLayout textInputLayout : textInputLayouts) {
                textInputLayout.setError("");
                textInputLayout.setErrorEnabled(false);
            }
        });
    }

    protected String getSalt() {
        return sharedPreferences.
                getString(VocabularyNoteKeyword.PASSWORD_SALT, new String(HashUtil.configSalt()));
    }

    protected void saveSalt(String passwordSalt) {
        sharedPreferences.edit()
                .putString(VocabularyNoteKeyword.PASSWORD_SALT, passwordSalt).apply();
    }
}
