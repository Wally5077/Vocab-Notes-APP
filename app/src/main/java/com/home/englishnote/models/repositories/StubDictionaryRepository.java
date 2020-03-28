package com.home.englishnote.models.repositories;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.home.englishnote.models.entities.Dictionary;
import com.home.englishnote.models.entities.Member;
import com.home.englishnote.models.entities.Type;
import com.home.englishnote.utils.RandomVacabProducer;

import java.util.ArrayList;
import java.util.List;

import static com.home.englishnote.models.entities.Type.OWN;

public class StubDictionaryRepository implements DictionaryRepository {

    private List<Dictionary> dictionaryList;

    @Override
    public Dictionary getDictionary(int dictionaryId) {
        return RandomVacabProducer.randomDictionary(Type.PUBLIC,
                1, 3, 5, 20);
    }

    @Override
    public List<Dictionary> getDictionaries(int offset, int limit) {
        if (dictionaryList == null) {
            dictionaryList = new ArrayList<>(50);
            for (int index = 0; index < 50; index++) {
                dictionaryList.add(RandomVacabProducer.randomDictionary(Type.PUBLIC,
                        1, 3, 5, 20));
            }
        }
        return dictionaryList;
    }
}
