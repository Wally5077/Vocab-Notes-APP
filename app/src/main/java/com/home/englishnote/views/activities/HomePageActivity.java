package com.home.englishnote.views.activities;


import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
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

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.home.englishnote.R;
import com.home.englishnote.exceptions.FragmentClassNotCreateException;
import com.home.englishnote.models.entities.Guest;
import com.home.englishnote.models.entities.Role;
import com.home.englishnote.models.entities.Token;
import com.home.englishnote.models.entities.User;
import com.home.englishnote.utils.BaseFragmentGenerator;
import com.home.englishnote.utils.CustomDialog;
import com.home.englishnote.utils.Global;
import com.home.englishnote.utils.RandomVacabGenerator;
import com.home.englishnote.utils.ThreadExecutor;
import com.home.englishnote.views.fragments.BaseFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
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
        setContainerMap();
    }

    private User user;

    private void setMember() {
        user = (User) getIntent().getSerializableExtra("user");
        user = RandomVacabGenerator.randomMember(Role.MEMBER);
    }

    public User getUser() {
        return user;
    }

    public Token getToken() {
        return user.getToken();
    }

    private SparseIntArray containerSparseArray;

    private void setContainerMap() {
        containerSparseArray = new SparseIntArray();
        containerSparseArray.put(R.layout.fragment_public_dictionary_page, R.id.dictionaryHomePageContainer);
        containerSparseArray.put(R.layout.fragment_member_profile_page, R.id.dictionaryHomePageContainer);
        containerSparseArray.put(R.layout.fragment_own_dictionary_page, R.id.dictionaryHomePageContainer);

        containerSparseArray.put(R.layout.fragment_public_dictionaries, R.id.publicDictionaryPageContainer);
        containerSparseArray.put(R.layout.fragment_public_word_groups, R.id.publicDictionaryPageContainer);
        containerSparseArray.put(R.layout.fragment_word, R.id.publicDictionaryPageContainer);
        containerSparseArray.put(R.layout.fragment_member_profile_modify, R.id.publicDictionaryPageContainer);

        containerSparseArray.put(R.layout.fragment_own_dictionaries, R.id.memberProfilePageContainer);
        containerSparseArray.put(R.layout.fragment_favorite_dictionaries, R.id.memberProfilePageContainer);
        containerSparseArray.put(R.layout.fragment_create_own_dictionary, R.id.memberProfilePageContainer);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Fragment mainFragment = switchMainFragment(R.layout.fragment_public_dictionary_page);
        switchSecondaryFragment(mainFragment, R.layout.fragment_public_dictionaries);
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
                            setDrawerFragment(
                                    R.layout.fragment_create_own_dictionary);
                            break;
                        case R.id.drawer_favorite:
                            setDrawerFragment(
                                    R.layout.fragment_favorite_dictionaries);
                            break;
                        case R.id.drawer_my_list:
                            setDrawerFragment(
                                    R.layout.fragment_own_dictionaries);
                            break;
                        case R.id.drawer_log_out:
                            createDialogAskForLogOut();
                            break;
                    }
                    return true;
                });
    }

    boolean hasBeenMemberPage = false;

    public void setDrawerFragment(int innerFragmentId) {
        Fragment mainFragment = null;
        if (!hasBeenMemberPage) {
            mainFragment = switchMainFragment(R.layout.fragment_member_profile_page);
            if (innerFragmentId != R.layout.fragment_own_dictionaries) {
                switchSecondaryFragment(mainFragment, R.layout.fragment_own_dictionaries);
            }
        }
        switchSecondaryFragment(mainFragment, innerFragmentId);
    }

    private void createDialogAskForLogOut() {
        new CustomDialog(this)
                .setMessage("Are you sure to log out ?")
                .setDialogButtonLeft("Yes", (v, event) -> {
                    finish();
                    return true;
                })
                .setDialogButtonRight("No", (v, event) -> {
                    dictionaryHomePageDrawer.closeDrawer(GravityCompat.START);
                    return true;
                }).showDialog();
    }

    public Fragment switchMainFragment(int fragmentId, Serializable... serializableArray) {
        BaseFragment nextFragment = BaseFragmentGenerator
                .createBaseFragmentInstance(fragmentId);
        try {
            Bundle bundle = new Bundle();
            if (serializableArray.length > 0) {
                bundle.putSerializable("VocabNoteObjects", serializableArray[0]);
            }
            String nextFragmentTag = nextFragment.getClass().getSimpleName();
            int containerId = containerSparseArray.get(fragmentId);

            if (isMainPageByFragmentTag(nextFragmentTag)) {
                fragmentManager.beginTransaction()
                        .replace(containerId, nextFragment, nextFragmentTag)
                        .addToBackStack(nextFragmentTag).commit();
            }
            nextFragment.setArguments(bundle);
            checkParticularEventPageByFragmentTag(nextFragmentTag);
            dictionaryHomePageDrawer.closeDrawer(GravityCompat.START);
        } catch (FragmentClassNotCreateException err) {
            Log.e(this.getClass().getSimpleName(), err.getMessage(), err);
        }
        return nextFragment;
    }

    public void switchSecondaryFragment(Fragment mainFragment, int fragmentId, Serializable... serializableArray) {
        try {
            BaseFragment nextFragment = BaseFragmentGenerator
                    .createBaseFragmentInstance(fragmentId);
            Bundle bundle = new Bundle();
            if (serializableArray.length > 0) {
                bundle.putSerializable("VocabNoteObjects", serializableArray[0]);
            }
            String nextFragmentTag = nextFragment.getClass().getSimpleName();
            int containerId = containerSparseArray.get(fragmentId);

            if (!isMainPageByFragmentTag(nextFragmentTag)) {
                ThreadExecutor threadExecutor = Global.threadExecutor();
                if (mainFragment == null) {
                    int fragmentCount = fragmentManager.getBackStackEntryCount();
                    String mainFragmentTag = fragmentManager.getBackStackEntryAt(fragmentCount - 1).getName();
                    mainFragment = fragmentManager.findFragmentByTag(mainFragmentTag);
                }
                Fragment finalMainFragment = mainFragment;
                threadExecutor.execute(() -> {
                    while (true) {
                        if (finalMainFragment.isAdded()) {
                            break;
                        }
                    }
                    threadExecutor.executeUiThread(() -> {
                        FragmentManager childFragmentManager = finalMainFragment.getChildFragmentManager();
                        Fragment secondaryFragment = childFragmentManager.findFragmentByTag(nextFragmentTag);
                        if (secondaryFragment == null) {
                            childFragmentManager.beginTransaction()
                                    .replace(containerId, nextFragment, nextFragmentTag)
                                    .addToBackStack(nextFragmentTag).commit();
                        }
                    });
                });
            }
            bundle.putInt("fragmentId", fragmentId);
            nextFragment.setArguments(bundle);
            checkParticularEventPageByFragmentTag(nextFragmentTag);
            dictionaryHomePageDrawer.closeDrawer(GravityCompat.START);
        } catch (FragmentClassNotCreateException err) {
            Log.e(this.getClass().getSimpleName(), err.getMessage(), err);
        }
    }

    private boolean isMainPageByFragmentTag(String nextFragmentTag) {
        return "PublicDictionaryPageFragment".equals(nextFragmentTag)
                || "MemberProfilePageFragment".equals(nextFragmentTag)
                || "OwnDictionaryPageFragment".equals(nextFragmentTag);
    }

    private void checkParticularEventPageByFragmentTag(String nextFragmentTag) {
        if ("MemberProfilePageFragment".equals(nextFragmentTag)) {
            hasBeenMemberPage = true;
        }
    }

    @Override
    public void onBackPressed() {
        if (dictionaryHomePageDrawer.isDrawerOpen(GravityCompat.START)) {
            dictionaryHomePageDrawer.closeDrawer(GravityCompat.START);
        } else {
            int mainFragmentCount = fragmentManager.getBackStackEntryCount();
            if (mainFragmentCount > 0) {
                String mainFragmentTag = fragmentManager
                        .getBackStackEntryAt(mainFragmentCount - 1).getName();
                Fragment mainFragment = fragmentManager.findFragmentByTag(mainFragmentTag);
                FragmentManager childFragmentManager = mainFragment.getChildFragmentManager();
                if (childFragmentManager != null) {
                    int childFragmentCount = childFragmentManager.getBackStackEntryCount();
                    if (childFragmentCount > 1) {
                        childFragmentManager.popBackStack();
                    } else {
                        hasBeenMemberPage = false;
                        if (mainFragmentCount == 1) {
                            createDialogAskForLogOut();
                        } else {
                            fragmentManager.popBackStack();
                        }
                    }
                }
            } else {
                createDialogAskForLogOut();
            }
        }
    }

    private void logd() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Fragment f : fragmentManager.getFragments()) {
            String s = f.getClass().getSimpleName();
            stringBuilder.append(s).append(" \n");
        }
        Log.d("TEST", stringBuilder.substring(0, stringBuilder.length() - 2));
    }
}
