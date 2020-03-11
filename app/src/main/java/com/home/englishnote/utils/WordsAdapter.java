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

import java.util.List;

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.WordsHolder> {

    private List<Word> wordList;
    private Context context;

    public WordsAdapter(List<Word> wordList) {
        this.wordList = wordList;
    }

    @NonNull
    @Override
    public WordsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_public_word, parent, false);
        return new WordsAdapter.WordsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordsHolder holder, int position) {
        holder.setData(wordList.get(position));
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }

    public void updateWordList(List<Word> wordList) {
        this.wordList = wordList;
        notifyDataSetChanged();
    }

    public class WordsHolder extends RecyclerView.ViewHolder {

        private ImageView wordItemImage;
        private TextView wordItemName;
        private TextView wordItemSynonym;

        public WordsHolder(@NonNull View itemView) {
            super(itemView);
            findViews(itemView);
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
                    .error(R.drawable.china_fresh_sweet_delicious_red_fruit_apples_jpg_350_x_350)
                    .into(wordItemImage);
            wordItemName.setText(word.getName());
            String wordSynonyms = "( " + word.getSynonyms() + " )";
            wordItemSynonym.setText(wordSynonyms);
        }
    }
}
