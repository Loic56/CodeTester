/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Loïc
 */
@Entity
@Table(name = "reponse_histo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReponseHisto.findAll", query = "SELECT r FROM ReponseHisto r"),
    @NamedQuery(name = "ReponseHisto.findByReponsedebut", query = "SELECT r FROM ReponseHisto r WHERE r.reponsedebut = :reponsedebut"),
    @NamedQuery(name = "ReponseHisto.findByReponsefin", query = "SELECT r FROM ReponseHisto r WHERE r.reponsefin = :reponsefin"),
    @NamedQuery(name = "ReponseHisto.findByReponsehistoid", query = "SELECT r FROM ReponseHisto r WHERE r.reponsehistoid = :reponsehistoid")})
public class ReponseHisto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "REPONSEHISTOID")
    private Integer reponsehistoid;

    @Lob
    @Size(max = 65535)
    @Column(name = "REPONSEVALEUR")
    private String reponsevaleur;

    @Column(name = "REPONSEDEBUT")
    private Integer reponsedebut;

    @Column(name = "REPONSEFIN")
    private Integer reponsefin;

    
    
    
    @JoinColumn(name = "QUESTIONID", referencedColumnName = "QUESTIONID")
    @ManyToOne(optional = false)
    private Question questionid;

    @JoinColumn(name = "PASSAGEID", referencedColumnName = "PASSAGEID")
    @ManyToOne(optional = false)
    private Passage passageid;

    public ReponseHisto() {
    }

    public ReponseHisto(Integer reponsehistoid) {
        this.reponsehistoid = reponsehistoid;
    }

    public String getReponsevaleur() {
        return reponsevaleur;
    }

    public void setReponsevaleur(String reponsevaleur) {
        this.reponsevaleur = reponsevaleur;
    }

    public Integer getReponsedebut() {
        return reponsedebut;
    }

    public void setReponsedebut(Integer reponsedebut) {
        this.reponsedebut = reponsedebut;
    }

    public Integer getReponsefin() {
        return reponsefin;
    }

    public void setReponsefin(Integer reponsefin) {
        this.reponsefin = reponsefin;
    }

    public Integer getReponsehistoid() {
        return reponsehistoid;
    }

    public void setReponsehistoid(Integer reponsehistoid) {
        this.reponsehistoid = reponsehistoid;
    }

    public Question getQuestionid() {
        return questionid;
    }

    public void setQuestionid(Question questionid) {
        this.questionid = questionid;
    }

    public Passage getPassageid() {
        return passageid;
    }

    public void setPassageid(Passage passageid) {
        this.passageid = passageid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reponsehistoid != null ? reponsehistoid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReponseHisto)) {
            return false;
        }
        ReponseHisto other = (ReponseHisto) object;
        if ((this.reponsehistoid == null && other.reponsehistoid != null) || (this.reponsehistoid != null && !this.reponsehistoid.equals(other.reponsehistoid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.ReponseHisto[ reponsehistoid=" + reponsehistoid + " ]";
    }

}
