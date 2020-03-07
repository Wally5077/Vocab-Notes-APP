package com.home.englishnote.views.fragments.dictionary;

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
import com.home.englishnote.models.entities.Dictionary;
import com.home.englishnote.utils.VocabularyNoteKeyword;
import com.home.englishnote.views.fragments.BaseFragment;

import java.util.HashMap;
import java.util.Map;

public class PublicDictionaryFragment extends BaseFragment {

    // Todo There're two inner fragment
    // Todo addFavoriteButton needs the spinner of Own Dictionary
    private View addFavoriteButton;
    private TextView userName;
    private ImageView userPic;
    private ImageView dictionaryContentBackButton;
    private Map<Integer, Fragment> fragmentMap = new HashMap<>();
    private Dictionary dictionary;
    private View dictionaryContentFavoriteButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_public_dictionary, container, false);
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
        dictionaryContentFavoriteButton = view.findViewById(R.id.dictionaryContentFavoriteButton);
        addFavoriteButton = view.findViewById(R.id.addFavoriteButton);
    }

    private void init() {
        dictionaryContentBackButton.setOnClickListener(this::onBackButtonClick);
        dictionaryContentFavoriteButton.setOnClickListener(this::onFavoriteButtonClick);
//        addFavoriteButton.setOnClickListener(v -> {
//            // Todo switch button background (2 pic)
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                addFavoriteButton.setBackground(getResources()
//                        .getDrawable(R.drawable.favorite_full));
//            }
//        });
        setMember();
        setDictionary();
        mainPageActivity.switchFragment(R.layout.fragment_word_group,
                VocabularyNoteKeyword.DICTIONARY_CONTENT_CONTAINER, dictionary);
    }

    private void onFavoriteButtonClick(View view) {

    }

    private void setMember() {
        String memberName = member.getLastName();
        userName.setText((memberName.isEmpty()) ? "userName" : memberName);
        userPic.setImageResource(R.drawable.small_user_pic);
        userName.setOnClickListener(this::onChangeProfilePage);
        userPic.setOnClickListener(this::onChangeProfilePage);
    }

    private void setDictionary() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            dictionary = (Dictionary) bundle.getSerializable("VocabNoteObjects");
        }
    }
}
