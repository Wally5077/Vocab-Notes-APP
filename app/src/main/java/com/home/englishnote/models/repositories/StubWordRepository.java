package com.home.englishnote.models.repositories;

import com.home.englishnote.models.entities.Word;

public class StubWordRepository implements WordRepository {
    @Override
    public Word getWord(String name) {
        return new Word(0, "name", "synonyms", "imageUrl");
    }
}
