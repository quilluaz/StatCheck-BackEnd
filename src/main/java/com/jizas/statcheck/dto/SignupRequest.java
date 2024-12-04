package com.jizas.statcheck.dto;

public class SignupRequest {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String socialMediaFacebook;
    private String socialMediaInstagram;
    private String socialMediaTwitter;

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
