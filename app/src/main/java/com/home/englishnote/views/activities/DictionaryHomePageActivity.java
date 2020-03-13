package com.home.englishnote.views.activities;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.home.englishnote.R;
import com.home.englishnote.views.fragments.OwnDictionaryPageFragment;
import com.home.englishnote.views.fragments.PublicDictionaryPageFragment;
import com.home.englishnote.views.fragments.MemberProfilePageFragment;
import com.home.englishnote.views.fragments.dictionary.PublicDictionariesFragment;
import com.home.englishnote.views.fragments.profile.MemberProfileModifyFragment;
import com.home.englishnote.views.fragments.dictionary.PublicWordGroupsFragment;
import com.home.englishnote.views.fragments.profile.OwnDictionariesFragment;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class DictionaryHomePageActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private Map<Integer, Fragment> fragmentMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_home_page);
        init();
    }

    private void init() {
        fragmentManager = getSupportFragmentManager();
        setFragments();
    }

    private void setFragments() {
        // DictionaryHomePageContainer
        fragmentMap.put(R.layout.fragment_public_dictionary_page, new PublicDictionaryPageFragment());
        fragmentMap.put(R.layout.fragment_member_profile_page, new MemberProfilePageFragment());
        fragmentMap.put(R.layout.fragment_own_dictionary_page, new OwnDictionaryPageFragment());

        // Public Dictionary
        fragmentMap.put(R.layout.fragment_public_dictionaries, new PublicDictionariesFragment());
        fragmentMap.put(R.layout.fragment_public_word_groups, new PublicWordGroupsFragment());
//        fragmentMap.put(R.layout.fragment_public_words, new WordsFragment());

        // Profile
        fragmentMap.put(R.layout.fragment_member_profile_modify, new MemberProfileModifyFragment());

        // Own Dictionary
        fragmentMap.put(R.layout.fragment_own_dictionaries, new OwnDictionariesFragment());

        switchFragment(R.layout.fragment_public_dictionary_page, R.id.DictionaryHomePageContainer);
    }

    public void switchFragment(int fragmentId, int containerId,
                               Serializable... serializableArray) {
        Fragment fragment = fragmentMap.get(fragmentId);
        if (fragment != null) {
            if (serializableArray.length > 0) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("VocabNoteObjects", serializableArray[0]);
                fragment.setArguments(bundle);
            }
            fragmentManager
                    .beginTransaction()
                    .replace(containerId, fragment)
                    .addToBackStack(String.valueOf(fragmentId))
                    .commit();
        }
    }
}
