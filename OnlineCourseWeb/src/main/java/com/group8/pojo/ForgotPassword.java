/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author TAN DAT
 */
@Entity
@Table(name = "forgotpassword")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ForgotPassword.findAll", query = "SELECT f FROM ForgotPassword f"),
    @NamedQuery(name = "ForgotPassword.findById", query = "SELECT f FROM ForgotPassword f WHERE f.id = :id"),
    @NamedQuery(name = "ForgotPassword.findByOtp", query = "SELECT f FROM ForgotPassword f WHERE f.otp = :otp"),
    @NamedQuery(name = "ForgotPassword.findByExpirationTime", query = "SELECT f FROM ForgotPassword f WHERE f.expirationTime = :expirationTime")})
public class ForgotPassword implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "otp")
    private int otp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "expirationTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationTime;
    @JoinColumn(name = "userId", referencedColumnName = "id")
    @ManyToOne
    private User user;

    public ForgotPassword() {
    }

    public ForgotPassword(Integer id) {
        this.id = id;
    }

    public ForgotPassword(Integer id, int otp, Date expirationTime) {
        this.id = id;
        this.otp = otp;
        this.expirationTime = expirationTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ForgotPassword)) {
            return false;
        }
        ForgotPassword other = (ForgotPassword) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tth.DTO.Forgotpassword[ id=" + id + " ]";
    }
    
}
