/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.forms;

import dao.IPassageDao;
import dao.ITestDao;
import java.io.File;
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
    private List<Reponse> listReponse;
    private Test theTest;

    public Recapitulatif() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        testDao = (ITestDao) ctx.getBean("testDao");
        passageDao = (IPassageDao) ctx.getBean("passageDao");

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();

        int passage_id = 1; //((Integer) sessionMap.get("passage_id"));
        String test_id = "1"; //((String) sessionMap.get("testid"));

        Test test = testDao.find(Long.valueOf(passage_id));
        setTheTest(test);
        Passage passage = passageDao.find(Long.valueOf(test_id));

        // list de questions par rubriques
        List<Reponse> list = utils.findReponses(passage, test);
        //       System.out.println("list.size: " + list.size());
        setListReponse(list);

//        for (Reponse r : getListReponse()) {
//            System.out.println(r.toString());
//        }
        // String image = "enonces_PHP/boucles/small/boucle_" + id_quest + ".JPG";
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

}
