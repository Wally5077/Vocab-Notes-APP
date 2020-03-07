package com.home.englishnote.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.home.englishnote.R;
import com.home.englishnote.models.entities.Dictionary;
import com.home.englishnote.presenters.PublicDictionaryPresenter;
import com.home.englishnote.presenters.PublicDictionaryPresenter.PublicDictionaryView;
import com.home.englishnote.utils.Global;
import com.home.englishnote.utils.VocabularyNoteKeyword;

import java.util.ArrayList;
import java.util.List;

public class PublicDictionariesFragment extends BaseFragment implements PublicDictionaryView {

    private RecyclerView dictionaryRecycler;
    private DictionaryAdapter dictionaryAdapter;
    private SwipeRefreshLayout dictionarySwipeRefreshLayout;
    private TextView userName;
    private ImageView userPic;
    private PublicDictionaryPresenter publicDictionaryPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_public_dictionaries, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        init();
    }

    private void findViews(View view) {
        dictionaryRecycler = view.findViewById(R.id.dictionaryRecycler);
        dictionarySwipeRefreshLayout = view.findViewById(R.id.dictionarySwipeRefreshLayout);
        userName = view.findViewById(R.id.dictionaryContentUserName);
        userPic = view.findViewById(R.id.dictionaryContentUserPic);
    }

    private void init() {
        publicDictionaryPresenter = new PublicDictionaryPresenter(
                this, Global.dictionaryRepository(), Global.threadExecutor());
        setMember();
        setDictionaryRecycler();
        setDictionarySwipeRefreshLayout();
        updateDictionaryList();
    }

    private void setMember() {
        String memberName = member.getLastName();
        userName.setText((memberName.isEmpty()) ? "userName" : memberName);
        userPic.setImageResource(R.drawable.small_user_pic);
        userName.setOnClickListener(this::onChangeProfilePage);
        userPic.setOnClickListener(this::onChangeProfilePage);
    }

    private List<Dictionary> dictionaryList = new ArrayList<>();

    private void setDictionaryRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainPageActivity);
        dictionaryRecycler.setHasFixedSize(true);
        dictionaryRecycler.setLayoutManager(linearLayoutManager);
        dictionaryAdapter = new DictionaryAdapter(dictionaryList);
        dictionaryRecycler.setAdapter(dictionaryAdapter);
        dictionaryRecycler.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastVisibleItemPosition =
                        linearLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == dictionaryAdapter.getItemCount()) {
                    updateDictionaryList();
                }
            }
        });
    }

    private void setDictionarySwipeRefreshLayout() {
        dictionarySwipeRefreshLayout.measure(0, 0);
        dictionarySwipeRefreshLayout.setProgressViewOffset(true, 80, 90);
        dictionarySwipeRefreshLayout.setOnRefreshListener(this::updateDictionaryList);
    }

    public void onVocabSearchClick(View view) {
    }

    public void onDictionaryListSearchClick(View view) {
    }

    private void updateDictionaryList() {
        setDictionarySwipeRefreshLayoutEnable(true);
        int dictionaryListSize = dictionaryList.size();
        dictionaryListSize = (dictionaryListSize == 0) ? dictionaryListSize : ++dictionaryListSize;
        publicDictionaryPresenter.getDictionaries(dictionaryListSize, dictionaryListSize + 3);
    }

    private void setDictionarySwipeRefreshLayoutEnable(boolean enable) {
        dictionarySwipeRefreshLayout.setEnabled(enable);
        dictionarySwipeRefreshLayout.setRefreshing(enable);
    }

    @Override
    public void onGetDictionariesSuccessfully(List<Dictionary> dictionaryList) {
        setDictionarySwipeRefreshLayoutEnable(false);
        // Todo might be over all dictionary count
        this.dictionaryList.clear();
        this.dictionaryList.addAll(dictionaryList);
        dictionaryAdapter.notifyDataSetChanged();
    }

    public class DictionaryAdapter extends Adapter<DictionaryAdapter.DictionaryHolder> {

        private List<Dictionary> dictionaryList;

        public DictionaryAdapter(List<Dictionary> dictionaryList) {
            this.dictionaryList = dictionaryList;
        }

        @NonNull
        @Override
        public DictionaryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater()
                    .inflate(R.layout.item_dictionary, parent, false);
            return new DictionaryHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DictionaryHolder holder, int position) {
            Dictionary dictionary = dictionaryList.get(position);
            holder.setData(dictionary);
            holder.setExploreButtonClick(dictionary);
        }

        @Override
        public int getItemCount() {
            return dictionaryList.size();
        }

        private class DictionaryHolder extends ViewHolder {

            private TextView dictionaryTitle, dictionaryDescription;
            private ImageView dictionaryImage1, dictionaryImage2, dictionaryImage3;
            private Button exploreButton;

            public DictionaryHolder(@NonNull View itemView) {
                super(itemView);
                findViews(itemView);
            }

            private void findViews(View itemView) {
                dictionaryTitle = itemView.findViewById(R.id.dictionaryTitle);
                dictionaryDescription = itemView.findViewById(R.id.dictionaryDescription);
                dictionaryImage1 = itemView.findViewById(R.id.dictionaryImage1);
                dictionaryImage2 = itemView.findViewById(R.id.dictionaryImage2);
                dictionaryImage3 = itemView.findViewById(R.id.dictionaryImage3);
                exploreButton = itemView.findViewById(R.id.exploreButton);
            }

            private void setExploreButtonClick(Dictionary dictionary) {
                exploreButton.setOnClickListener(
                        (v) -> mainPageActivity.switchFragment(R.layout.fragment_public_dictionary,
                                VocabularyNoteKeyword.DICTIONARY_HOME_PAGE_CONTAINER, dictionary));
            }

            private void setData(Dictionary dictionary) {
                dictionaryTitle.setText(dictionary.getTitle());
                dictionaryDescription.setText(dictionary.getDescription());
                dictionaryImage1.setImageResource(
                        R.drawable.pear_hydrosol_pyrus_communis_hydrolat_product_pic);
                dictionaryImage2.setImageResource(R.drawable.guava_1);
                dictionaryImage3.setImageResource(
                        R.drawable.china_fresh_sweet_delicious_red_fruit_apples_jpg_350_x_350);
            }
        }
    }
}
