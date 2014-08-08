/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

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
@ManagedBean(name = "logCandidat")
@SessionScoped
public class Log_Candidat extends BeanAdapter {

	// private Passage thePassage;
	private Candidat theCandidat;
	private List<Test> theTests;

	private String nom;
	private String prenom;
	private Date date;

	private String error;
	private String msg_error;


	public Log_Candidat() {
		super();
		error = "0";
	}

	public String deconnexion() {
		FacesUtil.invalidateSession();
		return CONSTANT_RETURN.INDEX.getReturn();

	}

	public String Connexion() {
		setError("0");
	
		// on cherche un candidat ds la base avec nom / prénom /date
		Candidat cand = PROVIDER.CANDIDATDAO.find(getNom(), getPrenom(),
				getDate());
		if (cand == null) {
			setError("1");
			Utils.Log("candidat non trouvé");
			setMsg_error("Le candidat n'est pas référencé dans la base / vérifier la saisie ");
		} else {
			// on met le candidat ds la session
			FacesUtil.setApplicationMapValue("theCandidat", cand);

			// on récupère le passage en BDD - pour le candidat - on le met en session
			Passage thePassage = PROVIDER.PASSAGEDAO.find(cand);
			
			FacesUtil.setApplicationMapValue("passageEnCours", thePassage);
			
			// on vérifie que le passage est fait ds les temps prévu
			if (thePassage == null) {
				Utils.Log("passage non trouvé");
				setError("1");
				setMsg_error("Aucun passage n'a été créé pour ce candidat");
			} else {
				// passage_id en session
				FacesUtil.setApplicationMapValue("passage_id", thePassage.getPassageid());

				theTests = new ArrayList<Test>();
				// jointure
				List<Object> list = Arrays.asList(thePassage.getJointureCollection()
						.toArray());

				for (Object o : list) {
					Jointure j = (Jointure) o;
					Test t = j.getTestid();
					theTests.add(t);
				}
				
				// les tests en session (ArrayList<Test>())
				FacesUtil.setApplicationMapValue("theTests", theTests);
			}
		}

	
		
		
		if (getError().equals("1")) {
			return CONSTANT_RETURN.LOG_CANDIDAT.getReturn();
		} else {
			// on remet les champs du formulaire à 0
			
			
			RAZFormulaire();
			return CONSTANT_RETURN.HELLO_CANDIDAT.getReturn();
		}
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom
	 *            the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * @param prenom
	 *            the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error
	 *            the error to set
	 */
	public void setError(String error) {
		this.error = error;
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
	 * @return the msg_error
	 */
	public String getMsg_error() {
		return msg_error;
	}

	/**
	 * @param msg_error
	 *            the msg_error to set
	 */
	public void setMsg_error(String msg_error) {
		this.msg_error = msg_error;
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

	private void RAZFormulaire() {
		setNom("");
		setPrenom("");
		setDate(null);
	}

}
