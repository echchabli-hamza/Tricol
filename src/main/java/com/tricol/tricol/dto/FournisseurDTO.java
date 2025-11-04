package com.tricol.tricol.dto;

import java.util.List;

public class FournisseurDTO {
    private Long id;
    private String societe;
    private String adresse;
    private String contact;
    private String email;
    private String telephone;
    private String ville;
    private String ice;


    private List<CommandeFournisseurSimpleDTO> commandes;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSociete() {
        return societe;
    }

    public void setSociete(String societe) {
        this.societe = societe;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getIce() {
        return ice;
    }

    public void setIce(String ice) {
        this.ice = ice;
    }

    public List<CommandeFournisseurSimpleDTO> getCommandes() {
        return commandes;
    }

    public void setCommandes(List<CommandeFournisseurSimpleDTO> commandes) {
        this.commandes = commandes;
    }

    @Override
    public String toString() {
        return "FournisseurDTO{" +
                "id=" + id +
                ", societe='" + societe + '\'' +
                ", adresse='" + adresse + '\'' +
                ", contact='" + contact + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", ville='" + ville + '\'' +
                ", ice='" + ice + '\'' +
                ", commandes=" + (commandes != null ? commandes.size() : 0) +
                '}';
    }
}
