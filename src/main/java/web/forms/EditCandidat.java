/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.forms;

import dao.IAdminDao;
import dao.ICandidatDao;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import jpa.Candidat;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tools.utils;

/**
 *
 * @author Loïc
 */
@ManagedBean(name = "edit_candidat")
@SessionScoped
public class EditCandidat implements Serializable{

    private ICandidatDao candidatDao = null;
    private IAdminDao adminDao = null;

// création nouveau candidat
    private String civilite;
    private String nom;
    private String prenom;
    private String email;
    private String email_confirme;
    private String date_naissance;

    public EditCandidat() {
    }

    @PostConstruct
    public void init() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        candidatDao = (ICandidatDao) ctx.getBean("candidatDao");
    }

    
    public String Ajouter() {
        System.out.println("Ajouter()");
        System.out.println(getCivilite() + "\n" + getNom() + "\n" + getPrenom() + "\n" + getEmail() + "\n" + utils.stringToMySQLDate(getDate_naissance()));
        Candidat c = new Candidat(getCivilite(), getNom(), getPrenom(), getEmail(), utils.stringToMySQLDate(getDate_naissance()));
        Candidat cand = candidatDao.create(c);
        // champs à zéro
        setCivilite("");
        setNom("");
        setPrenom("");
        setEmail("");
        setEmail_confirme("");
        setDate_naissance("");
        return "candidat?faces-redirect=true";
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
}
