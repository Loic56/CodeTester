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
@Table(name = "jointure")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jointure.findAll", query = "SELECT j FROM Jointure j"),
    @NamedQuery(name = "Jointure.findByJointureid", query = "SELECT j FROM Jointure j WHERE j.jointureid = :jointureid"),
    @NamedQuery(name = "Jointure.findByChampvide", query = "SELECT j FROM Jointure j WHERE j.champvide = :champvide")})
public class Jointure implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "JOINTUREID")
    private Integer jointureid;

    @Size(max = 255)
    @Column(name = "CHAMPVIDE")
    private String champvide;

    @JoinColumn(name = "PASSAGEID", referencedColumnName = "PASSAGEID")
    @ManyToOne
    private Passage passageid;

    @JoinColumn(name = "TESTID", referencedColumnName = "TESTID")
    @ManyToOne(optional = false)
    private Test testid;

    public Jointure() {
    }

    public Jointure(Test testid) {
        this.testid = testid;
    }

    public Jointure(Integer jointureid) {
        this.jointureid = jointureid;
    }

    public Integer getJointureid() {
        return jointureid;
    }

    public void setJointureid(Integer jointureid) {
        this.jointureid = jointureid;
    }

    public String getChampvide() {
        return champvide;
    }

    public void setChampvide(String champvide) {
        this.champvide = champvide;
    }

    public Passage getPassageid() {
        return passageid;
    }

    public void setPassageid(Passage passageid) {
        this.passageid = passageid;
    }

    public Test getTestid() {
        return testid;
    }

    public void setTestid(Test testid) {
        this.testid = testid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jointureid != null ? jointureid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jointure)) {
            return false;
        }
        Jointure other = (Jointure) object;
        if ((this.jointureid == null && other.jointureid != null) || (this.jointureid != null && !this.jointureid.equals(other.jointureid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.Jointure[ jointureid=" + jointureid + " ]";
    }

}
