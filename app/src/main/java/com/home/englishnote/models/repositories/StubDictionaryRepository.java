package com.home.englishnote.models.repositories;

import com.home.englishnote.models.entities.Dictionary;
import com.home.englishnote.models.entities.Member;

import java.util.ArrayList;
import java.util.List;

import static com.home.englishnote.models.entities.Type.OWN;

public class StubDictionaryRepository implements DictionaryRepository {

    @Override
    public Dictionary getDictionary(int dictionaryId) {
        return new Dictionary(0, "title", "description",
                new Member("firstName", "lastName",
                        25, "email", "password"), OWN);
    }

    @Override
    public List<Dictionary> getDictionaries(int offset, int limit) {
        List<Dictionary> dictionaryList = new ArrayList<>(10);
        dictionaryList.add(new Dictionary(0, "title", "description",
                new Member("firstName", "lastName",
                        25, "email", "password"), OWN));
        return dictionaryList;
    }
}
