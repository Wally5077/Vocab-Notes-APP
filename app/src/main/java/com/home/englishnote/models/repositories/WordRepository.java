package com.home.englishnote.models.repositories;

import com.home.englishnote.models.entities.Word;

import java.util.List;

public interface WordRepository {

    List<Word> getPossibleWord(String keyword);

    Word getWord(String name);
}
