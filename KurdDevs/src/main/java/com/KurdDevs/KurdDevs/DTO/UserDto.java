package com.KurdDevs.KurdDevs.DTO;

import jakarta.validation.constraints.Email;

public class UserDto {

    private String username;
    @Email(message = "Invalid email format")
    private String email;
    private String password;
    private String major;


    private String profileImage;


    private String aboutSection;


    private String githubUrl;


    private String linkedinUrl;


    private String pdfCv;
    private String ConfirmPassword;

    private String phoneNumber;
    private String location;
    private String behance_url;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getAboutSection() {
        return aboutSection;
    }

    public void setAboutSection(String aboutSection) {
        this.aboutSection = aboutSection;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    public void setLinkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
    }

    public String getPdfCv() {
        return pdfCv;
    }

    public void setPdfCv(String pdfCv) {
        this.pdfCv = pdfCv;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        ConfirmPassword = confirmPassword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBehance_url() {
        return behance_url;
    }

    public void setBehance_url(String behance_url) {
        this.behance_url = behance_url;
    }

    // ...
}
