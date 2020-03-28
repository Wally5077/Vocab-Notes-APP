package com.home.englishnote.models.entities;

import java.io.Serializable;
import java.util.List;

public class Word implements Serializable {
    private int id;
    private String name, description, imageUrl;
    private List<String> synonyms;

    public Word(int id, String name, List<String> synonyms, String imageUrl) {
        this.id = id;
        this.name = name;
        this.synonyms = synonyms;
        this.imageUrl = imageUrl;
    }

    public Word() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
