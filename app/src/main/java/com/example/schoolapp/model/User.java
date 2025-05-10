// User.java
package com.example.schoolapp.model;

public class User {
    private long id;
    private String email;
    private String password;
    private String firstname;
    private String lastname;

    private int age;

    public User(long id, String email, String password, String firstname, String lastname,int age) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
    }

    // Getters and setters
    public long getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }

    public int getAge() { return age; }
}