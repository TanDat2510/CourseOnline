/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.repository.impl;

import com.group8.pojo.Course;
import com.group8.pojo.Enrollment;
import com.group8.pojo.Invoice;
import com.group8.repository.StatsRepository;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author TAN DAT
 */
@Repository
@Transactional
public class StatsRepositoryImpl implements StatsRepository{
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Object[]> statsRevenueByMonth() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);

        Root<Invoice> rI = q.from(Invoice.class);

        q.where(b.equal(rI.get("status"), 1)); // Chỉ lấy hóa đơn đã thanh toán

        q.multiselect(
            b.function("YEAR", Integer.class, rI.get("createdDate")),
            b.function("MONTH", Integer.class, rI.get("createdDate")),
            b.sum(rI.get("totalAmount"))
        );
        q.groupBy(
            b.function("YEAR", Integer.class, rI.get("createdDate")),
            b.function("MONTH", Integer.class, rI.get("createdDate"))
        );
        q.orderBy(
            b.desc(b.function("YEAR", Integer.class, rI.get("createdDate"))),
            b.asc(b.function("MONTH", Integer.class, rI.get("createdDate")))
        );

        Query query = s.createQuery(q);
        return query.getResultList();
    }
 
}
