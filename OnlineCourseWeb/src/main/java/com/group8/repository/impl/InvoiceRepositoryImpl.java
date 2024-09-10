/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.repository.impl;

import com.group8.pojo.Invoice;
import com.group8.repository.InvoiceRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author thang
 * @author TAN DAT
 */
@Repository
@Transactional
public class InvoiceRepositoryImpl implements InvoiceRepository {

    private static final int PAGE_SIZE = 6;

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void addUpInvoice(Invoice invoice) {
        Session s = this.factory.getObject().getCurrentSession();
        if (invoice.getId() != null) {
            s.update(invoice);
        } else {
            s.save(invoice);
        }
    }

    @Override
    public Invoice checkrequestId(String RequestId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Invoice> cq = cb.createQuery(Invoice.class);
        Root<Invoice> invoice = cq.from(Invoice.class);

        // Đếm số lượng Invoice có transactionId tương ứng
        cq.where(cb.equal(invoice.get("referenceCode"), RequestId));

        Query query = s.createQuery(cq);
        return (Invoice) query.getSingleResult(); 
    }
    
    public List<Invoice> getAllInvoice(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Invoice> q = b.createQuery(Invoice.class);
        Root root = q.from(Invoice.class);
        q.select(root);
        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            String kw = params.get("kw");
            String status = params.get("status");

            if (kw != null && !kw.isEmpty()) {
                Predicate p1 = b.like(root.get("payerName"), String.format("%%%s%%", kw));
                predicates.add(p1);
            }
            if (status != null && !status.isEmpty()) {
                Boolean statusValue = status.equalsIgnoreCase("PAID") ? Boolean.TRUE : Boolean.FALSE;
                Predicate p2 = b.equal(root.get("status"), statusValue);
                predicates.add(p2);
            }
            q.where(predicates.toArray(Predicate[]::new));
        }

        Query query = s.createQuery(q);

        if (params != null) {
            String page = params.get("page");
            if (page != null && !page.isEmpty()) {
                int p = Integer.parseInt(page);
                int start = (p - 1) * PAGE_SIZE;

                query.setFirstResult(start);
                query.setMaxResults(PAGE_SIZE);
            }
        }
        return query.getResultList();
    }

    @Override
    public Invoice getInvoiceById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Invoice.class, id);
    }

    @Override
    public Invoice calculateTotalAmount(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Invoice.class, id);
    }

}
