package com.home.englishnote.utils;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import com.home.englishnote.R;
import com.home.englishnote.models.entities.Word;
import com.home.englishnote.models.entities.WordGroup;
import com.home.englishnote.utils.PublicWordGroupsAdapter.WordGroupsHolder;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class PublicWordGroupsAdapter extends Adapter<WordGroupsHolder> {

    private List<WordGroup> wordGroupList;
    private Context context;

    public PublicWordGroupsAdapter(List<WordGroup> wordGroupList) {
        this.wordGroupList = wordGroupList;
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
        WordGroup wordGroup = wordGroupList.get(position);
        holder.setData(wordGroup);
    }

    @Override
    public int getItemCount() {
        return wordGroupList.size();
    }

    public void updateWordGroupList(List<WordGroup> wordGroupList) {
        this.wordGroupList = wordGroupList;
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
            LayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
            publicWordsRecycler.setHasFixedSize(true);
            publicWordsRecycler.setLayoutManager(gridLayoutManager);
            PublicWordsAdapter publicWordsAdapter = new PublicWordsAdapter(wordList);
            publicWordsRecycler.setAdapter(publicWordsAdapter);
        }
    }
}

