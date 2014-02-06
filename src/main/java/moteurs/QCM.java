/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moteurs;

import dao.IPropositionDao;
import dao.IQuestionDao;
import dao.IReponseDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import jpa.Proposition;
import jpa.Question;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tools.Utils;

/**
 *
 * @author Loïc
 */
@ManagedBean(name = "qcm")
@SessionScoped
public class QCM implements Serializable {

    // les tests en session
    private Hashtable<String, Hashtable<Integer, List<Question>>> tab_test;

    // la liste de toutes les questions 
    private List<Question> the_list;

    private List<Proposition> mesElements;
    private List<String> maValeur;

    private String time;

    private String test_id;
    private int passage_id;
    private int dureeTest;
    private Integer nb_quest_total;
    private Integer count; // compteur pr affichage
    private Integer count2;
    private String questionid; // id de la question en cours
    private String enonce;
    private String rubrique;
    private String imagePath;

    private ApplicationContext ctx;
    private IQuestionDao questionDao = null;
    private IPropositionDao propositionDao = null;
    private IReponseDao reponseDao = null;

    public QCM() {

        ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        questionDao = (IQuestionDao) ctx.getBean("questionDao");
        propositionDao = (IPropositionDao) ctx.getBean("propositionDao");
        reponseDao = (IReponseDao) ctx.getBean("reponseDao");

        count = 0;

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();

        int passage_id = ((Integer) sessionMap.get("passage_id"));
        setPassage_id(passage_id);
        setTest_id(((String) sessionMap.get("testid")));

        System.out.println("test_id= " + getTest_id());
        System.out.println("passage_id= " + passage_id);

        this.tab_test = ((Hashtable<String, Hashtable<Integer, List<Question>>>) sessionMap.get("tab_test"));

        setDureeTest(Utils.getDureeTest(test_id));
        dureeToString(getDureeTest());

        // le tableau des (rubriques + questions)
        Hashtable<Integer, List<Question>> tab_rub = (Hashtable<Integer, List<Question>>) tab_test.get(test_id);
        // créer une liste avec toutes les questions
        the_list = new ArrayList<Question>();
        for (Map.Entry entry : tab_rub.entrySet()) {
            List<Question> list = (List<Question>) entry.getValue();
            for (Question l : list) {
                the_list.add(l);
            }
        }

        // mélange de la liste
        Collections.shuffle(the_list);
        nb_quest_total = the_list.size();
        System.out.println("nb_quest_total=" + nb_quest_total);
        initQuestion();
    }

    private void initQuestion() {
        // on récupère l'id de la question en cours 
        int id_quest = getThe_list().get(getCount()).getQuestionid();

        setQuestionid(String.valueOf(id_quest));

        // on défini l'énoncé
        setEnonce(getThe_list().get(getCount()).getQuestiontext());
        setEnonce(getEnonce());
        // image ?

//        if (getThe_list().get(getCount()).getQuestionimage() != null) {
//            System.out.println("Une image existe pour cette question");
        // setIsImagExist("1");
        setImagePath(getThe_list().get(getCount()).getQuestionimage());
        System.out.println("Path = " + getImagePath());
       // }

        // on définie sa rubrique
        setRubrique(getThe_list().get(getCount()).getRubriqueid().getRubriqueid().toString());
    }

    // on passe à la page suivante
    public String Suivant() {

        //on doit récupérer l'index de la checkboxe cochée
        System.out.println("id de la proposition cochée: " + getMaValeur());
        if (getMaValeur().equals("")) {
            // aucune case cochée
            Utils.enreg_ReponseQCM(getQuestionid(), 0, "Non répondue");
        } else {
            // la réponse du candidat + l'id de la question
            int retour = Utils.verif_ReponseQCM(getQuestionid(), getMaValeur()); // renvoie 1 si reponse OK 0 si KO
            // on met à jour la réponse ds la base
            if (retour == 1) {
                Utils.enreg_ReponseQCM(getQuestionid(), 1, "Répondue");
            } else if (retour == 0) {
                Utils.enreg_ReponseQCM(getQuestionid(), 0, "Répondue");
            }

            List<String> list = getMaValeur();

            for (String s : list) {
                System.out.println(s.toString());
            }
        }

//        System.out.println("*******************************************");
//        System.out.println("count : " + getCount() + " == nb_quest_total - 1 : " + (getNb_quest_total() - 1));
//        System.out.println("*******************************************");
        // si dernière question on renvoie vers la vue de recap
        if (getCount() == (getNb_quest_total() - 1)) {
            System.out.println("recap");
            String url = "http://localhost:8080/CodeTester/faces/recap.xhtml";
            Utils.redirect(url);
        } else {
            System.out.println("qcm");
            // sinon on réinitialise l'énoncé pr la question suivante
            setCount((Integer) (getCount() + 1));
            initQuestion();
            String url = "http://localhost:8080/CodeTester/faces/QCM.xhtml";
            Utils.redirect(url);
            return "QCM?faces-redirect=true";
        }
        System.out.println("void");
        return "";

    }

