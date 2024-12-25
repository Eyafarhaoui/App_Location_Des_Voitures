package com.example.eliteauto.model;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.google.gson.annotations.SerializedName;

public class Disponibilite {
    @SerializedName("id")
    private Long id;

    @SerializedName("dateDebutDisponibilite")
    private String dateDebutDisponibilite;

    @SerializedName("dateFinDisponibilite")
    private String dateFinDisponibilite;

    // Méthodes pour convertir les dates au format LocalDate si nécessaire
    public LocalDate getDateDebutDisponibilite() {
        return LocalDate.parse(dateDebutDisponibilite, DateTimeFormatter.ISO_DATE);
    }

    public LocalDate getDateFinDisponibilite() {
        return LocalDate.parse(dateFinDisponibilite, DateTimeFormatter.ISO_DATE);
    }
}
