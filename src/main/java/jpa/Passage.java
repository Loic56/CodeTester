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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Loïc
 */
@Entity
@Table(name = "passage")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Passage.findAll", query = "SELECT p FROM Passage p"),
    @NamedQuery(name = "Passage.findByPassageid", query = "SELECT p FROM Passage p WHERE p.passageid = :passageid"),
    @NamedQuery(name = "Passage.findByPassageEtat", query = "SELECT p FROM Passage p WHERE p.passageEtat = :passageEtat"),
    @NamedQuery(name = "Passage.findByPassageDate", query = "SELECT p FROM Passage p WHERE p.passageDate = :passageDate"),
    @NamedQuery(name = "Passage.findByPassagedebutVal", query = "SELECT p FROM Passage p WHERE p.passagedebutVal = :passagedebutVal"),
    @NamedQuery(name = "Passage.findByPassagefinVal", query = "SELECT p FROM Passage p WHERE p.passagefinVal = :passagefinVal")})
public class Passage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PASSAGEID")
    private Integer passageid;
    @Column(name = "PASSAGE_ETAT")
    private Integer passageEtat;
    @Column(name = "PASSAGE_DATE")
    @Temporal(TemporalType.DATE)
    private Date passageDate;
    @Column(name = "PASSAGEDEBUT_VAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date passagedebutVal;
    @Column(name = "PASSAGEFIN_VAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date passagefinVal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "passageid")
    private Collection<ReponseHisto> reponseHistoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "passageid")
    private Collection<Reponse> reponseCollection;
    @JoinColumn(name = "CANDIDATID", referencedColumnName = "CANDIDATID")
    @ManyToOne(optional = false)
    private Candidat candidatid;
    @OneToMany(mappedBy = "passageid")
    private Collection<Jointure> jointureCollection;

    public Passage() {
    }

    public Passage(Integer passageid) {
        this.passageid = passageid;
    }

    public Integer getPassageid() {
        return passageid;
    }

    public void setPassageid(Integer passageid) {
        this.passageid = passageid;
    }

    public Integer getPassageEtat() {
        return passageEtat;
    }

    public void setPassageEtat(Integer passageEtat) {
        this.passageEtat = passageEtat;
    }

    public Date getPassageDate() {
        return passageDate;
    }

    public void setPassageDate(Date passageDate) {
        this.passageDate = passageDate;
    }

    public Date getPassagedebutVal() {
        return passagedebutVal;
    }

    public void setPassagedebutVal(Date passagedebutVal) {
        this.passagedebutVal = passagedebutVal;
    }

    public Date getPassagefinVal() {
        return passagefinVal;
    }

    public void setPassagefinVal(Date passagefinVal) {
        this.passagefinVal = passagefinVal;
    }

    @XmlTransient
    public Collection<ReponseHisto> getReponseHistoCollection() {
        return reponseHistoCollection;
    }

    public void setReponseHistoCollection(Collection<ReponseHisto> reponseHistoCollection) {
        this.reponseHistoCollection = reponseHistoCollection;
    }

    @XmlTransient
    public Collection<Reponse> getReponseCollection() {
        return reponseCollection;
    }

    public void setReponseCollection(Collection<Reponse> reponseCollection) {
        this.reponseCollection = reponseCollection;
    }

    public Candidat getCandidatid() {
        return candidatid;
    }

    public void setCandidatid(Candidat candidatid) {
        this.candidatid = candidatid;
    }

    @XmlTransient
    public Collection<Jointure> getJointureCollection() {
        return jointureCollection;
    }

    public void setJointureCollection(Collection<Jointure> jointureCollection) {
        this.jointureCollection = jointureCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (passageid != null ? passageid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Passage)) {
            return false;
        }
        Passage other = (Passage) object;
        if ((this.passageid == null && other.passageid != null) || (this.passageid != null && !this.passageid.equals(other.passageid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.Passage[ passageid=" + passageid + " ]";
    }
    
}
