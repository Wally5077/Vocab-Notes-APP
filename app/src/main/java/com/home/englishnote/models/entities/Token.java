package com.home.englishnote.models.entities;

import java.io.Serializable;

public class Token implements Serializable {
    private String token;
    private int memberId;
    private int expired;

    public Token() {

    }

    public Token(String token, int memberId, int expired) {
        this.token = token;
        this.memberId = memberId;
        this.expired = expired;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getExpired() {
        return expired;
    }

    public void setExpired(int expired) {
        this.expired = expired;
    }
}
