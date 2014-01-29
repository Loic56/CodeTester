/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.bean;

import dao.IAdminDao;
import dao.ICandidatDao;
import dao.IJointureDao;
import dao.IPassageDao;
import dao.ITestDao;
import java.io.Serializable;
import jpa.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import jpa.Candidat;
import jpa.Jointure;
import jpa.Passage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tools.Utils;

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

    private Map<String, Object> sessionMap;
    private ExternalContext externalContext;

    public String Retour() {
        return "test?faces-redirect=true";
    }

    public String Annuler() {
        return "candidat?faces-redirect=true";
    }

    public InfoReservation() {
        Utils.printLine("InfoReservation");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        candidatDao = (ICandidatDao) ctx.getBean("candidatDao");
        testDao = (ITestDao) ctx.getBean("testDao");
        passageDao = (IPassageDao) ctx.getBean("passageDao");
        jointureDao = (IJointureDao) ctx.getBean("jointureDao");

        listCandidat = new ArrayList<Candidat>();

        externalContext = FacesContext.getCurrentInstance().getExternalContext();
        sessionMap = externalContext.getSessionMap();


        //System.out.println("theTests.size() = " + theTests.size());
        setTheCandidat((Candidat) sessionMap.get("theCandidat"));
        setTheTests((List<Test>) sessionMap.get("theTests"));
        
        
        
        
        
        listCandidat.add(theCandidat);
        for (Test test : theTests) {
            // System.out.println("test : " + test.toString());
            duree = duree + test.getTestduree();
        }
        dureeTotale = String.valueOf(duree);
    }

    
    
    public String Suivant() {
        // passage validé par l'admin - on enregistre le passage ds la BDD
        Passage p = Utils.createPassage(getTheCandidat(), getTheTests());
        // le passage est référencé ds la BDD on supprime les variables de session
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
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
        List<Test> list = (List<Test>) getSessionMap().get("theTests");
        return list;
    }

    /**
     * @param theTests the theTests to set
     */
    public void setTheTests(List<Test> theTests) {
        this.theTests = theTests;
    }

    /**
     * @return the listJointure
     */
    public List<Jointure> getListJointure() {
        return listJointure;
    }

    /**
     * @param listJointure the listJointure to set
     */
    public void setListJointure(List<Jointure> listJointure) {
        this.listJointure = listJointure;
    }

    /**
     * @return the duree
     */
    public Integer getDuree() {
        return duree;
    }

    /**
     * @param duree the duree to set
     */
    public void setDuree(Integer duree) {
        this.duree = duree;
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
     * @return the testDao
     */
    public ITestDao getTestDao() {
        return testDao;
    }

    /**
     * @param testDao the testDao to set
     */
    public void setTestDao(ITestDao testDao) {
        this.testDao = testDao;
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
     * @return the jointureDao
     */
    public IJointureDao getJointureDao() {
        return jointureDao;
    }

    /**
     * @param jointureDao the jointureDao to set
     */
    public void setJointureDao(IJointureDao jointureDao) {
        this.jointureDao = jointureDao;
    }

    /**
     * @return the sessionMap
     */
    public Map<String, Object> getSessionMap() {
        return getExternalContext().getSessionMap();
    }

    /**
     * @param sessionMap the sessionMap to set
     */
    public void setSessionMap(Map<String, Object> sessionMap) {
        this.sessionMap = sessionMap;
    }

    /**
     * @return the externalContext
     */
    public ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    /**
     * @param externalContext the externalContext to set
     */
    public void setExternalContext(ExternalContext externalContext) {
        this.externalContext = externalContext;
    }

}
