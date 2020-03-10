package com.home.englishnote.views.activities;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.home.englishnote.R;
import com.home.englishnote.utils.VocabularyNoteKeyword;
import com.home.englishnote.views.fragments.dictionary.PublicDictionaryFragment;
import com.home.englishnote.views.fragments.PublicDictionariesFragment;
import com.home.englishnote.views.fragments.profile.UserProfileFragment;
import com.home.englishnote.views.fragments.profile.UserProfileModifyFragment;
import com.home.englishnote.views.fragments.dictionary.WordGroupsFragment;
import com.home.englishnote.views.fragments.dictionary.WordsFragment;

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
        // Dictionary
        fragmentMap.put(R.layout.fragment_public_dictionaries, new PublicDictionariesFragment());
        fragmentMap.put(R.layout.fragment_public_dictionary, new PublicDictionaryFragment());
        fragmentMap.put(R.layout.fragment_public_word_groups, new WordGroupsFragment());
        fragmentMap.put(R.layout.fragment_public_words, new WordsFragment());

        // Profile
        fragmentMap.put(R.layout.fragment_member_profile_page, new UserProfileFragment());
        fragmentMap.put(R.layout.fragment_member_profile_modify, new UserProfileModifyFragment());

        switchFragment(R.layout.fragment_public_dictionaries,
                VocabularyNoteKeyword.DICTIONARY_HOME_PAGE_CONTAINER);
//        switchFragment(R.layout.fragment_member_profile_modify);
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
