package com.home.englishnote.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.home.englishnote.R;
import com.home.englishnote.models.entities.Dictionary;
import com.home.englishnote.models.entities.WordGroup;
import com.home.englishnote.presenters.OwnDictionaryPagePresenter;
import com.home.englishnote.presenters.OwnDictionaryPagePresenter.OwnDictionaryPageView;
import com.home.englishnote.utils.Global;
import com.home.englishnote.utils.OwnWordGroupsAdapter;

import java.util.ArrayList;
import java.util.List;

public class OwnDictionaryPageFragment extends BaseFragment implements OwnDictionaryPageView {

    private TextView ownDictionaryPageDictionaryName;
    private TextView ownDictionaryPageWordGroupCount;
    private ImageView ownDictionaryPagePhoto;
    private TextView ownDictionaryPageMemberName;
    private Button ownDictionaryPageEditButton;
    private ImageView ownDictionaryPageDictionaryNamePen;
    private Button ownDictionaryPageDeleteButton;
    private Button ownDictionaryPageSaveButton;
    private SearchView ownDictionaryPageSearch;
    private RecyclerView wordGroupsRecycler;
    private OwnWordGroupsAdapter ownWordGroupsAdapter;
    private OwnDictionaryPagePresenter ownDictionaryPagePresenter;
    private DrawerLayout ownDictionaryPageDrawer;
    private ImageView ownDictionaryPageDrawerButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater
                .inflate(R.layout.fragment_own_dictionary_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        init();
    }

    private void findViews(View view) {
        ownDictionaryPageDrawer = view.findViewById(R.id.ownDictionaryPageDrawer);
        ownDictionaryPageDrawerButton = view.findViewById(R.id.ownDictionaryPageDrawerButton);
        ownDictionaryPageDictionaryName = view.findViewById(R.id.ownDictionaryPageDictionaryName);
        ownDictionaryPageWordGroupCount = view.findViewById(R.id.ownDictionaryPageWordGroupCount);
        ownDictionaryPagePhoto = view.findViewById(R.id.ownDictionaryPagePhoto);
        ownDictionaryPageMemberName = view.findViewById(R.id.ownDictionaryPageMemberName);
        ownDictionaryPageEditButton = view.findViewById(R.id.ownDictionaryPageEditButton);
        ownDictionaryPageDictionaryNamePen = view.findViewById(R.id.ownDictionaryPageDictionaryNamePen);
        ownDictionaryPageDeleteButton = view.findViewById(R.id.ownDictionaryPageDeleteButton);
        ownDictionaryPageSaveButton = view.findViewById(R.id.ownDictionaryPageSaveButton);
        ownDictionaryPageSearch = view.findViewById(R.id.ownDictionaryPageSearch);
        wordGroupsRecycler = view.findViewById(R.id.wordGroupRecycler);
    }

    private void init() {
        ownDictionaryPagePresenter = new OwnDictionaryPagePresenter(
                this, Global.wordGroupRepository(), Global.threadExecutor());
        setToolBar();
        setDictionary();
        setMember();
        setWordGroupsRecycler();
        downloadWordGroupsList();
        setEditButton();
    }

    private void setToolBar() {
        ownDictionaryPageDrawerButton.setOnClickListener(
                v -> ownDictionaryPageDrawer.openDrawer(GravityCompat.START));
    }

    private void setDictionary() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            Dictionary dictionary = (Dictionary) bundle.getSerializable("VocabNoteObjects");
            ownDictionaryPageDictionaryName.setText(dictionary.getTitle());
        }
    }

    private void setMember() {
        ownDictionaryPageMemberName.setText(member.getFirstName());
        Glide.with(getContext())
                .asBitmap()
                .load(member.getImageURL())
                .fitCenter()
                .error(R.drawable.small_user_pic)
                .into(ownDictionaryPagePhoto);
    }

    private List<WordGroup> wordGroupsList = new ArrayList<>();

    private void setWordGroupsRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        wordGroupsRecycler.setHasFixedSize(true);
        wordGroupsRecycler.setLayoutManager(linearLayoutManager);
        ownWordGroupsAdapter = new OwnWordGroupsAdapter(wordGroupsList);
        wordGroupsRecycler.setAdapter(ownWordGroupsAdapter);
    }

    private void downloadWordGroupsList() {
        ownDictionaryPagePresenter.getWordGroups(member.getId(), 0, -1);
    }

    private void setEditButton() {
        setEditButtonEnable(true);
        ownDictionaryPageEditButton.setOnClickListener(v -> setEditButtonEnable(false));
        ownDictionaryPageSaveButton.setOnClickListener(v -> setEditButtonEnable(true));
    }

    private void setEditButtonEnable(boolean enable) {
        setViewsVisible(enable, ownDictionaryPageEditButton);
        setViewsFocusable(enable, ownDictionaryPageEditButton);
        setViewsVisible(!enable, ownDictionaryPageDeleteButton,
                ownDictionaryPageSaveButton, ownDictionaryPageDictionaryNamePen);
        setViewsFocusable(!enable,
                ownDictionaryPageDictionaryName, ownDictionaryPageDictionaryNamePen,
                ownDictionaryPageDeleteButton, ownDictionaryPageSaveButton);
        ownWordGroupsAdapter.setEditButtonClick(!enable);
    }

    private void setViewsVisible(boolean visible, View... views) {
        for (View view : views) {
            view.setVisibility((visible) ? View.VISIBLE : View.INVISIBLE);
        }
    }

    private void setViewsFocusable(boolean enable, View... views) {
        for (View view : views) {
            view.setFocusable(enable);
            view.setFocusableInTouchMode(enable);
            view.setClickable(enable);
            view.setLongClickable(enable);
        }
    }

    @Override
    public void onGetWordGroupsSuccessfully(List<WordGroup> wordGroupList) {
        this.wordGroupsList.clear();
        this.wordGroupsList.addAll(wordGroupList);
        ownWordGroupsAdapter.notifyDataSetChanged();
        String wordGroupCount = wordGroupList.size() + " Word groups";
        ownDictionaryPageWordGroupCount.setText(wordGroupCount);
    }

}
