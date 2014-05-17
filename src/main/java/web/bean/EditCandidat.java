/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import jpa.Candidat;
import tools.CONSTANT_RETURN;
import tools.Utils;

/**
 *
 * @author Loïc
 */
@ManagedBean(name = "edit_candidat")
@SessionScoped
public class EditCandidat extends BeanAdapter {
    
    
    private String civilite;
    private String nom;
    private String prenom;
    private String email;
    private String email_confirme;
    private String date_naissance;

    public EditCandidat() {
         super();
    }

    public String Ajouter() {
        System.out.println("Ajouter()");
        System.out.println(getCivilite() + "\n" + getNom() + "\n" + getPrenom() + "\n" + getEmail() + "\n" + Utils.stringToMySQLDate(getDate_naissance()));
        CANDIDATDAO.create(new Candidat(getCivilite(), getNom(), getPrenom(), getEmail(), Utils.stringToMySQLDate(getDate_naissance())));
        // remise à zéro des champs du formulaire
        RAZFormulaire();
        return CONSTANT_RETURN.CANDIDAT_REDIRECT.getReturn();
    }

    /**
     * @return the civilite
     */
    public String getCivilite() {
        return civilite;
    }

    /**
     * @param civilite the civilite to set
     */
    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * @param prenom the prenom to set
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the email_confirme
     */
    public String getEmail_confirme() {
        return email_confirme;
    }

    /**
     * @param email_confirme the email_confirme to set
     */
    public void setEmail_confirme(String email_confirme) {
        this.email_confirme = email_confirme;
    }

    /**
     * @return the date_naissance
     */
    public String getDate_naissance() {
        return date_naissance;
    }

    /**
     * @param date_naissance the date_naissance to set
     */
    public void setDate_naissance(String date_naissance) {
        this.date_naissance = date_naissance;
    }

    private void RAZFormulaire() {
        setCivilite("");
        setNom("");
        setPrenom("");
        setEmail("");
        setEmail_confirme("");
        setDate_naissance("");
    }
}
