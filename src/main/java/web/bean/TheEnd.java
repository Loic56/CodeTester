/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.bean;

import dao.IPassageDao;
import dao.ITestDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import jpa.Candidat;
import jpa.Passage;
import jpa.Reponse;
import jpa.Test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import adapter.BeanAdapter;
import tools.FacesUtil;
import tools.Utils;

/**
 * 
 * @author Loïc
 */
@ManagedBean(name = "theEnd")
@SessionScoped
public class TheEnd extends BeanAdapter {

	private Candidat theCandidat;
	private Test theTest;
	private String testid; // le test en cours parmi la liste de tests

	private ITestDao testDao = null;
	private IPassageDao passageDao = null;

	private int note;
	private int nbQuest;
	private List<Test> theTests;
	private String theDate;


	public TheEnd() {
		super();
		
		Date date = new Date();
		setTheDate(Utils.dateToMySQLString(date));
		Candidat cand = (Candidat) 
				FacesUtil.getApplicationMapValue(
				"theCandidat");
		System.out.println(cand.toString());
		setTheCandidat(cand);
		setTestid((String) 
				FacesUtil.getApplicationMapValue("testid"));

		// calcul de la note du candidat
		int passage_id = ((Integer) 
				FacesUtil.getApplicationMapValue(
				"passage_id"));
		String test_id = ((String) 
				FacesUtil.getApplicationMapValue("testid"));
		Test test = PROVIDER.TESTDAO.find(Long.valueOf(test_id));
		setTheTest(test);
		Passage passage = PROVIDER.PASSAGEDAO.find(Long.valueOf(passage_id));

		// list de questions par rubriques
		List<Reponse> list = Utils.findReponses(passage, test);
		setNbQuest(list.size());

		System.out.println("list = " + list.size());
		for (Reponse r : list) {
			note = note + (Integer.parseInt(r.getReponsemessage()));
		}

		System.out.println("note = " + note);
	}

	public void retourAccueil() {

		Test myTest = getTestDao().find(Long.valueOf(getTestid()));

		List<Test> theTests = ((List<Test>) FacesUtil.getApplicationMapValue(
				"theTests"));
		List<Test> theTestsAfter = new ArrayList<Test>();

		try {
			System.out.println("  ? ? "
					+ FacesUtil.getApplicationMapValue("theTests"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		FacesUtil.remove("theTests");

		try {
			System.out.println("  ? ? "
					+FacesUtil.getApplicationMapValue("theTests"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			for (Test t : theTests) {
				if (t.equals(myTest)) {
					theTests.remove(myTest);
				} else {
					theTestsAfter.add(t);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("theTestsAfter.size() : " + theTestsAfter.size());
		// tous les tests du candidat on étés effectués

		if (theTestsAfter.size() == 0) {
			// on vide la session
			
			FacesUtil.invalidateSession();

			// retour vers l'accueil
			String url = "http://localhost:8080/CodeTester/faces/index.xhtml";
			Utils.redirect(url);
		}

		System.out.println(" on remet la liste en session ");
		FacesUtil.setApplicationMapValue("theTests", theTestsAfter);

		for (Test t : (List<Test>) FacesUtil.getApplicationMapValue("theTests")) {
			System.out.println(" >> " + t.toString());
		}

		String url = "http://localhost:8080/CodeTester/faces/helloCandidat.xhtml";
		Utils.redirect(url);

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
	 * @return the testid
	 */
	public String getTestid() {
		return testid;
	}

	/**
	 * @param testid
	 *            the testid to set
	 */
	public void setTestid(String testid) {
		this.testid = testid;
	}

	/**
	 * @return the note
	 */
	public int getNote() {
		return note;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(int note) {
		this.note = note;
	}

	/**
	 * @return the theTest
	 */
	public Test getTheTest() {
		return theTest;
	}

	/**
	 * @param theTest
	 *            the theTest to set
	 */
	public void setTheTest(Test theTest) {
		this.theTest = theTest;
	}

	/**
	 * @return the nbQuest
	 */
	public int getNbQuest() {
		return nbQuest;
	}

	/**
	 * @param nbQuest
	 *            the nbQuest to set
	 */
	public void setNbQuest(int nbQuest) {
		this.nbQuest = nbQuest;
	}

	/**
	 * @return the theDate
	 */
	public String getTheDate() {
		return theDate;
	}

	/**
	 * @param theDate
	 *            the theDate to set
	 */
	public void setTheDate(String theDate) {
		this.theDate = theDate;
	}

	/**
	 * @return the testDao
	 */
	public ITestDao getTestDao() {
		return testDao;
	}

	/**
	 * @param testDao
	 *            the testDao to set
	 */
	public void setTestDao(ITestDao testDao) {
		this.testDao = testDao;
	}

	/**
	 * @return the passageDao
	 */
	public IPassageDao getPassageDao() {
		return passageDao;
	}

	/**
	 * @param passageDao
	 *            the passageDao to set
	 */
	public void setPassageDao(IPassageDao passageDao) {
		this.passageDao = passageDao;
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
