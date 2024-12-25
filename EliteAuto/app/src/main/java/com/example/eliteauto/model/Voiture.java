package com.example.eliteauto.model;

import java.math.BigDecimal;
import java.util.List;

public class Voiture {
    private Long id;
    private String marque;
    private String modele;
    private Integer annee;
    private BigDecimal prix;
    private Integer nombrePortes;
    private String typeCarburant;
    private BigDecimal montantCaution;
    private Integer nombreChevaux;
    private Integer nombrePassagers;
    private String imagePath; // Can be URL or base64 string (BLOB)
    private List<Disponibilite> disponibilites;
    private Integer proprietaireId;// Ajout de ce champ

    // Getters and Setters
    public String getImageUrl() {
        return "http://10.0.2.2:8081/api/voitures/getImage/" + id;
    }
    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }
    public Long getId() {
        return id;
    }
    public Integer getNombrePortes() {
        return nombrePortes;
    }

    public void setNombrePortes(Integer nombrePortes) {
        this.nombrePortes = nombrePortes;
    }

    public String getTypeCarburant() {
        return typeCarburant;
    }

    public void setTypeCarburant(String typeCarburant) {
        this.typeCarburant = typeCarburant;
    }

    public BigDecimal getMontantCaution() {
        return montantCaution;
    }

    public void setMontantCaution(BigDecimal montantCaution) {
        this.montantCaution = montantCaution;
    }

    public Integer getNombreChevaux() {
        return nombreChevaux;
    }

    public void setNombreChevaux(Integer nombreChevaux) {
        this.nombreChevaux = nombreChevaux;
    }

    public Integer getNombrePassagers() {
        return nombrePassagers;
    }

    public void setNombrePassagers(Integer nombrePassagers) {
        this.nombrePassagers = nombrePassagers;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public BigDecimal getPrixParJour() {
        return this.prix;
    }

    public String getImage() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<Disponibilite> getDisponibilites() {
        return disponibilites;
    }

    public void setDisponibilites(List<Disponibilite> disponibilites) {
        this.disponibilites = disponibilites;
    }

    public Integer getProprietaireId() {
        return proprietaireId;
    }

    public void setProprietaireId(Integer proprietaireId) {
        this.proprietaireId = proprietaireId;
    }
}
