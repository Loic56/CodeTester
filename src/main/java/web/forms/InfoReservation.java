/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.forms;

import dao.IAdminDao;
import dao.ICandidatDao;
import dao.IJointureDao;
import dao.IPassageDao;
import dao.IQuestionDao;
import dao.IRubriqueDao;
import dao.ITestDao;
import java.io.Serializable;
import jpa.Test;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import jpa.Candidat;
import jpa.Jointure;
import jpa.Passage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tools.utils;

/**
 *
 * @author Loïc
 */
@ManagedBean(name = "infoResa")
@SessionScoped
public class InfoReservation implements Serializable {

    // private List<Test> listTest;
    private Candidat theCandidat;
    private List<Test> theTests;
    private List<Jointure> listJointure;

    private List<Candidat> listCandidat;
    private String dureeTotale;

    private Integer duree = 0;

    private ICandidatDao candidatDao = null;
    private ITestDao testDao = null;
    private IPassageDao passageDao = null;
    private IJointureDao jointureDao = null;

    public String Retour() {
        return "test?faces-redirect=true";
    }

    public String Annuler() {
        return "candidat?faces-redirect=true";
    }

    public InfoReservation() {
        utils.printLine("InfoReservation");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        candidatDao = (ICandidatDao) ctx.getBean("candidatDao");
        testDao = (ITestDao) ctx.getBean("testDao");
        passageDao = (IPassageDao) ctx.getBean("passageDao");
        jointureDao = (IJointureDao) ctx.getBean("jointureDao");

        listCandidat = new ArrayList<Candidat>();

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        this.theTests = ((List<Test>) sessionMap.get("theTests"));
        this.theCandidat = (Candidat) sessionMap.get("theCandidat");

        listCandidat.add(theCandidat);
        for (Test test : theTests) {
            System.out.println("test : " + test.toString());
            duree = duree + test.getTestduree();
        }
        dureeTotale = String.valueOf(duree);
    }

    public String Suivant() {
        System.out.println("Suivant()");

        /// On créé un passage avec un candidat et une list de tests + persist
//        Passage passage = new Passage();
//        passage.setCandidatid(candidatDao.find(Long.valueOf(getTheCandidat().getCandidatid())));
//
//        listJointure = new ArrayList<Jointure>();
//        for (Test test : getTheTests()) {
//            Jointure j = new Jointure();
//            j.setTestid(test);
//            Jointure jPersist = jointureDao.create(j);
//            listJointure.add(jPersist);
//        }
//
//        Set<Jointure> jointureToSet = new HashSet<Jointure>(listJointure);  
//        passage.setJointureCollection(jointureToSet);
//        Passage thePassage = passageDao.create(passage);
//
//        // persist
//        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//        Map<String, Object> sessionMap = externalContext.getSessionMap();
//        sessionMap.put("thePassage", passage);
        // créer une hashmap pour indexer les tests en session
        return "log_candidat?faces-redirect=true";
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
     * @return the listCandidat
     */
    public List<Candidat> getListCandidat() {
        return listCandidat;
    }

    /**
     * @param listCandidat the listCandidat to set
     */
    public void setListCandidat(List<Candidat> listCandidat) {
        this.listCandidat = listCandidat;
    }

    /**
     * @return the dureeTotale
     */
    public String getDureeTotale() {
        return dureeTotale;
    }

    /**
     * @param dureeTotale the dureeTotale to set
     */
    public void setDureeTotale(String dureeTotale) {
        this.dureeTotale = dureeTotale;
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
