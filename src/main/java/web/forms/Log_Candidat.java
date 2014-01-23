/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.forms;

import dao.ICandidatDao;
import dao.IPassageDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import jpa.Candidat;
import jpa.Jointure;
import jpa.Passage;
import jpa.Question;
import jpa.Rubrique;
import jpa.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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
    private List<Test> theTests;

    private String nom;
    private String prenom;
    private Date date;

    private String error;
    private String msg_error;
    private IPassageDao passageDao = null;
    private ICandidatDao candidatDao = null;

    private Hashtable<String, Hashtable<Integer, List<Question>>> tab_test;
    private Hashtable<Integer, List<Question>> tab_rub;

    public Log_Candidat() {
        error = "0";
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        passageDao = (IPassageDao) ctx.getBean("passageDao");
        candidatDao = (ICandidatDao) ctx.getBean("candidatDao");
    }

    public String deconnexion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }

    public String Connexion() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();

        // on cherche un candidat ds la base avec nom / prénom /date
        System.out.println("date:" + getDate());
        Candidat cand = getCandidatDao().find(getNom(), getPrenom(), getDate());

        Passage pass = null;
        if (cand == null) {
            setError("1");
            System.out.println("candidat non trouvé");
            setMsg_error("Le candidat n'est pas référencé dans la base / vérifier la saisie ");
        } else {
            System.out.println("candidat:" + cand.toString());
            // le candidat en session
            sessionMap.put("theCandidat", cand);

            // on doit aller chercher le passage en BDD + le passer en session
            pass = getPassageDao().find(cand);
            // on vérifie que le passage est fait ds les temps prévu 
            if (pass == null) {
                System.out.println("passage non trouvé");
                setMsg_error("Aucun passage n'a été créé pour ce candidat");
            } else {
                System.out.println("pass:" + pass.toString());
                // passage_id en session
                sessionMap.put("passage_id", pass.getPassageid());
                theTests = new ArrayList<Test>();
                List<Object> list = Arrays.asList(pass.getJointureCollection().toArray());
                for (Object o : list) {
                    Jointure j = (Jointure) o;
                    Test t = j.getTestid();
                    theTests.add(t);
                }
                // les tests en session
                sessionMap.put("theTests", theTests);
            }
        }

        if (getError().equals("1")) {
            return "log_candidat?faces-redirect=true";
        } else {
            return "helloCandidat";
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

    /**
     * @return the theCandidat
     */
    public Candidat getTheCandidat() {
        return theCandidat;
    }

    /**
     * @param theCandidat the theCandidat to set
     */
    public void setTheCandidat(Candidat theCandidat) {
        this.theCandidat = theCandidat;
    }

    /**
     * @return the msg_error
     */
    public String getMsg_error() {
        return msg_error;
    }

    /**
     * @param msg_error the msg_error to set
     */
    public void setMsg_error(String msg_error) {
        this.msg_error = msg_error;
    }

    /**
     * @return the passageDao
     */
    public IPassageDao getPassageDao() {
        return passageDao;
    }

    /**
     * @param passageDao the passageDao to set
     */
    public void setPassageDao(IPassageDao passageDao) {
        this.passageDao = passageDao;
    }

    /**
     * @return the candidatDao
     */
    public ICandidatDao getCandidatDao() {
        return candidatDao;
    }

    /**
     * @param candidatDao the candidatDao to set
     */
    public void setCandidatDao(ICandidatDao candidatDao) {
        this.candidatDao = candidatDao;
    }

    /**
     * @return the tab_test
     */
    public Hashtable<String, Hashtable<Integer, List<Question>>> getTab_test() {
        return tab_test;
    }

    /**
     * @param tab_test the tab_test to set
     */
    public void setTab_test(Hashtable<String, Hashtable<Integer, List<Question>>> tab_test) {
        this.tab_test = tab_test;
    }

    /**
     * @return the tab_rub
     */
    public Hashtable<Integer, List<Question>> getTab_rub() {
        return tab_rub;
    }

    /**
     * @param tab_rub the tab_rub to set
     */
    public void setTab_rub(Hashtable<Integer, List<Question>> tab_rub) {
        this.tab_rub = tab_rub;
    }

    /**
     * @return the theTests
     */
    public List<Test> getTheTests() {
        return theTests;
    }

    /**
     * @param theTests the theTests to set
     */
    public void setTheTests(List<Test> theTests) {
        this.theTests = theTests;
    }

}
