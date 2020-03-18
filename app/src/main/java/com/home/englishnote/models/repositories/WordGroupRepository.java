package com.home.englishnote.models.repositories;

import com.home.englishnote.models.entities.WordGroup;

import java.util.List;

public interface WordGroupRepository {
    WordGroup getWordGroup(int wordGroupId);

    List<WordGroup> getWordGroupsFromPublicDictionary(int dictionaryId, int offset, int limit);

    List<WordGroup> getWordGroupsFromOwnDictionary(int memberId, int offset, int limit);

    List<WordGroup> getWordGroupsFromFavoriteDictionary(int memberId, int offset, int limit);
}
