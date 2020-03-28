package com.home.englishnote.views.fragments.profile;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.home.englishnote.R;
import com.home.englishnote.models.entities.Dictionary;
import com.home.englishnote.models.entities.WordGroup;
import com.home.englishnote.presenters.FavoriteDictionariesPresenter;
import com.home.englishnote.presenters.FavoriteWordGroupsPresenter;
import com.home.englishnote.presenters.FavoriteWordGroupsPresenter.FavoriteWordGroupsView;
import com.home.englishnote.utils.Global;
import com.home.englishnote.views.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class FavoriteDictionariesFragment extends BaseFragment
        implements FavoriteDictionariesPresenter.FavoriteDictionariesView {

    private FavoriteDictionariesPresenter favoriteDictionariesPresenter;
    private RecyclerView favoriteDictionariesRecycler;
    private FavoriteDictionariesAdapter favoriteDictionariesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater
                .inflate(R.layout.fragment_favorite_dictionaries, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        init();
    }

    private void findViews(View view) {
        favoriteDictionariesRecycler = view.findViewById(R.id.favoriteDictionariesRecycler);
    }

    private void init() {
        favoriteDictionariesPresenter = new FavoriteDictionariesPresenter(
                this, Global.memberRepository(), Global.threadExecutor());
        setDictionariesRecycler();
        downloadFavoriteDictionaryList();
    }

    private List<Dictionary> dictionaryList = new ArrayList<>();

    private void setDictionariesRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(dictionaryHomePageActivity);
        favoriteDictionariesRecycler.setHasFixedSize(true);
        favoriteDictionariesRecycler.setLayoutManager(linearLayoutManager);
        favoriteDictionariesAdapter = new FavoriteDictionariesAdapter(dictionaryList);
        favoriteDictionariesRecycler.setAdapter(favoriteDictionariesAdapter);
    }

    private void downloadFavoriteDictionaryList() {
        int dictionaryListSize = dictionaryList.size();
        favoriteDictionariesPresenter.getOwnDictionaries(
                member.getId(), dictionaryListSize, dictionaryListSize + 30);
    }

    @Override
    public void onGetDictionariesSuccessfully(List<Dictionary> dictionaryList) {
        this.dictionaryList.addAll(dictionaryList);
        favoriteDictionariesAdapter.notifyDataSetChanged();
    }

    public class FavoriteDictionariesAdapter extends RecyclerView.Adapter<FavoriteDictionariesAdapter.FavoriteDictionariesHolder> {

        private List<Dictionary> dictionaryList;
        private Context context;

        public FavoriteDictionariesAdapter(List<Dictionary> dictionaryList) {
            this.dictionaryList = dictionaryList;
        }

        @NonNull
        @Override
        public FavoriteDictionariesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            context = (context == null) ? parent.getContext() : context;
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.item_favorite_dictionary, parent, false);
            return new FavoriteDictionariesHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FavoriteDictionariesHolder holder, int position) {
            Dictionary dictionary = dictionaryList.get(position);
            holder.setData(dictionary);
            new FavoriteWordGroupsPresenter(
                    holder, Global.wordGroupRepository(), Global.threadExecutor())
                    .getWordGroups(dictionary.getOwnId(), 0, -1);
        }

        @Override
        public int getItemCount() {
            return dictionaryList.size();
        }

        public class FavoriteDictionariesHolder extends RecyclerView.ViewHolder
                implements FavoriteWordGroupsView {

            private TextView favoriteDictionaryName;
            private TextView favoriteWordGroups;

            public FavoriteDictionariesHolder(@NonNull View itemView) {
                super(itemView);
                findViews(itemView);
            }

            private void findViews(@NonNull View itemView) {
                favoriteDictionaryName = itemView.findViewById(R.id.favoriteDictionaryName);
                favoriteWordGroups = itemView.findViewById(R.id.favoriteWordGroups);
            }

            private Dictionary dictionary;

            public void setData(Dictionary dictionary) {
                this.dictionary = dictionary;
                favoriteDictionaryName.setText(dictionary.getTitle());
            }

            @Override
            public void onGetWordGroupsSuccessfully(List<WordGroup> wordGroupList) {
                String wordGroupSize = wordGroupList.size() + " Word groups";
                favoriteWordGroups.setText(wordGroupSize);
            }
        }
    }

}
