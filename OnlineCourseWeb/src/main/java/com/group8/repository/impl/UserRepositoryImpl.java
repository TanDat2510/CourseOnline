/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.repository.impl;

import com.group8.pojo.User;
import com.group8.repository.UserRepository;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author admin
 */
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private BCryptPasswordEncoder passEncoder;

    @Override
    public User getUserByUsername(String username) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("User.findByUsername");
        q.setParameter("username", username);

        return (User) q.getSingleResult();

    }


    @Override
    public boolean authUser(String username, String password) {
        User u = this.getUserByUsername(username);

        return this.passEncoder.matches(password, u.getPassword());
    }


    @Override
    public User addUser(User u) {
        Session s = this.factory.getObject().getCurrentSession();
        
        if (u.getId() != null) {
            s.update(u);

        } else {
            s.save(u);
        }
        return u;
    }
    

    @Override
    public User getUserByID(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(User.class, id);
    }

    @Override
    public void deleteUserInstructor(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        User u = this.getUserByID(id);
        s.delete(u);

    }


    @Override
    public long countUsers(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);
        Root<User> root = q.from(User.class);

        // Tạo truy vấn đếm
        q.select(b.count(root));

        // Tạo danh sách các điều kiện lọc
        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            String role1 = params.get("role1");
            String role2 = params.get("role2");

            if (role1 != null && !role1.isEmpty()) {
                Predicate p1 = b.equal(root.get("userRole"), role1);
                predicates.add(p1);
            }
            if (role2 != null && !role2.isEmpty()) {
                Predicate p2 = b.equal(root.get("userRole"), role2);
                predicates.add(p2);
            }

            // Áp dụng các điều kiện lọc vào truy vấn
            if (!predicates.isEmpty()) {
                q.where(predicates.toArray(Predicate[]::new));
            }
        }

        Query query = s.createQuery(q);
        return (long) query.getSingleResult();
    }

    
    
    @Override
    public void changePassword(User user) {
        Session s = this.factory.getObject().getCurrentSession();
        s.update(user);
    }
}
