package com.home.englishnote.models.repositories;


import com.home.englishnote.exceptions.WordNotFoundException;
import com.home.englishnote.models.entities.Word;
import com.home.englishnote.utils.DelayUtil;
import com.home.englishnote.utils.RandomVacabProducer;

import java.util.List;
import java.util.stream.Collectors;

public class StubWordRepository implements WordRepository {

    private List<Word> wordList;

    public StubWordRepository() {
        wordList = RandomVacabProducer.randomWordGroup(999, 1000).getWords();
    }

    @Override
    public List<Word> getPossibleWord(String keyword) {
        DelayUtil.delayExecuteThread(300);
        return wordList.stream()
                .filter(word -> word.getName().contains(keyword))
                .collect(Collectors.toList()).subList(0, 5);
    }

    @Override
    public Word getWord(String name) {
        DelayUtil.delayExecuteThread(300);
        return wordList.stream()
                .filter(word -> word.getName().equals(name))
                .findFirst().orElseThrow(WordNotFoundException::new);
    }
}
