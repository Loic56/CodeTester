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
import jpa.Candidat;
import tools.CONSTANT_RETURN;
import tools.Utils;

/**
 *
 * @author Loïc
 */
@ManagedBean(name = "menuCandidat")
@SessionScoped
public class MenuCandidat extends BeanAdapter {

// affichage listBox
    private List<Candidat> mesElements;
    private String maValeur; // id de l'objet présent ds la listBox

// le candidat choisi
    private Candidat candidatSelected;
    private String theNom;
    private String thePrenom;
    private String theMail;
    private String theDate;
    private ArrayList<Candidat> list;

// widgets 
    private String isSelected;
    private String check;

    public MenuCandidat() {
        isSelected = "0";
    }

    public String Annuler() {
        System.out.println("\n\n annuler \n\n ");
        setIsSelected("0");
        return CONSTANT_RETURN.CANDIDAT_REDIRECT.getReturn();
    }

    public String Suivant() {
        // candidat en session
        System.out.println("\n\n Le candidat en session : )" + getCandidatSelected() + " \n\n ");
        sharedData().put("theCandidat", getCandidatSelected());
        return CONSTANT_RETURN.TEST.getReturn();
    }

    public String Choisir() {
        // on affiche les infos du candidat ds la datatable
        afficheCandidat();
        return CONSTANT_RETURN.CANDIDAT_REDIRECT.getReturn();
    }

    /**
     * @return the mesElements
     */
    public List<Candidat> getMesElements() {
        mesElements = new ArrayList<Candidat>();
        for (Candidat cand : getMaListe()) {
            // System.out.println(">> " + cand.toString());
            Candidat c = new Candidat(cand.getCandidatid(), cand.getCandidatCivilite(), cand.getCandidatNom(), cand.getCandidatPrenom(), cand.getCandidatMail(), cand.getCandidatDateNaissance());
            mesElements.add(c);
        }
        return mesElements;
    }

    private List<Candidat> getMaListe() {
        return CANDIDATDAO.findAll();
    }

    /**
     * @param mesElements the mesElements to set
     */
    public void setMesElements(List<Candidat> mesElements) {
        this.mesElements = mesElements;
    }

    /**
     * @return the maValeur
     */
    public String getMaValeur() {
        return maValeur;
    }

    /**
     * @param maValeur the maValeur to set
     */
    public void setMaValeur(String maValeur) {
        this.maValeur = maValeur;
    }

    /**
     * @return the isSelected
     */
    public String getIsSelected() {
        return isSelected;
    }

    /**
     * @param aIsSelected the isSelected to set
     */
    public void setIsSelected(String aIsSelected) {
        isSelected = aIsSelected;
    }

    /**
     * @return the check
     */
    public String getCheck() {
        return check;
    }

    /**
     * @param check the check to set
     */
    public void setCheck(String check) {
        this.check = check;
    }

    /**
     * @return the theNom
     */
    public String getTheNom() {
        return theNom;
    }

    /**
     * @param theNom the theNom to set
     */
    public void setTheNom(String theNom) {
        this.theNom = theNom;
    }

    /**
     * @return the thePrenom
     */
    public String getThePrenom() {
        return thePrenom;
    }

    /**
     * @param thePrenom the thePrenom to set
     */
    public void setThePrenom(String thePrenom) {
        this.thePrenom = thePrenom;
    }

    /**
     * @return the theMail
     */
    public String getTheMail() {
        return theMail;
    }

    /**
     * @param theMail the theMail to set
     */
    public void setTheMail(String theMail) {
        this.theMail = theMail;
    }

    /**
     * @return the thedate
     */
    public String getTheDate() {
        return theDate;
    }

    /**
     * @param thedate the thedate to set
     */
    public void setTheDate(String thedate) {
        this.theDate = thedate;
    }

    /**
     * @return the candidatSelected
     */
    public Candidat getCandidatSelected() {
        return candidatSelected;
    }

    /**
     * @param candidatSelected the candidatSelected to set
     */
    public void setCandidatSelected(Candidat candidatSelected) {
        this.candidatSelected = candidatSelected;
    }

    /**
     * @return the list
     */
    public ArrayList<Candidat> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(ArrayList<Candidat> list) {
        this.list = list;
    }

    private void afficheCandidat() {
        Candidat theCandidat = CANDIDATDAO.find(Long.parseLong(getMaValeur()));
        List list = new ArrayList<Candidat>();
        list.add(theCandidat);
        setList((ArrayList<Candidat>) list);
        this.setCandidatSelected(theCandidat);

        setTheDate(Utils.dateToMySQLString(theCandidat.getCandidatDateNaissance()));
        setTheMail(theCandidat.getCandidatMail());
        setTheNom(theCandidat.getCandidatNom());
        setThePrenom(theCandidat.getCandidatPrenom());
        setIsSelected("1");
    }

}
