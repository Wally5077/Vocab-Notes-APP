package com.home.englishnote.models.repositories;


import com.home.englishnote.models.entities.Dictionary;
import com.home.englishnote.models.entities.Type;
import com.home.englishnote.utils.DelayUtil;
import com.home.englishnote.utils.RandomVacabProducer;

import java.util.ArrayList;
import java.util.List;


public class StubDictionaryRepository implements DictionaryRepository {

    private List<Dictionary> dictionaryList;

    @Override
    public Dictionary getDictionary(int dictionaryId) {
        DelayUtil.delayExecuteThread(1000);
        return RandomVacabProducer.randomDictionary(Type.PUBLIC,
                1, 3, 5, 20);
    }

    @Override
    public List<Dictionary> getDictionaries(int offset, int limit) {
        DelayUtil.delayExecuteThread(1000);
        if (dictionaryList == null) {
            dictionaryList = new ArrayList<>(50);
        }
        dictionaryList.clear();
        for (int index = 0; index < 2; index++) {
            dictionaryList.add(RandomVacabProducer.randomDictionary(Type.PUBLIC,
                    1, 3, 5, 20));
        }
        return dictionaryList;
    }
}
