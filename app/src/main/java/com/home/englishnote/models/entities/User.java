package com.home.englishnote.models.entities;

import java.io.Serializable;

public interface User extends Serializable {

    Credentials getCredentials();

    int getId();

    int getAge();

    int getExp();

    String getFirstName();

    String getLastName();

    String getEmail();

    String getPassword();

    int getLevel();

    Role getRole();

    String getImageURL();

    Token getToken();

    boolean isGuest();
}
