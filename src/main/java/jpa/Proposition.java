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
@Table(name = "proposition")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proposition.findAll", query = "SELECT p FROM Proposition p"),
    @NamedQuery(name = "Proposition.findByPropositionid", query = "SELECT p FROM Proposition p WHERE p.propositionid = :propositionid"),
    @NamedQuery(name = "Proposition.findByPropositionlibelle", query = "SELECT p FROM Proposition p WHERE p.propositionlibelle = :propositionlibelle"),
    @NamedQuery(name = "Proposition.findByPropositionvrai", query = "SELECT p FROM Proposition p WHERE p.propositionvrai = :propositionvrai")})
public class Proposition implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PROPOSITIONID")
    private Integer propositionid;
    
    @Size(max = 1000)
    @Column(name = "PROPOSITIONLIBELLE")
    private String propositionlibelle;
    
    @Column(name = "PROPOSITIONVRAI")
    private Short propositionvrai;
    
    @JoinColumn(name = "QUESTIONID", referencedColumnName = "QUESTIONID")
    @ManyToOne(optional = false)
    private Question questionid;

    public Proposition() {
    }

    public Proposition(Integer propositionid) {
        this.propositionid = propositionid;
    }

    public Integer getPropositionid() {
        return propositionid;
    }

    public void setPropositionid(Integer propositionid) {
        this.propositionid = propositionid;
    }

    public String getPropositionlibelle() {
        return propositionlibelle;
    }

    public void setPropositionlibelle(String propositionlibelle) {
        this.propositionlibelle = propositionlibelle;
    }

    public Short getPropositionvrai() {
        return propositionvrai;
    }

    public void setPropositionvrai(Short propositionvrai) {
        this.propositionvrai = propositionvrai;
    }

    public Question getQuestionid() {
        return questionid;
    }

    public void setQuestionid(Question questionid) {
        this.questionid = questionid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (propositionid != null ? propositionid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proposition)) {
            return false;
        }
        Proposition other = (Proposition) object;
        if ((this.propositionid == null && other.propositionid != null) || (this.propositionid != null && !this.propositionid.equals(other.propositionid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.Proposition[ propositionid=" + propositionid + " ]";
    }
    
}
