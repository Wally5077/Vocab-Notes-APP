package com.home.englishnote.views.fragments.secondary.profile;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.home.englishnote.R;
import com.home.englishnote.models.entities.Dictionary;
import com.home.englishnote.models.entities.WordGroup;
import com.home.englishnote.presenters.FavoriteDictionariesPresenter;
import com.home.englishnote.presenters.FavoriteWordGroupsPresenter;
import com.home.englishnote.presenters.FavoriteWordGroupsPresenter.FavoriteWordGroupsView;
import com.home.englishnote.utils.CustomDialog;
import com.home.englishnote.utils.Global;
import com.home.englishnote.views.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.home.englishnote.presenters.FavoriteDictionariesPresenter.*;

public class FavoriteDictionariesFragment extends BaseFragment
        implements FavoriteDictionariesView {

    private FavoriteDictionariesPresenter favoriteDictionariesPresenter;
    private RecyclerView favoriteDictionariesRecycler;
    private FavoriteDictionariesAdapter favoriteDictionariesAdapter;
    private AutoCompleteTextView favoriteDictionariesQuery;

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
    }

    private void findViews(View view) {
        favoriteDictionariesRecycler = view.findViewById(R.id.favoriteDictionariesRecycler);
        favoriteDictionariesQuery = view.findViewById(R.id.favoriteDictionariesQuery);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
        queryFavoriteDictionaryList();
    }

    private void init() {
        favoriteDictionariesPresenter = new FavoriteDictionariesPresenter(
                this, Global.memberRepository(), Global.threadExecutor());
        setUpDictionariesRecycler();
        setUpOwnDictionaryQuery();
    }

    private List<Dictionary> dictionaryList;

    private void setUpDictionariesRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(homePageActivity);
        favoriteDictionariesRecycler.setHasFixedSize(true);
        favoriteDictionariesRecycler.setLayoutManager(linearLayoutManager);
        dictionaryList = new ArrayList<>();
        favoriteDictionariesAdapter = new FavoriteDictionariesAdapter(dictionaryList);
        favoriteDictionariesRecycler.setAdapter(favoriteDictionariesAdapter);
    }

    private void setUpOwnDictionaryQuery() {
        favoriteDictionariesQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String filterPattern = s.toString().toLowerCase().trim();
                if (count == 0) {
                    favoriteDictionariesAdapter.updateDictionaryList(dictionaryList);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        favoriteDictionariesAdapter.updateDictionaryList(dictionaryList.stream()
                                .filter(dictionary -> dictionary.getTitle().contains(filterPattern))
                                .collect(Collectors.toList()));
                    }
                }
                favoriteDictionariesAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void queryFavoriteDictionaryList() {
        int dictionaryListSize = dictionaryList.size();
        favoriteDictionariesPresenter.getOwnDictionaries(
                user.getId(), dictionaryListSize, dictionaryListSize + 30);
    }

    @Override
    public void onGetDictionariesSuccessfully(List<Dictionary> dictionaryList) {
        this.dictionaryList.addAll(dictionaryList);
        favoriteDictionariesAdapter.notifyDataSetChanged();
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
            new FavoriteWordGroupsPresenter(
                    holder, Global.wordGroupRepository(), Global.threadExecutor())
                    .getWordGroups(dictionary.getOwnId(), 0, -1);
        }

        @Override
        public int getItemCount() {
            return dictionaryList.size();
        }

        public void updateDictionaryList(List<Dictionary> dictionaryList) {
            this.dictionaryList = dictionaryList;
        }

        public class FavoriteDictionariesHolder extends RecyclerView.ViewHolder
                implements FavoriteWordGroupsView {

            private TextView favoriteDictionaryName;
            private TextView favoriteWordGroups;

            public FavoriteDictionariesHolder(@NonNull View itemView) {
                super(itemView);
                findViews(itemView);
                itemView.setOnLongClickListener(v -> {
                    new CustomDialog(homePageActivity)
                            .setMessage("Are you sure to unfavorite this dictionary ?")
                            .setDialogButtonLeft("Yes", (view, event) -> {
                                dictionaryList.remove(dictionary);
                                notifyDataSetChanged();
                                return true;
                            })
                            .setDialogButtonRight("No", null)
                            .showDialog();
                    return true;
                });
            }

            private void findViews(@NonNull View itemView) {
                favoriteDictionaryName = itemView.findViewById(R.id.favoriteDictionaryName);
                favoriteWordGroups = itemView.findViewById(R.id.favoriteWordGroups);
            }

            private Dictionary dictionary;

            public void setData(Dictionary dictionary) {
                this.dictionary = dictionary;
                favoriteDictionaryName.setText(dictionary.getTitle());
            }

            @Override
            public void onGetWordGroupsSuccessfully(List<WordGroup> wordGroupList) {
                String wordGroupSize = wordGroupList.size() + " Word groups";
                favoriteWordGroups.setText(wordGroupSize);
            }
        }
    }

}
