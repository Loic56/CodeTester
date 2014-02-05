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
    @NamedQuery(name = "Question.findByQuestiontext", query = "SELECT q FROM Question q WHERE q.questiontext = :questiontext"),
    @NamedQuery(name = "Question.findByQuestionimage", query = "SELECT q FROM Question q WHERE q.questionimage = :questionimage")})
public class Question implements Serializable {

    private static long serialVersionUID = 1L;

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "QUESTIONID")
    private Integer questionid;

    @Size(max = 255)
    @Column(name = "QUESTIONTEXT")
    private String questiontext;

    @Size(max = 255)
    @Column(name = "QUESTIONIMAGE")
    private String questionimage;

    @JoinColumn(name = "RUBRIQUEID", referencedColumnName = "RUBRIQUEID")
    @ManyToOne
    private Rubrique rubriqueid;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionid")
    private Collection<ReponseHisto> reponseHistoCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionid")
    private Collection<Reponse> reponseCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionid")
    private Collection<Proposition> propositionCollection;

    public Question() {
    }

    public Question(Integer questionid) {
        this.questionid = questionid;
    }

    public Question(Integer questionid, String questionimage) {
        this.questionid = questionid;
        this.questionimage = questionimage;
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

    public String getQuestionimage() {
        return questionimage;
    }

    public void setQuestionimage(String questionimage) {
        this.questionimage = questionimage;
    }

    public Rubrique getRubriqueid() {
        return rubriqueid;
    }

    public void setRubriqueid(Rubrique rubriqueid) {
        this.rubriqueid = rubriqueid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getQuestionid() != null ? getQuestionid().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Question)) {
            return false;
        }
        Question other = (Question) object;
        if ((this.getQuestionid() == null && other.getQuestionid() != null) || (this.getQuestionid() != null && !this.questionid.equals(other.questionid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.Question[ questionid=" + getQuestionid() + " ]";
    }

    /**
     * @return the reponseCollection
     */
    public Collection<Reponse> getReponseCollection() {
        return reponseCollection;
    }

    /**
     * @param reponseCollection the reponseCollection to set
     */
    public void setReponseCollection(Collection<Reponse> reponseCollection) {
        this.reponseCollection = reponseCollection;
    }

    /**
     * @return the propositionCollection
     */
    public Collection<Proposition> getPropositionCollection() {
        return propositionCollection;
    }

    /**
     * @param propositionCollection the propositionCollection to set
     */
    public void setPropositionCollection(Collection<Proposition> propositionCollection) {
        this.propositionCollection = propositionCollection;
    }

    /**
     * @return the reponseHistoCollection
     */
    public Collection<ReponseHisto> getReponseHistoCollection() {
        return reponseHistoCollection;
    }

    /**
     * @param reponseHistoCollection the reponseHistoCollection to set
     */
    public void setReponseHistoCollection(Collection<ReponseHisto> reponseHistoCollection) {
        this.reponseHistoCollection = reponseHistoCollection;
    }

}
