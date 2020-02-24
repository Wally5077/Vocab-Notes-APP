package com.home.englishnote.models;

import com.home.englishnote.utils.Word;

public interface WordRepository {

    Word getWord(String name);
}
