package com.jizas.statcheck.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String phoneNumber;

    private String role; // e.g., "admin" or "user"

    @Column(nullable = false)
    private String name;

    // Social Media Links
    @Column
    private String socialMediaFacebook;

    @Column
    private String socialMediaInstagram;

    @Column
    private String socialMediaTwitter;

    // Getters and setters
    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSocialMediaFacebook() {
        return socialMediaFacebook;
    }

    public void setSocialMediaFacebook(String socialMediaFacebook) {
        this.socialMediaFacebook = socialMediaFacebook;
    }

    public String getSocialMediaInstagram() {
        return socialMediaInstagram;
    }

    public void setSocialMediaInstagram(String socialMediaInstagram) {
        this.socialMediaInstagram = socialMediaInstagram;
    }

    public String getSocialMediaTwitter() {
        return socialMediaTwitter;
    }

    public void setSocialMediaTwitter(String socialMediaTwitter) {
        this.socialMediaTwitter = socialMediaTwitter;
    }
}
