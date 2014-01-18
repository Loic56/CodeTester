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
@Table(name = "question")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Question.findAll", query = "SELECT q FROM Question q"),
    @NamedQuery(name = "Question.findByQuestionid", query = "SELECT q FROM Question q WHERE q.questionid = :questionid"),
    @NamedQuery(name = "Question.findByQuestiontext", query = "SELECT q FROM Question q WHERE q.questiontext = :questiontext")})
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "QUESTIONID")
    private Integer questionid;

    @Size(max = 255)
    @Column(name = "QUESTIONTEXT")
    private String questiontext;

    @JoinColumn(name = "RUBRIQUEID", referencedColumnName = "RUBRIQUEID")
    @ManyToOne(optional = false)
    private Rubrique rubriqueid;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionid")
    private Collection<Proposition> propositionCollection;

    
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionid")
    private Collection<ReponseHisto> reponseHistoCollection;

    @JoinColumn(name = "REPONSEID", referencedColumnName = "REPONSEID")
    @ManyToOne(optional = false)
    private Reponse reponseid;

    
    
    
    
    public Question() {
    }

    public Question(Integer questionid) {
        this.questionid = questionid;
    }

    public Integer getQuestionid() {
        return questionid;
    }

    public void setQuestionid(Integer questionid) {
        this.questionid = questionid;
    }

    public String getQuestiontext() {
        return questiontext;
    }

    public void setQuestiontext(String questiontext) {
        this.questiontext = questiontext;
    }

    @XmlTransient
    public Collection<ReponseHisto> getReponseHistoCollection() {
        return reponseHistoCollection;
    }

    public void setReponseHistoCollection(Collection<ReponseHisto> reponseHistoCollection) {
        this.reponseHistoCollection = reponseHistoCollection;
    }

    public Reponse getReponseid() {
        return reponseid;
    }

    public void setReponseid(Reponse reponseid) {
        this.reponseid = reponseid;
    }

    public Rubrique getRubriqueid() {
        return rubriqueid;
    }

    public void setRubriqueid(Rubrique rubriqueid) {
        this.rubriqueid = rubriqueid;
    }

    @XmlTransient
    public Collection<Proposition> getPropositionCollection() {
        return propositionCollection;
    }

    public void setPropositionCollection(Collection<Proposition> propositionCollection) {
        this.propositionCollection = propositionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (questionid != null ? questionid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Question)) {
            return false;
        }
        Question other = (Question) object;
        if ((this.questionid == null && other.questionid != null) || (this.questionid != null && !this.questionid.equals(other.questionid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.Question[ questionid=" + questionid + " ]";
    }

}
