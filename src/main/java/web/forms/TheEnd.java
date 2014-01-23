/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.forms;

import dao.IPassageDao;
import dao.ITestDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
    private List<Test> theTests;
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
        int passage_id = ((Integer) sessionMap.get("passage_id"));
        String test_id = ((String) sessionMap.get("testid"));

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
    }

    public void retourAccueil() {
        Test myTest = getTestDao().find(Long.valueOf(getTestid()));

        setExternalContext(FacesContext.getCurrentInstance().getExternalContext());
        setSessionMap(getExternalContext().getSessionMap());

        List<Test> theTests = ((List<Test>) getSessionMap().get("theTests"));
        List<Test> theTestsAfter = new ArrayList<Test>();

        try {
            System.out.println("  ? ? " + getSessionMap().get("theTests"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        getSessionMap().remove("theTests");

        try {
            System.out.println("  ? ? " + getSessionMap().get("theTests"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            for (Test t : theTests) {
                if (t.equals(myTest)) {
                    theTests.remove(myTest);
                } else {
                    theTestsAfter.add(t);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("theTestsAfter.size() : " + theTestsAfter.size());

        System.out.println("-------------------------------");
// on remet la liste en session
        getSessionMap().put("theTests", theTestsAfter);

        for(Test t : (List<Test>)getSessionMap().get("theTests")){
             System.out.println(" >> "+t.toString() );
        }
    
                
        String url = "http://localhost:8080/CodeTester/faces/helloCandidat.xhtml";
        utils.redirect(url);

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
     * @return the externalContext
     */
    public ExternalContext getExternalContext() {
        return externalContext;
    }

    /**
     * @param externalContext the externalContext to set
     */
    public void setExternalContext(ExternalContext externalContext) {
        this.externalContext = externalContext;
    }

    /**
     * @return the sessionMap
     */
    public Map<String, Object> getSessionMap() {
        return sessionMap;
    }

    /**
     * @param sessionMap the sessionMap to set
     */
    public void setSessionMap(Map<String, Object> sessionMap) {
        this.sessionMap = sessionMap;
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
