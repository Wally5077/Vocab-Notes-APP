package com.home.englishnote.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.home.englishnote.R;
import com.home.englishnote.models.entities.Word;
import com.home.englishnote.models.entities.WordGroup;

import java.util.LinkedList;
import java.util.List;

public class OwnWordGroupsAdapter extends RecyclerView.Adapter<OwnWordGroupsAdapter.OwnWordGroupsHolder> {

    private List<WordGroup> wordGroupsList;
    private Context context;
    private List<OwnWordGroupsHolder> ownWordGroupsHolderList = new LinkedList<>();

    public OwnWordGroupsAdapter(List<WordGroup> wordGroupsList) {
        this.wordGroupsList = wordGroupsList;
    }

    @NonNull
    @Override
    public OwnWordGroupsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = (context == null) ? parent.getContext() : context;
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_own_word_group, parent, false);
        return new OwnWordGroupsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnWordGroupsHolder holder, int position) {
        ownWordGroupsHolderList.add(holder);
        WordGroup wordGroup = wordGroupsList.get(position);
        holder.setData(wordGroup);
    }

    @Override
    public int getItemCount() {
        return wordGroupsList.size();
    }

    public void setEditButtonClick(boolean isEditButtonClick) {
        for (OwnWordGroupsHolder ownWordGroupsHolder : ownWordGroupsHolderList) {
            ownWordGroupsHolder.setDeleteButtonEnable(isEditButtonClick);
        }
    }

    public class OwnWordGroupsHolder extends RecyclerView.ViewHolder {

        private TextView ownWordGroupTitle;
        private RecyclerView ownWordsRecycler;
        private ImageView ownWordGroupDelete;

        public OwnWordGroupsHolder(@NonNull View itemView) {
            super(itemView);
            findViews(itemView);
        }

        private void findViews(View itemView) {
            ownWordGroupTitle = itemView.findViewById(R.id.ownWordGroupTitle);
            ownWordsRecycler = itemView.findViewById(R.id.ownWordsRecycler);
            ownWordGroupDelete = itemView.findViewById(R.id.ownWordGroupDelete);
        }

        private List<Word> wordList;

        private void setData(WordGroup wordGroup) {
            ownWordGroupTitle.setText(wordGroup.getTitle());
            wordList = wordGroup.getWords();
            setWordsRecycler();
            ownWordGroupDelete.setOnClickListener(v -> {
                wordGroupsList.remove(wordGroup);
                ownWordGroupsHolderList.remove(OwnWordGroupsHolder.this);
                notifyDataSetChanged();
            });
        }

        private void setDeleteButtonEnable(boolean enable) {
            ownWordGroupDelete.setVisibility((enable) ? View.VISIBLE : View.INVISIBLE);
            ownWordGroupDelete.setFocusable(enable);
        }

        private void setWordsRecycler() {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
            ownWordsRecycler.setHasFixedSize(true);
            ownWordsRecycler.setLayoutManager(gridLayoutManager);
            OwnWordsAdapter ownWordsAdapter = new OwnWordsAdapter(wordList);
            ownWordsRecycler.setAdapter(ownWordsAdapter);
        }
    }
}
