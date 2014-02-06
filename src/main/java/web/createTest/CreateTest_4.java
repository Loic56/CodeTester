/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.createTest;

import dao.IQuestionDao;
import dao.IRubriqueDao;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import jpa.Proposition;
import jpa.Question;
import jpa.Rubrique;
import jpa.Test;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tools.Document;
import tools.Utils;

/**
 *
 * @author Loïc
 */
@ManagedBean(name = "create4")
@SessionScoped
public class CreateTest_4 implements Serializable {

    private List<Question> listQuestion;
    private Test theTest;

    private IQuestionDao questionDao = null;
    private IRubriqueDao rubriqueDao = null;

    // 
    private TreeNode root;
    private Document selectedDocument;

    private TreeNode[] selectedTreeNode;

    public CreateTest_4() {
        System.out.println(">> Début constructeur");
        
        
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        setTheTest((Test) sessionMap.get("theTest"));

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        setQuestionDao((IQuestionDao) ctx.getBean("questionDao"));
        setRubriqueDao((IRubriqueDao) ctx.getBean("rubriqueDao"));

        setRoot(new DefaultTreeNode("root", null));

        // on supprime tous les noeuds existants
        List<TreeNode> nodes = getRoot().getChildren();
        
//        Iterator<TreeNode> i = nodes.iterator();
//        while (i.hasNext()) {
//            TreeNode t = i.next();
//            System.out.println("on supprime tous les noeuds");
//            i.remove();
//        }
        
        
        // toutes les rubriques pour un test
        List<Rubrique> list2 = getRubriqueDao().find(getTheTest());
        System.out.println("on récupère la liste des rubriques du test");
        if (list2 != null) {
            System.out.println("Nombre de rubriques pour le test n°" + getTheTest().getTestid() + " : " + list2.size());
            for (Rubrique r : list2) {
                System.out.println(" rub : " + r.getRubriquenom());
                System.out.println("on crée un 1er noeud pr chaque rubrique ");
                TreeNode rubrique = new DefaultTreeNode(new Document(r.getRubriquenom(), "", "Rubrique"), getRoot());

                Collection<Question> col = r.getQuestionCollection();
                System.out.println("on récupère la liste des questions pour une rubrique ");
                int index = 1;
                int index2 = 1;

                if (col != null) {
                    System.out.println("Nombre de questions pour la rubrique n°" + r.getRubriqueid() + " : " + col.size());

                    for (Question q : col) {
                        System.out.println("On crée un sous-noeud pour chaque question ");
                        TreeNode question = new DefaultTreeNode("rubrique", new Document(index + " - " + q.getQuestiontext(), "-", "Question"), rubrique);

                        Collection<Proposition> props = q.getPropositionCollection();
                        System.out.println("on récupère la liste des propositions pour une question ");
                        if (props != null) {

                            for (Proposition p : props) {
                                System.out.println("On crée un sous-sous-noeud pour chaque proposition ");
                                TreeNode proposition = new DefaultTreeNode("proposition", new Document(index2 + " - " + p.getPropositionlibelle(), "-", "Proposition"), question);
                                index2++;
                            }
                        }
                        index++;
                    }
                }
            }
        }
        System.out.println(">>> Fin constructeur");
    }

    public String test() {
        System.out.println("test()");
        String url = "http://localhost:8080/CodeTester/faces/index.xhtml";
        Utils.redirect(url);
        return "";
    }

    public String enregTest() {
        System.out.println("enregTest");
//        String url = "http://localhost:8080/CodeTester/faces/index.xhtml";
//        Utils.redirect(url);
        return "index?faces-redirect=true";
    }

    /**
     * @return the listQuestion
     */
    public List<Question> getListQuestion() {
        //System.out.println(" >> getListQuestion()");
        List<Question> list = getQuestionDao().findByTest(getTheTest());
        for (Question q : list) {
            //System.out.println(" >> " + q.toString());
        }
        return list;
    }

    public void onRowSelect(SelectEvent event) {
        Rubrique r = (Rubrique) event.getObject();
        System.out.println("Suppression => id_rub = " + r.getRubriqueid());
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
        System.out.println("? selectedTreeNode = " + selectedTreeNode);
        this.setSelectedTreeNode(selectedTreeNode);
    }

    /**
     * @param listQuestion the listQuestion to set
     */
    public void setListQuestion(List<Question> listQuestion) {
        this.listQuestion = listQuestion;
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
