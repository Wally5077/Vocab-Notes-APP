package com.home.englishnote.views.fragments.main;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import com.bumptech.glide.Glide;
import com.home.englishnote.R;
import com.home.englishnote.models.entities.Dictionary;
import com.home.englishnote.models.entities.Word;
import com.home.englishnote.presenters.PublicDictionaryPagePresenter;
import com.home.englishnote.presenters.PublicDictionaryPagePresenter.PublicDictionaryPageView;
import com.home.englishnote.utils.DictionarySearchAdapter;
import com.home.englishnote.utils.Global;
import com.home.englishnote.views.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.home.englishnote.utils.ViewEnableUtil.setViewsFocusable;
import static com.home.englishnote.utils.ViewEnableUtil.setViewsVisible;

public class PublicDictionaryPageFragment extends BaseFragment
        implements PublicDictionaryPageView {

    private TextView memberName;
    private ImageView memberPhoto;
    private View vocabSearch;
    private View dictionarySearch;
    private View memberProfile;
    private View vocabSearchFeature;
    private AutoCompleteTextView vocabAutoSearch;
    private ImageView vocabSearchImage;
    private TextView vocabSearchText;
    private ImageView dictionarySearchImage;
    private TextView dictionarySearchText;
    private PublicDictionaryPagePresenter publicDictionaryPagePresenter;
    private RecyclerView dictionarySearchRecycler;
    private DictionarySearchAdapter dictionarySearchAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater
                .inflate(R.layout.fragment_public_dictionary_page,
                        container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        init();
    }

    private void findViews(View view) {
        // vocabSearch
        vocabSearch = view.findViewById(R.id.publicDictionaryPageVocabSearch);
        vocabSearchFeature = view.findViewById(R.id.publicDictionaryPageVocabSearchFeature);
        vocabAutoSearch = view.findViewById(R.id.publicDictionaryPageVocabAutoSearch);
        vocabSearchImage = view.findViewById(R.id.publicDictionaryPageVocabSearchImage);
        vocabSearchText = view.findViewById(R.id.publicDictionaryPageVocabSearchText);

        // dictionarySearch Todo
        dictionarySearch = view.findViewById(R.id.publicDictionaryPageDictionarySearch);
        dictionarySearchImage = view.findViewById(R.id.publicDictionaryPageDictionarySearchImage);
        dictionarySearchText = view.findViewById(R.id.publicDictionaryPageDictionarySearchText);
        dictionarySearchRecycler = view.findViewById(R.id.publicDictionaryPageDictionarySearchRecycler);

        // memberProfile
        memberProfile = view.findViewById(R.id.publicDictionaryPageMemberProfile);
        memberName = view.findViewById(R.id.publicDictionaryPageMemberName);
        memberPhoto = view.findViewById(R.id.publicDictionaryPageMemberPhoto);
    }

    private void init() {
        publicDictionaryPagePresenter = new PublicDictionaryPagePresenter(
                this, Global.dictionaryRepository(),
                Global.wordRepository(), Global.memberRepository(), Global.threadExecutor());
        setDefaultPage();
        setVocabAutoSearch();
        setOnVocabSearchClick();
        setDictionarySearchRecycler();
        setOnDictionarySearchClick();
    }

    @Override
    public void onResume() {
        super.onResume();
        setMemberPhoto();
        setMemberName();
    }

    private Set<String> wordSet = new HashSet<>();

    private void setVocabAutoSearch() {
        vocabAutoSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString();
                if (keyword.isEmpty()) {
                    vocabAutoSearch.dismissDropDown();
                } else {
                    publicDictionaryPagePresenter.getPossibleWord(keyword);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private boolean isVocabSearchClick = false;

    private void setOnVocabSearchClick() {
        vocabSearch.setOnClickListener(v -> {
            isVocabSearchClick = !isVocabSearchClick;
            setSearchImageDrawable(isVocabSearchClick, vocabSearchImage);
            setSearchTextColor(isVocabSearchClick, vocabSearchText);
            setViewsVisible(isVocabSearchClick, vocabSearchFeature);
            setViewsFocusable(isVocabSearchClick, vocabSearchFeature);
        });
    }

    private List<Dictionary> dictionaryList = new ArrayList<>();

    private void setDictionarySearchRecycler() {
        LayoutManager layoutManager = new LinearLayoutManager(dictionaryHomePageActivity);
        dictionarySearchRecycler.setHasFixedSize(true);
        dictionarySearchRecycler.setLayoutManager(layoutManager);
        dictionarySearchAdapter = new DictionarySearchAdapter(dictionaryList);
        dictionarySearchRecycler.setAdapter(dictionarySearchAdapter);
    }

    private boolean isDictionarySearchClick = false;

    private void setOnDictionarySearchClick() {
        dictionarySearch.setOnClickListener(v -> {
            isDictionarySearchClick = !isDictionarySearchClick;
            setSearchImageDrawable(isDictionarySearchClick, dictionarySearchImage);
            setSearchTextColor(isDictionarySearchClick, dictionarySearchText);
            setViewsVisible(isDictionarySearchClick, dictionarySearchRecycler);
            setViewsFocusable(isDictionarySearchClick, dictionarySearchRecycler);
            if (isDictionarySearchClick) {
                publicDictionaryPagePresenter.getDictionaryList();
            }
        });
    }

    private void setSearchImageDrawable(boolean enable, View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(
                    enable ? getResources().getDrawable(R.drawable.green_search_button)
                            : getResources().getDrawable(R.drawable.grey_search_button));
        }
    }

    private void setSearchTextColor(boolean enable, TextView textView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            textView.setTextColor(
                    enable ? getResources().getColor(R.color.searchTextGreen)
                            : getResources().getColor(R.color.searchTextGrey));
        }
    }

    private void setMemberPhoto() {
        Glide.with(this)
                .asBitmap()
                .load(user.getImageURL())
                .fitCenter()
                .error(R.drawable.small_user_pic)
                .into(memberPhoto);
    }

    private void setMemberName() {
        String memberName = user.getFirstName();
        this.memberName.setText((memberName.isEmpty()) ? "memberName" : memberName);
    }

    private void setDefaultPage() {
        switchFragment(R.layout.fragment_public_dictionaries, PUBLIC_DICTIONARY_CONTAINER);
    }

    @Override
    public void onGetPossibleWordSuccessfully(List<Word> wordList) {
        List<String> possibleWordList = new ArrayList<>();
        for (int wordCount = 0; wordCount < Math.min(5, wordList.size()); wordCount++) {
            String wordName = wordList.get(wordCount).getName();
            possibleWordList.add(wordName);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getContext(), android.R.layout.simple_list_item_1, possibleWordList);
        vocabAutoSearch.setAdapter(arrayAdapter);
        if (!wordList.isEmpty()) {
            vocabAutoSearch.showDropDown();
        }
        vocabAutoSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vocabAutoSearch.dismissDropDown();
            }
        });
    }

    @Override
    public void onGetDictionaryListSuccessfully(List<Dictionary> dictionaryList) {
        this.dictionaryList.clear();
        this.dictionaryList.addAll(dictionaryList);
        dictionarySearchAdapter.setDefaultItemBackground();
        dictionarySearchAdapter.notifyDataSetChanged();
    }
}
