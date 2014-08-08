/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import dao.IQuestionDao;
import dao.IRubriqueDao;
import dao.ITestDao;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import jpa.Candidat;
import jpa.Passage;
import jpa.Question;
import jpa.Rubrique;
import jpa.Test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import adapter.BeanAdapter;
import tools.CONSTANT_RETURN;
import tools.FacesUtil;
import tools.Utils;

/**
 * 
 * @author Loïc
 */
@ManagedBean(name = "testControler")
@SessionScoped
public class TestControler extends BeanAdapter {

	private Hashtable<String, Hashtable<Integer, List<Question>>> tab_test;
	private Hashtable<Integer, List<Question>> tab_rub;

	private Candidat theCandidat;

	// le test en cours parmi la liste de tests
	private Test theTest;
	private Integer nb_rub;
	private List list_rub;
	private Integer nb_quest;

	// tous les tests du passage
	private List<Test> theTests;

	public TestControler() {
		super();
	}


	public String DebuterTest() {

		Utils.printLine("       Controller - DébuterTest        ");

		int passage_id = (Integer) FacesUtil
				.getApplicationMapValue("passage_id");

		// le test en cours
		theTest = (Test) FacesUtil.getApplicationMapValue("testEnCours");

		// nature du test QCM / CODE ?
		String testNature = theTest.getTestnature();
		String testFormat = theTest.getTestformat();

		// toutes les rubriques pr un test
		List<Rubrique> list = PROVIDER.RUBRIQUEDAO.find(theTest);
		System.out.println("list.size() = " + list.size());

		// toutes les questions pr une rubrique
		tab_rub = new Hashtable<Integer, List<Question>>();
		for (Rubrique rub : list) {
			tab_rub.put(rub.getRubriqueid(), PROVIDER.QUESTIONDAO.find(rub));
		}

		Utils.Log("tab_rub.size() = " + tab_rub.size());

		// toutes les (rubriques + questions) pour un test
		tab_test = new Hashtable<String, Hashtable<Integer, List<Question>>>();
		tab_test.put(getTest().getTestid().toString(), tab_rub);

		FacesUtil.setApplicationMapValue("tab_test", tab_test);

		Utils.printLine(testFormat);

		theCandidat = (Candidat) FacesUtil.getApplicationMapValue("theCandidat");
		
		if (testFormat.equals("QCM")) {

			return "QCM";
		} else if (testFormat.equals("CODE")) {

			// uniquement pour un type CODE
			// créer un dossier de réception pour les réponse
			String rep = passage_id + "-" + theCandidat.getCandidatNom()
					+ "-" + getTheCandidat().getCandidatPrenom();
			Utils.Log("     rep :    " + rep);
			// for (Test test : list) {
			// = > à rajouter pour plusieurs test
			String dirNamePHP = "C:\\Project\\Workspace\\CodeTester\\PASSAGES\\"
					+ rep + "\\" + theTest.getTheme() + "\\PHP\\";
			String dirNameXML = "C:\\Project\\Workspace\\CodeTester\\PASSAGES\\"
					+ rep + "\\" + theTest.getTheme() + "\\XML\\";

			Utils.Log("     dirNamePHP :    " + dirNamePHP);
			Utils.Log("     dirNameXML :    " + dirNameXML);
			FacesUtil.setApplicationMapValue("dirNamePHP", dirNamePHP);
			FacesUtil.setApplicationMapValue("dirNameXML", dirNameXML);
			File file = new File(dirNamePHP);
			File file2 = new File(dirNameXML);
			boolean isCreated = file.mkdirs();
			boolean isCreated2 = file2.mkdirs();

			return CONSTANT_RETURN.CODE_TESTER.getReturn();

		} else {

			return CONSTANT_RETURN.TEST_INFOS.getReturn();
		}

	}

	// les tests en attente
	public String Link(Test test) {
		System.out.println("Link()");
		Utils.Log("le test cliqué : " + test.toString());
		System.out.println(PROVIDER.ADMINDAO.getConnexionCount());

		// On set le test pr le Controler
		setTest(test);

		// affichage des infos
		List<Rubrique> list = PROVIDER.RUBRIQUEDAO.find(test);
		setList_rub(list);
		setNb_rub(list.size());
		setNb_quest(test.getTestNbquestionRub());

		// test en session
		FacesUtil.setApplicationMapValue("testEnCours", test);

		// on affiche les infos avt de lancer le test
		return CONSTANT_RETURN.TEST_INFOS.getReturn();
	}

	/**
	 * @return the nb_rub
	 */
	public Integer getNb_rub() {
		return nb_rub;
	}

	/**
	 * @param nb_rub
	 *            the nb_rub to set
	 */
	public void setNb_rub(Integer nb_rub) {
		this.nb_rub = nb_rub;
	}

	/**
	 * @return the list_rub
	 */
	public List getList_rub() {
		list_rub = new ArrayList();
		list_rub = PROVIDER.RUBRIQUEDAO.find(getTest());
		return list_rub;
	}

	/**
	 * @param list_rub
	 *            the list_rub to set
	 */
	public void setList_rub(List list_rub) {
		this.list_rub = list_rub;
	}

	/**
	 * @return the nb_quest
	 */
	public Integer getNb_quest() {
		return nb_quest;
	}

	/**
	 * @param nb_quest
	 *            the nb_quest to set
	 */
	public void setNb_quest(Integer nb_quest) {
		this.nb_quest = nb_quest;
	}

	/**
	 * @return the test
	 */
	public Test getTest() {
		return theTest;
	}

	/**
	 * @param test
	 *            the test to set
	 */
	public void setTest(Test test) {
		this.theTest = test;
	}

	/**
	 * @return the theCandidat
	 */
	public Candidat getTheCandidat() {
		return theCandidat;
	}

	/**
	 * @param theCandidat
	 *            the theCandidat to set
	 */
	public void setTheCandidat(Candidat theCandidat) {
		this.theCandidat = theCandidat;
	}

	/**
	 * @return the theTests
	 */
	public List<Test> getTheTests() {
		return theTests;
	}

	/**
	 * @param theTests
	 *            the theTests to set
	 */
	public void setTheTests(List<Test> theTests) {
		this.theTests = theTests;
	}

}
