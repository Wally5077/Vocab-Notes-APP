package com.home.englishnote.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.home.englishnote.R;
import com.home.englishnote.models.entities.Dictionary;
import com.home.englishnote.presenters.MainPagePresenter;
import com.home.englishnote.presenters.MainPagePresenter.MainPageView;
import com.home.englishnote.utils.Global;

import java.util.ArrayList;
import java.util.List;

public class DictionaryFragment extends BaseFragment implements MainPageView {

    private RecyclerView dictionaryRecycler;
    private DictionaryAdapter dictionaryAdapter;
    private SwipeRefreshLayout dictionarySwipeRefreshLayout;
    private TextView userName;
    private ImageView userPic;
    private MainPagePresenter mainPagePresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_public_dictionary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        init();
    }

    private void findViews(View view) {
        dictionaryRecycler = view.findViewById(R.id.dictionaryRecycler);
        dictionarySwipeRefreshLayout = view.findViewById(R.id.dictionarySwipeRefreshLayout);
        userName = view.findViewById(R.id.mainPageUserName);
        userPic = view.findViewById(R.id.mainPageUserPic);
    }

    private void init() {
        mainPagePresenter = new MainPagePresenter(
                this, Global.dictionaryRepository(), Global.threadExecutor());
        setDictionaryRecycler();
        setDictionarySwipeRefreshLayout();
        String memberName = member.getLastName();
        userName.setText((memberName.isEmpty()) ? "userName" : memberName);
        userPic.setImageResource(R.drawable.user_pic);
    }

    public void onVocabSearchClick(View view) {
    }

    public void onDictionaryListSearchClick(View view) {
    }

    public void onUserPicClick(View view) {
    }

    private List<Dictionary> dictionaryList = new ArrayList<>();

    private void setDictionaryRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainPageActivity);
        dictionaryRecycler.setHasFixedSize(true);
        dictionaryRecycler.setLayoutManager(linearLayoutManager);
        dictionaryAdapter = new DictionaryAdapter(dictionaryList);
        dictionaryRecycler.setAdapter(dictionaryAdapter);
        dictionaryRecycler.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastVisibleItemPosition =
                        linearLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == dictionaryAdapter.getItemCount()) {
                    updateDictionaryList();
                }
            }
        });
    }

    private void setDictionarySwipeRefreshLayout() {
        dictionarySwipeRefreshLayout.setProgressViewOffset(true, 80, 90);
        dictionarySwipeRefreshLayout.setOnRefreshListener(this::updateDictionaryList);
    }

    private void updateDictionaryList() {
        dictionarySwipeRefreshLayout.measure(0, 0);
        dictionarySwipeRefreshLayout.setEnabled(true);
        dictionarySwipeRefreshLayout.setRefreshing(true);
        mainPagePresenter.getDictionaries();
    }

    @Override
    public void onDictionariesSuccessfully(List<Dictionary> dictionaryList) {
        dictionarySwipeRefreshLayout.setEnabled(false);
        dictionarySwipeRefreshLayout.setRefreshing(false);
        this.dictionaryList.clear();
        this.dictionaryList.addAll(dictionaryList);
        dictionaryAdapter.notifyDataSetChanged();
    }

    public class DictionaryAdapter extends Adapter<DictionaryAdapter.DictionaryHolder> {

        private List<Dictionary> dictionaryList;

        public DictionaryAdapter(List<Dictionary> dictionaryList) {
            this.dictionaryList = dictionaryList;
        }

        @NonNull
        @Override
        public DictionaryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater()
                    .inflate(R.layout.item_dictionary, parent, false);
            return new DictionaryHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DictionaryHolder holder, int position) {
            Dictionary dictionary = dictionaryList.get(position);
            holder.setDictionary(dictionary);
        }

        @Override
        public int getItemCount() {
            return dictionaryList.size();
        }

        private class DictionaryHolder extends ViewHolder {

            private TextView dictionaryTitle, dictionaryDescription;
            private ImageView dictionaryImage1, dictionaryImage2;
            private Button exploreButton;

            public DictionaryHolder(@NonNull View itemView) {
                super(itemView);
                findViews(itemView);
                init();
            }

            private void findViews(View itemView) {
                dictionaryTitle = itemView.findViewById(R.id.dictionaryTitle);
                dictionaryDescription = itemView.findViewById(R.id.dictionaryDescription);
                dictionaryImage1 = itemView.findViewById(R.id.dictionaryImage1);
                dictionaryImage2 = itemView.findViewById(R.id.dictionaryImage2);
                exploreButton = itemView.findViewById(R.id.exploreButton);
            }

            private void init() {
                exploreButton.setOnClickListener((v) -> {
                    // Todo
                });
            }

            private void setDictionary(Dictionary dictionary) {
                dictionaryTitle.setText(dictionary.getTitle());
                dictionaryDescription.setText(dictionary.getDescription());
                dictionaryImage1.setImageResource(
                        R.drawable.pear_hydrosol_pyrus_communis_hydrolat_product_pic);
                dictionaryImage2.setImageResource(R.drawable.guava_1);
            }
        }
    }
}
