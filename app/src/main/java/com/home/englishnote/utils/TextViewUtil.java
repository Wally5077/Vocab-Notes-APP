package com.home.englishnote.utils;

import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.home.englishnote.R;

import static com.home.englishnote.utils.ViewEnableUtil.setViewsVisible;

public class TextViewUtil {

    public static void showErrorMessage(String errMessage, TextInputLayout... textInputLayouts) {
        for (TextInputLayout textInputLayout : textInputLayouts) {
            TextView textView = textInputLayout.getEditText();
            boolean isTextContentEmpty = textView.getText().toString().trim().isEmpty();
            String emptyErrMessage = textInputLayout.getContext().getString(R.string.inputEmpty);
            if (isTextContentEmpty) {
                textInputLayout.setErrorEnabled(true);
                textInputLayout.setError(emptyErrMessage);
            } else {
                if (!emptyErrMessage.equals(errMessage)) {
                    textView.setText("");
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(errMessage);
                }
            }
        }
        clearAllTextInputLayoutError(textInputLayouts);
    }

    private static void clearAllTextInputLayoutError(TextInputLayout[] textInputLayouts) {
        ThreadExecutor threadExecutor = Global.threadExecutor();
        threadExecutor.execute(() -> {
            DelayUtil.delayExecuteThread(1500);
            threadExecutor.executeUiThread(() -> {
                for (TextInputLayout textInputLayout : textInputLayouts) {
                    textInputLayout.setError("");
                    textInputLayout.setErrorEnabled(false);
                }
            });
        });
    }

    public static void showAgeSpinnerError(TextView textView) {
        ThreadExecutor threadExecutor = Global.threadExecutor();
        setViewsVisible(true, textView);
        threadExecutor.execute(() -> {
            DelayUtil.delayExecuteThread(1500);
            threadExecutor.executeUiThread(() -> setViewsVisible(false, textView));
        });
    }
}
