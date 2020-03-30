package com.home.englishnote.models.repositories;


import com.home.englishnote.models.entities.Word;
import com.home.englishnote.utils.DelayUtil;
import com.home.englishnote.utils.RandomVacabProducer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StubWordRepository implements WordRepository {

    private List<Word> wordList;

    @Override
    public List<Word> getPossibleWord(String keyword) {
        DelayUtil.delayExecuteThread(300);
        if (wordList == null) {
            wordList = RandomVacabProducer.randomWordGroup(999, 1000).getWords();
        }
//        return wordList.stream()
//                .filter(word -> word.getName().contains(keyword))
//                .collect(Collectors.toCollection(ArrayList::new));
        return wordList;
    }

    @Override
    public Word getWord(String name) {
        DelayUtil.delayExecuteThread(300);
        return getPossibleWord(name).get(1);
    }
}
