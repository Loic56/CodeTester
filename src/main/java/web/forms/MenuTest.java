/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.forms;

import dao.ITestDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import jpa.Test;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tools.Utils;

/**
 *
 * @author Loïc
 */
@ManagedBean(name = "menuTest")
@SessionScoped
public class MenuTest implements Serializable {

    private MenuModel model;
    private ITestDao testDao = null;

    private int id;
    private String description;
    private String theme;
    private String duree;
    private String niv;

    private String testIsSelected;

    private Test theTest;
    private ArrayList<Test> listTest = new ArrayList<Test>();

    private List<Test> list;

    
    
    
    public MenuTest() {
        testIsSelected = "0";
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        testDao = (ITestDao) ctx.getBean("testDao");

        this.model = new DefaultMenuModel();
        //PHP
        DefaultSubMenu firstSubmenu = new DefaultSubMenu("PHP");
        DefaultSubMenu subSubmenu1 = new DefaultSubMenu("PHP - QCM");
        DefaultSubMenu subSubmenu2 = new DefaultSubMenu("PHP - CODE");
        firstSubmenu.addElement(subSubmenu1);
        firstSubmenu.addElement(subSubmenu2);
        DefaultMenuItem item = null;

        list = testDao.findAll();

        for (Test test : list) {
            if (test.getCategorieid().getCategorielibelle().equals("PHP") && test.getTestformat().equals("CODE")) {
                item = new DefaultMenuItem(test.getTheme());
                item.setIcon("ui-icon-home");
                subSubmenu2.addElement(item);
                String id = String.valueOf(test.getTestid());
                item.setCommand("#{menuTest.AfficheInfo(" + id + ")}");
            }
        }

        for (Test test : list) {
            if (test.getCategorieid().getCategorielibelle().equals("PHP") && test.getTestformat().equals("QCM")) {
                item = new DefaultMenuItem(test.getTheme());
                item.setIcon("ui-icon-home");
                subSubmenu1.addElement(item);
                String id = String.valueOf(test.getTestid());
                item.setCommand("#{menuTest.AfficheInfo(" + id + ")}");
            }
        }
        model.addElement(firstSubmenu);

        //JAVA 
        DefaultSubMenu secondSubmenu = new DefaultSubMenu("JAVA");
        DefaultSubMenu secondSubSubmenu1 = new DefaultSubMenu("JAVA - QCM");
        DefaultSubMenu secondSubmenu2 = new DefaultSubMenu("JAVA - CODE");
        secondSubmenu.addElement(secondSubSubmenu1);
        secondSubmenu.addElement(secondSubmenu2);
        model.addElement(secondSubmenu);

        
        
        //SQL 
        DefaultSubMenu thirdSubmenu = new DefaultSubMenu("SQL");
        DefaultSubMenu thirdSubSubmenu1 = new DefaultSubMenu("SQL - QCM");
        DefaultSubMenu thirdSubSubmenu2 = new DefaultSubMenu("SQL - CODE");
        thirdSubmenu.addElement(thirdSubSubmenu1);
        thirdSubmenu.addElement(thirdSubSubmenu1);
        model.addElement(thirdSubmenu);

    }

    public String AfficheInfo(String id) {
        System.out.println("id = " + id);
        setTheTest(testDao.find(Long.valueOf(id)));
        setTheme(getTheTest().getTheme());
        setDescription(getTheTest().getTestDescription());
        setDuree(String.valueOf(getTheTest().getTestduree()));
        setNiv(String.valueOf(getTheTest().getNiveau()));
        setTestIsSelected("1");
        return "test?faces-redirect=true";
    }

    public String Annuler() {
        return "candidat";
    }

    public String Suivant() {
        // on mets la liste de tests en session
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        sessionMap.put("theTests", getListTest());

        Utils.printLine(" Liste des tests ");
        System.out.println("nb de test : " + getListTest().size());
        return "info_reservation?faces-redirect=true";
    }

    public String Choisir() {
        setTestIsSelected("2");
        getListTest().add(getTheTest());
        return "test?faces-redirect=true";
    }

    /**
     * @param aListTest the listTest to set
     */
    public void setListTest(ArrayList<Test> aListTest) {
        listTest = aListTest;
    }

    /**
     * @return the listTest
     */
    public ArrayList<Test> getListTest() {
        return listTest;
    }

    public Test getTheTest() {
        return theTest;
    }

    public String Reinit() {
        getListTest().clear();
        setTestIsSelected("0");
        return "test?faces-redirect=true";
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }

    public MenuModel getModel() {
        return model;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the theme
     */
    public String getTheme() {
        return theme;
    }

    /**
     * @param theme the theme to set
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     * @return the duree
     */
    public String getDuree() {
        return duree;
    }

    /**
     * @param duree the duree to set
     */
    public void setDuree(String duree) {
        this.duree = duree;
    }

    /**
     * @return the niv
     */
    public String getNiv() {
        return niv;
    }

    /**
     * @param niv the niv to set
     */
    public void setNiv(String niv) {
        this.niv = niv;
    }

    /**
     * @return the testIsSelected
     */
    public String getTestIsSelected() {
        return testIsSelected;
    }

    /**
     * @param testIsSelected the testIsSelected to set
     */
    public void setTestIsSelected(String testIsSelected) {
        this.testIsSelected = testIsSelected;
    }

    /**
     * @param theTest the theTest to set
     */
    public void setTheTest(Test theTest) {
        this.theTest = theTest;
    }

}
