package com.home.englishnote.views.activities;


import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.home.englishnote.R;
import com.home.englishnote.views.fragments.OwnDictionaryPageFragment;
import com.home.englishnote.views.fragments.PublicDictionaryPageFragment;
import com.home.englishnote.views.fragments.MemberProfilePageFragment;
import com.home.englishnote.views.fragments.dictionary.PublicDictionariesFragment;
import com.home.englishnote.views.fragments.profile.CreateOwnDictionaryFragment;
import com.home.englishnote.views.fragments.profile.FavoriteDictionariesFragment;
import com.home.englishnote.views.fragments.profile.MemberProfileModifyFragment;
import com.home.englishnote.views.fragments.dictionary.PublicWordGroupsFragment;
import com.home.englishnote.views.fragments.profile.OwnDictionariesFragment;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@RequiresApi(api = Build.VERSION_CODES.N)
public class DictionaryHomePageActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private Map<Integer, Fragment> fragmentMap = new HashMap<>();
    private DrawerLayout dictionaryHomePageDrawer;
    private NavigationView dhp_ngv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_home_page);
        findViews();
        init();
    }

    private void findViews() {
        dictionaryHomePageDrawer = findViewById(R.id.dictionaryHomePageDrawer);
        dhp_ngv = findViewById(R.id.dhp_ngv);
    }

    private void init() {
        fragmentManager = getSupportFragmentManager();
        setFragments();
        setNavigationView();
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
        fragmentMap.put(R.layout.fragment_favorite_dictionaries, new FavoriteDictionariesFragment());
        fragmentMap.put(R.layout.fragment_create_own_dictionary, new CreateOwnDictionaryFragment());
        fragmentMap.put(R.layout.fragment_member_profile_modify, new MemberProfileModifyFragment());

        // Own Dictionary

        switchFragment(R.layout.fragment_public_dictionary_page, R.id.dictionaryHomePageContainer);
    }

    public void onDrawerClick(View view) {
        dictionaryHomePageDrawer.openDrawer(GravityCompat.START);
    }

    private void setNavigationView() {
        dhp_ngv.setNavigationItemSelectedListener(
                menuItem -> {
                    switch (menuItem.getItemId()) {
                        case R.id.drawer_create_dictionary:
                            setInnerFragmentIntoMemberProfilePage(
                                    R.layout.fragment_create_own_dictionary);
                            break;
                        case R.id.drawer_favorite:
                            setInnerFragmentIntoMemberProfilePage(
                                    R.layout.fragment_favorite_dictionaries);
                            break;
                        case R.id.drawer_my_list:
                            setInnerFragmentIntoMemberProfilePage(
                                    R.layout.fragment_own_dictionaries);
                            break;
                        case R.id.drawer_log_out:
                            finish();
                            break;
                    }
                    return true;
                });
        View view = LayoutInflater.from(this)
                .inflate(R.layout.drawer_header, dhp_ngv, false);
    }

    private void setInnerFragmentIntoMemberProfilePage(int innerFragmentId) {
        switchFragment(R.layout.fragment_member_profile_page, R.id.dictionaryHomePageContainer);
        switchFragment(innerFragmentId, R.id.memberProfilePageContainer);
    }

    private Stack<Integer> containerStack = new Stack<>();
    private Stack<Integer> fragmentStack = new Stack<>();

    public void switchFragment(int fragmentId, int containerId,
                               Serializable... serializableArray) {
        Fragment nextFragment = fragmentMap.get(fragmentId);
        if (nextFragment != null) {
            // 傳遞參數給其他 Fragment
            if (serializableArray.length > 0) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("VocabNoteObjects", serializableArray[0]);
                nextFragment.setArguments(bundle);
            }
            Fragment hideFragment;
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (nextFragment.isAdded()) {
                // fragmentStack.peek() 為當前片段 layout
                hideFragment = fragmentMap.get(fragmentStack.peek());
            } else {
                hideFragment = nextFragment;
                // 加入新片段進 container (無論 container 內或外)
                fragmentTransaction
                        .add(containerId, nextFragment);
                // 換頁記錄
                fragmentStack.add(fragmentId);
                containerStack.add(containerId);
            }
            // 隱藏當前片段 , 顯示下一片段
            fragmentTransaction.hide(hideFragment).show(nextFragment).commit();
            dictionaryHomePageDrawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onBackPressed() {
        if (dictionaryHomePageDrawer.isDrawerOpen(GravityCompat.START)) {
            dictionaryHomePageDrawer.closeDrawer(GravityCompat.START);
        } else {
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
    }

    private void backLastFragment(int fragmentId) {
        Fragment fragment = fragmentMap.get(fragmentId);
        containerStack.pop();
        fragmentManager.beginTransaction().remove(fragment).commit();
        switchFragment(fragmentStack.peek(), containerStack.peek());
    }
}
