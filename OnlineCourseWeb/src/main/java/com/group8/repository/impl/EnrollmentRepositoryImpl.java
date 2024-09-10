/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.repository.impl;

import com.group8.pojo.Course;
import com.group8.pojo.Enrollment;
import com.group8.pojo.Enum.EnrollmentStatus;
import com.group8.repository.EnrollmentRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author thang
 */
@Repository
@Transactional
public class EnrollmentRepositoryImpl implements EnrollmentRepository {

    @Autowired
    private Environment env;

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Enrollment> getEnrollments(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Enrollment> q = b.createQuery(Enrollment.class);
        Root<Enrollment> root = q.from(Enrollment.class);
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            // Lọc theo ngày ghi danh
            String fromCreatedDate = params.get("fromCreatedDate");
            String toCreatedDate = params.get("toCreatedDate");
            if (fromCreatedDate != null && !fromCreatedDate.isEmpty()) {
                Date fromDate = java.sql.Date.valueOf(fromCreatedDate);
                Predicate p1 = b.greaterThanOrEqualTo(root.get("createdDate"), fromDate);
                predicates.add(p1);
            }
            if (toCreatedDate != null && !toCreatedDate.isEmpty()) {
                Date toDate = java.sql.Date.valueOf(toCreatedDate);
                Predicate p2 = b.lessThanOrEqualTo(root.get("createdDate"), toDate);
                predicates.add(p2);
            }

            // Lọc theo ngày cập nhật
            String fromUpdatedDate = params.get("fromUpdatedDate");
            String toUpdatedDate = params.get("toUpdatedDate");
            if (fromUpdatedDate != null && !fromUpdatedDate.isEmpty()) {
                Date fromDate = java.sql.Date.valueOf(fromUpdatedDate);
                Predicate p3 = b.greaterThanOrEqualTo(root.get("updatedDate"), fromDate);
                predicates.add(p3);
            }
            if (toUpdatedDate != null && !toUpdatedDate.isEmpty()) {
                Date toDate = java.sql.Date.valueOf(toUpdatedDate);
                Predicate p4 = b.lessThanOrEqualTo(root.get("updatedDate"), toDate);
                predicates.add(p4);
            }

            // Lọc theo từ khóa trong tiêu đề khóa học
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                Join<Enrollment, Course> courseJoin = root.join("courseId"); // Ensure "course" is correct or use "courseId" if it's an ID
                Predicate p5 = b.like(courseJoin.get("title"), String.format("%%%s%%", kw));
                predicates.add(p5);
            }

            // Lọc theo trạng thái
            String status = params.get("status");
            if (status != null && !status.isEmpty()) {
                EnrollmentStatus statusEnum = EnrollmentStatus.valueOf(status.toUpperCase());
                Predicate p6 = b.equal(root.get("status"), statusEnum);
                predicates.add(p6);
            }

            q.where(predicates.toArray(new Predicate[0]));
        }

        Query query = s.createQuery(q);
        int PAGE_SIZE = Integer.parseInt(env.getProperty("page.size.enrollment"));
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
    public void addOrUpEnrollment(Enrollment enrollment) {
        Session s = this.factory.getObject().getCurrentSession();
        if (enrollment.getId() != null) {
            s.update(enrollment);
        } else {
            s.save(enrollment);
        }
    }

    @Override
    public boolean checkEnrollment(int courseId, int userId) {
        Session session = this.factory.getObject().getCurrentSession();
        if (userId == 0) {
            return false;
        }
        // Sử dụng CriteriaBuilder để tạo truy vấn
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Enrollment> root = cq.from(Enrollment.class);

        // Xác định điều kiện WHERE
        cq.select(cb.count(root))
                .where(
                        cb.equal(root.get("courseId"), courseId),
                        cb.equal(root.get("userId"), userId)
                );

        // Thực thi truy vấn và lấy kết quả
        Long count = session.createQuery(cq).getSingleResult();

        return count > 0;

    }

    @Override
    public Enrollment getEnrollmentByCourseIdAndUserId(int courseId, int userId) {
        // Get the current session
        Session s = this.factory.getObject().getCurrentSession();

        // Ensure userId is valid, otherwise return null
        if (userId == 0) {
            return null;
        }

        // Create CriteriaBuilder and CriteriaQuery
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Enrollment> q = b.createQuery(Enrollment.class);
        Root<Enrollment> root = q.from(Enrollment.class);

        // Build the query with conditions
        q.select(root).where(
                b.equal(root.get("courseId"), courseId),
                b.equal(root.get("userId"), userId)
        );

        // Execute the query and handle potential NoResultException
        Enrollment enrollment = null;
        try {
            enrollment = s.createQuery(q).getSingleResult();
        } catch (NoResultException ex) {
            // Handle the exception: No entity found for the query
            // You can return null or handle it in another way if needed
            System.out.println("No enrollment found for the given courseId and userId.");
        }

        return enrollment;
    }

}
