package com.home.englishnote.views.fragments.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class OwnDictionariesFragment extends BaseFragment implements OwnDictionariesView {

    private OwnDictionariesPresenter ownDictionariesPresenter;
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

    private void findViews(View view) {
        ownDictionariesSwipeRefreshLayout = view.findViewById(R.id.ownDictionariesSwipeRefreshLayout);
        ownDictionariesRecycler = view.findViewById(R.id.ownDictionariesRecycler);
    }

    private void init() {
        ownDictionariesPresenter = new OwnDictionariesPresenter(
                this, Global.memberRepository(), Global.threadExecutor());
        setDictionariesSwipeRefreshLayout();
        setDictionariesRecycler();
        updateDictionaryList();
    }

    private void setDictionariesSwipeRefreshLayout() {
        ownDictionariesSwipeRefreshLayout.measure(0, 0);
        ownDictionariesSwipeRefreshLayout.setProgressViewOffset(true, 80, 90);
        ownDictionariesSwipeRefreshLayout.setOnRefreshListener(this::updateDictionaryList);
    }

    private List<Dictionary> dictionaryList = new ArrayList<>();

    private void setDictionariesRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainPageActivity);
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
                    updateDictionaryList();
                }
            }
        });
    }

    private void updateDictionaryList() {
        setDictionariesSwipeRefreshLayoutEnable(true);
        int dictionaryListSize = dictionaryList.size();
        ownDictionariesPresenter.getOwnDictionaries(
                member.getId(), dictionaryListSize, dictionaryListSize + 3);
    }

    private void setDictionariesSwipeRefreshLayoutEnable(boolean enable) {
        ownDictionariesSwipeRefreshLayout.setEnabled(enable);
        ownDictionariesSwipeRefreshLayout.setRefreshing(enable);
    }

    @Override
    public void onGetDictionariesSuccessfully(List<Dictionary> dictionaryList) {
        setDictionariesSwipeRefreshLayoutEnable(false);
        this.dictionaryList.addAll(dictionaryList);
        ownDictionariesAdapter.notifyDataSetChanged();
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
