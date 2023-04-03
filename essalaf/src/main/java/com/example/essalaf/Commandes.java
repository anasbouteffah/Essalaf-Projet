package com.example.essalaf;

public class Commandes {
    private int num;
    private String client;
    private String date;

    private int montant;
    private int paye;
    private int credit;
    private String etat;

    public Commandes(int num , String client, String date, int montant , int paye , int credit , String etat) {
        this.num = num;
        this.client = client;
        this.date = date;
        this.montant = montant;
        this.paye=paye;
        this.credit = credit;
        this.etat=etat;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPaye() {
        return paye;
    }

    public void setPaye(int paye) {
        this.paye = paye;
    }
}
