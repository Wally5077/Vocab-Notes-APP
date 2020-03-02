package com.home.englishnote.models.repositories;

import com.home.englishnote.models.entities.WordGroup;

import java.util.List;

public interface WordGroupRepository {
    WordGroup getWordGroup(int wordGroupId);

    List<WordGroup> getWordGroupsFromDictionary(int dictionaryId, int offset, int limit);
}
