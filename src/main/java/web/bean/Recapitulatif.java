/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.bean;

import dao.IPassageDao;
import dao.IReponseDao;
import dao.ITestDao;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import jpa.Passage;
import jpa.Reponse;
import jpa.Test;

/**
 *
 * @author Loïc
 */
@ManagedBean(name = "recap")
@SessionScoped
public class Recapitulatif extends BeanAdapter{

    /// on doit récupérer toutes les réponses pour un passage pour un test
    private ITestDao testDao = null;
    private IPassageDao passageDao = null;
    private IReponseDao reponseDao = null;
    private List<Reponse> listReponse;
    private Test theTest;
    private Passage thepassage;
    private String test_format;

    
    
    public Recapitulatif() {
   

        int passage_id = ((Integer) sharedData().get("passage_id"));
        String test_id = ((String) sharedData().get("testid"));
        System.out.println("passage_id=" + passage_id + " \ntest_id=" + test_id);

        setTheTest(TESTDAO.find(Long.valueOf(test_id)));
        setThepassage(PASSAGEDAO.find(Long.valueOf(passage_id)));
        if(TESTDAO.find(Long.valueOf(test_id)).getTestformat().equals("QCM"))
        setTest_format("1");

        try {
            Passage p = PASSAGEDAO.find(Long.valueOf(getThepassage().getPassageid().toString()));
            Test t = TESTDAO.find(Long.valueOf(getTheTest().getTestid().toString()));

            List<Reponse> list = REPONSEDAO.find(p, t);
            listReponse = new ArrayList<Reponse>();
            if (list != null) {
                for (Reponse rep : list) {
                    listReponse.add(rep);
                }
            } else {
                System.out.println("list=null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Tester() {

    }

    /**
     * @return the list
     */
    public List<Reponse> getListReponse() {
        return listReponse;
    }

    public String FinishHim() {
        return "theEnd?faces-redirect=true";
    }

    /**
     * @param list the list to set
     */
    public void setListReponse(List<Reponse> listReponse) {
        this.listReponse = listReponse;
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
     * @return the reponseDao
     */
    public IReponseDao getReponseDao() {
        return reponseDao;
    }

    /**
     * @param reponseDao the reponseDao to set
     */
    public void setReponseDao(IReponseDao reponseDao) {
        this.reponseDao = reponseDao;
    }

    /**
     * @return the thepassage
     */
    public Passage getThepassage() {
        return thepassage;
    }

    /**
     * @param thepassage the thepassage to set
     */
    public void setThepassage(Passage thepassage) {
        this.thepassage = thepassage;
    }

    /**
     * @return the test_format
     */
    public String getTest_format() {
        return test_format;
    }

    /**
     * @param test_format the test_format to set
     */
    public void setTest_format(String test_format) {
        this.test_format = test_format;
    }

}
