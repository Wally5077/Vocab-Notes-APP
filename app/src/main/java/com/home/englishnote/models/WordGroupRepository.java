package com.home.englishnote.models;

import com.home.englishnote.utils.WordGroup;

import java.util.List;

public interface WordGroupRepository {
    WordGroup getWordGroup(int wordGroupId);

    List<WordGroup> getWordGroupsFromDictionary(int dictionaryId, int offset, int limit);
}
