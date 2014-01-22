/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.forms;

import dao.IPassageDao;
import dao.IReponseDao;
import dao.ITestDao;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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
@ManagedBean(name = "recap")
@SessionScoped
public class Recapitulatif implements Serializable {

    /// on doit récupérer toutes les réponses pour un passage pour un test
    private ITestDao testDao = null;
    private IPassageDao passageDao = null;
    private IReponseDao reponseDao = null;
    private List<Reponse> listReponse;
    private Test theTest;
    private Passage thepassage;

    public Recapitulatif() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        testDao = (ITestDao) ctx.getBean("testDao");
        passageDao = (IPassageDao) ctx.getBean("passageDao");
        reponseDao = (IReponseDao) ctx.getBean("reponseDao");

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();

        int passage_id = ((Integer) sessionMap.get("passage_id"));
        String test_id = ((String) sessionMap.get("testid"));
        System.out.println("passage_id=" + passage_id + " test_id=" + test_id);

        setTheTest(testDao.find(Long.valueOf(passage_id)));
        setThepassage(passageDao.find(Long.valueOf(passage_id)));

        // String image = "enonces_PHP/boucles/small/boucle_" + id_quest + ".JPG";
    }

    public void Tester() {
        System.out.println("test=" + getTheTest().toString());
        System.out.println("passage=" + getThepassage().toString());

        try {
            // list de questions par rubriques
            List<Reponse> list = reponseDao.findAll();
            if (list != null) {
                System.out.println("size=" + list.size());
            } else {
                System.out.println("size=null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // list de questions par rubriques
            List<Reponse> list2 = reponseDao.find(getThepassage(), getTheTest());
            if (list2 != null) {
                System.out.println("size2=" + list2.size());
            } else {
                System.out.println("size2=null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // list de questions par rubriques
            Passage p = passageDao.find(Long.valueOf(1));
            Test t = testDao.find(Long.valueOf(6));

            List<Reponse> list3 = reponseDao.find(p, t);
            if (list3 != null) {
                System.out.println("size3=" + list3.size());
            } else {
                System.out.println("size3=null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

}
