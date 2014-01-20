/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moteurs;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.xml.transform.TransformerException;
import jpa.Question;
import tools.utils;
import controler.TestControler;

/**
 *
 * @author Loïc
 */
@ManagedBean(name = "theCodeTester")
@SessionScoped
public class TheCodeTester implements Serializable {

    private String leCode;
    private String pathApplication;
    private String codeIsCompiled;

    private String pathCodePHP;
    private String pathEnoncePHP;
    private String codeEnonce;
    private String codeSaisit;
    private String myPath, myPath2, myPath3;
    private String urlPHP = "http://localhost:8080/CodeTester/PHP/code.php";

    private boolean actif = true;

    // les tests en session
    // test id / des rubrique(s) id / liste de questions pr une rubrique
    private Hashtable<String, Hashtable<Integer, List<Question>>> tab_test;

    //enoncé de la question
    private String rubrique;
    private String image;
    private String image_small;
    private String image_exist;
    private String image_resultat_exist;
    private String image_resultat;
    private String enonce;

    private String testid;
    private String questionid;
    private Integer count;
    private Integer nb_quest_total;

    private String time;
    private int dureeTest;

    // la liste de toutes les questions 
    private List<Question> the_list;
    
    private String dirNamePHP, dirNameXML;
    

    public TheCodeTester() {
        codeIsCompiled = "0";

        // count compteur pour l'affichage des questions
        count = 0;
        myPath2 = "C:\\NetBeansProjects\\CodeTester\\target\\CodeTester-1.0-SNAPSHOT\\PHP\\";
        pathCodePHP = myPath2 + "code.php";

        // récupérer le test en session - id du test / liste de questions du test
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        // 
        int passage_id = ((Integer) sessionMap.get("passage_id"));
        String test_id = ((String) sessionMap.get("testid"));
        System.out.println("passage_id=" + passage_id + " test_id=" + test_id);

        // on recupere le tableau en session + rep des réponses
        this.tab_test = ((Hashtable<String, Hashtable<Integer, List<Question>>>) sessionMap.get("tab_test"));
        this.dirNamePHP = (String) sessionMap.get("dirNamePHP");
        this.dirNameXML = (String) sessionMap.get("dirNameXML");

        // duree du test
        setDureeTest(utils.getDureeTest(test_id));
        //System.out.println("Durée : " + getDureeTest());
        dureeToString(getDureeTest());

        
        // je l'ai déjà en session ? ?? ?
        // id du test en cours
        for (Map.Entry entry : tab_test.entrySet()) {
            testid = (String) entry.getKey();
        }
        // je l'ai déjà en session ? ?? ?
        
        

        // le tableau des (rubriques + questions)
        Hashtable<Integer, List<Question>> tab_rub = (Hashtable<Integer, List<Question>>) tab_test.get(testid);
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

    // on passe à la page suivante
    public String Valider() throws IOException {
        // on récupère le code saisit par le candidat + on l'écrit ds le rep des réponses
        String repPHP = getDirNamePHP() + "\\question_" + (getCount()) + ".php";

        // System.out.println("count : "+getCount());
        File destinationFile = new File(repPHP);
        System.out.println("repPHP : " + repPHP);

        // on écrit le code sur le poste
        try {
            destinationFile.createNewFile();
            final FileWriter writer = new FileWriter(destinationFile);
            try {
                writer.write(getCodeSaisit());
            } finally {
                writer.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(TestControler.class.getName()).log(Level.SEVERE, null, ex);
        }

        //System.out.println("création du xml");
        try {
            utils.buildXML(getDirNameXML() + "question_" + (getCount()) + ".xml", repPHP);
        } catch (TransformerException ex) {
            Logger.getLogger(TheCodeTester.class.getName()).log(Level.SEVERE, null, ex);
        }

        // on doit lui passer en paramètre le path de la réponse ?
        int retour = utils.verifXML(); // renvoie 1 si reponse OK 0 si KO

        // on met à jour la réponse ds la base
        System.out.println("vérifXML = " + retour);
        if (retour == 1) {
            utils.Enreg_rep(getQuestionid(), 1, getCodeIsCompiled());
        } else if (retour == 0) {
            utils.Enreg_rep(getQuestionid(), 0, getCodeIsCompiled());
        }

        // on vide le fichier de réponse
        try {
            new FileWriter(new File(getPathCodePHP())).close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("********************************************");
        System.out.println("count : " + getCount() + " == nb_quest_total : " + getNb_quest_total());
        System.out.println("********************************************");

        // si dernière question on renvoie vers la vue de recap
        if (getCount() == (getNb_quest_total() - 1)) {
            String url = "http://localhost:8080/CodeTester/faces/recap.xhtml";
            utils.redirect(url);
        }

        // sinon on réinitialise l'énoncé pr la question suivante
        setCount((Integer) (getCount() + 1));
        System.out.println(" count ++ => count = " + getCount());
        initQuestion();
        String url = "http://localhost:8080/CodeTester/faces/theCodeTester.xhtml";
        utils.redirect(url);
        return "theCodeTester?faces-redirect=true";
    }

    private void initQuestion() {
        System.out.println("**************");
        System.out.println(" initQuestion");
        System.out.println("**************");
        codeIsCompiled = "0";

        // on récupère l'id de la question en cours 
        int id_quest = getThe_list().get(getCount()).getQuestionid();
        System.out.println("count= " + getCount());
        System.out.println("id_quest= " + id_quest);
        setQuestionid(String.valueOf(id_quest));
        // on défini le path de l'énoncé de la question en cours
        pathEnoncePHP = "C:\\NetBeansProjects\\CodeTester\\ENONCES\\question_" + id_quest + ".php";
        // on défini les path des images associées à la question en cours
        setImage("../resources/images/enonces_PHP/boucles/large/boucle_" + id_quest + ".JPG");
        setImage_small("enonces_PHP/boucles/small/boucle_" + id_quest + ".JPG");
        setImage_resultat("../resources/images/enonces_PHP/boucles/resultat/boucle_" + id_quest + ".JPG");
        // on vérifie que ces images existent
        File f = new File("C:\\NetBeansProjects\\CodeTester\\target\\CodeTester-1.0-SNAPSHOT\\resources\\images\\enonces_PHP\\boucles\\large\\boucle_" + id_quest + ".JPG");
        File f2 = new File("C:\\NetBeansProjects\\CodeTester\\target\\CodeTester-1.0-SNAPSHOT\\resources\\images\\enonces_PHP\\boucles\\resultat\\boucle_" + id_quest + ".JPG");
        if (f.exists()) {
            setImage_exist("1");
        } else {
            setImage_exist("0");
        }
        if (f2.exists()) {
            setImage_resultat_exist("1");
        } else {
            setImage_resultat_exist("0");
        }
        setImage(image);
        setImage_small(image_small);
//        System.out.println("Image_resultat_exist=" + getImage_resultat_exist());
//        System.out.println("Image_exist=" + getImage_exist());
        // on défini l'énoncé
        enonce = getThe_list().get(getCount()).getQuestiontext();
        setEnonce(enonce);
        // on définie sa rubrique
        setRubrique(getThe_list().get(getCount()).getRubriqueid().getRubriqueid().toString());
    }

    public void Compiler() {
        codeIsCompiled = "1";
        utils.writeOnDisk(getPathCodePHP(), getLeCode());
        String url = "http://localhost:8080/CodeTester/faces/theCodeTester.xhtml";
        utils.redirect(url);
    }

    public void Decrement() {
        dureeTest--;
        System.out.println("decremente, durée = " + getDureeTest());
        if (dureeTest == 0) {
            // mais impossible de retour en arrière
            String url = "http://localhost:8080/CodeTester/faces/recap.xhtml";
            utils.redirect(url);
        }
        dureeToString(dureeTest);

    }

    private void dureeToString(int dureeTest) {
        int hours = dureeTest / 3600;
        int minutes = (dureeTest % 3600) / 60;
        int seconds = dureeTest % 60;
        setTime(utils.twoDigitString(minutes) + " : " + utils.twoDigitString(seconds));
    }

    public String getCodeSaisit() {
//        System.out.println("code saisit : ");
        String codeSaisit = "";

        try {
            InputStream flux = new FileInputStream(getPathCodePHP());
            InputStreamReader lecture = new InputStreamReader(flux);
            BufferedReader buff = new BufferedReader(lecture);
            String line;
            while ((line = buff.readLine()) != null) {
                codeSaisit += line + "\n";
            }
            buff.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
//        System.out.println("code Saisit : " + codeSaisit);
        return codeSaisit;
    }

    /**
     * @return the codeEnonce
     */
    public String getCodeEnonce() {
        String questionid = getQuestionid();
        String codeEnonce = "";
        try {
            InputStream flux = new FileInputStream(getPathEnoncePHP());
            InputStreamReader lecture = new InputStreamReader(flux);
            BufferedReader buff = new BufferedReader(lecture);
            String line;
            while ((line = buff.readLine()) != null) {
                codeEnonce += line + "\n";
            }
            buff.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        // System.out.println("codeEnonce : " + codeEnonce);
        return codeEnonce;
    }

    /**
     * @return the leCode
     */
    public String getLeCode() {
        return leCode;
    }

    /**
     * @param leCode the leCode to set
     */
    public void setLeCode(String leCode) {
        this.leCode = leCode;
    }

    /**
     * @return the pathApplication
     */
    public String getPathApplication() {
        return pathApplication;
    }

    /**
     * @param pathApplication the pathApplication to set
     */
    public void setPathApplication(String pathApplication) {
        this.pathApplication = pathApplication;
    }

    /**
     * @return the codeIsCompiled
     */
    public String getCodeIsCompiled() {
        return codeIsCompiled;
    }

    /**
     * @param codeIsCompiled the codeIsCompiled to set
     */
    public void setCodeIsCompiled(String codeIsCompiled) {
        this.codeIsCompiled = codeIsCompiled;
    }

    /**
     * @param codeEnonce the codeEnonce to set
     */
    public void setCodeEnonce(String codeEnonce) {
        this.codeEnonce = codeEnonce;
    }

    /**
     * @param codeSaisit the codeSaisit to set
     */
    public void setCodeSaisit(String codeSaisit) {
        this.codeSaisit = codeSaisit;
    }

    /**
     * @return the pathCodePHP
     */
    public String getPathCodePHP() {
        return pathCodePHP;
    }

    /**
     * @param pathCodePHP the pathCodePHP to set
     */
    public void setPathCodePHP(String pathCodePHP) {
        this.pathCodePHP = pathCodePHP;
    }

    /**
     * @return the pathEnoncePHP
     */
    public String getPathEnoncePHP() {
        return pathEnoncePHP;
    }

    /**
     * @param pathEnoncePHP the pathEnoncePHP to set
     */
    public void setPathEnoncePHP(String pathEnoncePHP) {
        this.pathEnoncePHP = pathEnoncePHP;
    }

    /**
     * @return the urlPHP
     */
    public String getUrlPHP() {
        return urlPHP;
    }

    /**
     * @param urlPHP the urlPHP to set
     */
    public void setUrlPHP(String urlPHP) {
        this.urlPHP = urlPHP;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the image_small
     */
    public String getImage_small() {
        return image_small;
    }

    /**
     * @param image_small the image_small to set
     */
    public void setImage_small(String image_small) {
        this.image_small = image_small;
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
     * @return the count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param aCount the count to set
     */
    public void setCount(Integer aCount) {
        count = aCount;
    }

    /**
     * @return the nb_quest_total
     */
    public Integer getNb_quest_total() {
        return nb_quest_total;
    }

    /**
     * @param aNb_quest_total the nb_quest_total to set
     */
    public void setNb_quest_total(Integer aNb_quest_total) {
        nb_quest_total = aNb_quest_total;
    }

    /**
     * @return the image_exist
     */
    public String getImage_exist() {
        return image_exist;
    }

    /**
     * @param image_exist the image_exist to set
     */
    public void setImage_exist(String image_exist) {
        this.image_exist = image_exist;
    }

    /**
     * @return the dirNamePHP
     */
    public String getDirNamePHP() {
        return dirNamePHP;
    }

    /**
     * @param dirNamePHP the dirNamePHP to set
     */
    public void setDirNamePHP(String dirNamePHP) {
        this.dirNamePHP = dirNamePHP;
    }

    /**
     * @return the dirNameXML
     */
    public String getDirNameXML() {
        return dirNameXML;
    }

    /**
     * @param dirNameXML the dirNameXML to set
     */
    public void setDirNameXML(String dirNameXML) {
        this.dirNameXML = dirNameXML;
    }

    /**
     * @return the image_resultat
     */
    public String getImage_resultat() {
        return image_resultat;
    }

    /**
     * @param image_resultat the image_resultat to set
     */
    public void setImage_resultat(String image_resultat) {
        this.image_resultat = image_resultat;
    }

    /**
     * @return the image_resultat_exist
     */
    public String getImage_resultat_exist() {
        return image_resultat_exist;
    }

    /**
     * @param image_resultat_exist the image_resultat_exist to set
     */
    public void setImage_resultat_exist(String image_resultat_exist) {
        this.image_resultat_exist = image_resultat_exist;
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

}
