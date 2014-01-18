/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jpa.Admin;
import jpa.Candidat;
import jpa.Jointure;
import jpa.Test;
import jpa.Passage;
import jpa.Proposition;
import jpa.Question;
import jpa.Reponse;
import jpa.ReponseHisto;
import jpa.Rubrique;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tools.utils;

/**
 *
 * @author Loïc
 */
public class DaoTest {

    private IAdminDao adminDao = null;
    private ICandidatDao candidatDao = null;
    private ITestDao testDao = null;
    private IPassageDao passageDao = null;
    private IJointureDao jointureDao = null;
    private IRubriqueDao rubriqueDao = null;
    private IQuestionDao questionDao = null;
    private IPropositionDao propositionDao = null;
    private IReponseDao reponseDao = null;

    Candidat sam, rom, loic, rv;
    Jointure join1;

    public DaoTest() {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        adminDao = (IAdminDao) ctx.getBean("adminDao");
        candidatDao = (ICandidatDao) ctx.getBean("candidatDao");
        testDao = (ITestDao) ctx.getBean("testDao");
        passageDao = (IPassageDao) ctx.getBean("passageDao");
        jointureDao = (IJointureDao) ctx.getBean("jointureDao");
        rubriqueDao = (IRubriqueDao) ctx.getBean("rubriqueDao");
        questionDao = (IQuestionDao) ctx.getBean("questionDao");
        propositionDao = (IPropositionDao) ctx.getBean("propositionDao");
        reponseDao = (IReponseDao) ctx.getBean("reponseDao");

        this.sam = new Candidat("Mr", "Faber", "Samuel", "grosLard@hotmail.fr", utils.stringToMySQLDate("01-01-1985"));
        this.rom = new Candidat("Mr", "Crusson", "Romain", "nazbrock.grave@hotmail.fr", utils.stringToMySQLDate("10-03-1983"));
        this.loic = new Candidat("Mr", "Crusson", "loïc", "beaugosse.laclasse@hotmail.fr", utils.stringToMySQLDate("21-10-1980"));
        this.rv = new Candidat("Mr", "Halgand", "Hervé", "duped.freeMan@hotmail.fr", utils.stringToMySQLDate("07-06-1983"));

        delete();
        test01();
        test02();
        test03();
        //test04();
        test05();
        test06();
        test07();
        test08();
        test09();
        test10();
        test11();
        test12();
        test13();
        test14();

    }

    // dao admin
    public void test01() {
        printLine("TEST01");
        List<Admin> list = adminDao.findAll();
        if (list == null) {
            System.out.println("test 01 >>  NULL");
        } else {
            for (Admin ad : list) {
                System.out.println("test 01 >> " + ad.toString());
            }
        }
    }

    // dao candidat create
    public void test02() {
        printLine("TEST02");
        System.out.println("stringToMySQLDate  : " + utils.stringToMySQLDate("01-01-1985"));

        Candidat candidatInsere1 = candidatDao.create(sam);
        Candidat candidatInsere2 = candidatDao.create(rom);
        Candidat candidatInsere3 = candidatDao.create(loic);
        Candidat candidatInsere4 = candidatDao.create(rv);

        System.out.println("Candidats insérés : \n" + candidatInsere1.toString() + "\n" + candidatInsere2.toString() + "\n" + candidatInsere3.toString() + "\n" + candidatInsere4.toString());
    }

    // dao candidat
    public void test03() {
        printLine("TEST03");
        List<Candidat> list = candidatDao.findAll();

        if (list == null) {
            System.out.println("test 03 >>  NULL");
        } else {
            for (Candidat cand : list) {
                System.out.println("test 03 >> " + cand.toString());
            }
        }
        System.out.println("test 03 >>  END");
    }

//    private void test04() {
//        printLine("TEST04");
//        List<Test> list = testDao.find("PHP");
//        if (list == null) {
//            System.out.println("test 04 >>  NULL");
//        } else {
//            for (Test test : list) {
//                System.out.println(test.toString());
//            }
//        }
//    }
    private void test05() {
        printLine("TEST05");
        List<Test> list = testDao.findAll();
        if (list == null) {
            System.out.println("test 05 >>  NULL");
        } else {
            for (Test test : list) {
                System.out.println(test.toString() + " => " + test.getTheme());
            }
        }
    }

