/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.bean;

import dao.IAdminDao;
import dao.ICandidatDao;
import dao.IJointureDao;
import dao.IPassageDao;
import dao.IReponseDao;
import dao.ITestDao;
import javax.annotation.PostConstruct;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Loïc
 */
public class DaoProvider {

    private ApplicationContext ctx;
    protected IPassageDao PASSAGEDAO = null;
    protected ICandidatDao CANDIDATDAO = null;
    protected ITestDao TESTDAO = null;
    protected IJointureDao JOINTUREDAO = null;
    protected IAdminDao ADMINDAO = null;
    protected IReponseDao REPONSEDAO = null;

    @PostConstruct
    public void init() {
        ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    public DaoProvider() {
        PASSAGEDAO = (IPassageDao) ctx.getBean("passageDao");
        CANDIDATDAO = (ICandidatDao) ctx.getBean("candidatDao");
        TESTDAO = (ITestDao) ctx.getBean("testDao");
        JOINTUREDAO = (IJointureDao) ctx.getBean("jointureDao");
        ADMINDAO = (IAdminDao) ctx.getBean("adminDao");
        REPONSEDAO = (IReponseDao) ctx.getBean("reponseDao");
    }


}
