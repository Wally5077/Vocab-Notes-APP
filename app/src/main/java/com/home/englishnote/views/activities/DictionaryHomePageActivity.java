package com.home.englishnote.views.activities;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.home.englishnote.R;
import com.home.englishnote.views.fragments.OwnDictionaryPageFragment;
import com.home.englishnote.views.fragments.PublicDictionaryPageFragment;
import com.home.englishnote.views.fragments.MemberProfilePageFragment;
import com.home.englishnote.views.fragments.dictionary.PublicDictionariesFragment;
import com.home.englishnote.views.fragments.profile.CreateOwnDictionaryFragment;
import com.home.englishnote.views.fragments.profile.MemberProfileModifyFragment;
import com.home.englishnote.views.fragments.dictionary.PublicWordGroupsFragment;
import com.home.englishnote.views.fragments.profile.OwnDictionariesFragment;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


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
        fragmentMap.put(R.layout.fragment_own_dictionaries, new OwnDictionariesFragment());
        fragmentMap.put(R.layout.fragment_create_own_dictionary, new CreateOwnDictionaryFragment());
        fragmentMap.put(R.layout.fragment_member_profile_modify, new MemberProfileModifyFragment());

        // Own Dictionary

        switchFragment(R.layout.fragment_public_dictionary_page, R.id.DictionaryHomePageContainer);
    }

    private Stack<Integer> containerStack = new Stack<>();
    private Stack<Integer> fragmentStack = new Stack<>();

    public void switchFragment(int fragmentId, int containerId,
                               Serializable... serializableArray) {
        Fragment nextFragment = fragmentMap.get(fragmentId);
        if (nextFragment != null) {
            if (serializableArray.length > 0) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("VocabNoteObjects", serializableArray[0]);
                nextFragment.setArguments(bundle);
            }
            Fragment hideFragment;
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (nextFragment.isAdded()) {
                hideFragment = fragmentMap.get(fragmentStack.peek());
            } else {
                hideFragment = nextFragment;
                fragmentTransaction
                        .add(containerId, nextFragment);
                fragmentStack.add(fragmentId);
                containerStack.add(containerId);
            }
            fragmentTransaction.hide(hideFragment).show(nextFragment).commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (fragmentStack.size() > 2) {
            int fragmentId = fragmentStack.pop();
            backLastFragment(fragmentId);
            fragmentId = fragmentStack.peek();
            if (fragmentId == R.layout.fragment_public_dictionary_page
                    || fragmentId == R.layout.fragment_member_profile_page
                    || fragmentId == R.layout.fragment_own_dictionary_page) {
                backLastFragment(fragmentStack.pop());
            }
        }
    }

    private void backLastFragment(int fragmentId) {
        Fragment fragment = fragmentMap.get(fragmentId);
        containerStack.pop();
        fragmentManager.beginTransaction().remove(fragment).commit();
        switchFragment(fragmentStack.peek(), containerStack.peek());
    }
}
