package com.home.englishnote.views.activities;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.home.englishnote.R;
import com.home.englishnote.views.fragments.PublicDictionaryFragment;
import com.home.englishnote.views.fragments.UserProfileModifyFragment;

import java.io.Serializable;
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
        fragmentMap.put(R.layout.fragment_public_dictionary, new PublicDictionaryFragment());
        fragmentMap.put(R.layout.fragment_user_profile_modify, new UserProfileModifyFragment());
        switchFragment(R.layout.fragment_public_dictionary);
//        switchFragment(R.layout.fragment_user_profile_modify);
    }

    public void switchFragment(int fragmentId, Serializable... serializableArray) {
        Fragment fragment = fragmentMap.get(fragmentId);
        if (fragment != null) {
            if (serializableArray.length > 0) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("VocabNoteObjects", serializableArray);
                fragment.setArguments(bundle);
            }
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.mainPageContainer, fragment)
                    .addToBackStack(String.valueOf(fragmentId))
                    .commit();
        }
    }

}
