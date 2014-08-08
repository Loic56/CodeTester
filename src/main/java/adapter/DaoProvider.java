/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adapter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dao.IAdminDao;
import dao.ICandidatDao;
import dao.IJointureDao;
import dao.IPassageDao;
import dao.IPropositionDao;
import dao.IQuestionDao;
import dao.IReponseDao;
import dao.IRubriqueDao;
import dao.ITestDao;

/**
 * 
 * @author Loïc
 */
public class DaoProvider {

	private ApplicationContext ctx;

	public IPassageDao PASSAGEDAO = null;
	public ICandidatDao CANDIDATDAO = null;
	public ITestDao TESTDAO = null;
	public IJointureDao JOINTUREDAO = null;
	public IAdminDao ADMINDAO = null;
	public IReponseDao REPONSEDAO = null;
	public IQuestionDao QUESTIONDAO = null;
	public IPropositionDao PROPOSITIONDAO = null;
	public IRubriqueDao RUBRIQUEDAO = null;
	


	public DaoProvider() {

		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

		PASSAGEDAO = (IPassageDao) ctx.getBean("passageDao");
		CANDIDATDAO = (ICandidatDao) ctx.getBean("candidatDao");
		TESTDAO = (ITestDao) ctx.getBean("testDao");
		JOINTUREDAO = (IJointureDao) ctx.getBean("jointureDao");
		ADMINDAO = (IAdminDao) ctx.getBean("adminDao");
		REPONSEDAO = (IReponseDao) ctx.getBean("reponseDao");
		QUESTIONDAO = (IQuestionDao) ctx.getBean("questionDao");
		PROPOSITIONDAO = (IPropositionDao) ctx.getBean("propositionDao");
		RUBRIQUEDAO = (IRubriqueDao) ctx.getBean("rubriqueDao");
		
	}

}
