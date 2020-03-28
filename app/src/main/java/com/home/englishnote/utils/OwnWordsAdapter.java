package com.home.englishnote.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.home.englishnote.R;
import com.home.englishnote.models.entities.Word;

import java.util.ArrayList;
import java.util.List;

public class OwnWordsAdapter extends RecyclerView.Adapter<OwnWordsAdapter.OwnWordsHolder> {

    private Context context;
    private List<Word> minWordList;
    private List<Word> maxWordList;
    private List<Word> wordList;

    public OwnWordsAdapter(List<Word> wordList) {
        this.maxWordList = wordList;
        this.minWordList = new ArrayList<>(3);
        setMinWordList(wordList);
    }

    private void setMinWordList(List<Word> wordList) {
        for (int index = 0; index < 3; index++) {
            minWordList.add(wordList.get(index));
        }
        this.wordList = minWordList;
    }

    @NonNull
    @Override
    public OwnWordsAdapter.OwnWordsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = (context == null) ? parent.getContext() : context;
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_word, parent, false);
        return new OwnWordsAdapter.OwnWordsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnWordsAdapter.OwnWordsHolder holder, int position) {
        holder.setData(wordList.get(position));
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }

    public class OwnWordsHolder extends RecyclerView.ViewHolder {

        private ImageView wordItemImage;
        private TextView wordItemName;
        private TextView wordItemSynonym;

        public OwnWordsHolder(@NonNull View itemView) {
            super(itemView);
            findViews(itemView);
            itemView.setOnClickListener(this::onWordsRecyclerClick);
        }

        private boolean isWordsRecyclerClick = false;

        private void onWordsRecyclerClick(View v) {
            isWordsRecyclerClick = !isWordsRecyclerClick;
            wordList = (isWordsRecyclerClick) ? maxWordList : minWordList;
            notifyDataSetChanged();
        }

        private void findViews(View view) {
            wordItemImage = view.findViewById(R.id.wordItemImage);
            wordItemName = view.findViewById(R.id.wordItemName);
            wordItemSynonym = view.findViewById(R.id.wordItemSynonym);
        }

        public void setData(Word word) {
            Glide.with(context)
                    .asBitmap()
                    .load(word.getImageUrl())
                    .error(R.drawable.apple)
                    .into(wordItemImage);
            wordItemName.setText(word.getName());
            String wordSynonyms = "( " + word.getSynonyms() + " )";
            wordItemSynonym.setText(wordSynonyms);
        }
    }

}
