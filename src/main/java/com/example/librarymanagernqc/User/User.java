package com.example.librarymanagernqc.User;

public class User {
    private String username;
    private String fullName;
    private String citizenId;
    private String gender;
    private String dateOfBirth;
    private String phoneNumber;

    public User(String username, String fullName, String citizenId, String gender, String dateOfBirth, String phoneNumber) {
        this.username = username;
        this.fullName = fullName;
        this.citizenId = citizenId;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(String citizenId) {
        this.citizenId = citizenId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUser(User user) {
        this.username = user.getUsername();
        this.fullName = user.getFullName();
        this.citizenId = user.getCitizenId();
        this.gender = user.getGender();
        this.dateOfBirth = user.getDateOfBirth();
        this.phoneNumber = user.getPhoneNumber();
    }
}
