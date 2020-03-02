package com.home.englishnote.models.entities;

public class Word {
    private int id;
    private String name, synonyms, imageUrl;

    public Word(int id, String name, String synonyms, String imageUrl) {
        this.id = id;
        this.name = name;
        this.synonyms = synonyms;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(String synonyms) {
        this.synonyms = synonyms;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
