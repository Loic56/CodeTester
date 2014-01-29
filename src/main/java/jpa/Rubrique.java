/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.faces.model.DataModel;
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
@Table(name = "rubrique")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rubrique.findAll", query = "SELECT r FROM Rubrique r"),
    @NamedQuery(name = "Rubrique.findByRubriqueid", query = "SELECT r FROM Rubrique r WHERE r.rubriqueid = :rubriqueid"),
    @NamedQuery(name = "Rubrique.findByRubriquenom", query = "SELECT r FROM Rubrique r WHERE r.rubriquenom = :rubriquenom")})
public class Rubrique implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "RUBRIQUEID")
    private Integer rubriqueid;

    @Size(max = 255)
    @Column(name = "RUBRIQUENOM")
    private String rubriquenom;

    @JoinColumn(name = "TESTID", referencedColumnName = "TESTID")
    @ManyToOne(optional = false)
    private Test testid;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rubriqueid")
    private Collection<Question> questionCollection;

    public Rubrique() {
    }

    public Rubrique(Integer rubriqueid) {
        this.rubriqueid = rubriqueid;
    }

    public Integer getRubriqueid() {
        return rubriqueid;
    }

    public void setRubriqueid(Integer rubriqueid) {
        this.rubriqueid = rubriqueid;
    }

    public String getRubriquenom() {
        return rubriquenom;
    }

    public void setRubriquenom(String rubriquenom) {
        this.rubriquenom = rubriquenom;
    }

    public Test getTestid() {
        return testid;
    }

    public void setTestid(Test testid) {
        this.testid = testid;
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
        hash += (rubriqueid != null ? rubriqueid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rubrique)) {
            return false;
        }
        Rubrique other = (Rubrique) object;
        if ((this.rubriqueid == null && other.rubriqueid != null) || (this.rubriqueid != null && !this.rubriqueid.equals(other.rubriqueid))) {
            return false;
        }
        return true;
    }



    @Override
    public String toString() {
        return "jpa.Rubrique[ rubriqueid=" + rubriqueid + " ]";
    }



}
