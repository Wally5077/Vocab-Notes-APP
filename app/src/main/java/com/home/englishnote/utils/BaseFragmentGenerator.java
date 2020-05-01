package com.home.englishnote.utils;

import android.util.SparseArray;

import com.home.englishnote.R;
import com.home.englishnote.exceptions.FragmentClassNotCreateException;
import com.home.englishnote.views.fragments.*;
import com.home.englishnote.views.fragments.main.*;
import com.home.englishnote.views.fragments.secondary.dictionary.*;
import com.home.englishnote.views.fragments.secondary.profile.*;

public abstract class BaseFragmentGenerator {

    private static SparseArray<Supplier<BaseFragment>> fragmentArray;

    static {
        fragmentArray = new SparseArray<>();
        fragmentArray.put(R.layout.fragment_public_dictionary_page, PublicDictionaryPageFragment::new);
        fragmentArray.put(R.layout.fragment_member_profile_page, MemberProfilePageFragment::new);
        fragmentArray.put(R.layout.fragment_own_dictionary_page, OwnDictionaryPageFragment::new);

        fragmentArray.put(R.layout.fragment_public_dictionaries, PublicDictionariesFragment::new);
        fragmentArray.put(R.layout.fragment_public_word_groups, PublicWordGroupsFragment::new);
        fragmentArray.put(R.layout.fragment_word, WordsFragment::new);

        fragmentArray.put(R.layout.fragment_own_dictionaries, OwnDictionariesFragment::new);
        fragmentArray.put(R.layout.fragment_favorite_dictionaries, FavoriteDictionariesFragment::new);
        fragmentArray.put(R.layout.fragment_create_own_dictionary, CreateOwnDictionaryFragment::new);
        fragmentArray.put(R.layout.fragment_member_profile_modify, MemberProfileModifyFragment::new);
    }

    public static BaseFragment createBaseFragmentInstance(int fragmentId) {
        BaseFragment baseFragment = fragmentArray.get(fragmentId).get();
        if (baseFragment == null) {
            throw new FragmentClassNotCreateException();
        }
        return baseFragment;
    }

}
