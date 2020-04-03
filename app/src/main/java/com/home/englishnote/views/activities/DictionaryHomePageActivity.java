package com.home.englishnote.views.activities;


import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.home.englishnote.R;
import com.home.englishnote.models.entities.Guest;
import com.home.englishnote.models.entities.Role;
import com.home.englishnote.models.entities.Token;
import com.home.englishnote.models.entities.User;
import com.home.englishnote.utils.RandomVacabProducer;
import com.home.englishnote.views.fragments.BaseFragment;
import com.home.englishnote.views.fragments.main.OwnDictionaryPageFragment;
import com.home.englishnote.views.fragments.main.PublicDictionaryPageFragment;
import com.home.englishnote.views.fragments.main.MemberProfilePageFragment;
import com.home.englishnote.views.fragments.WordsFragment;
import com.home.englishnote.views.fragments.secondary.dictionary.PublicDictionariesFragment;
import com.home.englishnote.views.fragments.secondary.profile.CreateOwnDictionaryFragment;
import com.home.englishnote.views.fragments.secondary.profile.FavoriteDictionariesFragment;
import com.home.englishnote.views.fragments.secondary.profile.MemberProfileModifyFragment;
import com.home.englishnote.views.fragments.secondary.dictionary.PublicWordGroupsFragment;
import com.home.englishnote.views.fragments.secondary.profile.OwnDictionariesFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class DictionaryHomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_home_page);
        findViews();
        init();
    }

    private void findViews() {
        dictionaryHomePageDrawer = findViewById(R.id.dictionaryHomePageDrawer);
        dictionaryHomePageNavigationView = findViewById(R.id.dictionaryHomePageNavigationView);
    }

    private FragmentManager fragmentManager;

    private void init() {
        fragmentManager = getSupportFragmentManager();
        setMember();
        setNavigationView();
        setFragmentMap();
    }

    private User user;

    private void setMember() {
        user = (User) getIntent().getSerializableExtra("user");
//        user = RandomVacabProducer.randomMember(Role.MEMBER);
    }

    public User getUser() {
        return user;
    }

    public Token getToken() {
        return user.getToken();
    }

    private Map<Integer, BaseFragment> fragmentMap = new HashMap<>();

    private void setFragmentMap() {
        // DictionaryHomePageContainer
        fragmentMap.put(R.layout.fragment_public_dictionary_page, new PublicDictionaryPageFragment());
        fragmentMap.put(R.layout.fragment_member_profile_page, new MemberProfilePageFragment());
        fragmentMap.put(R.layout.fragment_own_dictionary_page, new OwnDictionaryPageFragment());

        // Public Dictionary
        fragmentMap.put(R.layout.fragment_public_dictionaries, new PublicDictionariesFragment());
        fragmentMap.put(R.layout.fragment_public_word_groups, new PublicWordGroupsFragment());
        fragmentMap.put(R.layout.fragment_word, new WordsFragment());

        // Profile
        fragmentMap.put(R.layout.fragment_own_dictionaries, new OwnDictionariesFragment());
        fragmentMap.put(R.layout.fragment_favorite_dictionaries, new FavoriteDictionariesFragment());
        fragmentMap.put(R.layout.fragment_create_own_dictionary, new CreateOwnDictionaryFragment());
        fragmentMap.put(R.layout.fragment_member_profile_modify, new MemberProfileModifyFragment());

        // Own Dictionary
        containerNameMap.put(R.id.dictionaryHomePageContainer, "DICTIONARY_HOME_PAGE_CONTAINER");
        containerNameMap.put(R.id.publicDictionaryPageContainer, "PUBLIC_DICTIONARY_PAGE_CONTAINER");
        containerNameMap.put(R.id.memberProfilePageContainer, "MEMBER_PROFILE_PAGE_CONTAINER");

        switchFragment(R.layout.fragment_public_dictionary_page, R.id.dictionaryHomePageContainer);
    }

    private DrawerLayout dictionaryHomePageDrawer;
    private NavigationView dictionaryHomePageNavigationView;

    public void onDrawerClick(View view) {
        dictionaryHomePageDrawer.openDrawer(GravityCompat.START);
        if (user instanceof Guest) {
            Menu menu = dictionaryHomePageNavigationView.getMenu();
            int itemSize = menu.size();
            List<MenuItem> menuItemListToRemove = new ArrayList<>(itemSize - 1);
            if (itemSize > 1) {
                for (int itemIndex = 0; itemIndex < itemSize - 1; itemIndex++) {
                    menuItemListToRemove.add(menu.getItem(itemIndex));
                }
                for (MenuItem menuItem : menuItemListToRemove) {
                    menu.removeItem(menuItem.getItemId());
                }
            }
        }
    }

    private void setNavigationView() {
        setHeaderView();
        setOnDictionaryHomePageNavigationItemSelected();
    }

    private void setHeaderView() {
        View headerView = dictionaryHomePageNavigationView.getHeaderView(0);
        ImageView userPhoto = headerView.findViewById(R.id.headViewMemberPhoto);
        TextView userName = headerView.findViewById(R.id.headViewMemberName);
        Glide.with(this)
                .asBitmap()
                .load(user.getImageURL())
                .circleCrop()
                .error(R.drawable.big_user_pic)
                .into(userPhoto);
        userName.setText(user.getFirstName());
    }

    private void setOnDictionaryHomePageNavigationItemSelected() {
        dictionaryHomePageNavigationView.setNavigationItemSelectedListener(
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
    }

    private void setInnerFragmentIntoMemberProfilePage(int innerFragmentId) {
        switchFragment(R.layout.fragment_member_profile_page, R.id.dictionaryHomePageContainer);
        switchFragment(innerFragmentId, R.id.memberProfilePageContainer);
    }

    private Stack<Integer> containerStack = new Stack<>();
    private Stack<Integer> fragmentStack = new Stack<>();
    private Map<Integer, String> containerNameMap = new HashMap<>();

    public void switchFragment(int fragmentId, int containerId,
                               Serializable... serializableArray) {
        BaseFragment nextFragment = fragmentMap.get(fragmentId);
        if (nextFragment != null) {
            // 傳遞參數給其他 Fragment
            if (serializableArray.length > 0) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("VocabNoteObjects", serializableArray[0]);
                nextFragment.setArguments(bundle);
                nextFragment.updateFragmentData();
            }
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (nextFragment.isAdded()) {
                // fragmentStack.peek() 為當前片段 layout
                fragmentTransaction.hide(fragmentMap.get(fragmentStack.peek()));
                nextFragment.updateFragmentData();
            } else {
                // 加入新片段進 container (無論 container 內或外)
                fragmentTransaction
                        .add(containerId, nextFragment);
                // 換頁記錄
                fragmentStack.add(fragmentId);
                containerStack.add(containerId);
            }
//            Log.d(this.getClass().getSimpleName(), getFragmentStackLog());
//            Log.d(this.getClass().getSimpleName(), getContainerStackLog());
            fragmentTransaction.show(nextFragment).commit();
            // 隱藏當前片段 , 顯示下一片段
            dictionaryHomePageDrawer.closeDrawer(GravityCompat.START);
        }
    }

    private String getFragmentStackLog() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer fragmentId : fragmentStack) {
            Fragment fragment = fragmentMap.get(fragmentId);
            stringBuilder.append(fragment.getClass().getSimpleName()).append("\n");
        }
        return stringBuilder.toString();
    }

    private String getContainerStackLog() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer containerId : containerStack) {
            String containerName = containerNameMap.get(containerId);
            stringBuilder.append(containerName).append("\n");
        }
        return stringBuilder.toString();
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
            } else {
                finish();
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
