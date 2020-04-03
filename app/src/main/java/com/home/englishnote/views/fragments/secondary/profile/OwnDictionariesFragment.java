package com.home.englishnote.views.fragments.secondary.profile;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.home.englishnote.R;
import com.home.englishnote.models.entities.Dictionary;
import com.home.englishnote.models.entities.WordGroup;
import com.home.englishnote.presenters.OwnDictionariesPresenter;
import com.home.englishnote.presenters.OwnDictionariesPresenter.OwnDictionariesView;
import com.home.englishnote.presenters.OwnWordGroupsPresenter;
import com.home.englishnote.presenters.OwnWordGroupsPresenter.OwnWordGroupsView;
import com.home.englishnote.utils.Global;
import com.home.englishnote.views.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OwnDictionariesFragment extends BaseFragment implements OwnDictionariesView {

    private OwnDictionariesPresenter ownDictionariesPresenter;
    private AutoCompleteTextView ownDictionariesQuery;
    private SwipeRefreshLayout ownDictionariesSwipeRefreshLayout;
    private RecyclerView ownDictionariesRecycler;
    private OwnDictionariesAdapter ownDictionariesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater
                .inflate(R.layout.fragment_own_dictionaries, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        init();
    }

    @Override
    public void updateFragmentData() {
        dictionaryList.clear();
        queryDictionaryList();
    }

    private void findViews(View view) {
        ownDictionariesSwipeRefreshLayout = view.findViewById(R.id.ownDictionariesSwipeRefreshLayout);
        ownDictionariesRecycler = view.findViewById(R.id.ownDictionariesRecycler);
        ownDictionariesQuery = view.findViewById(R.id.ownDictionariesQuery);
    }

    private void init() {
        ownDictionariesPresenter = new OwnDictionariesPresenter(
                this, Global.memberRepository(), Global.threadExecutor());
        dictionaryList.clear();
        initDictionariesSwipeRefreshLayout();
        initDictionariesRecycler();
        initOwnDictionaryQuery();
        queryDictionaryList();
    }

    private void initDictionariesSwipeRefreshLayout() {
        ownDictionariesSwipeRefreshLayout.measure(0, 0);
        ownDictionariesSwipeRefreshLayout.setProgressViewOffset(true, 80, 90);
        ownDictionariesSwipeRefreshLayout.setOnRefreshListener(this::queryDictionaryList);
    }

    private List<Dictionary> dictionaryList = new ArrayList<>();

    private void initDictionariesRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(dictionaryHomePageActivity);
        ownDictionariesRecycler.setHasFixedSize(true);
        ownDictionariesRecycler.setLayoutManager(linearLayoutManager);
        ownDictionariesAdapter = new OwnDictionariesAdapter(dictionaryList);
        ownDictionariesRecycler.setAdapter(ownDictionariesAdapter);
        ownDictionariesRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastVisibleItemPosition =
                        linearLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == ownDictionariesAdapter.getItemCount()) {
                    if (!isPullToRefreshTriggered) {
                        isPullToRefreshTriggered = true;
                        queryDictionaryList();
                    }
                }
            }
        });
    }

    private void initOwnDictionaryQuery() {
        ownDictionariesQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String filterPattern = s.toString().toLowerCase().trim();
                if (count == 0) {
                    ownDictionariesAdapter.updateDictionaryList(dictionaryList);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        ownDictionariesAdapter.updateDictionaryList(dictionaryList.stream()
                                .filter(dictionary -> dictionary.getTitle().contains(filterPattern))
                                .collect(Collectors.toList()));
                    }
                }
                ownDictionariesAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private static final int PAGE_LIMIT = 10;

    private void queryDictionaryList() {
        setDictionariesSwipeRefreshLayoutEnable(true);
        int dictionaryListSize = dictionaryList.size();
        ownDictionariesPresenter.getOwnDictionaries(
                user.getId(), dictionaryListSize, PAGE_LIMIT);
    }

    private boolean isPullToRefreshTriggered = false;

    @Override
    public void onGetDictionariesSuccessfully(List<Dictionary> dictionaryList) {
        isPullToRefreshTriggered = false;
        setDictionariesSwipeRefreshLayoutEnable(false);
        this.dictionaryList.addAll(dictionaryList);
        ownDictionariesAdapter.notifyDataSetChanged();
    }

    private void setDictionariesSwipeRefreshLayoutEnable(boolean enable) {
        ownDictionariesSwipeRefreshLayout.setEnabled(enable);
        ownDictionariesSwipeRefreshLayout.setRefreshing(enable);
    }

    public class OwnDictionariesAdapter extends RecyclerView.Adapter<OwnDictionariesAdapter.OwnDictionariesHolder> {

        private List<Dictionary> dictionaryList;
        private Context context;

        public OwnDictionariesAdapter(List<Dictionary> dictionaryList) {
            this.dictionaryList = dictionaryList;
        }

        @NonNull
        @Override
        public OwnDictionariesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            context = (context == null) ? parent.getContext() : context;
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.item_own_dictionary, parent, false);
            return new OwnDictionariesHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull OwnDictionariesHolder holder, int position) {
            Dictionary dictionary = dictionaryList.get(position);
            holder.setData(dictionary);
            new OwnWordGroupsPresenter(
                    holder, Global.wordGroupRepository(), Global.threadExecutor())
                    .getWordGroups(dictionary.getOwnId(), 0, -1);
        }

        @Override
        public int getItemCount() {
            return dictionaryList.size();
        }

        public void updateDictionaryList(List<Dictionary> dictionaryList) {
            this.dictionaryList = dictionaryList;
        }

        public class OwnDictionariesHolder
                extends RecyclerView.ViewHolder implements OwnWordGroupsView {

            private TextView ownDictionaryName;
            private TextView ownWordGroups;

            public OwnDictionariesHolder(@NonNull View itemView) {
                super(itemView);
                findViews(itemView);
                itemView.setOnClickListener(this::switchOwnDictionaryPage);
            }

            private void findViews(View view) {
                ownDictionaryName = view.findViewById(R.id.ownDictionaryTitle);
                ownWordGroups = view.findViewById(R.id.ownWordGroups);
            }

            private Dictionary dictionary;

            public void setData(Dictionary dictionary) {
                this.dictionary = dictionary;
                ownDictionaryName.setText(dictionary.getTitle());
            }

            private void switchOwnDictionaryPage(View v) {
                switchFragment(R.layout.fragment_own_dictionary_page,
                        R.id.dictionaryHomePageContainer, dictionary);
            }

            @Override
            public void onGetWordGroupsSuccessfully(List<WordGroup> wordGroupList) {
                String wordGroupSize = wordGroupList.size() + " Word groups";
                ownWordGroups.setText(wordGroupSize);
            }
        }
    }
}
