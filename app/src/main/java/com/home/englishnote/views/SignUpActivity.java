package com.home.englishnote.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.home.englishnote.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SignUpActivity extends AppCompatActivity {

    private Spinner ageSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        findViews();
        init();
    }

    private void findViews() {
        ageSpinner = findViewById(R.id.signUpAge);
    }

    private void init() {
        ageSpinner.setAdapter(new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_dropdown_item, configAgeRange(150)));
    }

    private List<String> configAgeRange(int ageRange) {
        List<String> ageList = new ArrayList<>(ageRange);
        ageList.add("Age");
        for (int age = 1; age <= ageRange; age++) {
            ageList.add(String.valueOf(age));
        }
        return ageList;
    }

    public void onSignInClick(View view) {
    }
}
