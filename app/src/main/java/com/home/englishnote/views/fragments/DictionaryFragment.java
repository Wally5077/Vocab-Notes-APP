package com.home.englishnote.views.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.home.englishnote.R;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DictionaryFragment extends BaseFragment {

    // Todo There're two inner fragment
    // Todo addFavoriteButton needs the spinner of Own Dictionary
    private View dictionaryContentBackButton;
    private View addFavoriteButton;
    private TextView userName;
    private ImageView userPic;
    private Map<Integer, Fragment> fragmentMap = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_dictionary_content, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        init();
    }

    private void findViews(View view) {
        dictionaryContentBackButton = view.findViewById(R.id.dictionaryContentBackButton);
        userName = view.findViewById(R.id.dictionaryContentUserName);
        userPic = view.findViewById(R.id.dictionaryContentUserPic);
        addFavoriteButton = view.findViewById(R.id.addFavoriteButton);
    }

    private void init() {
        setBackButton(dictionaryContentBackButton);
        addFavoriteButton.setOnClickListener(v -> {
            // Todo switch button background (2 pic)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                addFavoriteButton.setBackground(getResources()
                        .getDrawable(R.drawable.favorite_full));
            }
        });
        setFragment();
        setMember();
        switchFragment(R.layout.fragment_word_group);
    }

    private void setFragment() {
        fragmentMap.put(R.layout.fragment_word_group, new WordGroupsFragment());
        fragmentMap.put(R.layout.fragment_words, new WordGroupsFragment());
    }

    private void setMember() {
        String memberName = member.getLastName();
        userName.setText((memberName.isEmpty()) ? "userName" : memberName);
        userPic.setImageResource(R.drawable.user_pic);
        userName.setOnClickListener(this::onChangeProfilePage);
        userPic.setOnClickListener(this::onChangeProfilePage);
    }

    protected void switchFragment(int fragmentId, Serializable... serializableArray) {
        Fragment fragment = fragmentMap.get(fragmentId);
        if (fragment != null) {
            if (serializableArray.length > 0) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("VocabNoteObjects", serializableArray);
                fragment.setArguments(bundle);
            }
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.dictionaryContentContainer, fragment)
                    .addToBackStack(String.valueOf(fragmentId))
                    .commit();
        }
    }
}
