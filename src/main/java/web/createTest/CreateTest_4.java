/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.createTest;

import dao.IQuestionDao;
import dao.IRubriqueDao;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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
@ViewScoped
public class CreateTest_4 {
    
    private List<Question> listQuestion;
    private Test theTest;
    
    private IQuestionDao questionDao = null;
    private IRubriqueDao rubriqueDao = null;

    // 
    private TreeNode root;
    private Document selectedDocument;
    private TreeNode[] selectedTreeNode;
    
    public CreateTest_4() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        setTheTest((Test) sessionMap.get("theTest"));
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        setQuestionDao((IQuestionDao) ctx.getBean("questionDao"));
        setRubriqueDao((IRubriqueDao) ctx.getBean("rubriqueDao"));
        getListQuestion();
        
        root = new DefaultTreeNode("root", null);

        // on supprime tous les noeuds existants
        List<TreeNode> nodes = getRoot().getChildren();
        
        System.out.println("2");
        Iterator<TreeNode> i = nodes.iterator();
        while (i.hasNext()) {
            TreeNode t = i.next();
            // Use isLeaf() method to check doesn't have childs.
            i.remove();
        }
        System.out.println("3");
        // toutes les rubriques pour un test
        List<Rubrique> list2 = getRubriqueDao().find(getTheTest());
        
        if (list2 != null) {
            System.out.println("Nombre de rubriques pour le test n°" + getTheTest().getTestid() + " : " + list2.size());
            System.out.println("4");
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
        
    }
    
    
    public String enregTest() {
        System.out.println("enregTest");
        String url = "http://localhost:8080/CodeTester/faces/index.xhtml";
        Utils.redirect(url);
        return "";
    }

    /**
     * @return the listQuestion
     */
    public List<Question> getListQuestion() {
        List<Question> list = getQuestionDao().findByTest(getTheTest());
        
        for (Question q : list) {
            System.out.println(" >> " + q.toString());
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
