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
import com.home.englishnote.exceptions.FragmnetClassNotCreateException;
import com.home.englishnote.models.entities.Guest;
import com.home.englishnote.models.entities.Role;
import com.home.englishnote.models.entities.Token;
import com.home.englishnote.models.entities.User;
import com.home.englishnote.utils.BaseFragmentCreator;
import com.home.englishnote.utils.CustomDialog;
import com.home.englishnote.utils.RandomVacabProducer;
import com.home.englishnote.views.fragments.BaseFragment;
import com.home.englishnote.views.fragments.main.MemberProfilePageFragment;
import com.home.englishnote.views.fragments.main.PublicDictionaryPageFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        setContainerMap();
    }

    private User user;

    private void setMember() {
        user = (User) getIntent().getSerializableExtra("user");
        user = RandomVacabProducer.randomMember(Role.MEMBER);
    }

    public User getUser() {
        return user;
    }

    public Token getToken() {
        return user.getToken();
    }

    private Map<Integer, Integer> containerMap = new HashMap<>();

    private void setContainerMap() {
        containerMap.put(R.layout.fragment_public_dictionary_page, R.id.dictionaryHomePageContainer);
        containerMap.put(R.layout.fragment_member_profile_page, R.id.dictionaryHomePageContainer);
        containerMap.put(R.layout.fragment_own_dictionary_page, R.id.dictionaryHomePageContainer);
        containerMap.put(R.layout.fragment_member_profile_modify, R.id.dictionaryHomePageContainer);

        containerMap.put(R.layout.fragment_public_dictionaries, R.id.publicDictionaryPageContainer);
        containerMap.put(R.layout.fragment_public_word_groups, R.id.publicDictionaryPageContainer);
        containerMap.put(R.layout.fragment_word, R.id.publicDictionaryPageContainer);

        containerMap.put(R.layout.fragment_own_dictionaries, R.id.memberProfilePageContainer);
        containerMap.put(R.layout.fragment_favorite_dictionaries, R.id.memberProfilePageContainer);
        containerMap.put(R.layout.fragment_create_own_dictionary, R.id.memberProfilePageContainer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        switchFragment(R.layout.fragment_public_dictionary_page);
        switchFragment(R.layout.fragment_public_dictionaries);
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
                            createDialogAskForLogOut();
                            break;
                    }
                    return true;
                });
    }

    private void setInnerFragmentIntoMemberProfilePage(int innerFragmentId) {
        switchFragment(R.layout.fragment_member_profile_page);
        if (innerFragmentId != R.layout.fragment_own_dictionaries) {
            switchFragment(R.layout.fragment_own_dictionaries);
        }
        switchFragment(innerFragmentId);
    }

    private void createDialogAskForLogOut() {
        CustomDialog customDialog = new CustomDialog(this)
                .setMessage("Are you sure to log out ?")
                .setDialogButtonLeft("Yes", (v, event) -> {
                    finish();
                    return true;
                })
                .setDialogButtonRight("No", (v, event) -> {
                    dictionaryHomePageDrawer.closeDrawer(GravityCompat.START);
                    return true;
                });
        customDialog.show();
    }

    private BaseFragmentCreator baseFragmentCreator = new BaseFragmentCreator();
    private List<String> fragmentTagList = new ArrayList<>(10);

    public void switchFragment(int fragmentId, Serializable... serializableArray) {
        try {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            int containerId = containerMap.get(fragmentId);
//            if (containerId == R.id.publicDictionaryPageContainer) {
//                switchFragment(R.layout.fragment_public_dictionary_page);
//            } else if (containerId == R.id.memberProfilePageContainer) {
//                switchFragment(R.layout.fragment_member_profile_page);
//            }
            BaseFragment nextFragment = baseFragmentCreator
                    .createBaseFragmentInstance(fragmentId);
            if (serializableArray.length > 0) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("VocabNoteObjects", serializableArray[0]);
                nextFragment.setArguments(bundle);
            }
            String nextFragmentTag = nextFragment.getClass().getSimpleName();
            fragmentTransaction
                    .replace(containerMap.get(fragmentId), nextFragment, nextFragmentTag)
                    .addToBackStack(nextFragmentTag)
                    .commit();
            fragmentTagList.add(nextFragmentTag);
            logd();
            dictionaryHomePageDrawer.closeDrawer(GravityCompat.START);
        } catch (FragmnetClassNotCreateException err) {
            Log.d(this.getClass().getSimpleName(), err.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        if (dictionaryHomePageDrawer.isDrawerOpen(GravityCompat.START)) {
            dictionaryHomePageDrawer.closeDrawer(GravityCompat.START);
        } else {
            int fragmentCount = fragmentManager.getBackStackEntryCount();
            if (fragmentCount > 2) {
                fragmentManager.popBackStack();
                fragmentCount--;
                String fragmentTag = fragmentManager
                        .getBackStackEntryAt(fragmentCount - 1).getName();
                Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
                fragmentTagList.remove(fragmentCount - 1);
                logd();
                if (fragment instanceof PublicDictionaryPageFragment
                        || fragment instanceof MemberProfilePageFragment) {
                    fragmentManager.popBackStack();
                    fragmentTagList.remove(fragmentTagList.size() - 1);
                    logd();
                }
            } else {
//                fragmentManager.popBackStack();
//                fragmentManager.popBackStack();
//                switchFragment(R.layout.fragment_public_dictionary_page);
//                switchFragment(R.layout.fragment_public_dictionaries);
                createDialogAskForLogOut();
            }
        }
    }

    private void logd() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : fragmentTagList) {
            stringBuilder.append(s).append(" \n");
        }
        Log.d("TEST", stringBuilder.substring(0, stringBuilder.length() - 2));
    }
}
