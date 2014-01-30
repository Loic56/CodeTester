/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.createTest;

import dao.IPropositionDao;
import dao.IQuestionDao;
import dao.IRubriqueDao;
import dao.QuestionDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import jpa.Proposition;
import jpa.Question;
import jpa.Reponse;
import jpa.ReponseHisto;
import jpa.Rubrique;
import jpa.Test;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.TreeNode;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tools.Document;
import tools.Utils;

/**
 *
 * @author Loïc
 */
@ManagedBean(name = "create3")
@SessionScoped
public class CreateTest_3 implements Serializable {  // enreg proposition 

    // 
    private TreeNode root;
    private Document selectedDocument;
    private TreeNode[] selectedTreeNode;

    // le test en cours
    private Test theTest;
    private String type;

    private IRubriqueDao rubriqueDao = null;
    private IPropositionDao propositionDao = null;
    private IQuestionDao questionDao = null;

    // création d'une question
    private String enonce;
    private String code_enonce;

    // proposition
    private String theProposition;
    private String propositionEtat;

    private List<Proposition> listProposition;

    private String nomRubrique;
    private List<Rubrique> listRubrique;

    public CreateTest_3() {
        System.out.println("CreateTest_3");

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        setRubriqueDao((IRubriqueDao) ctx.getBean("rubriqueDao"));
        setPropositionDao((IPropositionDao) ctx.getBean("propositionDao"));
        setQuestionDao((IQuestionDao) ctx.getBean("questionDao"));

        listProposition = new ArrayList<Proposition>();

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        setTheTest((Test) sessionMap.get("theTest"));

        System.out.println("test: " + getTheTest());

        setType((String) sessionMap.get("theType"));
        System.out.println("type : " + getType());

        setListRubrique((List<Rubrique>) sessionMap.put("theRubriques", getListRubrique()));

        System.out.println("1");

//        root = new DefaultTreeNode("root", null);
//
//        // on supprime tous les noeuds existants
//        List<TreeNode> nodes = getRoot().getChildren();
//
//        System.out.println("2");
//        Iterator<TreeNode> i = nodes.iterator();
//        while (i.hasNext()) {
//            TreeNode t = i.next();
//            // Use isLeaf() method to check doesn't have childs.
//            i.remove();
//        }
//        System.out.println("3");
//        // toutes les rubriques pour un test
//        List<Rubrique> list2 = getRubriqueDao().find(getTheTest());
//
//        if (list2 != null) {
//            System.out.println("Nombre de rubriques pour le test n°" + getTheTest().getTestid() + " : " + list2.size());
//            System.out.println("4");
//            for (Rubrique r : list2) {
//                System.out.println("rub : " + r.getRubriquenom());
//                Document doc = new Document(r.getRubriquenom());
//                System.out.println("Doc : " + doc.toString());
//                TreeNode rubriques = new DefaultTreeNode(doc, getRoot());
//
//                Collection<Question> col = r.getQuestionCollection();
//                int index = 1;
//                if (col != null) {
//                    for (Question q : col) {
//                        TreeNode question_recp = new DefaultTreeNode(index + " - " + q.getQuestiontext(), rubriques);
//                    }
//                }
//            }
//        }
    }

    public void onRowSelect(SelectEvent event) {
        Rubrique r = (Rubrique) event.getObject();
        System.out.println("Suppression => id_rub = " + r.getRubriqueid());
    }

    public String validerQuestion() {
        System.out.println("validerQuestion()");
        return "createTest_2?faces-redirect=true";
    }

    public String ajouterProposition() {
        System.out.println("ajouterProposition()");
        System.out.println("rubrique : " + getNomRubrique());
        System.out.println("enonce : " + getEnonce());
        System.out.println("theProposition : " + getTheProposition());
        System.out.println("état = " + getPropositionEtat());

        Collection<ReponseHisto> reponseHistoCollection = new ArrayList<ReponseHisto>();
        Collection<Reponse> reponseCollection = new ArrayList<Reponse>();
        Collection<Proposition> propositionCollection = new ArrayList<Proposition>();

        Question quest = new Question();
        quest.setPropositionCollection(propositionCollection);
        quest.setReponseCollection(reponseCollection);
        quest.setReponseHistoCollection(reponseHistoCollection);
        quest.setRubriqueid(null);
        Question theQuestion = getQuestionDao().create(quest);

        //Question quest = QuestionDao.create();
        // type QCM
        Proposition p = new Proposition();
        p.setPropositionlibelle(getTheProposition());
        
        
       //  String s = getPropositionEtat();

        System.out.println("3");

        // String etat = (s.equals("Vraie")) ? "1" : "0";
        // p.setPropositionvrai(Short.valueOf(etat));
        System.out.println("4");
        p.setQuestionid(null);

        // System.out.println("libellé:" + getTheProposition() + " - vrai=" + etat);
        Proposition theProp = getPropositionDao().create(p);
        getListProposition().add(theProp);

        String url = "http://localhost:8080/CodeTester/faces/createTest_2.xhtml";
        Utils.redirect(url);
        return "";
    }

    public void onNodeUnselect(NodeUnselectEvent event) {
        getRoot().getChildren().remove(event.getTreeNode());
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
        //sessionMap.get("theTest"));
        return getRubriqueDao().find(getTheTest());
        // return listRubrique;
    }

    /**
     * @param listRubrique the listRubrique to set
     */
    public void setListRubrique(List<Rubrique> listRubrique) {
        this.listRubrique = listRubrique;
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

    /**
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
     * @return the questionDao
     */
    public IQuestionDao getQuestionDao() {
        return questionDao;
    }

    /**
     * @param questionDao the questionDao to set
     */
    public void setQuestionDao(IQuestionDao questionDao) {
        this.questionDao = questionDao;
    }

}
