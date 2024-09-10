/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.repository.impl;

import com.group8.pojo.Content;
import com.group8.pojo.Course;
import com.group8.pojo.Enrollment;
import com.group8.pojo.Invoice;
import com.group8.repository.StatsRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
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
public class StatsRepositoryImpl implements StatsRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Object[]> statsContentOfCourse(int courseId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);

        Root<Content> content = q.from(Content.class);

        q.multiselect(content.get("entityType"), b.count(content));

        Predicate coursePredicate = b.equal(content.get("courseId"), courseId);
        q.where(coursePredicate);

        q.groupBy(content.get("entityType"));

        Query query = s.createQuery(q);

        return query.getResultList();
    }

    @Override
    public List<Object[]> statsEnrollmentByPeriod(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);

        // Define the root of your query
        Root<Enrollment> rE = q.from(Enrollment.class);

        // Where clause to filter by year
        q.where(b.equal(b.function("YEAR", Integer.class, rE.get("createdDate")), params.get("year")));

        // Select the period (DAY, MONTH, etc.) and count the number of enrollments
        q.multiselect(
                b.function(params.get("period"), Integer.class, rE.get("createdDate")),
                b.count(rE.get("id")));

        // Group by the period (e.g., DAY, MONTH, etc.)
        q.groupBy(b.function(params.get("period"), Integer.class, rE.get("createdDate")));

        Query query = s.createQuery(q);

        return query.getResultList();
    }

    @Override
    public List<Object[]> statsEnrollmentGroupByCourse(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);

        Root rE = q.from(Enrollment.class);
        Join<Enrollment, Course> rC = rE.join("courseId");
        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            String fromCreatedDate = params.get("fromCreatedDate");
            String toCreatedDate = params.get("toCreatedDate");
            if (fromCreatedDate != null && !fromCreatedDate.isEmpty()) {
                Date fromDate = java.sql.Date.valueOf(fromCreatedDate);
                Predicate p1 = b.greaterThanOrEqualTo(rE.get("createdDate"), fromDate);
                predicates.add(p1);
            }
            if (toCreatedDate != null && !toCreatedDate.isEmpty()) {
                Date toDate = java.sql.Date.valueOf(toCreatedDate);
                Predicate p2 = b.lessThanOrEqualTo(rE.get("createdDate"), toDate);
                predicates.add(p2);
            }

            q.where(predicates.toArray(Predicate[]::new));
        }
        q.multiselect(
                rC.get("title"),
                b.count(rE.get("id")));

        // Group by the period (e.g., DAY, MONTH, etc.)
        q.groupBy(rC.get("id"));

        Query query = s.createQuery(q);

        return query.getResultList();

    }

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
            b.sum(rI.get("total"))
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
