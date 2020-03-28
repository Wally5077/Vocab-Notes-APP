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

/*
    設定button數量：1
    使用setButtonFirst();

    設定button數量：2
    使用setButtonFirst();
       setButtonSecond();
*/

public class CustomDialog extends Dialog{

    private TextView contentView;
    private Button buttonFirst;
    private Button buttonSecond;
    private Dialog dialog;

    public CustomDialog(Context context, String content) {
        super(context);
        dialog = setCustomDialog(context);
        this.contentView.setText(content);
        ColorDrawable background = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable insetDrawable = new InsetDrawable(background, 80);
        dialog.getWindow().setBackgroundDrawable(insetDrawable);
        dialog.show();
    }

    private Dialog setCustomDialog(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null);
        Dialog customDialog = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(true)
                .create();
        findView(view);
        return customDialog;
    }

    private void findView(View view){
        contentView = view.findViewById(R.id.dialog_tv_content);
        buttonFirst = view.findViewById(R.id.dialog_btn_left);
        buttonSecond = view.findViewById(R.id.dialog_btn_right);
        buttonSecond.setVisibility(View.GONE);
        buttonFirst.setVisibility(View.GONE);
    }

    public CustomDialog setButtonFirst(String buttonFirstMsg, View.OnClickListener clickListener){
        buttonFirst.setText(buttonFirstMsg);
        buttonFirst.setOnClickListener(clickListener);
        buttonFirst.setVisibility(View.VISIBLE);
        return this;
    }

    public CustomDialog setButtonSecond(String buttonSecondMsg, View.OnClickListener clickListener){
        buttonSecond.setText(buttonSecondMsg);
        buttonSecond.setOnClickListener(clickListener);
        buttonSecond.setVisibility(View.VISIBLE);
        return this;
    }

}
