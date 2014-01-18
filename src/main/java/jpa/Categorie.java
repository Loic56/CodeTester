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
import javax.persistence.Lob;
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
@Table(name = "categorie")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categorie.findAll", query = "SELECT c FROM Categorie c"),
    @NamedQuery(name = "Categorie.findByCategorieid", query = "SELECT c FROM Categorie c WHERE c.categorieid = :categorieid")})
public class Categorie implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CATEGORIEID")
    private Integer categorieid;
    @Lob
    @Size(max = 65535)
    @Column(name = "CATEGORIELIBELLE")
    private String categorielibelle;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categorieid")
    private Collection<Test> testCollection;

    public Categorie() {
    }

    public Categorie(Integer categorieid) {
        this.categorieid = categorieid;
    }

    public Integer getCategorieid() {
        return categorieid;
    }

    public void setCategorieid(Integer categorieid) {
        this.categorieid = categorieid;
    }

    public String getCategorielibelle() {
        return categorielibelle;
    }

    public void setCategorielibelle(String categorielibelle) {
        this.categorielibelle = categorielibelle;
    }

    @XmlTransient
    public Collection<Test> getTestCollection() {
        return testCollection;
    }

    public void setTestCollection(Collection<Test> testCollection) {
        this.testCollection = testCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (categorieid != null ? categorieid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categorie)) {
            return false;
        }
        Categorie other = (Categorie) object;
        if ((this.categorieid == null && other.categorieid != null) || (this.categorieid != null && !this.categorieid.equals(other.categorieid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.Categorie[ categorieid=" + categorieid + " ]";
    }
    
}
