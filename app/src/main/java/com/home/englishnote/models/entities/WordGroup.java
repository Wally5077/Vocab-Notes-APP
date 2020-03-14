package com.home.englishnote.models.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class WordGroup implements Serializable {
    private int id;
    private String title;
    private List<Word> words;

    public WordGroup(int id, String title, List<Word> words) {
        this.id = id;
        this.title = title;
        this.words = words;
    }

    public WordGroup() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    public void setWords(Set<Word> words) {
        this.words.addAll(words);
    }
}
