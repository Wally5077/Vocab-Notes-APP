package com.home.englishnote.views.fragments.secondary.dictionary;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.home.englishnote.R;
import com.home.englishnote.models.entities.Dictionary;
import com.home.englishnote.models.entities.WordGroup;
import com.home.englishnote.presenters.PublicWordGroupsPresenter;
import com.home.englishnote.presenters.PublicWordGroupsPresenter.PublicWordGroupsView;
import com.home.englishnote.utils.Global;
import com.home.englishnote.utils.PublicWordGroupsAdapter;
import com.home.englishnote.views.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PublicWordGroupsFragment extends BaseFragment implements PublicWordGroupsView {

    private Button publicDictionaryFavoriteButton;
    private TextView publicDictionaryName;
    private SwipeRefreshLayout wordGroupsSwipeRefreshLayout;
    private RecyclerView wordGroupsRecycler;
    private PublicWordGroupsAdapter publicWordGroupsAdapter;
    private PublicWordGroupsPresenter publicWordGroupsPresenter;
    private AutoCompleteTextView publicDictionaryQuery;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater
                .inflate(R.layout.fragment_public_word_groups, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        init();
    }

    private void findViews(View view) {
        // Todo need a searchView
        publicDictionaryQuery = view.findViewById(R.id.publicDictionaryQuery);
        publicDictionaryName = view.findViewById(R.id.publicDictionaryName);
        publicDictionaryFavoriteButton = view.findViewById(R.id.publicDictionaryFavoriteButton);
        wordGroupsSwipeRefreshLayout = view.findViewById(R.id.publicWordGroupsSwipeRefreshLayout);
        wordGroupsRecycler = view.findViewById(R.id.publicWordGroupsRecycler);
    }

    private void init() {
        publicWordGroupsPresenter = new PublicWordGroupsPresenter(this,
                Global.memberRepository(), Global.wordGroupRepository(), Global.threadExecutor());
        publicDictionaryFavoriteButton.setOnClickListener(this::onFavoriteButtonClick);
        setUpWordGroupsRecycler();
        setUpWordGroupsSwipeRefreshLayout();
        setUpPublicDictionarySearchBar();
    }

    @Override
    public void onResume() {
        super.onResume();
        wordGroupsList.clear();
        fetchDictionaryFromBundle();
        queryWordGroupsList();
    }

    private Dictionary dictionary;

    private void fetchDictionaryFromBundle() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            dictionary = (Dictionary) bundle.getSerializable("VocabNoteObjects");
            publicDictionaryName.setText(dictionary.getTitle());
        }
    }

    private void onFavoriteButtonClick(View view) {
        // Todo check this public vocabulary dictionary has been added to favorite dictionary
        publicWordGroupsPresenter.addFavoriteDictionaryList(dictionary.getId(), user.getId());
    }

    private List<WordGroup> wordGroupsList = new ArrayList<>();

    private void setUpWordGroupsRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(dictionaryHomePageActivity);
        wordGroupsRecycler.setHasFixedSize(true);
        wordGroupsRecycler.setLayoutManager(linearLayoutManager);
        publicWordGroupsAdapter = new PublicWordGroupsAdapter(wordGroupsList);
        wordGroupsRecycler.setAdapter(publicWordGroupsAdapter);
        wordGroupsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastVisibleItemPosition =
                        linearLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == publicWordGroupsAdapter.getItemCount()) {
                    queryWordGroupsList();
                }
            }
        });
    }

    private void setUpWordGroupsSwipeRefreshLayout() {
        wordGroupsSwipeRefreshLayout.measure(0, 0);
        wordGroupsSwipeRefreshLayout.setProgressViewOffset(true, 80, 90);
        wordGroupsSwipeRefreshLayout.setOnRefreshListener(this::queryWordGroupsList);
    }

    private static final int WORD_GROUP_LIMIT = 10;

    private void queryWordGroupsList() {
        setWordGroupSwipeRefreshLayoutEnable(true);
        publicWordGroupsPresenter.getWordGroups(
                dictionary.getId(), wordGroupsList.size(), WORD_GROUP_LIMIT);
    }

    private void setUpPublicDictionarySearchBar() {
        publicDictionaryQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String filterPattern = s.toString().toLowerCase().trim();
                if (count == 0) {
                    publicWordGroupsAdapter.updateWordGroupList(wordGroupsList);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        publicWordGroupsAdapter.updateWordGroupList(wordGroupsList.stream()
                                .filter(wordGroup ->
                                        wordGroup.getTitle().contains(filterPattern))
                                .collect(Collectors.toList()));
                    }
                }
                publicWordGroupsAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onGetWordGroupsSuccessfully(List<WordGroup> wordGroupList) {
        setWordGroupSwipeRefreshLayoutEnable(false);
        this.wordGroupsList.addAll(wordGroupList);
        publicWordGroupsAdapter.notifyDataSetChanged();
    }

    private void setWordGroupSwipeRefreshLayoutEnable(boolean enable) {
        wordGroupsSwipeRefreshLayout.setEnabled(enable);
        wordGroupsSwipeRefreshLayout.setRefreshing(enable);
    }

    @Override
    public void onAddFavoriteDictionaryListSuccessfully() {
        Toast.makeText(getActivity(), "It had been already removed.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddFavoriteDictionaryListFailed() {
        Toast.makeText(getActivity(), "It had been already added.", Toast.LENGTH_SHORT).show();
    }

}
