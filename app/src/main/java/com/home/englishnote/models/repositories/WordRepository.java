package com.home.englishnote.models.repositories;

import com.home.englishnote.models.entities.Word;

public interface WordRepository {

    Word getWord(String name);
}
