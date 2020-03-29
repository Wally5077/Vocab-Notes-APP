package com.home.englishnote.models.entities;

public class Guest implements User {

    private Credentials credentials = new Credentials(getEmail(), getPassword());

    @Override
    public Credentials getCredentials() {
        return credentials;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public int getAge() {
        return 0;
    }

    @Override
    public int getExp() {
        return 0;
    }

    @Override
    public String getFirstName() {
        return "firstName";
    }

    @Override
    public String getLastName() {
        return "lastName";
    }

    @Override
    public String getEmail() {
        return "email";
    }

    @Override
    public String getPassword() {
        return "password";
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public Role getRole() {
        return Role.GUEST;
    }

    @Override
    public String getImageURL() {
        return "imageURL";
    }

    private Token token = new Token("token", 0, 0);

    @Override
    public Token getToken() {
        return token;
    }

    @Override
    public boolean isGuest() {
        return true;
    }
}
