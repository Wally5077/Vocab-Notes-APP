package com.home.englishnote.views.activities;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.home.englishnote.R;
import com.home.englishnote.views.fragments.DictionaryFragment;

import java.util.HashMap;
import java.util.Map;


public class MainPageActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private Map<Integer, Fragment> fragmentMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        init();
    }

    private void init() {
        fragmentManager = getSupportFragmentManager();
        setFragment();
    }

    private void setFragment() {
        fragmentMap.put(R.layout.fragment_public_dictionary, new DictionaryFragment());
        switchFragment(R.layout.fragment_public_dictionary);
    }

    public void switchFragment(int fragmentId) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.mainPageContainer, fragmentMap.get(fragmentId))
                .commit();
    }

}
