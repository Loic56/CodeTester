/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import dao.IQuestionDao;
import dao.IRubriqueDao;
import dao.ITestDao;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import jpa.Candidat;
import jpa.Question;
import jpa.Rubrique;
import jpa.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tools.utils;


// tets git 
/**
 *
 * @author Loïc
 */
@ManagedBean(name = "testInfo")
@SessionScoped
public class TestControler implements Serializable {

    private IRubriqueDao rubriqueDao = null;
    private ITestDao testDao = null;
    private IQuestionDao questionDao = null;

    private Hashtable<String, Hashtable<Integer, List<Question>>> tab_test;
    private Hashtable<Integer, List<Question>> tab_rub;

    private Test test;
    private Integer nb_rub;
    private List list_rub;
    private Integer nb_quest;

    private Candidat theCandidat;
    private List<Test> theTests;
    private String testid; // le test en cours parmi la liste de tests

    public TestControler() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        rubriqueDao = (IRubriqueDao) ctx.getBean("rubriqueDao");
        testDao = (ITestDao) ctx.getBean("testDao");
        questionDao = (IQuestionDao) ctx.getBean("questionDao");
    }


    public String DebuterTest() {
        utils.printLine("       DébuterTest        ");
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();

        System.out.println("1:" + (Integer) sessionMap.get("passage_id"));
        System.out.println("2:" + (List<Test>) sessionMap.get("theTests"));
        System.out.println("3:" + (Candidat) sessionMap.get("theCandidat"));
        System.out.println("4:" + ((String) sessionMap.get("testid")));
        int passage_id = ((Integer) sessionMap.get("passage_id"));

        setTheTests((List<Test>) sessionMap.get("theTests"));
        setTheCandidat((Candidat) sessionMap.get("theCandidat"));
        setTestid((String) sessionMap.get("testid")); // l'id du test en cours

        Test t = testDao.find(Long.valueOf(getTestid()));
        // nature du test QCM / CODE ?
        String testNature = t.getTestnature();

        // toutes les rubriques pr un test
        List<Rubrique> list = rubriqueDao.find(t);
        System.out.println("list.size() = " + list.size());

        
        // toutes les questions pr une rubrique
        tab_rub = new Hashtable<Integer, List<Question>>();

        for (Rubrique rub : list) {
            tab_rub.put(rub.getRubriqueid(), questionDao.find(rub));
        }

        // toutes les (rubriques + questions) pour un test
        tab_test = new Hashtable<String, Hashtable<Integer, List<Question>>>();
        tab_test.put(getTestid(), tab_rub);
        sessionMap.put("tab_test", tab_test);

        System.out.println("***************************************");
        System.out.println("    test nature = " + testNature);
        System.out.println("    test id = " + t.getTestid());
        System.out.println("***************************************");

        if (testNature.equals("QCM")) {
            return "QCM";
        } else if (testNature.equals("THEM") | testNature.equals("GEN")) {
            // uniquement pour un type CODE
            // créer un dossier de réception pour les réponse
            String rep = passage_id + "-" + getTheCandidat().getCandidatNom() + "-" + getTheCandidat().getCandidatPrenom();
            //for (Test test : list) {
            //   =  > à rajouter pour plusieurs test
            String dirNamePHP = "C:\\NetBeansProjects\\CodeTester\\PASSAGES\\" + rep + "\\" + getTest().getTheme() + "\\PHP\\";
            String dirNameXML = "C:\\NetBeansProjects\\CodeTester\\PASSAGES\\" + rep + "\\" + getTest().getTheme() + "\\XML\\";
            sessionMap.put("dirNamePHP", dirNamePHP);
            sessionMap.put("dirNameXML", dirNameXML);
            File file = new File(dirNamePHP);
            File file2 = new File(dirNameXML);
            boolean isCreated = file.mkdirs();
            boolean isCreated2 = file2.mkdirs();
            return "theCodeTester";
        } else {
            return "testInfo";
        }
    }

    
    
    
    // les tests en attente
    public String Link() {
        System.out.println("Link()");

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();

        FacesContext fc = FacesContext.getCurrentInstance();
        this.setTestid(fc.getExternalContext().getRequestParameterMap().get("testid"));

        
        
        sessionMap.put("testid", getTestid());

        // le test qui correspond au lien que l'on vient de cliquer
        Test t = testDao.find(Long.valueOf(getTestid()));
        setTest(t);

        List<Rubrique> list = rubriqueDao.find(t);
        setList_rub(list);
        setNb_rub(list.size());
        setNb_quest(t.getTestNbquestionRub());

        return "testInfo";
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
     * @return the nb_rub
     */
    public Integer getNb_rub() {
        return nb_rub;
    }

    /**
     * @param nb_rub the nb_rub to set
     */
    public void setNb_rub(Integer nb_rub) {
        this.nb_rub = nb_rub;
    }

    /**
     * @return the list_rub
     */
    public List getList_rub() {
        list_rub = new ArrayList();
        list_rub = rubriqueDao.find(getTest());
        return list_rub;
    }

    /**
     * @param list_rub the list_rub to set
     */
    public void setList_rub(List list_rub) {
        this.list_rub = list_rub;
    }

    /**
     * @return the nb_quest
     */
    public Integer getNb_quest() {
        return nb_quest;
    }

    /**
     * @param nb_quest the nb_quest to set
     */
    public void setNb_quest(Integer nb_quest) {
        this.nb_quest = nb_quest;
    }

    /**
     * @return the test
     */
    public Test getTest() {
        return test;
    }

    /**
     * @param test the test to set
     */
    public void setTest(Test test) {
        this.test = test;
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
