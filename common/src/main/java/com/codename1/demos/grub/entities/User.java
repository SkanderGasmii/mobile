package com.codename1.demos.grub.entities;

import java.util.Arrays;

public class User {
    private int id;
    private String email;
    private String[] roles;
    private String password;
    private String firstName;
    private String lastName;
    private String image;
    private String facebookId;
    private String googleId;
    private String $createdAt;//datetime

    public User() {
    }

    public User(int id, String email, String[] roles, String password, String firstName, String lastName, String image, String facebookId, String googleId, String $createdAt) {
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
        this.facebookId = facebookId;
        this.googleId = googleId;
        this.$createdAt = $createdAt;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String[] getRoles() {
        return roles;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getImage() {
        return image;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public String get$createdAt() {
        return $createdAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public void set$createdAt(String $createdAt) {
        this.$createdAt = $createdAt;
    }

    @Override
    public String   toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", roles=" + Arrays.toString(roles) +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", image='" + image + '\'' +
                ", facebookId='" + facebookId + '\'' +
                ", googleId='" + googleId + '\'' +
                ", $createdAt='" + $createdAt + '\'' +
                '}';
    }
}

