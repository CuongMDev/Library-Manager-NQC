package com.example.librarymanagernqc.User;

public class User {
    private final String username;
    private final String fullName;
    private final String citizenId;
    private final String gender;
    private final String dateOfBirth;
    private final String phoneNumber;

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

    public String getFullName() {
        return fullName;
    }

    public String getCitizenId() {
        return citizenId;
    }

    public String getGender() {
        return gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
