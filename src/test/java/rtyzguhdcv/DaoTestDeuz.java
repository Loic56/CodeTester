/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtyzguhdcv;

import dao.*;
import java.util.List;

import jpa.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 *
 * @author Loïc
 */
public class DaoTestDeuz {

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

    public DaoTestDeuz() {

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

        test01();
    }

    // Dao proposition find the good one
    public void test01() {
        printLine("TEST01");

        Passage p = passageDao.find(Long.valueOf(1));
        Test t = testDao.find(Long.valueOf(6));

        List<Reponse> list = reponseDao.find(p, t);
        if (list == null) {
            System.out.println("Réponse >> aucune ! ");
        } else {
            for (Reponse rep : list) {
                System.out.println("Réponse >> " + rep.toString());
            }
        }
    }

    public void printLine(String test) {
        System.out.println("////////////////////////////////////////////////////////////");
        System.out.println("\n============================================================");
        System.out.println("                    " + test + "                                 ");
        System.out.println("------------------------------------------------------------");
    }
}
