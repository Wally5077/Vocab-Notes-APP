package com.home.englishnote.models.entities;

import java.util.List;

public class WordGroup {
    private int id;
    private String title;
    private List<Word> words;

    public WordGroup(int id, String title, List<Word> words) {
        this.id = id;
        this.title = title;
        this.words = words;
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
}
