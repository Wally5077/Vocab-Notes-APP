package com.home.englishnote.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.home.englishnote.R;
import com.home.englishnote.models.entities.Word;
import com.home.englishnote.models.entities.WordGroup;
import com.home.englishnote.presenters.WordsPresenter;
import com.home.englishnote.presenters.WordsPresenter.WordsView;
import com.home.englishnote.utils.Global;

import java.util.ArrayList;
import java.util.List;

public class WordsFragment extends DictionaryFragment implements WordsView {

    private WordGroup wordGroup;
    private RecyclerView wordGroupRecycler;
    private WordAdapter wordGroupAdapter;
    private WordsPresenter wordsPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_words, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        init();
    }

    private void findViews(View view) {
        wordGroupRecycler = view.findViewById(R.id.wordRecycler);
    }

    private void init() {
        wordsPresenter = new WordsPresenter(
                this, Global.wordRepository(), Global.threadExecutor());
        setWordGroup();
        setWordsRecycler();
        updateWordsList();
    }

    private void setWordGroup() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            wordGroup = (WordGroup) bundle.getSerializable("VocabNoteObjects");
        }
    }

    private List<Word> wordList = new ArrayList<>();

    private void setWordsRecycler() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mainPageActivity, 3);
        wordGroupRecycler.setHasFixedSize(true);
        wordGroupRecycler.setLayoutManager(gridLayoutManager);
        wordGroupAdapter = new WordAdapter(wordList);
        wordGroupRecycler.setAdapter(wordGroupAdapter);
    }

    private void updateWordsList() {
        if (wordGroup != null) {
            wordList.addAll(wordGroup.getWords());
        }
    }

    @Override
    public void onGetWordSuccessfully(Word word) {

    }

    public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordHolder> {

        private List<Word> wordList;

        public WordAdapter(List<Word> wordList) {
            this.wordList = wordList;
        }

        @NonNull
        @Override
        public WordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_word, parent, false);
            return new WordHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull WordHolder holder, int position) {
            holder.setData(wordList.get(position));
        }

        @Override
        public int getItemCount() {
            return wordList.size();
        }

        private class WordHolder extends RecyclerView.ViewHolder {

            private ImageView wordItemImage;
            private TextView wordItemName;
            private TextView wordItemSynonym;

            public WordHolder(@NonNull View itemView) {
                super(itemView);
                findViews(itemView);
            }

            private void findViews(View view) {
                wordItemImage = view.findViewById(R.id.wordItemImage);
                wordItemName = view.findViewById(R.id.wordItemName);
                wordItemSynonym = view.findViewById(R.id.wordItemSynonym);
            }

            public void setData(Word word) {
                Glide.with(WordsFragment.this)
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
}
