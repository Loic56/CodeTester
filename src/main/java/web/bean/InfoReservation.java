/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.eclipse.persistence.sessions.server.ExternalConnectionPool;

import jpa.Candidat;
import jpa.Jointure;
import jpa.Passage;
import jpa.Test;
import tools.CONSTANT_RETURN;
import tools.FacesUtil;
import tools.Utils;
import adapter.BeanAdapter;

/**
 * 
 * @author Loïc
 */
@ManagedBean(name = "infoResa")
@SessionScoped
public class InfoReservation extends BeanAdapter {

	private Candidat theCandidat;
	private List<Test> theTests;
	
	private List<Jointure> listJointure;

	private List<Candidat> listCandidat;
	private String dureeTotale;

	private Integer duree = 0;

	public InfoReservation() {
		super();

		Utils.printLine("InfoReservation");
		listCandidat = new ArrayList<Candidat>();
		theCandidat = (Candidat) FacesUtil.getApplicationMapValue("theCandidat");
		listCandidat.add(theCandidat);
		
		setTheCandidat(theCandidat); // ?
		Utils.Log("Candidat en session : "+theCandidat.toString());
		
		theTests = (List<Test>) FacesUtil.getApplicationMapValue("theTests");
		setTheTests(theTests); // ?
		Utils.Log("Tests en session : "+theTests.toString());
		
		for (Test test : theTests) {
			duree = duree + test.getTestduree();
		}
		
		dureeTotale = String.valueOf(duree);
		Utils.Log("durée totale : " + dureeTotale); 
	}

	public String Retour() {
		return CONSTANT_RETURN.TEST_REDIRECT.getReturn();
	}

	public String Annuler() {
		return CONSTANT_RETURN.CANDIDAT_REDIRECT.getReturn();
	}

	public String Suivant() {
		Utils.Log("Nouveau passage pour le candidat : "+getTheCandidat()+ " \n avec le(s) test(s) : " + getTheTests());

		Passage thePassage = Utils.createPassage(getTheCandidat(), getTheTests());

		// le passage est référencé ds la BDD on supprime les variables de session 
		FacesUtil.invalidateSession();
		
		return CONSTANT_RETURN.LOG_CANDIDAT.getReturn();
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
	 * @return the listCandidat
	 */
	public List<Candidat> getListCandidat() {
		return listCandidat;
	}

	/**
	 * @param listCandidat
	 *            the listCandidat to set
	 */
	public void setListCandidat(List<Candidat> listCandidat) {
		this.listCandidat = listCandidat;
	}

	/**
	 * @return the dureeTotale
	 */
	public String getDureeTotale() {
		return dureeTotale;
	}

	/**
	 * @param dureeTotale
	 *            the dureeTotale to set
	 */
	public void setDureeTotale(String dureeTotale) {
		this.dureeTotale = dureeTotale;
	}

	/**
	 * @return the theTests
	 */
	public List<Test> getTheTests() {
		return (List<Test>) FacesUtil.getApplicationMapValue("theTests");
	}

	/**
	 * @param theTests
	 *            the theTests to set
	 */
	public void setTheTests(List<Test> theTests) {
		this.theTests = theTests;
	}

	/**
	 * @return the listJointure
	 */
	public List<Jointure> getListJointure() {
		return listJointure;
	}

	/**
	 * @param listJointure
	 *            the listJointure to set
	 */
	public void setListJointure(List<Jointure> listJointure) {
		this.listJointure = listJointure;
	}

	/**
	 * @return the duree
	 */
	public Integer getDuree() {
		return duree;
	}

	/**
	 * @param duree
	 *            the duree to set
	 */
	public void setDuree(Integer duree) {
		this.duree = duree;
	}

}
