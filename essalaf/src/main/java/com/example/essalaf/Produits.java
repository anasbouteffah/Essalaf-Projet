package com.example.essalaf;

public class Produits {
    private String nom;
    private int prix;
    private String quantite;

    public Produits(String nom,int prix,String quantite) {
        this.nom = nom;
        this.prix=prix;
        this.quantite=quantite;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getQuantite() {
        return quantite;
    }

    public void setQuantite(String quantite) {
        this.quantite = quantite;
    }
}
