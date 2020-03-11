package com.home.englishnote.views.fragments.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.home.englishnote.R;
import com.home.englishnote.presenters.OwnDictionariesPresenter;
import com.home.englishnote.presenters.OwnDictionariesPresenter.OwnDictionariesView;
import com.home.englishnote.utils.Global;
import com.home.englishnote.views.fragments.BaseFragment;

public class OwnDictionariesFragment extends BaseFragment implements OwnDictionariesView {

    private OwnDictionariesPresenter ownDictionariesPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater
                .inflate(R.layout.fragment_own_dictionaries, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        init();
    }

    private void findViews(View view) {
        SwipeRefreshLayout ownDictionariesSwipeRefreshLayout = view.findViewById(R.id.ownDictionariesSwipeRefreshLayout);
        RecyclerView ownDictionariesRecycler = view.findViewById(R.id.ownDictionariesRecycler);
    }

    private void init() {
        ownDictionariesPresenter = new OwnDictionariesPresenter(
                this, Global.memberRepository(), Global.threadExecutor());
    }
}
