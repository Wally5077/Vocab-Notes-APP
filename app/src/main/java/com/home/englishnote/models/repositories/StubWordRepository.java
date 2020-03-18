package com.home.englishnote.models.repositories;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.home.englishnote.models.entities.Word;
import com.home.englishnote.utils.RandomVacabProducer;

@RequiresApi(api = Build.VERSION_CODES.N)
public class StubWordRepository implements WordRepository {

    @Override
    public Word getWord(String name) {
        return RandomVacabProducer.randomWordGroup(5, 10).getWords().get(1);
    }
}
