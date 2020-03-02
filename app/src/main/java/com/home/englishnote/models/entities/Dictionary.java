package com.home.englishnote.models.entities;

public class Dictionary {
    private int id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Member getMember() {
        return owner;
    }

    public void setMember(Member owner) {
        this.owner = owner;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
