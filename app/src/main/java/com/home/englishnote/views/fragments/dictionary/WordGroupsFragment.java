package com.home.englishnote.views.fragments.dictionary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.home.englishnote.R;
import com.home.englishnote.models.entities.Dictionary;
import com.home.englishnote.models.entities.Word;
import com.home.englishnote.models.entities.WordGroup;
import com.home.englishnote.presenters.WordGroupsPresenter;
import com.home.englishnote.presenters.WordGroupsPresenter.WordGroupsView;
import com.home.englishnote.utils.Global;
import com.home.englishnote.utils.VocabularyNoteKeyword;
import com.home.englishnote.views.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class WordGroupsFragment extends BaseFragment implements WordGroupsView {

    private Dictionary dictionary;
    private RecyclerView wordGroupRecycler;
    private SwipeRefreshLayout wordGroupSwipeRefreshLayout;
    private WordGroupAdapter wordGroupAdapter;
    private WordGroupsPresenter wordGroupsPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_word_group, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        init();
    }

    private void findViews(View view) {
        wordGroupRecycler = view.findViewById(R.id.wordGroupRecycler);
        wordGroupSwipeRefreshLayout = view.findViewById(R.id.dictionaryContentSwipeRefreshLayout);
    }

    private void init() {
        wordGroupsPresenter = new WordGroupsPresenter(
                this, Global.wordGroupRepository(), Global.threadExecutor());
        setDictionary();
        setWordGroupRecycler();
        setWordGroupSwipeRefreshLayout();
        updateWordGroupsList();
    }

    private void setDictionary() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            dictionary = (Dictionary) bundle.getSerializable("VocabNoteObjects");
        }
    }

    private List<WordGroup> wordGroupList = new ArrayList<>();

    private void setWordGroupRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainPageActivity);
        wordGroupRecycler.setHasFixedSize(true);
        wordGroupRecycler.setLayoutManager(linearLayoutManager);
        wordGroupAdapter = new WordGroupAdapter(wordGroupList);
        wordGroupRecycler.setAdapter(wordGroupAdapter);
        wordGroupRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastVisibleItemPosition =
                        linearLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == wordGroupAdapter.getItemCount()) {
                    updateWordGroupsList();
                }
            }
        });
    }

    private void setWordGroupSwipeRefreshLayout() {
        wordGroupSwipeRefreshLayout.measure(0, 0);
        wordGroupSwipeRefreshLayout.setProgressViewOffset(true, 80, 90);
        wordGroupSwipeRefreshLayout.setOnRefreshListener(this::updateWordGroupsList);
    }

    private void updateWordGroupsList() {
        setWordGroupSwipeRefreshLayoutEnable(true);
        int wordGroupListSize = wordGroupList.size();
        wordGroupListSize = (wordGroupListSize == 0) ? wordGroupListSize : ++wordGroupListSize;
        wordGroupsPresenter.getWordGroups(
                dictionary.getId(), wordGroupListSize, wordGroupListSize + 3);
    }

    private void setWordGroupSwipeRefreshLayoutEnable(boolean enable) {
        wordGroupSwipeRefreshLayout.setEnabled(enable);
        wordGroupSwipeRefreshLayout.setRefreshing(enable);
    }

    @Override
    public void onGetWordGroupsSuccessfully(List<WordGroup> wordGroupList) {
        // Todo might be over all dictionary count
        setWordGroupSwipeRefreshLayoutEnable(false);
        this.wordGroupList.clear();
        this.wordGroupList.addAll(wordGroupList);
        wordGroupAdapter.notifyDataSetChanged();
    }

    // Todo
    public class WordGroupAdapter extends RecyclerView.Adapter<WordGroupAdapter.WordGroupHolder> {

        private List<WordGroup> wordGroupList;

        public WordGroupAdapter(List<WordGroup> wordGroupList) {
            this.wordGroupList = wordGroupList;
        }

        @NonNull
        @Override
        public WordGroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater()
                    .inflate(R.layout.item_word_group, parent, false);
            return new WordGroupHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull WordGroupHolder holder, int position) {
            WordGroup wordGroup = wordGroupList.get(position);
            holder.setData(wordGroup);
            holder.setHolderClick(wordGroup);
        }

        @Override
        public int getItemCount() {
            return wordGroupList.size();
        }

        private class WordGroupHolder extends RecyclerView.ViewHolder {

            private TextView wordGroupTitle;
            private ImageView wordImage1;
            private ImageView wordImage2;
            private ImageView wordImage3;
            private TextView wordName1;
            private TextView wordName2;
            private TextView wordName3;
            private TextView wordSynonym1;
            private TextView wordSynonym2;
            private TextView wordSynonym3;
            private View itemView;

            public WordGroupHolder(@NonNull View itemView) {
                super(itemView);
                findViews(itemView);
                this.itemView = itemView;
            }

            private void findViews(View itemView) {
                // Todo add in button
                wordGroupTitle = itemView.findViewById(R.id.wordGroupTitle);
                wordImage1 = itemView.findViewById(R.id.WordImage1);
                wordImage2 = itemView.findViewById(R.id.WordImage2);
                wordImage3 = itemView.findViewById(R.id.WordImage3);
                wordName1 = itemView.findViewById(R.id.WordName1);
                wordName2 = itemView.findViewById(R.id.WordName2);
                wordName3 = itemView.findViewById(R.id.WordName3);
                wordSynonym1 = itemView.findViewById(R.id.WordSynonym1);
                wordSynonym2 = itemView.findViewById(R.id.WordSynonym2);
                wordSynonym3 = itemView.findViewById(R.id.WordSynonym3);
            }

            private void setData(WordGroup wordGroup) {
                wordGroupTitle.setText(wordGroup.getTitle());
                List<Word> wordList = wordGroup.getWords();
                setWordView(wordList.get(0), wordImage1, wordName1, wordSynonym1);
                setWordView(wordList.get(1), wordImage2, wordName2, wordSynonym2);
                setWordView(wordList.get(2), wordImage3, wordName3, wordSynonym3);
            }

            private void setWordView(Word word, ImageView wordImage,
                                     TextView wordName, TextView wordSynonym) {
                Glide.with(WordGroupsFragment.this)
                        .asBitmap()
                        .load(word.getImageUrl())
                        .error(R.drawable.pear_hydrosol_pyrus_communis_hydrolat_product_pic)
                        .into(wordImage);
                wordName.setText(word.getName());
                String wordSynonyms = "( " + word.getSynonyms() + " )";
                wordSynonym.setText(wordSynonyms);
            }

            public void setHolderClick(WordGroup wordGroup) {
                mainPageActivity.switchFragment(R.layout.fragment_words,
                        VocabularyNoteKeyword.DICTIONARY_CONTENT_CONTAINER, wordGroup);
            }
        }
    }
}