    private void dureeToString(int dureeTest) {
        int hours = dureeTest / 3600;
        int minutes = (dureeTest % 3600) / 60;
        int seconds = dureeTest % 60;
        setTime(Utils.twoDigitString(minutes) + " : " + Utils.twoDigitString(seconds));
    }

    public void Decrement() {
        setDureeTest(getDureeTest() - 1);
        //  System.out.println("decremente, durée = " + getDureeTest());
        if (getDureeTest() == 0) {
            // mais impossible de retour en arrière
            String url = "http://localhost:8080/CodeTester/faces/recap.xhtml";
            Utils.redirect(url);
        }
        dureeToString(getDureeTest());

    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return the dureeTest
     */
    public int getDureeTest() {
        return dureeTest;
    }

    /**
     * @param dureeTest the dureeTest to set
     */
    public void setDureeTest(int dureeTest) {
        this.dureeTest = dureeTest;
    }

    /**
     * @return the tab_test
     */
    public Hashtable<String, Hashtable<Integer, List<Question>>> getTab_test() {
        return tab_test;
    }

    /**
     * @param tab_test the tab_test to set
     */
    public void setTab_test(Hashtable<String, Hashtable<Integer, List<Question>>> tab_test) {
        this.tab_test = tab_test;
    }

    /**
     * @return the the_list
     */
    public List<Question> getThe_list() {
        return the_list;
    }

    /**
     * @param the_list the the_list to set
     */
    public void setThe_list(List<Question> the_list) {
        this.the_list = the_list;
    }

    /**
     * @return the nb_quest_total
     */
    public Integer getNb_quest_total() {
        return nb_quest_total;
    }

    /**
     * @param nb_quest_total the nb_quest_total to set
     */
    public void setNb_quest_total(Integer nb_quest_total) {
        this.nb_quest_total = nb_quest_total;
    }

    /**
     * @return the count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * @return the questionid
     */
    public String getQuestionid() {
        return questionid;
    }

    /**
     * @param questionid the questionid to set
     */
    public void setQuestionid(String questionid) {
        this.questionid = questionid;
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
     * @return the rubrique
     */
    public String getRubrique() {
        return rubrique;
    }

    /**
     * @param rubrique the rubrique to set
     */
    public void setRubrique(String rubrique) {
        this.rubrique = rubrique;
    }

    /**
     * @return the mesElements
     */
    public List<Proposition> getMesElements() {
        mesElements = new ArrayList<Proposition>();
        Question quest = getQuestionDao().find(Long.valueOf(getQuestionid()));
        List<Proposition> list = getPropositionDao().find(quest);
        for (Proposition prop : list) {
            Proposition p = new Proposition();
            p.setPropositionid(prop.getPropositionid());
            p.setPropositionlibelle(prop.getPropositionlibelle());
            mesElements.add(p);
        }
        return mesElements;
    }

    /**
     * @param mesElements the mesElements to set
     */
    public void setMesElements(List<Proposition> mesElements) {
        this.mesElements = mesElements;
    }

    /**
     * @return the maValeur
     */
    public List<String> getMaValeur() {
        return maValeur;
    }

    /**
     * @param maValeur the maValeur to set
     */
    public void setMaValeur(List<String> maValeur) {
        this.maValeur = maValeur;
    }

    /**
     * @return the ctx
     */
    public ApplicationContext getCtx() {
        return ctx;
    }

    /**
     * @param ctx the ctx to set
     */
    public void setCtx(ApplicationContext ctx) {
        this.ctx = ctx;
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
     * @return the reponseDao
     */
    public IReponseDao getReponseDao() {
        return reponseDao;
    }

    /**
     * @param reponseDao the reponseDao to set
     */
    public void setReponseDao(IReponseDao reponseDao) {
        this.reponseDao = reponseDao;
    }

    /**
     * @return the test_id
     */
    public String getTest_id() {
        return test_id;
    }

    /**
     * @param test_id the test_id to set
     */
    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }

    /**
     * @return the passage_id
     */
    public int getPassage_id() {
        return passage_id;
    }

    /**
     * @param passage_id the passage_id to set
     */
    public void setPassage_id(int passage_id) {
        this.passage_id = passage_id;
    }

    /**
     * @return the count2
     */
    public Integer getCount2() {
        return (getCount() + 1);
    }

    /**
     * @param count2 the count2 to set
     */
    public void setCount2(Integer count2) {
        this.count2 = count2;
    }

    /**
     * @return the imagePath
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * @param imagePath the imagePath to set
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
