/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import dao.IAdminDao;
import dao.ICandidatDao;
import dao.IJointureDao;
import dao.IPassageDao;
import dao.IQuestionDao;
import dao.IReponseDao;
import dao.IRubriqueDao;
import dao.ITestDao;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.swing.Timer;
import jpa.Candidat;
import jpa.Jointure;
import jpa.Passage;
import jpa.Reponse;
import jpa.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tools.utils;
import tools.utils;
import controler.TestControler;
import tools.RegisterAction;

/**
 *
 * @author Loïc
 */
class MetierTest {

    private String path = "";
    private String pathPHP = "";
    private boolean actif = true;

    private IAdminDao adminDao = null;
    private ICandidatDao candidatDao = null;
    private ITestDao testDao = null;
    private IPassageDao passageDao = null;
    private IJointureDao jointureDao = null;
    private IRubriqueDao rubriqueDao = null;
    private IQuestionDao questionDao = null;
    private IReponseDao reponseDao = null;

    public MetierTest() {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        adminDao = (IAdminDao) ctx.getBean("adminDao");
        candidatDao = (ICandidatDao) ctx.getBean("candidatDao");
        testDao = (ITestDao) ctx.getBean("testDao");
        passageDao = (IPassageDao) ctx.getBean("passageDao");
        jointureDao = (IJointureDao) ctx.getBean("jointureDao");
        rubriqueDao = (IRubriqueDao) ctx.getBean("rubriqueDao");
        questionDao = (IQuestionDao) ctx.getBean("questionDao");
        reponseDao = (IReponseDao) ctx.getBean("reponseDao");

//        test01();
//        test02();
//        test03();
//        test04();
//        test05();
//        test06();
//        test07();
//        test08();
//        test09();
//        test10();
//       test11(1800);
        
        // test Java Mail
//        test12();
//        test13();
//        test14();
        test15();
    }

    private void test01() {
        printLine("TEST01");
        try {
            path = new java.io.File(".").getCanonicalPath();
            System.out.println(" >> path: " + path);
        } catch (Exception e) {
        }

    }

    private void test02() {
        printLine("TEST02");
        try {
            pathPHP = path + "/TEST/CODE/PHP/code.php";
            System.out.println("path: " + pathPHP);
            PrintWriter fichier = new PrintWriter(new FileWriter(pathPHP), false);
            fichier.println("Hello");
            fichier.println(1234);
            fichier.close();
        } catch (Exception e) {
            System.out.println("foirage");
        }

    }

    private void test03() {
        printLine("TEST03");
        try {
            InputStream flux = new FileInputStream(pathPHP);
            InputStreamReader lecture = new InputStreamReader(flux);
            BufferedReader buff = new BufferedReader(lecture);
            String ligne;
            while ((ligne = buff.readLine()) != null) {
                System.out.println(ligne);
            }
            buff.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void test04() {
        printLine("TEST04");
        Thread t = new Thread();
        t.start();
    }

    public void run() {
        int i = 0;
        while (actif) {
            System.out.println("i = " + i);
            i++;
        }
    }

    private void test05() {
        printLine("TEST05");

        Candidat loic = candidatDao.find(Long.valueOf(1));
        Passage passage = new Passage();
        passage.setCandidatid(loic);
        passage.setPassageEtat(0);
        passage.setPassageDate(new Date());

        Passage thePassage = passageDao.create(passage);

        List<Test> list = testDao.findAll();
        List<Jointure> listJointure = new ArrayList<Jointure>();
        for (Test test : list) {
            Jointure j = new Jointure();
            j.setTestid(test);
            listJointure.add(j);
        }
        passage.setJointureCollection(listJointure);

        if (thePassage == null) {
            System.out.println("test 08 >>  NULL");
        } else {
            System.out.println("test 08 >> " + thePassage.toString());
        }

        String rep = passage.getPassageid() + "-" + passage.getCandidatid().getCandidatPrenom() + "-" + passage.getCandidatid().getCandidatNom();

        for (Test test : list) {
            String dirName = "C:\\NetBeansProjects\\CodeTester\\PASSAGES\\" + rep + "\\" + test.getTheme();

            File file = new File(dirName);
            boolean isCreated = file.mkdirs();

            File destinationFile = new File(dirName + "\\le(s)Fichier(s)DeReponse(s).txt");
            try {
                destinationFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(TestControler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void test06() {
        printLine("TEST06");
        for (int i = 0; i < 10; i++) {
            int retour = utils.verifXML();
            System.out.println("retour : " + retour);
        }

    }

    //enreg_reponse
    private void test07() {
        printLine("TEST07");
        //   utils.Enreg_rep("6", 1);

    }

    // Dao question 
    public void test08() {
        printLine("TEST08");
        Test test = testDao.find(Long.valueOf(1));
        Passage passage = passageDao.find(Long.valueOf(1));

        List<Reponse> list = reponseDao.find(passage, test);

        for (Reponse r : list) {
            System.out.println("test 08 >> Reponse" + r.toString());
        }
    }

    public void test09() {
        printLine("TEST08");
        // utils.reponseAnswered("2", "Répondue");
    }

    //timer
    public void test10() {
        printLine("TEST10");
//        Timer timer = new Timer(1000, new MyTimer(1, 50));
//        timer.start();
//        try {
//            Thread.sleep(1800000); // pdt 20 sec
//            // 1800 000 => 30 min
//            // 180 000 => 3 min
//        } catch (InterruptedException e) {
//        }
//        timer.stop();
        //redirection d'url
    }

    // seconde to string
    public void test11(int dureeTest) {
        printLine("TEST11");

        int hours = dureeTest / 3600;
        int minutes = (dureeTest % 3600) / 60;
        int seconds = dureeTest % 60;

        System.out.println(hours + ":" + minutes + ":" + seconds);

    }

    public void test12() {
        printLine("TEST12");
        try {
            utils.sendEmail();
            System.out.println("sendEmail >> OK");
        } catch (Exception e) {
            System.out.println("sendEmail >> KO");
            e.printStackTrace();
        }

    }

    public void test13() {
        printLine("TEST13");
        try {
            utils.sendEmail2();
            System.out.println("sendEmail2 >> OK");
        } catch (Exception e) {
            System.out.println("sendEmail2 >> KO");
            e.printStackTrace();
        }

    }

    public void test14() {
        printLine("TEST14");
        // accès a mon comte ggogle pr 10 min
        // https://accounts.google.com/b/0/DisplayUnlockCaptcha
        try {
            RegisterAction reg = new RegisterAction();
            reg.execute();
            System.out.println("RegisterAction >> OK");
        } catch (Exception e) {
            System.out.println("RegisterAction >> KO");
            e.printStackTrace();
        }

    }

    public void test15() {
        printLine("TEST15");
        try {
            utils.sendEmail3();
            System.out.println("sendEmail3 >> OK");
        } catch (Exception e) {
            System.out.println("sendEmail3 >> KO");
            e.printStackTrace();
        }

    }

    public void printLine(String test) {
        System.out.println("////////////////////////////////////////////////////////////");
        System.out.println("\n============================================================");
        System.out.println("                    " + test + "                                 ");
        System.out.println("------------------------------------------------------------");
    }
}
