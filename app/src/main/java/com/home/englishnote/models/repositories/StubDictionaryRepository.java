package com.home.englishnote.models.repositories;


import com.home.englishnote.exceptions.DictionaryNotFoundException;
import com.home.englishnote.models.entities.Dictionary;
import com.home.englishnote.models.entities.Type;
import com.home.englishnote.utils.DelayUtil;
import com.home.englishnote.utils.RandomVacabGenerator;

import java.util.ArrayList;
import java.util.List;


public class StubDictionaryRepository implements DictionaryRepository {

    private List<Dictionary> dictionaryList;

    public StubDictionaryRepository() {
        dictionaryList = new ArrayList<>(100);
        for (int index = 0; index < 100; index++) {
            dictionaryList.add(RandomVacabGenerator.randomDictionary(Type.PUBLIC,
                    3, 9, 5, 20));
        }
    }

    @Override
    public Dictionary getDictionary(int dictionaryId) {
        DelayUtil.delayExecuteThread(500);
        return dictionaryList.stream()
                .filter(dictionary -> dictionary.getId() == dictionaryId)
                .findFirst()
                .orElseThrow(DictionaryNotFoundException::new);
    }

    @Override
    public List<Dictionary> getDictionaries(int offset, int limit) {
        DelayUtil.delayExecuteThread(500);
        if (offset < dictionaryList.size()) {
            if (offset > -1) {
                return dictionaryList.subList(
                        Math.max(0, offset), Math.min(offset + limit, dictionaryList.size()));
            }
        }
        return dictionaryList;
    }
}
