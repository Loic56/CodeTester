/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.createTest;

import dao.ICategorieDao;
import dao.ITestDao;
import java.io.Serializable;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import jpa.Categorie;
import jpa.Rubrique;
import jpa.Test;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Loïc
 */
@ManagedBean(name = "create1")
@SessionScoped
public class CreateTest_1 implements Serializable {

    // page1
    private String type, theType;

    private String categorie;
    private String theme;
    private String duree;
    private String niveau;
    private String description;

    // le test en cours
    private Test theTest;

    // dao
    private ICategorieDao categorieDao = null;
    private ITestDao testDao = null;

    public CreateTest_1() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        categorieDao = (ICategorieDao) ctx.getBean("categorieDao");
        testDao = (ITestDao) ctx.getBean("testDao");
    }

 

    public String creerTest() {
        System.out.println("suivant");
        Test test = new Test();
        String matiere = "";
        String type = "";
        Categorie cat = getCategorieDao().find(Long.valueOf(getCategorie()));
        test.setCategorieid(cat);
        if (cat.getCategorieid() == 1) {
            matiere = "Développement PHP";
        } else if (cat.getCategorieid() == 2) {
            matiere = "Développement JAVA";
        } else if (cat.getCategorieid() == 3) {
            matiere = "Développement SQL";
        }
        if (getType().equals("2")) {
            setTheType("QCM");
        } else if (getType().equals("1")) {
            setTheType("CODE");
        }
        test.setNiveau(Integer.parseInt(getNiveau()));
        test.setTestDescription(getDescription());
        test.setTestduree(Short.valueOf(getDuree()));
        test.setTestmatiere(matiere);
        test.setTestformat(getType());
        test.setTheme(getTheme());
        Test theTest = getTestDao().create(test);
        setTheTest(theTest);

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        sessionMap.put("theTest", theTest);
        sessionMap.put("theType", getType());

        return "createTest_2?faces-redirect=true";
    }

    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile uploadedFile = event.getFile();
        if (uploadedFile != null) {
            System.out.println(uploadedFile.getFileName());
        } else {
            System.out.println("The file object is null.");
        }
//        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void handleFileUploadDeuz(FileUploadEvent event) {
        UploadedFile uploadedFile = event.getFile();
        if (uploadedFile != null) {
            System.out.println(uploadedFile.getFileName());
        } else {
            System.out.println("The file object is null.");
        }
//        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the categorie
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * @param categorie the categorie to set
     */
    public void setCategorie(String categorie) {
        this.categorie = categorie;
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
     * @return the niveau
     */
    public String getNiveau() {
        return niveau;
    }

    /**
     * @param niveau the niveau to set
     */
    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    /**
     * @return the decsription
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param decsription the decsription to set
     */
    public void setDescription(String description) {
        this.description = description;
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
     * @return the categorieDao
     */
    public ICategorieDao getCategorieDao() {
        return categorieDao;
    }

    /**
     * @param categorieDao the categorieDao to set
     */
    public void setCategorieDao(ICategorieDao categorieDao) {
        this.categorieDao = categorieDao;
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
     * @param selectedTreeNode the selectedTreeNode to set
     */
    public void setSelectedTreeNode(TreeNode[] selectedTreeNode) {
        this.setSelectedTreeNode(selectedTreeNode);
    }

    /**
     * @return the theType
     */
    public String getTheType() {
        return theType;
    }

    /**
     * @param theType the theType to set
     */
    public void setTheType(String theType) {
        this.theType = theType;
    }

}
