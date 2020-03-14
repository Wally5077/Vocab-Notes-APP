package com.home.englishnote.models.entities;

import java.io.Serializable;
import java.util.Set;

public class Dictionary implements Serializable {
    private int id, ownId;
    private String title, description;
    private Member owner;
    private Type type;

    public Dictionary(int id, String title, String description, Member owner, Type type) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.type = type;
    }

    public Dictionary() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwnId() {
        return ownId;
    }

    public void setOwnId(int ownId) {
        this.ownId = ownId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Member getOwner() {
        return owner;
    }

    public void setOwner(Member owner) {
        this.owner = owner;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    private Set<WordGroup> wordGroups;

    public Set<WordGroup> getWordGroups() {
        return wordGroups;
    }

    public void setWordGroups(Set<WordGroup> wordGroups) {
        this.wordGroups = wordGroups;
    }
}
