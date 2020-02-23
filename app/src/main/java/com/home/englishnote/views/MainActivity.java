package com.home.englishnote.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.home.englishnote.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSignInClick(View view) {
//        startActivity(new Intent(this,));
    }

    public void onSignUpClick(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }
}
