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
@Table(name = "admin")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Admin.findAll", query = "SELECT a FROM Admin a"),
    @NamedQuery(name = "Admin.findByAdminid", query = "SELECT a FROM Admin a WHERE a.adminid = :adminid"),
    @NamedQuery(name = "Admin.findByAdminlogin", query = "SELECT a FROM Admin a WHERE a.adminlogin = :adminlogin"),
    @NamedQuery(name = "Admin.findByAdminpassword", query = "SELECT a FROM Admin a WHERE a.adminpassword = :adminpassword")})
public class Admin implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ADMINID")
    private Integer adminid;
    @Size(max = 255)
    @Column(name = "ADMINLOGIN")
    private String adminlogin;
    @Size(max = 255)
    @Column(name = "ADMINPASSWORD")
    private String adminpassword;

    public Admin() {
    }

    public Admin(Integer adminid) {
        this.adminid = adminid;
    }

    public Integer getAdminid() {
        return adminid;
    }

    public void setAdminid(Integer adminid) {
        this.adminid = adminid;
    }

    public String getAdminlogin() {
        return adminlogin;
    }

    public void setAdminlogin(String adminlogin) {
        this.adminlogin = adminlogin;
    }

    public String getAdminpassword() {
        return adminpassword;
    }

    public void setAdminpassword(String adminpassword) {
        this.adminpassword = adminpassword;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (adminid != null ? adminid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Admin)) {
            return false;
        }
        Admin other = (Admin) object;
        if ((this.adminid == null && other.adminid != null) || (this.adminid != null && !this.adminid.equals(other.adminid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.Admin[ adminid=" + adminid + " ]";
    }
    
}
