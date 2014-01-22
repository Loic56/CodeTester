/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.forms;

import java.io.Serializable;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import jpa.Candidat;
import jpa.Passage;
import jpa.Question;
import jpa.Test;
import tools.utils;

/**
 *
 * @author Loïc
 */
@ManagedBean(name = "logCandidat")
@SessionScoped
public class Log_Candidat implements Serializable {

    // private Passage thePassage;
    private Candidat theCandidat;
    private String nom;
    private String prenom;
    private Date date;

    private String error;

    private Hashtable<String, Hashtable<Integer, List<Question>>> tab_test;
    private Hashtable<Integer, List<Question>> tab_rub;

    
    
    
    public Log_Candidat() {
        error = "0";
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        this.theCandidat = ((Candidat) sessionMap.get("theCandidat"));
    }

    
    public String deconnexion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }

    
    
    
    
    public String Connexion() {
        System.out.println(getNom() + "\n" + getPrenom() + "\n" + getDate());
        System.out.println(utils.dateToMySQLString(getDate()));

        System.out.println(theCandidat.getCandidatNom() + "\n" + theCandidat.getCandidatNom() + "\n" + theCandidat.getCandidatDateNaissance());

        if (theCandidat.getCandidatNom().equals(getNom()) && theCandidat.getCandidatPrenom().equals(getPrenom()) && theCandidat.getCandidatDateNaissance().equals(getDate())) {
            System.out.println("\n OK");

            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = externalContext.getSessionMap();

            List<Test> theTests = (List<Test>) sessionMap.get("theTests");
            Candidat theCandidat = (Candidat) sessionMap.get("theCandidat");

            for(Test t : theTests){
                System.out.println(t.toString());
            }
            
            // le candidat s'est logué avec succès on peut créer un passage
            Passage p = utils.createPassage(theCandidat, theTests);
            
            utils.printLine("            Create Passage              ");
            int passage_id = p.getPassageid();
            System.out.println("passage_id="+passage_id);
            sessionMap.put("passage_id", passage_id);

            return "helloCandidat";

        } else {
            setError("1");
            System.out.println("\n KO");
            return "log_candidat?faces-redirect=true";
        }
    }

    
    
    
    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
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
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * @param error the error to set
     */
    public void setError(String error) {
        this.error = error;
    }

}
