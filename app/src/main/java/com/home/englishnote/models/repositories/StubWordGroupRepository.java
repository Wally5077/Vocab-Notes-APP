package com.home.englishnote.models.repositories;

import com.home.englishnote.models.entities.WordGroup;

import java.util.ArrayList;
import java.util.List;

public class StubWordGroupRepository implements WordGroupRepository {
    @Override
    public WordGroup getWordGroup(int wordGroupId) {
        return new WordGroup(0,"title",new ArrayList<>());
    }

    @Override
    public List<WordGroup> getWordGroupsFromDictionary(int dictionaryId, int offset, int limit) {
        return new ArrayList<>();
    }
}
