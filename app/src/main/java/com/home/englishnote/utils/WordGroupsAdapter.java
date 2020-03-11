package com.home.englishnote.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.home.englishnote.R;
import com.home.englishnote.models.entities.Word;
import com.home.englishnote.models.entities.WordGroup;
import com.home.englishnote.utils.WordGroupsAdapter.WordGroupsHolder;

import java.util.ArrayList;
import java.util.List;

public class WordGroupsAdapter extends Adapter<WordGroupsHolder> {

    private List<WordGroup> wordGroupsList;
    private Context context;

    public WordGroupsAdapter(List<WordGroup> wordGroupsList) {
        this.wordGroupsList = wordGroupsList;
    }

    @NonNull
    @Override
    public WordGroupsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
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

    public class WordGroupsHolder extends RecyclerView.ViewHolder {

        private TextView publicWordGroupTitle;
        private Button publicWordGroupFavoriteButton;
        private RecyclerView publicWordsRecycler;
        private WordsAdapter wordsAdapter;

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

        private List<Word> maxWordList;

        private void setData(WordGroup wordGroup) {
            publicWordGroupTitle.setText(wordGroup.getTitle());
            maxWordList = wordGroup.getWords();
            setMinWordList(maxWordList);
            setWordsRecycler();
            publicWordsRecycler.setOnClickListener(this::onWordsRecyclerClick);
        }

        private List<Word> minWordList = new ArrayList<>(3);

        private void setMinWordList(List<Word> maxWordList) {
            for (int index = 0; index < 3; index++) {
                minWordList.add(maxWordList.get(index));
            }
        }

        private void setWordsRecycler() {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
            publicWordsRecycler.setHasFixedSize(true);
            publicWordsRecycler.setLayoutManager(gridLayoutManager);
            wordsAdapter = new WordsAdapter(minWordList);
            publicWordsRecycler.setAdapter(wordsAdapter);
        }

        private boolean isWordsRecyclerClick = false;

        private void onWordsRecyclerClick(View v) {
            isWordsRecyclerClick = !isWordsRecyclerClick;
            wordsAdapter
                    .updateWordList((isWordsRecyclerClick) ? maxWordList : minWordList);
        }
    }
}

