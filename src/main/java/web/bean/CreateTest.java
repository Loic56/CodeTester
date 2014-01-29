/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.bean;

import dao.ICategorieDao;
import dao.IPassageDao;
import dao.IPropositionDao;
import dao.IRubriqueDao;
import dao.ITestDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import jpa.Categorie;
import jpa.Proposition;
import jpa.Question;
import jpa.Rubrique;
import jpa.Test;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tools.Document;
import tools.Utils;

/**
 *
 * @author Loïc
 */
@ManagedBean(name = "create")
@ViewScoped
public class CreateTest implements Serializable {

    // page1
    private String type, theType;
    private String categorie;
    private String theme;
    private String duree;
    private String niveau;
    private String description;

    //page2
    private String nomRubrique;
    private List<Rubrique> listRubrique;
    private Rubrique rubToSup;

    private String theProposition;
    private String propositionEtat;
    private List<Proposition> listProposition;

    // le test en cours
    private Test theTest;

    // 
    private TreeNode root;
    private Document selectedDocument;
    private TreeNode[] selectedTreeNode;

    // dao
    private ICategorieDao categorieDao = null;
    private ITestDao testDao = null;
    private IRubriqueDao rubriqueDao = null;
    private IPropositionDao propositionDao = null;

    // création d'une question
    private String enonce;
    private String code_enonce;

    public CreateTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        categorieDao = (ICategorieDao) ctx.getBean("categorieDao");
        testDao = (ITestDao) ctx.getBean("testDao");
        rubriqueDao = (IRubriqueDao) ctx.getBean("rubriqueDao");
        propositionDao = (IPropositionDao) ctx.getBean("propositionDao");

        listRubrique = new ArrayList<Rubrique>();
        root = new DefaultTreeNode("root", null);
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

        String url = "http://localhost:8080/CodeTester/faces/createTest_2.xhtml";
        Utils.redirect(url);
    }

    public void onRowSelect(SelectEvent event) {
        Rubrique r = (Rubrique) event.getObject();
        System.out.println("Suppression => id_rub = " + r.getRubriqueid());
    }

    public void creerRubrique() {
        System.out.println("creerRubrique");
        // on supprime tous les noeuds existants
        List<TreeNode> nodes = getRoot().getChildren();
        Iterator<TreeNode> i = nodes.iterator();
        while (i.hasNext()) {
            TreeNode t = i.next();
            // Use isLeaf() method to check doesn't have childs.
            i.remove();
        }
        Rubrique rubrique = new Rubrique();
        rubrique.setRubriquenom(getNomRubrique());
        rubrique.setTestid(getTheTest());
        List<Question> list = new ArrayList<Question>();
        Set<Question> collection = new HashSet<Question>(list);
        rubrique.setQuestionCollection(collection);

        Rubrique theRubrique = getRubriqueDao().create(rubrique);
        System.out.println("test : " + getTheTest());

        // toutes les rubriques pour un test
        List<Rubrique> list2 = getRubriqueDao().find(getTheTest());
        if (list2 != null) {
            System.out.println("Nombre de rubriques pour le test n°" + getTheTest().getTestid() + " : " + list2.size());

            for (Rubrique r : list2) {
                System.out.println("rub : " + r.getRubriquenom());
                Document doc = new Document(r.getRubriquenom());
                System.out.println("Doc : " + doc.toString());
                TreeNode rubriques = new DefaultTreeNode(doc, getRoot());

                Collection<Question> col = r.getQuestionCollection();
                int index = 1;
                if (col != null) {
                    for (Question q : col) {
                        TreeNode question_recp = new DefaultTreeNode(index + " - " + q.getQuestiontext(), rubriques);
                    }
                }
            }
        }

        //raz inputtext
        setNomRubrique("");
        getListRubrique().add(theRubrique);

        String url = "http://localhost:8080/CodeTester/faces/createTest_2.xhtml";
        Utils.redirect(url);
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
        
        
        return "createTest_2?faces-redirect=true";
    }

    public String ajouterProposition() {
        System.out.println("ajouterProposition()");
        // type QCM
//        Proposition p = new Proposition();
//        p.setPropositionlibelle(getTheProposition());
//        System.out.println("2");
//        String s = getPropositionEtat();
//        System.out.println("3");
//        String etat = (s.equals("vraie")) ? "1" : "0";
//        p.setPropositionvrai(Short.valueOf(etat));
//                System.out.println("4");
//        p.setQuestionid(null);
//
//        System.out.println("libellé:" + getTheProposition() + " - vrai=" + etat);
//
//        Proposition theProp = getPropositionDao().create(p);
//        getListProposition().add(theProp);

        String url = "http://localhost:8080/CodeTester/faces/createTest_2.xhtml";
        Utils.redirect(url);
        return "";
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

    public void onNodeUnselect(NodeUnselectEvent event) {
        getRoot().getChildren().remove(event.getTreeNode());
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

    /**
     * @return the selectedTreeNode
     */
    public TreeNode[] getSelectedTreeNode() {
        return selectedTreeNode;
    }

    /**
     * @param selectedTreeNode the selectedTreeNode to set
     */
    public void setSelectedTreeNode(TreeNode[] selectedTreeNode) {
        this.setSelectedTreeNode(selectedTreeNode);
    }

    /**
     * @return the enonce
     */
    public String getEnonce() {
        return enonce;
    }

    /**
     * @param enonce the enonce to set
     */
    public void setEnonce(String enonce) {
        this.enonce = enonce;
    }

    /**
     * @return the code_enonce
     */
    public String getCode_enonce() {
        return code_enonce;
    }

    /**
     * @param code_enonce the code_enonce to set
     */
    public void setCode_enonce(String code_enonce) {
        this.code_enonce = code_enonce;
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

    /**
     * @return the listProposition
     */
    public List<Proposition> getListProposition() {
        return listProposition;
    }

    /**
     * @param listProposition the listProposition to set
     */
    public void setListProposition(List<Proposition> listProposition) {
        this.listProposition = listProposition;
    }

    /**
     * @return the theProposition
     */
    public String getTheProposition() {
        return theProposition;
    }

    /**
     * @param theProposition the theProposition to set
     */
    public void setTheProposition(String theProposition) {
        this.theProposition = theProposition;
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

    /*
     * @return the propositionDao
     */
    public IPropositionDao getPropositionDao() {
        return propositionDao;
    }

    /**
     * @param propositionDao the propositionDao to set
     */
    public void setPropositionDao(IPropositionDao propositionDao) {
        this.propositionDao = propositionDao;
    }

    /**
     * @return the propositionEtat
     */
    public String getPropositionEtat() {
        return propositionEtat;
    }

    /**
     * @param propositionEtat the propositionEtat to set
     */
    public void setPropositionEtat(String propositionEtat) {
        this.propositionEtat = propositionEtat;
    }

}
