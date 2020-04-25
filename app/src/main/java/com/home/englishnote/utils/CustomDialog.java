package com.home.englishnote.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.home.englishnote.R;

import static com.home.englishnote.utils.ViewEnableUtil.*;

/*
    設定button數量：1
    使用setButtonFirst();

    設定button數量：2
    使用setButtonFirst();
       setDialogButtonRight();
*/

public class CustomDialog extends AlertDialog {

    private TextView dialogMessage;
    private Button dialogButtonLeft;
    private Button dialogButtonMiddle;
    private Button dialogButtonRight;

    public CustomDialog(Context context) {
        super(context);
        setCustomDialog(context);
    }

    private Dialog dialog;

    private void setCustomDialog(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null);
        dialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();
        findViews(view);
        init();
    }

    private void findViews(View view) {
        dialogMessage = view.findViewById(R.id.customDialogMessage);
        dialogButtonLeft = view.findViewById(R.id.customDialogButtonLeft);
        dialogButtonMiddle = view.findViewById(R.id.customDialogButtonMiddle);
        dialogButtonRight = view.findViewById(R.id.customDialogButtonRight);
    }

    private void init() {
        setViewsVisible(false, dialogButtonLeft, dialogButtonMiddle, dialogButtonRight);
        setViewsFocusable(false, dialogButtonLeft, dialogButtonMiddle, dialogButtonRight);
    }

    public CustomDialog setMessage(String message) {
        dialogMessage.setText(message);
        return this;
    }

    private View.OnTouchListener onDialogButtonLeftTouchListener;

    public CustomDialog setDialogButtonLeft(CharSequence dialogButtonLeftText,
                                            View.OnTouchListener onTouchListener) {
        onDialogButtonLeftTouchListener = onTouchListener;
        dialogButtonLeft.setText(dialogButtonLeftText);
        dialogButtonLeft.setOnTouchListener((v, event) -> {
            if (onDialogButtonLeftTouchListener != null) {
                onDialogButtonLeftTouchListener.onTouch(v, event);
            }
            dialog.dismiss();
            return true;
        });
        setViewsVisible(true, dialogButtonLeft);
        setViewsFocusable(true, dialogButtonLeft);
        return this;
    }

    private View.OnTouchListener onDialogButtonRightTouchListener;

    public CustomDialog setDialogButtonRight(CharSequence dialogButtonRightText,
                                             View.OnTouchListener onTouchListener) {
        onDialogButtonRightTouchListener = onTouchListener;
        dialogButtonRight.setText(dialogButtonRightText);
        dialogButtonRight.setOnTouchListener((v, event) -> {
            if (onDialogButtonRightTouchListener != null) {
                onDialogButtonRightTouchListener.onTouch(v, event);
            }
            dialog.dismiss();
            return true;
        });
        setViewsVisible(true, dialogButtonRight);
        setViewsFocusable(true, dialogButtonRight);
        return this;
    }

    private View.OnTouchListener onDialogButtonMiddleTouchListener;

    public CustomDialog setDialogButtonMiddle(CharSequence dialogButtonMiddleText,
                                              View.OnTouchListener onTouchListener) {
        onDialogButtonMiddleTouchListener = onTouchListener;
        dialogButtonMiddle.setText(dialogButtonMiddleText);
        dialogButtonMiddle.setOnTouchListener((v, event) -> {
            if (onDialogButtonMiddleTouchListener != null) {
                onDialogButtonMiddleTouchListener.onTouch(v, event);
            }
            dialog.dismiss();
            return true;
        });
        setViewsVisible(true, dialogButtonMiddle);
        setViewsFocusable(true, dialogButtonMiddle);
        return this;
    }

    @Override
    public void show() {
        ColorDrawable background = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable insetDrawable = new InsetDrawable(background, 150);
        dialog.getWindow().setBackgroundDrawable(insetDrawable);
        dialog.show();
    }
}
