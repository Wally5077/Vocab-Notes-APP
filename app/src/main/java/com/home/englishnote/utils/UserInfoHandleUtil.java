package com.home.englishnote.utils;

import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class UserInfoHandleUtil {

    public static void showErrorMessage(String errMessage, TextInputLayout... textInputLayouts) {
        for (TextInputLayout textInputLayout : textInputLayouts) {
            textInputLayout.setErrorEnabled(true);
            TextView textView = textInputLayout.getEditText();
            boolean isTextContentEmpty = textView.getText().toString().isEmpty();
            if (isTextContentEmpty) {
                textInputLayout.setError("輸入欄不可為空");
            } else {
                clearTextViewContent(textView);
                textInputLayout.setError(errMessage);
            }
        }
        clearAllTextInputLayoutError(textInputLayouts);
    }

    public static void clearTextViewContent(TextView textView) {
        textView.setText("");
    }

    private static void clearAllTextInputLayoutError(TextInputLayout[] textInputLayouts) {
        Global.threadExecutor().executeUiThread(() -> {
            DelayUtil.delayExecuteThread(1500);
            for (TextInputLayout textInputLayout : textInputLayouts) {
                textInputLayout.setError("");
                textInputLayout.setErrorEnabled(false);
            }
        });
    }
}
