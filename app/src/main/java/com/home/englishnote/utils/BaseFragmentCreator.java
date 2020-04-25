package com.home.englishnote.utils;

import com.home.englishnote.R;
import com.home.englishnote.exceptions.FragmnetClassNotCreateException;
import com.home.englishnote.views.fragments.*;
import com.home.englishnote.views.fragments.main.*;
import com.home.englishnote.views.fragments.secondary.dictionary.*;
import com.home.englishnote.views.fragments.secondary.profile.*;

public class BaseFragmentCreator {

    public BaseFragment createBaseFragmentInstance(int fragmentId) {
        BaseFragment baseFragment;
        switch (fragmentId) {
            case R.layout.fragment_public_dictionary_page:
                baseFragment = new PublicDictionaryPageFragment();
                break;
            case R.layout.fragment_member_profile_page:
                baseFragment = new MemberProfilePageFragment();
                break;
            case R.layout.fragment_own_dictionary_page:
                baseFragment = new OwnDictionaryPageFragment();
                break;
            case R.layout.fragment_public_dictionaries:
                baseFragment = new PublicDictionariesFragment();
                break;
            case R.layout.fragment_public_word_groups:
                baseFragment = new PublicWordGroupsFragment();
                break;
            case R.layout.fragment_word:
                baseFragment = new WordsFragment();
                break;
            case R.layout.fragment_own_dictionaries:
                baseFragment = new OwnDictionariesFragment();
                break;
            case R.layout.fragment_favorite_dictionaries:
                baseFragment = new FavoriteDictionariesFragment();
                break;
            case R.layout.fragment_create_own_dictionary:
                baseFragment = new CreateOwnDictionaryFragment();
                break;
            case R.layout.fragment_member_profile_modify:
                baseFragment = new MemberProfileModifyFragment();
                break;
            default:
                throw new FragmnetClassNotCreateException();
        }
        return baseFragment;
    }

}
