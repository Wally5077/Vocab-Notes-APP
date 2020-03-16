package com.home.englishnote.views.fragments.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.home.englishnote.R;
import com.home.englishnote.models.entities.Dictionary;
import com.home.englishnote.views.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class FavoriteDictionariesFragment extends BaseFragment {

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
        setDictionariesRecycler();
    }

    private List<Dictionary> dictionaryList = new ArrayList<>();

    private void setDictionariesRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainPageActivity);
        favoriteDictionariesRecycler.setHasFixedSize(true);
        favoriteDictionariesRecycler.setLayoutManager(linearLayoutManager);
        favoriteDictionariesAdapter = new FavoriteDictionariesAdapter(dictionaryList);
        favoriteDictionariesRecycler.setAdapter(favoriteDictionariesAdapter);
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
        }

        @Override
        public int getItemCount() {
            return dictionaryList.size();
        }

        public class FavoriteDictionariesHolder extends RecyclerView.ViewHolder {

            private TextView dictionaryName;
            private TextView favoriteWordGroups;
            private Dictionary dictionary;

            public FavoriteDictionariesHolder(@NonNull View itemView) {
                super(itemView);
                findViews(itemView);
            }

            private void findViews(@NonNull View itemView) {
                dictionaryName = itemView.findViewById(R.id.favoriteDictionaryName);
                favoriteWordGroups = itemView.findViewById(R.id.favoriteWordGroups);
            }

            public void setData(Dictionary dictionary) {
                this.dictionary = dictionary;
                dictionaryName.setText(dictionary.getTitle());
            }
        }
    }

}
