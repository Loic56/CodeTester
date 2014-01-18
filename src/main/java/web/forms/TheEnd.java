/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.forms;

import dao.IPassageDao;
import dao.ITestDao;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import jpa.Candidat;
import jpa.Passage;
import jpa.Reponse;
import jpa.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tools.utils;

/**
 *
 * @author Loïc
 */
@ManagedBean(name = "theEnd")
@SessionScoped
public class TheEnd implements Serializable {

    private Candidat theCandidat;
    private Test theTest;
    private String testid; // le test en cours parmi la liste de tests

    private ITestDao testDao = null;
    private IPassageDao passageDao = null;

    private ExternalContext externalContext;
    private Map<String, Object> sessionMap;

    private int note;
    private int nbQuest;

    private String theDate;

    public TheEnd() {

        Date date = new Date();
        setTheDate(utils.dateToMySQLString(date));

        externalContext = FacesContext.getCurrentInstance().getExternalContext();
        sessionMap = externalContext.getSessionMap();

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        testDao = (ITestDao) ctx.getBean("testDao");
        passageDao = (IPassageDao) ctx.getBean("passageDao");

        Candidat cand = (Candidat) sessionMap.get("theCandidat");
        System.out.println(cand.toString());

        setTheCandidat(cand);
        setTestid((String) sessionMap.get("testid"));

        //calcul de la note du candidat
        int passage_id = 1; //((Integer) sessionMap.get("passage_id"));
        String test_id = "1"; //((String) sessionMap.get("testid"));

        Test test = testDao.find(Long.valueOf(test_id));
        setTheTest(test);
        Passage passage = passageDao.find(Long.valueOf(passage_id));

        // list de questions par rubriques
        List<Reponse> list = utils.findReponses(passage, test);
        setNbQuest(list.size());

        System.out.println("list = " + list.size());
        for (Reponse r : list) {
            note = note + (Integer.parseInt(r.getReponsemessage()));
        }

        System.out.println("note = " + note);
        System.out.println("note = " + note);
        System.out.println("note = " + note);
        System.out.println("note = " + note);
        System.out.println("note = " + note);

    }

    public String retourAccueil() {
        // on enlève le test de la list 
        Test test = testDao.find(Long.valueOf(getTestid()));
        List<Test> theTests = ((List<Test>) sessionMap.get("theTests"));
        theTests.remove(test);
        // on remet la list en session
        sessionMap.put("theTests", theTests);

        return "index?faces-redirect=true";

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
     * @return the testid
     */
    public String getTestid() {
        return testid;
    }

    /**
     * @param testid the testid to set
     */
    public void setTestid(String testid) {
        this.testid = testid;
    }

    /**
     * @return the note
     */
    public int getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(int note) {
        this.note = note;
    }

    /**
     * @return the theTest
     */
    public Test getTheTest() {
        return theTest;
    }

    /**
     * @param theTest the theTest to set
     */
    public void setTheTest(Test theTest) {
        this.theTest = theTest;
    }

    /**
     * @return the nbQuest
     */
    public int getNbQuest() {
        return nbQuest;
    }

    /**
     * @param nbQuest the nbQuest to set
     */
    public void setNbQuest(int nbQuest) {
        this.nbQuest = nbQuest;
    }

    /**
     * @return the theDate
     */
    public String getTheDate() {
        return theDate;
    }

    /**
     * @param theDate the theDate to set
     */
    public void setTheDate(String theDate) {
        this.theDate = theDate;
    }

}
