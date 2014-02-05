/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.createTest;

import dao.IPropositionDao;
import dao.IQuestionDao;
import dao.IRubriqueDao;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import jpa.Proposition;
import jpa.Question;
import jpa.Reponse;
import jpa.ReponseHisto;
import jpa.Rubrique;
import jpa.Test;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
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
@ViewScoped
public class CreateTest_3 implements Serializable {

    // le test en cours
    private Test theTest;
    private String type;
    private static Question theQuestion;
    private static String questionIsCreated;
    private static String isImageUploaded;
    private static String isRubriqueChoose;
    private static String pathEnonce;
    private static String isQuestionOk;

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

    private static int count = 1;

    
    
    public CreateTest_3() {
        System.out.println("CreateTest_3");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        setRubriqueDao((IRubriqueDao) ctx.getBean("rubriqueDao"));
        setPropositionDao((IPropositionDao) ctx.getBean("propositionDao"));
        setQuestionDao((IQuestionDao) ctx.getBean("questionDao"));
        setListProposition(new ArrayList<Proposition>());

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        setTheTest((Test) sessionMap.get("theTest"));
        //System.out.println("test: " + getTheTest());
        setType((String) sessionMap.get("theType"));
        //System.out.println("type : " + getType());
        setListRubrique((List<Rubrique>) sessionMap.put("theRubriques", getListRubrique()));

        try {
            System.out.println("rub id : " + getTheQuestion().getRubriqueid().getRubriqueid());
            System.out.println("path enonce : " + getPathEnonce());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
    public String creerQuestion() {
        System.out.println("creerQuestion");
        Collection<ReponseHisto> list = new ArrayList<ReponseHisto>();
        Set<ReponseHisto> reponseHistoCollection = new HashSet<ReponseHisto>(list);
        Collection<Reponse> list2 = new ArrayList<Reponse>();
        Set<Reponse> reponseCollection = new HashSet<Reponse>(list2);
        Collection<Proposition> list3 = new ArrayList<Proposition>();
        Set<Proposition> propositionCollection = new HashSet<Proposition>(list3);

        Question quest = new Question();
        quest.setPropositionCollection(propositionCollection);
        quest.setReponseCollection(reponseCollection);
        quest.setReponseHistoCollection(reponseHistoCollection);
        quest.setRubriqueid(null);
        // quest.setQuestionimage("");
        quest.setQuestiontext(getEnonce());
        Question theQuestion = getQuestionDao().create(quest);
        System.out.println("theQuestion =  " + theQuestion);
        setTheQuestion(theQuestion);

        setQuestionIsCreated("1");

        String url = "http://localhost:8080/CodeTester/faces/createTest_3.xhtml";
        Utils.redirect(url);
        return "";
    }

    public String choisirRubrique() {
        Question quest = getQuestionDao().find(Long.valueOf(getTheQuestion().getQuestionid()));
        quest.setRubriqueid(getRubriqueDao().find(Long.valueOf(getNomRubrique())));
        // merge
        Question theQuestion = getQuestionDao().edit(quest);

        setTheQuestion(theQuestion);
        setIsRubriqueChoose("1");
        String url = "http://localhost:8080/CodeTester/faces/createTest_3.xhtml";

        Utils.redirect(url);
        return "";
    }

    public String ajouterImage() {
        setIsImageUploaded("1");
        String url = "http://localhost:8080/CodeTester/faces/createTest_3.xhtml";
        Utils.redirect(url);
        return "";
    }

    public String ajouterProposition() {
        try {
            System.out.println(" Question exist ? = " + getTheQuestion());
        } catch (Exception e) {
            System.out.println("Question exist ? = null ");
            e.printStackTrace();
        }

        // type QCM
        Proposition p = new Proposition();
        p.setQuestionid(getTheQuestion());
        p.setPropositionlibelle(getTheProposition());
        p.setPropositionvrai(Short.valueOf(getPropositionEtat()));
        Proposition theProp = getPropositionDao().create(p);

        setTheProposition("");
        setPropositionEtat("");

        System.out.println("proposition ajoutée ! ");
        String url = "http://localhost:8080/CodeTester/faces/createTest_3.xhtml";
        Utils.redirect(url);
        return "";
    }

    private void raz() {
        // on remet les champs a zéro
        setQuestionIsCreated(null);
        setIsRubriqueChoose(null);
        setIsImageUploaded(null);
        setEnonce("");
        setTheProposition("");
        setPropositionEtat("");
        setTheQuestion(null);
        setIsQuestionOk(null);
    }

    
    
    public String voirQuestion() {
        System.out.println("voirQuestion()");
        return "createTest_4?faces-redirect=true";
    }
    
    

    public String validerQuestion() {
        System.out.println("validerQuestion()");
        raz();
        int i = getCount();
        setCount(i++);
        setListProposition(new ArrayList<Proposition>());
        return "createTest_3?faces-redirect=true";
    }

    public String okQuestion() {
        setIsQuestionOk("1");
        String url = "http://localhost:8080/CodeTester/faces/createTest_3.xhtml";
        Utils.redirect(url);
        return "";
    }

    public void handleFileUpload(FileUploadEvent event) {
        System.out.println("handleFileUpload() ");
        FacesMessage msg = new FacesMessage("Fichier uploadé", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();

        String dirname = (String) sessionMap.get("dirName");
        System.out.println("dirname = " + dirname);

        String pathEnonce = (String) sessionMap.get("pathEnonce");
        String filename = "QID_" + getTheQuestion().getQuestionid() + ".jpg";
        System.out.println("filename = " + filename);

        //enonces_PHP\TESTID_21\small\QID_26.jpg
        setPathEnonce(pathEnonce + filename);
        // en base !
        Question q = getTheQuestion();
        q.setQuestionimage(getPathEnonce());
        setTheQuestion(questionDao.edit(q));
        
        System.out.println("path enonce = " + getPathEnonce());

        InputStream input = null;
        OutputStream output = null;

        try {
            input = event.getFile().getInputstream();
            output = new FileOutputStream(new File(dirname, filename));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            IOUtils.copy(input, output);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(output);
        }
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
        //return listProposition;
        return getPropositionDao().find(getTheQuestion());
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

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @return the theQuestion
     */
    public Question getTheQuestion() {
        return theQuestion;
    }

    /**
     * @param theQuestion the theQuestion to set
     */
    public void setTheQuestion(Question theQuestion) {
        System.out.println("setTheQuestion()");
        this.theQuestion = theQuestion;
    }

    /**
     * @return the questionIsCreated
     */
    public String getQuestionIsCreated() {
        return questionIsCreated;
    }

    /**
     * @param questionIsCreated the questionIsCreated to set
     */
    public void setQuestionIsCreated(String questionIsCreated) {

        System.out.println("setQuestionIsCreated : " + questionIsCreated);
        this.questionIsCreated = questionIsCreated;
    }

    /**
     * @return the isImageUploaded
     */
    public String getIsImageUploaded() {
        return isImageUploaded;
    }

    /**
     * @param isImageUploaded the isImageUploaded to set
     */
    public void setIsImageUploaded(String isImageUploaded) {
        System.out.println("setIsImageUploaded : " + isImageUploaded);
        this.isImageUploaded = isImageUploaded;
    }

    /**
     * @return the isRubriqueChoose
     */
    public String getIsRubriqueChoose() {
        return isRubriqueChoose;
    }

    /**
     * @param aIsRubriqueChoose the isRubriqueChoose to set
     */
    public void setIsRubriqueChoose(String aIsRubriqueChoose) {
        System.out.println("setIsRubriqueChoose : " + aIsRubriqueChoose);
        isRubriqueChoose = aIsRubriqueChoose;
    }

    /**
     * @return the pathEnonce
     */
    public String getPathEnonce() {
        return pathEnonce;
    }

    /**
     * @param pathEnonce the pathEnonce to set
     */
    public void setPathEnonce(String pathEnonce) {
        this.pathEnonce = pathEnonce;
    }

    /**
     * @return the isQuestionOk
     */
    public String getIsQuestionOk() {
        return isQuestionOk;
    }

    /**
     * @param aIsQuestionOk the isQuestionOk to set
     */
    public void setIsQuestionOk(String aIsQuestionOk) {
        isQuestionOk = aIsQuestionOk;
    }

}
