/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Loïc
 */
@Entity
@Table(name = "candidat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Candidat.findAll", query = "SELECT c FROM Candidat c"),
    @NamedQuery(name = "Candidat.findByCandidatid", query = "SELECT c FROM Candidat c WHERE c.candidatid = :candidatid"),
    @NamedQuery(name = "Candidat.findByCandidatCivilite", query = "SELECT c FROM Candidat c WHERE c.candidatCivilite = :candidatCivilite"),
    @NamedQuery(name = "Candidat.findByCandidatNom", query = "SELECT c FROM Candidat c WHERE c.candidatNom = :candidatNom"),
    @NamedQuery(name = "Candidat.findByCandidatPrenom", query = "SELECT c FROM Candidat c WHERE c.candidatPrenom = :candidatPrenom"),
    @NamedQuery(name = "Candidat.findByCandidatMail", query = "SELECT c FROM Candidat c WHERE c.candidatMail = :candidatMail"),
    @NamedQuery(name = "Candidat.findByCandidatDateNaissance", query = "SELECT c FROM Candidat c WHERE c.candidatDateNaissance = :candidatDateNaissance")})
public class Candidat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CANDIDATID")
    private Integer candidatid;

    @Size(max = 5)
    @Column(name = "CANDIDAT_CIVILITE")
    private String candidatCivilite;

    @Size(max = 255)
    @Column(name = "CANDIDAT_NOM")
    private String candidatNom;

    @Size(max = 255)
    @Column(name = "CANDIDAT_PRENOM")
    private String candidatPrenom;

    @Size(max = 255)
    @Column(name = "CANDIDAT_MAIL")
    private String candidatMail;

    @Column(name = "CANDIDAT_DATE_NAISSANCE")
    @Temporal(TemporalType.DATE)
    private Date candidatDateNaissance;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidatid")
    private Collection<Passage> passageCollection;

    public Candidat() {
    }

    public Candidat(String candidatCivilite, String candidatNom, String candidatPrenom, String candidatMail, Date candidatDateNaissance) {
        this.candidatCivilite = candidatCivilite;
        this.candidatNom = candidatNom;
        this.candidatPrenom = candidatPrenom;
        this.candidatMail = candidatMail;
        this.candidatDateNaissance = candidatDateNaissance;
    }

    public Candidat(Integer candidatid, String candidatCivilite, String candidatNom, String candidatPrenom, String candidatMail, Date candidatDateNaissance) {
        this.candidatid = candidatid;
        this.candidatCivilite = candidatCivilite;
        this.candidatNom = candidatNom;
        this.candidatPrenom = candidatPrenom;
        this.candidatMail = candidatMail;
        this.candidatDateNaissance = candidatDateNaissance;
    }

    public Candidat(Integer candidatid) {
        this.candidatid = candidatid;
    }

    public Integer getCandidatid() {
        return candidatid;
    }

    public void setCandidatid(Integer candidatid) {
        this.candidatid = candidatid;
    }

    public String getCandidatCivilite() {
        return candidatCivilite;
    }

    public void setCandidatCivilite(String candidatCivilite) {
        this.candidatCivilite = candidatCivilite;
    }

    public String getCandidatNom() {
        return candidatNom;
    }

    public void setCandidatNom(String candidatNom) {
        this.candidatNom = candidatNom;
    }

    public String getCandidatPrenom() {
        return candidatPrenom;
    }

    public void setCandidatPrenom(String candidatPrenom) {
        this.candidatPrenom = candidatPrenom;
    }

    public String getCandidatMail() {
        return candidatMail;
    }

    public void setCandidatMail(String candidatMail) {
        this.candidatMail = candidatMail;
    }

    public Date getCandidatDateNaissance() {
        return candidatDateNaissance;
    }

    public void setCandidatDateNaissance(Date candidatDateNaissance) {
        this.candidatDateNaissance = candidatDateNaissance;
    }

    @XmlTransient
    public Collection<Passage> getPassageCollection() {
        return passageCollection;
    }

    public void setPassageCollection(Collection<Passage> passageCollection) {
        this.passageCollection = passageCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (candidatid != null ? candidatid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Candidat)) {
            return false;
        }
        Candidat other = (Candidat) object;
        if ((this.candidatid == null && other.candidatid != null) || (this.candidatid != null && !this.candidatid.equals(other.candidatid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.Candidat[ candidatid=" + candidatid + " ]";
    }

}
