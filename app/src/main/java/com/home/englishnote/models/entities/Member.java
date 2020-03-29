package com.home.englishnote.models.entities;


public class Member implements User {
    private int id, age, level, exp;
    private String firstName, lastName, email, password, imageURL;
    private Credentials credentials;
    private Role role;
    private Token token;

    public Member() {

    }

    // Todo delete password constructor parameter

    public Member(String firstName, String lastName, int age, String email, String password) {
        this.age = age;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        setCredentials(email, password);
    }

    @Override
    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(String email, String password) {
        credentials = new Credentials(email, password);
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String getImageURL() {
        return imageURL;
    }

    @Override
    public boolean isGuest() {
        return false;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
