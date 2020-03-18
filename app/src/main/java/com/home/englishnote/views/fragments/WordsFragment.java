package com.home.englishnote.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.home.englishnote.R;
import com.home.englishnote.models.entities.Word;
import com.home.englishnote.presenters.WordsPresenter.WordsView;
import com.home.englishnote.views.fragments.BaseFragment;


public class WordsFragment extends BaseFragment implements WordsView {

    private TextView wordTitle;
    private ImageView wordImageUp;
    private TextView wordContentDown;
    private TextView synonymLeftTop;
    private TextView synonymLeftCenter;
    private TextView synonymLeftBottom;
    private TextView synonymRightTop;
    private TextView synonymRightCenter;
    private TextView synonymRightBottom;
    private ImageView wordSpeaker;
    private ImageView wordImageDown;
    private TextView wordContentUp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater
                .inflate(R.layout.fragment_word, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        init();
    }

    private void findViews(View view) {
        // begin
        wordTitle = view.findViewById(R.id.wordTitle);
        wordImageUp = view.findViewById(R.id.wordImageUp);
        wordContentDown = view.findViewById(R.id.wordContentDown);

        // six synonym
        synonymLeftTop = view.findViewById(R.id.synonymLeftTop);
        synonymLeftCenter = view.findViewById(R.id.synonymLeftCenter);
        synonymLeftBottom = view.findViewById(R.id.synonymLeftBottom);
        synonymRightTop = view.findViewById(R.id.synonymRightTop);
        synonymRightCenter = view.findViewById(R.id.synonymRightCenter);
        synonymRightBottom = view.findViewById(R.id.synonymRightBottom);

        wordSpeaker = view.findViewById(R.id.wordSpeaker);

        // change
        wordImageDown = view.findViewById(R.id.wordImageDown);
        wordContentUp = view.findViewById(R.id.wordContentUp);
    }

    private void init() {

    }

    @Override
    public void onGetWordSuccessfully(Word word) {

    }
}
