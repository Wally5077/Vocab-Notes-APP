package com.home.englishnote.models.entities;

public class Guest implements User {

    private Credentials credentials;

    @Override
    public Credentials getCredentials() {
        return credentials = (credentials == null) ?
                new Credentials(getEmail(), getPassword()) : credentials;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public int getAge() {
        return 30;
    }

    @Override
    public int getExp() {
        return 0;
    }

    @Override
    public String getFirstName() {
        return "Guest";
    }

    @Override
    public String getLastName() {
        return "Guest";
    }

    @Override
    public String getEmail() {
        return "w@gmail.com";
    }

    @Override
    public String getPassword() {
        return "w0000000";
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
