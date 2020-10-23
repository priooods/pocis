package com.kbs.pocis.model;

public class Model_User {

    String username_model, password_model;

    public Model_User() {
    }

    public Model_User(String username_model, String password_model) {
        this.username_model = username_model;
        this.password_model = password_model;
    }

    public String getUsername_model() {
        return username_model;
    }

    public void setUsername_model(String username_model) {
        this.username_model = username_model;
    }

    public String getPassword_model() {
        return password_model;
    }

    public void setPassword_model(String password_model) {
        this.password_model = password_model;
    }
}
