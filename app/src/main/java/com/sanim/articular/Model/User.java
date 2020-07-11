package com.sanim.articular.Model;

public class User {
    public String id;
    public String fullname;
    public String email;
    public String password;
    public String profileImage;
    public String phoneNumber;

    public User(String id, String fullname, String email, String password, String profileImage, String phoneNumber) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
        this.phoneNumber = phoneNumber;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
