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
import javax.persistence.Lob;
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
@Table(name = "test")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Test.findAll", query = "SELECT t FROM Test t"),
    @NamedQuery(name = "Test.findByTestid", query = "SELECT t FROM Test t WHERE t.testid = :testid"),
    @NamedQuery(name = "Test.findByTestmatiere", query = "SELECT t FROM Test t WHERE t.testmatiere = :testmatiere"),
    @NamedQuery(name = "Test.findByTestduree", query = "SELECT t FROM Test t WHERE t.testduree = :testduree"),
    @NamedQuery(name = "Test.findByTestNbquestionRub", query = "SELECT t FROM Test t WHERE t.testNbquestionRub = :testNbquestionRub"),
    @NamedQuery(name = "Test.findByTestnature", query = "SELECT t FROM Test t WHERE t.testnature = :testnature"),
    @NamedQuery(name = "Test.findByTestformat", query = "SELECT t FROM Test t WHERE t.testformat = :testformat"),
    @NamedQuery(name = "Test.findByTheme", query = "SELECT t FROM Test t WHERE t.theme = :theme"),
    @NamedQuery(name = "Test.findByNiveau", query = "SELECT t FROM Test t WHERE t.niveau = :niveau")})
public class Test implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "TESTID")
    private Integer testid;

    @Size(max = 255)
    @Column(name = "TESTMATIERE")
    private String testmatiere;

    @Column(name = "TESTDUREE")
    private Short testduree;

    @Column(name = "TEST_NBQUESTION_RUB")
    private Integer testNbquestionRub;

    @Size(max = 50)
    @Column(name = "TESTNATURE")
    private String testnature;

    @Size(max = 255)
    @Column(name = "TESTFORMAT")
    private String testformat;

    @Lob
    @Size(max = 65535)
    @Column(name = "TEST_START")
    private String testStart;

    @Lob
    @Size(max = 65535)
    @Column(name = "TEST_DESCRIPTION")
    private String testDescription;

    @Size(max = 255)
    @Column(name = "THEME")
    private String theme;

    @Column(name = "NIVEAU")
    private Integer niveau;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "testid")
    private Collection<Rubrique> rubriqueCollection;

    @JoinColumn(name = "CATEGORIEID", referencedColumnName = "CATEGORIEID")
    @ManyToOne(optional = false)
    private Categorie categorieid;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "testid")
    private Collection<Jointure> jointureCollection;

    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Test)) {
            return false;
        }
        Test other = (Test) object;
        if ((this.testid == null && other.testid != null) || (this.testid != null && !this.testid.equals(other.testid))) {
            return false;
        }
        return true;
    }


    public Test() {
    }

    public Test(Integer testid) {
        this.testid = testid;
    }

    public Integer getTestid() {
        return testid;
    }

    public void setTestid(Integer testid) {
        this.testid = testid;
    }

    public String getTestmatiere() {
        return testmatiere;
    }

    public void setTestmatiere(String testmatiere) {
        this.testmatiere = testmatiere;
    }

    public Short getTestduree() {
        return testduree;
    }

    public void setTestduree(Short testduree) {
        this.testduree = testduree;
    }

    public Integer getTestNbquestionRub() {
        return testNbquestionRub;
    }

    public void setTestNbquestionRub(Integer testNbquestionRub) {
        this.testNbquestionRub = testNbquestionRub;
    }

    public String getTestnature() {
        return testnature;
    }

    public void setTestnature(String testnature) {
        this.testnature = testnature;
    }

    public String getTestformat() {
        return testformat;
    }

    public void setTestformat(String testformat) {
        this.testformat = testformat;
    }

    public String getTestStart() {
        return testStart;
    }

    public void setTestStart(String testStart) {
        this.testStart = testStart;
    }

    public String getTestDescription() {
        return testDescription;
    }

    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Integer getNiveau() {
        return niveau;
    }

    public void setNiveau(Integer niveau) {
        this.niveau = niveau;
    }

    @XmlTransient
    public Collection<Rubrique> getRubriqueCollection() {
        return rubriqueCollection;
    }

    public void setRubriqueCollection(Collection<Rubrique> rubriqueCollection) {
        this.rubriqueCollection = rubriqueCollection;
    }

    public Categorie getCategorieid() {
        return categorieid;
    }

    public void setCategorieid(Categorie categorieid) {
        this.categorieid = categorieid;
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
        hash += (testid != null ? testid.hashCode() : 0);
        return hash;
    }

    
    
    @Override
    public String toString() {
        return "jpa.Test[ testid=" + testid + " ]";
    }

}
