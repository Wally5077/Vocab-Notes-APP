package com.home.englishnote.models.repositories;

import com.home.englishnote.models.entities.Dictionary;

import java.util.List;

public interface DictionaryRepository {

    Dictionary getDictionary(int dictionaryId);

    List<Dictionary> getDictionaries(int offset, int limit);
}