    private void test06() {
        printLine("TEST06");
        Test test = testDao.find(Long.valueOf(1));
        if (test == null) {
            System.out.println("test 06 >>  NULL");
        } else {
            System.out.println(test.toString() + " \nDescription: " + test.getTestDescription() + "\nThème: " + test.getTheme() + "\nDurée: " + test.getTestduree() + "\nNiv: " + test.getNiveau() + "\nNature: " + test.getTestnature());
        }
    }

    //Dao jointure
    private void test07() {
        printLine("TEST07");
        Test t = testDao.find(Long.valueOf(1));
        System.out.println("test:" + t.toString() + "\ncatégorie:" + t.getCategorieid().getCategorielibelle());
        join1 = new Jointure(t);
        Jointure jPersist = jointureDao.create(join1);
        System.out.println("test 07 >> :" + jPersist.toString());
        /// OK - on supprime
        jointureDao.destroy(join1);
    }

    //Dao Passage
    public void test08() {
        printLine("TEST08");

        Passage passage = new Passage();
        passage.setCandidatid(candidatDao.find(Long.valueOf(1)));
        Passage thePassage = passageDao.create(passage);

        List<Test> list = testDao.findAll();
        List<Jointure> listJointure = new ArrayList<Jointure>();

        for (Test test : list) {
            Jointure j = new Jointure();
            j.setTestid(test);
            j.setPassageid(thePassage);
            Jointure jPersist = jointureDao.create(j);
            listJointure.add(jPersist);
        }

        Set<Jointure> listJointureToSet = new HashSet<Jointure>(listJointure);
        thePassage.setJointureCollection(listJointureToSet);
        thePassage = passageDao.update(thePassage);

        if (thePassage == null) {
            System.out.println("test 08 >>  NULL");
        } else {
            System.out.println("test 08 >> " + thePassage.toString());
        }

        /// OK - on supprime
        List<Passage> list2 = passageDao.findAll();
        for (Passage p : list2) {
            passageDao.destroy(p);
        }
        List<Jointure> list3 = jointureDao.findAll();
        for (Jointure j : list3) {
            jointureDao.destroy(j);
        }
    }

    // Dao rubrique
    public void test09() {
        printLine("TEST09");
        Test test = testDao.find(Long.valueOf(1));
        if (test == null) {
            System.out.println("test 09 >>  NULL");
        } else {
            System.out.println("test 09 >> " + test.toString() + " \nDescription: " + test.getTestDescription() + "\nThème: " + test.getTheme() + "\nDurée: " + test.getTestduree() + "\nNiv: " + test.getNiveau() + "\nNature: " + test.getTestnature());
        }

        List<Rubrique> list = rubriqueDao.find(test);
        for (Rubrique r : list) {
            System.out.println("test 09 >> " + r.getRubriquenom());
        }
    }

    // Dao question
    public void test10() {
        printLine("TEST10");
        Test test = testDao.find(Long.valueOf(1));

        List<Rubrique> list = rubriqueDao.find(test);
        for (Rubrique r : list) {
            System.out.println("test 10 >> Rub" + r.getRubriqueid());
            List<Question> list2 = questionDao.find(r);
            if (list2 == null) {
                System.out.println("    >> Pas encore de questions pour cette rubrique ");
            } else {
                for (Question quest : list2) {
                    System.out.println("    >> " + quest.toString() + "\n");
                }
            }
        }
    }

    // Dao - à chaque question on associe une proposition et on défini une réponse
    public void test11() {
        printLine("TEST11");
        Test test = testDao.find(Long.valueOf(1));

        System.out.println("test n°:" + test.getTestid());
        List<Rubrique> list = rubriqueDao.find(test);
        System.out.println("Nombre de rubriques :" + list.size());
        for (Rubrique r : list) {
            System.out.println("Rub" + r.getRubriqueid() + ": " + r.getRubriquenom());
            List<Question> list2 = questionDao.find(r);
            System.out.println("Nombre de questions pour cette rubrique :" + list2.size());
            if (list2 == null) {
            } else {
                for (Question quest : list2) {

                    Proposition p = new Proposition();
                    //p.getReponseCollection().add(rep);
                    Collection<ReponseHisto> reponseHistoCollection = new ArrayList<ReponseHisto>();
                    Set<ReponseHisto> theReponseHistoCollection = new HashSet<ReponseHisto>(reponseHistoCollection);

                    Collection<Reponse> reponseCollection = new ArrayList<Reponse>();
                    Set<Reponse> theReponseCollection = new HashSet<Reponse>(reponseCollection);

                   // p.setReponseCollection(theReponseCollection);
                    //  p.setReponseHistoCollection(theReponseHistoCollection);
                    p.setQuestionid(quest);
                    short s = (short) 1;

                    p.setPropositionlibelle("ds ton cul");
                    p.setPropositionvrai(s);

                    Proposition pr = propositionDao.create(p);
                    /// OK - on supprime cette proposition (on garde sjuste Les proposition  déjà entrées ds la base)
                    propositionDao.destroy(pr);

                }
            }
        }

    }

