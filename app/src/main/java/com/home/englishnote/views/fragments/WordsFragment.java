package com.home.englishnote.views.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.home.englishnote.R;
import com.home.englishnote.models.entities.Word;
import com.home.englishnote.presenters.WordsPresenter.WordsView;

import java.util.List;

import static com.home.englishnote.utils.ViewEnableUtil.setViewsFocusable;
import static com.home.englishnote.utils.ViewEnableUtil.setViewsVisible;

public class WordsFragment extends BaseFragment implements WordsView {

    // Todo
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
    private Word word;
    private TextView wordDescription;

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

        // explanation
        wordSpeaker = view.findViewById(R.id.wordSpeaker);
        wordDescription = view.findViewById(R.id.wordDescription);

        // change
        wordImageDown = view.findViewById(R.id.wordImageDown);
        wordContentUp = view.findViewById(R.id.wordContentUp);
    }

    private void init() {
        setDefaultViews();
        setExplanationTrigger();
        setWord();
    }

    private void setDefaultViews() {
        showExplanation(false);
    }

    private void setExplanationTrigger() {
        wordImageUp.setOnClickListener(v -> showExplanation(true));
        wordDescription.setOnClickListener(v -> showExplanation(false));
    }

    private void showExplanation(boolean enable) {
        setViewsVisible(enable, wordContentUp, wordImageDown, wordDescription);
        setViewsVisible(!enable, wordContentDown, wordImageUp);
        setViewsFocusable(!enable, wordImageUp);
        setViewsFocusable(enable, wordDescription);
    }

    private void setWord() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            word = (Word) bundle.getSerializable("VocabNoteObjects");
            setWordText(word.getName(), wordTitle, wordContentUp, wordContentDown);
            setWordText(word.getDescription(), wordDescription);
            setWordImage(wordImageUp, wordImageDown);
            setWordSynonyms(word.getSynonyms());
        }
    }

    private void setWordText(String wordName, TextView... textViews) {
        for (TextView textView : textViews) {
            textView.setText(wordName);
        }
    }

    private void setWordImage(ImageView... imageViews) {
        for (ImageView imageView : imageViews) {
            Glide.with(this)
                    .asBitmap()
                    .load(word.getImageUrl())
                    .fitCenter()
                    .error(R.drawable.apple)
                    .into(imageView);
        }
    }

    private void setWordSynonyms(List<String> synonymsList) {
        setViewsVisible(false,
                synonymLeftTop, synonymLeftCenter, synonymLeftBottom,
                synonymRightTop, synonymRightCenter, synonymRightBottom);
        switch (synonymsList.size()) {
            case 1:
                setWordSynonyms(synonymsList, synonymLeftCenter);
                break;
            case 2:
                setWordSynonyms(synonymsList, synonymLeftCenter, synonymRightCenter);
                break;
            case 3:
                setWordSynonyms(synonymsList, synonymLeftTop,
                        synonymLeftBottom, synonymRightCenter);
                break;
            case 4:
                setWordSynonyms(synonymsList,
                        synonymLeftTop, synonymLeftBottom,
                        synonymRightTop, synonymRightBottom);
                break;
            case 5:
                setWordSynonyms(synonymsList, synonymLeftTop, synonymLeftCenter,
                        synonymLeftBottom, synonymRightCenter, synonymRightBottom);
                break;
            case 6:
                setWordSynonyms(synonymsList,
                        synonymLeftTop, synonymLeftCenter, synonymLeftBottom,
                        synonymRightTop, synonymRightCenter, synonymRightBottom);
                break;
            default:
                break;
        }
    }

    private void setWordSynonyms(List<String> synonymsList, TextView... textViews) {
        setViewsVisible(true, textViews);
        for (int index = 0; index < synonymsList.size(); index++) {
            textViews[index].setText(synonymsList.get(index));
        }
    }
}
