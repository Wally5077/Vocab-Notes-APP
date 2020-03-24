package com.home.englishnote.models.repositories;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.home.englishnote.models.entities.Word;
import com.home.englishnote.utils.RandomVacabProducer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.N)
public class StubWordRepository implements WordRepository {

    private List<Word> wordList;

    @Override
    public List<Word> getPossibleWord(String keyword) {
        if (wordList == null) {
            wordList = RandomVacabProducer.randomWordGroup(999, 1000).getWords();
        }
        return wordList.stream()
                .filter(word -> word.getName().contains(keyword))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Word getWord(String name) {
        return getPossibleWord(name).get(1);
    }
}
