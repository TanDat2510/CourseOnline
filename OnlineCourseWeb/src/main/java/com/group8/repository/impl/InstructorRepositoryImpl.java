/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.repository.impl;

import com.group8.pojo.Course;
import com.group8.pojo.Instructor;
import com.group8.pojo.User;
import com.group8.repository.InstructorRepository;
import java.util.ArrayList;
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
 */
@Repository
@Transactional
public class InstructorRepositoryImpl implements InstructorRepository {

    private static final int PAGE_SIZE = 6;

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Instructor> getAllInstructors(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Instructor> q = b.createQuery(Instructor.class);
        Root root = q.from(Instructor.class);
        q.select(root);
        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            String kw = params.get("kw");
            String userId = params.get("userId");
            
            if (kw != null && !kw.isEmpty()) {
                Predicate p1 = b.like(root.get("expertise"), String.format("%%%s%%", kw));
                predicates.add(p1);
            }
            if (userId != null && !userId.isEmpty()) {
                Join<Instructor, User> userJoin = root.join("userId");
                Predicate userIdPredicate = b.equal(userJoin.get("id"), userId);
                predicates.add(userIdPredicate);
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
    public Instructor addInstructor(Instructor i) {
        Session s = this.factory.getObject().getCurrentSession();
        if (i.getId() != null) {
            s.update(i);
        } else {
            s.save(i);
        }
        return i;
    }

    @Override
    public Instructor getInstructorById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
      
        return s.get(Instructor.class, id);
    }

}
