/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.createTest;

import dao.IRubriqueDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import jpa.Question;
import jpa.Rubrique;
import jpa.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tools.Utils;

/**
 *
 * @author Loïc
 */
@ManagedBean(name = "create2")
@SessionScoped
public class CreateTest_2 implements Serializable {

    private IRubriqueDao rubriqueDao = null;
    // private ITestDao testDao = null;

    //page2
    private String nomRubrique;
    private List<Rubrique> listRubrique;
    private Rubrique rubToSup;

    // le test en cours
    private Test theTest;

    public CreateTest_2() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        rubriqueDao = (IRubriqueDao) ctx.getBean("rubriqueDao");
        // testDao = (ITestDao) ctx.getBean("testDao");
        listRubrique = new ArrayList<Rubrique>();

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        setTheTest((Test) sessionMap.get("theTest"));
    }
    
    public void suivant(){
       String url = "http://localhost:8080/CodeTester/faces/createTest_3.xhtml";
        Utils.redirect(url);
    }
    

    public void creerRubrique() {
        System.out.println(" *********************************************** ");
        System.out.println("creerRubrique");

        // on créé une nouvelle rubrique
        Rubrique rubrique = new Rubrique();
        rubrique.setRubriquenom(getNomRubrique());
        System.out.println("le test en cours : " + getTheTest());
        rubrique.setTestid(getTheTest());

        List<Question> list = new ArrayList<Question>();
        Set<Question> collection = new HashSet<Question>(list);
        rubrique.setQuestionCollection(collection);

        // persist
        Rubrique theRubrique = getRubriqueDao().create(rubrique);
        System.out.println("on ajoute : " + theRubrique);

        //on l'ajoute à la liste
        getListRubrique().add(theRubrique);

        for (Rubrique r : getListRubrique()) {
            System.out.println(" >> " + r.toString());
        }

        // on met la liste en session
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        sessionMap.put("theRubriques", getListRubrique());

        //raz inputtext
        setNomRubrique("");
        System.out.println(" *********************************************** ");
        String url = "http://localhost:8080/CodeTester/faces/createTest_2.xhtml";
        Utils.redirect(url);
    }

    public void deleteRubrique() {
        FacesContext fc = FacesContext.getCurrentInstance();
        String s = fc.getExternalContext().getRequestParameterMap().get("rubToSup");

        System.out.println("rubToSup = " + s);
        Rubrique r = getRubriqueDao().find(Long.valueOf(s));
        String sup = "";
        Rubrique rubr = null;

        for (Rubrique rub : getListRubrique()) {
            if (rub.equals(r)) {
                sup = "ok";
                rubr = rub;
            }
        }
        if (sup.equals("ok")) {
            System.out.println("ok");
            getListRubrique().remove(rubr);
        }

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        sessionMap.put("theRubriques", getListRubrique());

        String url = "http://localhost:8080/CodeTester/faces/createTest_2.xhtml";
        Utils.redirect(url);
    }

    /**
     * @return the rubriqueDao
     */
    public IRubriqueDao getRubriqueDao() {
        return rubriqueDao;
    }

    /**
     * @param rubriqueDao the rubriqueDao to set
     */
    public void setRubriqueDao(IRubriqueDao rubriqueDao) {
        this.rubriqueDao = rubriqueDao;
    }

    /**
     * @return the nomRubrique
     */
    public String getNomRubrique() {
        return nomRubrique;
    }

    /**
     * @param nomRubrique the nomRubrique to set
     */
    public void setNomRubrique(String nomRubrique) {
        this.nomRubrique = nomRubrique;
    }

    /**
     * @return the listRubrique
     */
    public List<Rubrique> getListRubrique() {
        return rubriqueDao.find(getTheTest()); // return listRubrique;
    }

    /**
     * @param listRubrique the listRubrique to set
     */
    public void setListRubrique(List<Rubrique> listRubrique) {
        this.listRubrique = listRubrique;
    }

    /**
     * @return the rubToSup
     */
    public Rubrique getRubToSup() {
        return rubToSup;
    }

    /**
     * @param rubToSup the rubToSup to set
     */
    public void setRubToSup(Rubrique rubToSup) {
        this.rubToSup = rubToSup;
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
