package com.home.englishnote.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.home.englishnote.R;
import com.home.englishnote.models.entities.Dictionary;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DictionarySearchAdapter extends RecyclerView.Adapter<DictionarySearchAdapter.DictionarySearchViewHolder> {

    private List<Dictionary> dictionaryList;
    private Set<DictionarySearchViewHolder> dictionarySearchViewHolderSet = new HashSet<>();
    private Context context;

    public DictionarySearchAdapter(List<Dictionary> dictionaryList) {
        this.dictionaryList = dictionaryList;
    }

    @NonNull
    @Override
    public DictionarySearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = (context == null) ? parent.getContext() : context;
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_dictionary_search, parent, false);
        return new DictionarySearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DictionarySearchViewHolder holder, int position) {
        dictionarySearchViewHolderSet.add(holder);
        holder.setData(dictionaryList.get(position));
    }

    @Override
    public int getItemCount() {
        return dictionaryList.size();
    }

    public void setDefaultItemBackground() {
        for (DictionarySearchViewHolder dictionarySearchViewHolder : dictionarySearchViewHolderSet) {
            dictionarySearchViewHolder.setDictionaryItemBackgroundEnable(false);
        }
    }

    public class DictionarySearchViewHolder extends RecyclerView.ViewHolder {

        private View dictionaryItemBackground;
        private TextView dictionaryItemName;

        public DictionarySearchViewHolder(@NonNull View itemView) {
            super(itemView);
            findViews(itemView);
            setOnItemClick(itemView);
        }

        private void findViews(View itemView) {
            dictionaryItemBackground = itemView.findViewById(R.id.dictionaryItemBackground);
            dictionaryItemName = itemView.findViewById(R.id.dictionaryItemName);
        }

        private void setOnItemClick(@NonNull View itemView) {
            itemView.setOnClickListener(v -> {
//                dictionarySearchViewHolderSet.stream()
//                        .filter(dictionarySearchViewHolder -> !dictionarySearchViewHolder
//                                .equals(DictionarySearchViewHolder.this))
//                        .forEach(dictionarySearchViewHolder -> dictionarySearchViewHolder.
//                                setDictionaryItemBackgroundEnable(false));
                setDictionaryItemBackgroundEnable(true);
            });
        }

        public void setData(Dictionary dictionary) {
            dictionaryItemName.setText(dictionary.getTitle());
        }

        public void setDictionaryItemBackgroundEnable(boolean enable) {
            Drawable drawable = enable ?
                    context.getDrawable(R.drawable.bg_dictionary_item_selected) :
                    context.getDrawable(R.drawable.bg_dictionary_item_unselected);
            dictionaryItemBackground.setBackground(drawable);
        }
    }
}
