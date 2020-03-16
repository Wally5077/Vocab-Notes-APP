package com.home.englishnote.views.fragments.dictionary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class PublicWordGroupsFragment extends BaseFragment implements PublicWordGroupsView {

    private Button publicDictionaryFavoriteButton;
    private TextView publicDictionaryName;
    private SwipeRefreshLayout wordGroupsSwipeRefreshLayout;
    private RecyclerView wordGroupsRecycler;
    private PublicWordGroupsAdapter publicWordGroupsAdapter;
    private PublicWordGroupsPresenter publicWordGroupsPresenter;

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
        publicDictionaryName = view.findViewById(R.id.publicDictionaryName);
        publicDictionaryFavoriteButton = view.findViewById(R.id.publicDictionaryFavoriteButton);
        wordGroupsSwipeRefreshLayout = view.findViewById(R.id.publicWordGroupsSwipeRefreshLayout);
        wordGroupsRecycler = view.findViewById(R.id.publicWordGroupsRecycler);
    }

    private void init() {
        publicWordGroupsPresenter = new PublicWordGroupsPresenter(
                this, Global.wordGroupRepository(), Global.threadExecutor());
        setDictionary();
        publicDictionaryFavoriteButton.setOnClickListener(this::onFavoriteButtonClick);
        setWordGroupsRecycler();
        setWordGroupsSwipeRefreshLayout();
        updateWordGroupsList();
    }

    private void onFavoriteButtonClick(View view) {
        // Todo check this public vocabulary dictionary has been added to favorite dictionary
        Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
    }

    private Dictionary dictionary;

    private void setDictionary() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            dictionary = (Dictionary) bundle.getSerializable("VocabNoteObjects");
            publicDictionaryName.setText(dictionary.getTitle());
        }
    }

    private List<WordGroup> wordGroupsList = new ArrayList<>();

    private void setWordGroupsRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
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
                    updateWordGroupsList();
                }
            }
        });
    }

    private void setWordGroupsSwipeRefreshLayout() {
        wordGroupsSwipeRefreshLayout.measure(0, 0);
        wordGroupsSwipeRefreshLayout.setProgressViewOffset(true, 80, 90);
        wordGroupsSwipeRefreshLayout.setOnRefreshListener(this::updateWordGroupsList);
    }

    private void updateWordGroupsList() {
        setWordGroupSwipeRefreshLayoutEnable(true);
        int wordGroupListSize = wordGroupsList.size();
        publicWordGroupsPresenter.getWordGroups(
                dictionary.getId(), wordGroupListSize, wordGroupListSize + 3);
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

}
