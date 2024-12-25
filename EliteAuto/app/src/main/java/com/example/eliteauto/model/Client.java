package com.example.eliteauto.model;


public class Client {

    private String facebookId;  // Utiliser l'ID Facebook
    private String name;        // Le nom de l'utilisateur
    private String email;       // Email si tu veux le garder

    public Client() {

    }
    public Client(String email) {
        this.email=email;
    }

    // Getters et setters

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

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
}