    // test complet
    private void test12() {
        printLine("TEST12");

        // nouveau passage
        Passage passage = new Passage();
        // sam faber
        passage.setCandidatid(candidatDao.find(Long.valueOf(1)));
        Passage thePassage = passageDao.create(passage);
        System.out.println("Passage id = " + thePassage.getPassageid());

        // on choisit tous les tests 
        // List<Test> listTest = testDao.findAll();
        Test test_ = testDao.find(Long.valueOf(1));
        System.out.println("test id = " + test_.getTestid());

        // on créé une liste de jointures
        List<Jointure> listJointure = new ArrayList<Jointure>();

        // on associe chaque test à sa jointure et chaque jointure à un passage + persist la jointure
//        for (Test test_ : listTest) {
        Jointure j = new Jointure();
        j.setTestid(test_); // test 
        j.setPassageid(thePassage); // passage
        Jointure jPersist = jointureDao.create(j);
        System.out.println("jointure id = " + jPersist.getJointureid());
        listJointure.add(jPersist);
//        }

        Set<Jointure> listJointureToSet = new HashSet<Jointure>(listJointure);

        thePassage.setJointureCollection(listJointureToSet);

        // on associe le passage et le test à la liste de jointure
        thePassage = passageDao.update(thePassage);

        // on récupère toutes rubriques associées à un test
        List<Object> list = Arrays.asList(test_.getRubriqueCollection().toArray());
        System.out.println("nbres de rubriques pr ce test = " + list.size());

        for (Object o : list) {
            Rubrique rub = (Rubrique) o;

            // pour chaque rubriques on récupère sa liste questions
            List<Object> list2 = Arrays.asList(rub.getQuestionCollection().toArray());
            System.out.println("  >> Pour la rubrique n°" + rub.getRubriqueid() + ", Nombre de question = " + list2.size());
            for (Object o2 : list2) {

                // pour chaque question on récupère une liste de propositions (déjà en base!)
                Question quest = (Question) o2;
                List<Object> list3 = Arrays.asList(quest.getPropositionCollection().toArray());

                List<Proposition> listProp = propositionDao.find(quest);

                for (Proposition prop : listProp) {
                    System.out.println("\n*************************************");
                    System.out.println("prop : " + prop.getPropositionlibelle());
                    
                    //    List<Object> listReponse = Arrays.asList(prop.getReponseCollection().toArray());
                }
            }
        }

        // OK - on supprime tout ce qui vient d'être créé
        List<Passage> list2 = passageDao.findAll();
        for (Passage p : list2) {
            passageDao.destroy(p);
        }
        List<Jointure> list3 = jointureDao.findAll();
        for (Jointure jo : list3) {
            jointureDao.destroy(jo);
        }
        List<Reponse> list4 = reponseDao.findAll();
        for (Reponse r : list4) {
            reponseDao.destroy(r);
        }
    }

    public void test13() {
        printLine("TEST13");
        Reponse rep = reponseDao.find(Long.valueOf(1));
    }

    // Dao question
    public void test14() {
        printLine("TEST14");
        Test test = testDao.find(Long.valueOf(1));
        List<Proposition> list = propositionDao.find(test);
        for (Proposition p : list) {
            System.out.println("test 14 >> Proposition" + p.toString());
        }
    }

    public void printLine(String test) {
        System.out.println("////////////////////////////////////////////////////////////");
        System.out.println("\n============================================================");
        System.out.println("                    " + test + "                                 ");
        System.out.println("------------------------------------------------------------");
    }

    private void delete() {
        printLine("Delete");
        //  supprimer tous les candidats
        List<Candidat> list = candidatDao.findAll();
        if (list == null) {
            System.out.println("Delete Candidat >>  NULL");
        } else {
            for (Candidat cand : list) {
                System.out.println("Delete => " + cand.toString());
                candidatDao.destroy(cand);
            }
        }
    }

}
