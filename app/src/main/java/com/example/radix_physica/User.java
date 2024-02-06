package com.example.radix_physica;

public class User {
    public String id, name, email, password, password_retry;

    public User() {
    }

    public User(String id, String name, String email, String password, String password_retry) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.password_retry = password_retry;
    }
}
