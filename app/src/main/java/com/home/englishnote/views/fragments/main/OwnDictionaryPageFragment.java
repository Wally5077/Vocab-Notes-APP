package com.home.englishnote.views.fragments.main;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.home.englishnote.R;
import com.home.englishnote.models.entities.Dictionary;
import com.home.englishnote.models.entities.WordGroup;
import com.home.englishnote.presenters.OwnDictionaryPagePresenter;
import com.home.englishnote.presenters.OwnDictionaryPagePresenter.OwnDictionaryPageView;
import com.home.englishnote.utils.Global;
import com.home.englishnote.utils.OwnWordGroupsAdapter;
import com.home.englishnote.views.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.home.englishnote.utils.ViewEnableUtil.*;

public class OwnDictionaryPageFragment extends BaseFragment implements OwnDictionaryPageView {

    private TextView ownDictionaryPageDictionaryName;
    private TextView ownDictionaryPageWordGroupCount;
    private ImageView ownDictionaryPagePhoto;
    private TextView ownDictionaryPageMemberName;
    private Button ownDictionaryPageEditButton;
    private ImageView ownDictionaryPageDictionaryNamePen;
    private Button ownDictionaryPageDeleteButton;
    private Button ownDictionaryPageSaveButton;
    private AutoCompleteTextView ownDictionaryPageQuery;
    private RecyclerView wordGroupsRecycler;
    private OwnWordGroupsAdapter ownWordGroupsAdapter;
    private OwnDictionaryPagePresenter ownDictionaryPagePresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater
                .inflate(R.layout.fragment_own_dictionary_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
    }

    private void findViews(View view) {
        ownDictionaryPageDictionaryName = view.findViewById(R.id.ownDictionaryPageDictionaryName);
        ownDictionaryPageWordGroupCount = view.findViewById(R.id.ownDictionaryPageWordGroupCount);
        ownDictionaryPagePhoto = view.findViewById(R.id.ownDictionaryPagePhoto);
        ownDictionaryPageMemberName = view.findViewById(R.id.ownDictionaryPageMemberName);
        ownDictionaryPageEditButton = view.findViewById(R.id.ownDictionaryPageEditButton);
        ownDictionaryPageDictionaryNamePen = view.findViewById(R.id.ownDictionaryPageDictionaryNamePen);
        ownDictionaryPageDeleteButton = view.findViewById(R.id.ownDictionaryPageDeleteButton);
        ownDictionaryPageSaveButton = view.findViewById(R.id.ownDictionaryPageSaveButton);
        ownDictionaryPageQuery = view.findViewById(R.id.ownDictionaryPageQuery);
        wordGroupsRecycler = view.findViewById(R.id.wordGroupRecycler);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
        fetchDictionaryFromBundle();
        queryWordGroupsList();
    }

    private void init() {
        ownDictionaryPagePresenter = new OwnDictionaryPagePresenter(
                this, Global.wordGroupRepository(), Global.threadExecutor());
        setUpMember();
        setUpWordGroupsRecycler();
        setUpEditButton();
        setUpOwnDictionarySearchBar();
    }

    private void setUpMember() {
        ownDictionaryPageMemberName.setText(user.getFirstName());
        Glide.with(getContext())
                .asBitmap()
                .load(user.getImageURL())
                .fitCenter()
                .error(R.drawable.small_user_pic)
                .into(ownDictionaryPagePhoto);
    }

    private List<WordGroup> wordGroupsList;

    private void setUpWordGroupsRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        wordGroupsRecycler.setHasFixedSize(true);
        wordGroupsRecycler.setLayoutManager(linearLayoutManager);
        wordGroupsList = new ArrayList<>();
        ownWordGroupsAdapter = new OwnWordGroupsAdapter(wordGroupsList);
        wordGroupsRecycler.setAdapter(ownWordGroupsAdapter);
        wordGroupsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastVisibleItemPosition =
                        linearLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + WORD_GROUP_LIMIT / 3
                        >= ownWordGroupsAdapter.getItemCount()) {
                    queryWordGroupsList();
                }
            }
        });
    }

    private void setUpEditButton() {
        setEditButtonEnable(true);
        ownDictionaryPageEditButton.setOnTouchListener((v, event) -> {
            setEditButtonEnable(false);
            return true;
        });
        ownDictionaryPageSaveButton.setOnTouchListener((v, event) -> {
            setEditButtonEnable(true);
            return true;
        });
    }

    private void setEditButtonEnable(boolean enable) {
        setViewsVisible(enable, ownDictionaryPageEditButton);
        setViewsFocusable(enable, ownDictionaryPageEditButton);
        setViewsVisible(!enable, ownDictionaryPageDeleteButton,
                ownDictionaryPageSaveButton, ownDictionaryPageDictionaryNamePen);
        setViewsFocusable(!enable,
                ownDictionaryPageDictionaryName, ownDictionaryPageDictionaryNamePen,
                ownDictionaryPageDeleteButton, ownDictionaryPageSaveButton);
        ownWordGroupsAdapter.setEditButtonClick(!enable);
    }

    private void setUpOwnDictionarySearchBar() {
        ownDictionaryPageQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String filterPattern = s.toString().toLowerCase().trim();
                if (count == 0) {
                    ownWordGroupsAdapter.updateWordGroupList(wordGroupsList);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        ownWordGroupsAdapter.updateWordGroupList(wordGroupsList.stream()
                                .filter(wordGroup ->
                                        wordGroup.getTitle().contains(filterPattern))
                                .collect(Collectors.toList()));
                    }
                }
                ownWordGroupsAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private Dictionary dictionary;

    private void fetchDictionaryFromBundle() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            dictionary = (Dictionary) bundle.getSerializable("VocabNoteObjects");
            ownDictionaryPageDictionaryName.setText(dictionary.getTitle());
        }
    }

    private static final int WORD_GROUP_LIMIT = 10;

    private void queryWordGroupsList() {
        ownDictionaryPagePresenter.getWordGroups(
                user.getId(), dictionary.getId(), wordGroupsList.size(), WORD_GROUP_LIMIT);
    }

    @Override
    public void onGetWordGroupsSuccessfully(List<WordGroup> wordGroupList) {
        int originalWordGroupsListSize = this.wordGroupsList.size();
        this.wordGroupsList.addAll(wordGroupList);
        ownWordGroupsAdapter.notifyItemRangeChanged(
                originalWordGroupsListSize, this.wordGroupsList.size());
        String wordGroupCount = wordGroupList.size() + " Word groups";
        ownDictionaryPageWordGroupCount.setText(wordGroupCount);
    }

}
