package com.KurdDevs.KurdDevs.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "activated")
    private boolean activated;

    @Column(name = "activation_token")
    private String activationToken;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;

    @Column(name = "major")
    private String major;

    @Column(name = "about_section", columnDefinition = "TEXT")
    @Lob
    private String aboutSection;


    @Column(name = "github_url")
    private String githubUrl;

    @Column(name = "linkedin_url")
    private String linkedinUrl;
    @Column(name = "behance_url")
    private String behance_url;
    @Column(name = "pdf_cv")
    private String pdfCv;
    @Column(name = "profileImage")
    private String profileImage;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "location")
    private String location;

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getActivationToken() {
        return activationToken;
    }

    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getProfileImage() {
        return this.profileImage;
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

    public Long getId() {
        return id;
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
}
