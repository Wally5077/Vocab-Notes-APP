package com.home.englishnote.models;

import com.home.englishnote.utils.Dictionary;

import java.util.List;

public interface DictionaryRepository {

    Dictionary getDictionary(int dictionaryId);

    List<Dictionary> getDictionaries(int offset, int limit);
}
