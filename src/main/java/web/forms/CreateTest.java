/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.forms;

import dao.ICategorieDao;
import dao.IPassageDao;
import dao.IRubriqueDao;
import dao.ITestDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import jpa.Categorie;
import jpa.Question;
import jpa.Rubrique;
import jpa.Test;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tools.Document;

/**
 *
 * @author Loïc
 */
@ManagedBean(name = "create")
@SessionScoped
public class CreateTest implements Serializable {

    // page1
    private String type;
    private String categorie;
    private String theme;
    private String duree;
    private String niveau;
    private String description;

    //page2
    private String nomRubrique;
    private List<Rubrique> listRubrique;

    // le test en session
    private Test theTest;
    // 
    private TreeNode root;
    private Document selectedDocument;

    private ICategorieDao categorieDao = null;
    private ITestDao testDao = null;
    private IRubriqueDao rubriqueDao = null;

    public CreateTest() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Test test = (Test) sessionMap.get("test");
        setTheTest(test);

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        categorieDao = (ICategorieDao) ctx.getBean("categorieDao");
        testDao = (ITestDao) ctx.getBean("testDao");
        rubriqueDao = (IRubriqueDao) ctx.getBean("rubriqueDao");

        root = new DefaultTreeNode("root", null);

        // toutes les rubriques pour un test
        List<Rubrique> list = rubriqueDao.find(getTheTest());
        if (list != null) {
            for (Rubrique r : list) {
                TreeNode rubriques = new DefaultTreeNode(r.getRubriquenom(), getRoot());

                Collection<Question> col = r.getQuestionCollection();
                int index = 1;
                for (Question q : col) {
                    TreeNode question_recp = new DefaultTreeNode(index + " - " + q.getQuestiontext(), rubriques);
                }
            }

        }

//        TreeNode documents = new DefaultTreeNode(new Document("Documents", "-", "Folder"), getRoot());
//        TreeNode pictures = new DefaultTreeNode(new Document("Pictures", "-", "Folder"), getRoot());
//        TreeNode music = new DefaultTreeNode(new Document("Music", "-", "Folder"), getRoot());
//        TreeNode work = new DefaultTreeNode(new Document("Work", "-", "Folder"), documents);
//        TreeNode primefaces = new DefaultTreeNode(new Document("PrimeFaces", "-", "Folder"), documents);
//
//        //Documents  
//        TreeNode expenses = new DefaultTreeNode("document", new Document("Expenses.doc", "30 KB", "Word Document"), work);
//        TreeNode resume = new DefaultTreeNode("document", new Document("Resume.doc", "10 KB", "Word Document"), work);
//        TreeNode refdoc = new DefaultTreeNode("document", new Document("RefDoc.pages", "40 KB", "Pages Document"), primefaces);
//
//        //Pictures  
//        TreeNode barca = new DefaultTreeNode("picture", new Document("barcelona.jpg", "30 KB", "JPEG Image"), pictures);
//        TreeNode primelogo = new DefaultTreeNode("picture", new Document("logo.jpg", "45 KB", "JPEG Image"), pictures);
//        TreeNode optimus = new DefaultTreeNode("picture", new Document("optimusprime.png", "96 KB", "PNG Image"), pictures);
//
//        //Music  
//        TreeNode turkish = new DefaultTreeNode(new Document("Turkish", "-", "Folder"), music);
//
//        TreeNode cemKaraca = new DefaultTreeNode(new Document("Cem Karaca", "-", "Folder"), turkish);
//        TreeNode erkinKoray = new DefaultTreeNode(new Document("Erkin Koray", "-", "Folder"), turkish);
//        TreeNode mogollar = new DefaultTreeNode(new Document("Mogollar", "-", "Folder"), turkish);
//
//        TreeNode nemalacak = new DefaultTreeNode("mp3", new Document("Nem Alacak Felek Benim", "1500 KB", "Audio File"), cemKaraca);
//        TreeNode resimdeki = new DefaultTreeNode("mp3", new Document("Resimdeki Gozyaslari", "2400 KB", "Audio File"), cemKaraca);
//
//        TreeNode copculer = new DefaultTreeNode("mp3", new Document("Copculer", "2351 KB", "Audio File"), erkinKoray);
//        TreeNode oylebirgecer = new DefaultTreeNode("mp3", new Document("Oyle bir Gecer", "1794 KB", "Audio File"), erkinKoray);
//
//        TreeNode toprakana = new DefaultTreeNode("mp3", new Document("Toprak Ana", "1536 KB", "Audio File"), mogollar);
//        TreeNode bisiyapmali = new DefaultTreeNode("mp3", new Document("Bisi Yapmali", "2730 KB", "Audio File"), mogollar);
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
        if (getType().equals("1")) {
            type = "QCM";
        } else if (getType().equals("1")) {
            type = "CODE";
        }
        test.setNiveau(Integer.parseInt(getNiveau()));
        test.setTestDescription(getDescription());
        test.setTestduree(Short.valueOf(getDuree()));
        test.setTestmatiere(matiere);
        test.setTestformat(getType());
        test.setTheme(getTheme());

        Test theTest = getTestDao().create(test);

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        sessionMap.put("test", theTest);

        return "createTest_2?faces-redirect=true";
    }

    public String creerRubrique() {
        System.out.println("creerRubrique");

        Rubrique rubrique = new Rubrique();
        rubrique.setRubriquenom(getNomRubrique());
        rubrique.setTestid(getTheTest());
        Collection<Question> collection = new ArrayList<Question>();
        rubrique.setQuestionCollection(collection);
        Rubrique theRubrique = getRubriqueDao().create(rubrique);

        // getListRubrique().add(theRubrique);
        return "createTest_2?faces-redirect=true";
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
     * @return the root
     */
    public TreeNode getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(TreeNode root) {
        this.root = root;
    }

    /**
     * @return the selectedDocument
     */
    public Document getSelectedDocument() {
        return selectedDocument;
    }

    /**
     * @param selectedDocument the selectedDocument to set
     */
    public void setSelectedDocument(Document selectedDocument) {
        this.selectedDocument = selectedDocument;
    }

    /**
     * @return the listRubrique
     */
    public List<Rubrique> getListRubrique() {
        return listRubrique;
    }

    /**
     * @param listRubrique the listRubrique to set
     */
    public void setListRubrique(List<Rubrique> listRubrique) {
        this.listRubrique = listRubrique;
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
}
