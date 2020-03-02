package com.home.englishnote.views.activities;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.home.englishnote.R;
import com.home.englishnote.utils.Global;

public class BaseActivity extends AppCompatActivity {

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
}
