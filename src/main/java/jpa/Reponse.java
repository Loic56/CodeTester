/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa;

import java.io.Serializable;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Loïc
 */
@Entity
@Table(name = "reponse")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reponse.findAll", query = "SELECT r FROM Reponse r"),
    @NamedQuery(name = "Reponse.findByReponseid", query = "SELECT r FROM Reponse r WHERE r.reponseid = :reponseid"),
    @NamedQuery(name = "Reponse.findByReponsetexte", query = "SELECT r FROM Reponse r WHERE r.reponsetexte = :reponsetexte"),
    @NamedQuery(name = "Reponse.findByReponseduree", query = "SELECT r FROM Reponse r WHERE r.reponseduree = :reponseduree"),
    @NamedQuery(name = "Reponse.findByReponsemessage", query = "SELECT r FROM Reponse r WHERE r.reponsemessage = :reponsemessage")})
public class Reponse implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "REPONSEID")
    private Integer reponseid;
    
    @Size(max = 255)
    @Column(name = "REPONSETEXTE")
    private String reponsetexte;
    
    @Column(name = "REPONSEDUREE")
    private Integer reponseduree;
    
    @Size(max = 255)
    @Column(name = "REPONSEMESSAGE")
    private String reponsemessage;
    
    @JoinColumn(name = "PASSAGEID", referencedColumnName = "PASSAGEID")
    @ManyToOne(optional = false)
    private Passage passageid;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reponseid")
    private Collection<Question> questionCollection;

    public Reponse() {
    }

    public Reponse(Integer reponseid) {
        this.reponseid = reponseid;
    }

    public Integer getReponseid() {
        return reponseid;
    }

    public void setReponseid(Integer reponseid) {
        this.reponseid = reponseid;
    }

    public String getReponsetexte() {
        return reponsetexte;
    }

    public void setReponsetexte(String reponsetexte) {
        this.reponsetexte = reponsetexte;
    }

    public Integer getReponseduree() {
        return reponseduree;
    }

    public void setReponseduree(Integer reponseduree) {
        this.reponseduree = reponseduree;
    }

    public String getReponsemessage() {
        return reponsemessage;
    }

    public void setReponsemessage(String reponsemessage) {
        this.reponsemessage = reponsemessage;
    }

    public Passage getPassageid() {
        return passageid;
    }

    public void setPassageid(Passage passageid) {
        this.passageid = passageid;
    }

    @XmlTransient
    public Collection<Question> getQuestionCollection() {
        return questionCollection;
    }

    public void setQuestionCollection(Collection<Question> questionCollection) {
        this.questionCollection = questionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reponseid != null ? reponseid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reponse)) {
            return false;
        }
        Reponse other = (Reponse) object;
        if ((this.reponseid == null && other.reponseid != null) || (this.reponseid != null && !this.reponseid.equals(other.reponseid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.Reponse[ reponseid=" + reponseid + " ]";
    }
    
}
