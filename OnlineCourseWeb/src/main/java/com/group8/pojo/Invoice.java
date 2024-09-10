/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author thang
 */
@Entity
@Table(name = "invoice")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Invoice.findAll", query = "SELECT i FROM Invoice i"),
    @NamedQuery(name = "Invoice.findById", query = "SELECT i FROM Invoice i WHERE i.id = :id"),
    @NamedQuery(name = "Invoice.findByCreatedDate", query = "SELECT i FROM Invoice i WHERE i.createdDate = :createdDate"),
    @NamedQuery(name = "Invoice.findByUpdatedDate", query = "SELECT i FROM Invoice i WHERE i.updatedDate = :updatedDate"),
    @NamedQuery(name = "Invoice.findByStatus", query = "SELECT i FROM Invoice i WHERE i.status = :status"),
    @NamedQuery(name = "Invoice.findByTransactionId", query = "SELECT i FROM Invoice i WHERE i.transactionId = :transactionId"),
    @NamedQuery(name = "Invoice.findByReferenceCode", query = "SELECT i FROM Invoice i WHERE i.referenceCode = :referenceCode"),
    @NamedQuery(name = "Invoice.findByPayerName", query = "SELECT i FROM Invoice i WHERE i.payerName = :payerName"),
    @NamedQuery(name = "Invoice.findByPayerPhone", query = "SELECT i FROM Invoice i WHERE i.payerPhone = :payerPhone"),
    @NamedQuery(name = "Invoice.findByPayerEmail", query = "SELECT i FROM Invoice i WHERE i.payerEmail = :payerEmail")})
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @Column(name = "status")
    private Boolean status;
    @Size(max = 100)
    @Column(name = "transaction_id")
    private String transactionId;
    @Size(max = 100)
    @Column(name = "reference_code")
    private String referenceCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "payer_name")
    private String payerName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "payer_phone")
    private String payerPhone;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "payer_email")
    private String payerEmail;
    @Column(name = "total")
    private double total;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "invoiceId")
    private Set<Enrollment> enrollmentSet;
    
    
    

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    public Invoice() {
    }

    public Invoice(Integer id) {
        this.id = id;
    }

    public Invoice(Integer id, Date createdDate, Date updatedDate, String payerName, String payerPhone, String payerEmail) {
        this.id = id;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.payerName = payerName;
        this.payerPhone = payerPhone;
        this.payerEmail = payerEmail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getPayerPhone() {
        return payerPhone;
    }

    public void setPayerPhone(String payerPhone) {
        this.payerPhone = payerPhone;
    }

    public String getPayerEmail() {
        return payerEmail;
    }

    public void setPayerEmail(String payerEmail) {
        this.payerEmail = payerEmail;
    }

    @JsonIgnore
    @XmlTransient
    public Set<Enrollment> getEnrollmentSet() {
        return enrollmentSet;
    }

    public void setEnrollmentSet(Set<Enrollment> enrollmentSet) {
        this.enrollmentSet = enrollmentSet;
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
        if (!(object instanceof Invoice)) {
            return false;
        }
        Invoice other = (Invoice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.group8.pojo.Invoice[ id=" + id + " ]";
    }

    
}
