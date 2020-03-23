package com.home.englishnote.utils;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.home.englishnote.R;
import com.home.englishnote.models.entities.Word;
import com.home.englishnote.models.entities.WordGroup;
import com.home.englishnote.utils.PublicWordGroupsAdapter.WordGroupsHolder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PublicWordGroupsAdapter extends Adapter<WordGroupsHolder> {

    private List<WordGroup> wordGroupsList;
    private Context context;

    public PublicWordGroupsAdapter(List<WordGroup> wordGroupsList) {
        this.wordGroupsList = wordGroupsList;
    }

    @NonNull
    @Override
    public WordGroupsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = (context == null) ? parent.getContext() : context;
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_public_word_group, parent, false);
        return new WordGroupsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordGroupsHolder holder, int position) {
        WordGroup wordGroup = wordGroupsList.get(position);
        holder.setData(wordGroup);
    }

    @Override
    public int getItemCount() {
        return wordGroupsList.size();
    }


    public void updateWordGroupsList(List<WordGroup> wordGroupsList) {
        this.wordGroupsList = wordGroupsList;
    }

    public class WordGroupsHolder extends RecyclerView.ViewHolder {

        private TextView publicWordGroupTitle;
        private Button publicWordGroupFavoriteButton;
        private RecyclerView publicWordsRecycler;

        public WordGroupsHolder(@NonNull View itemView) {
            super(itemView);
            findViews(itemView);
        }

        private void findViews(View itemView) {
            // Todo add in button
            publicWordGroupTitle = itemView.findViewById(R.id.publicWordGroupTitle);
            publicWordGroupFavoriteButton = itemView.findViewById(R.id.publicWordGroupFavoriteButton);
            publicWordsRecycler = itemView.findViewById(R.id.publicWordsRecycler);
        }

        private List<Word> wordList;

        private void setData(WordGroup wordGroup) {
            publicWordGroupTitle.setText(wordGroup.getTitle());
            wordList = wordGroup.getWords();
            setWordsRecycler();
        }

        private void setWordsRecycler() {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
            publicWordsRecycler.setHasFixedSize(true);
            publicWordsRecycler.setLayoutManager(gridLayoutManager);
            PublicWordsAdapter publicWordsAdapter = new PublicWordsAdapter(wordList);
            publicWordsRecycler.setAdapter(publicWordsAdapter);
        }
    }
}

