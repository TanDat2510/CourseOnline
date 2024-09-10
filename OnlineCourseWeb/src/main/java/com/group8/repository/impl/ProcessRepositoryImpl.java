/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.repository.impl;

import com.group8.pojo.Process;
import com.group8.repository.ProcessRepository;
import javax.persistence.NoResultException;
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
 * @author thang
 */
@Repository
@Transactional
public class ProcessRepositoryImpl implements ProcessRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public int checkProcess(int userId, int contentId) {
        Session session = this.factory.getObject().getCurrentSession();

        if (userId == 0) {
            return -1;
        }

        // Sử dụng CriteriaBuilder để tạo truy vấn
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Process> cq = cb.createQuery(Process.class);
        Root<Process> root = cq.from(Process.class);

        // Xác định điều kiện WHERE
        cq.select(root)
                .where(
                        cb.equal(root.get("contentId"), contentId),
                        cb.equal(root.get("userId"), userId)
                );

        // Thực thi truy vấn và lấy kết quả
        try {
            Process process = session.createQuery(cq).getSingleResult();
            // Nếu tìm thấy Process, trả về giá trị của point
            return process.getPoint();
        } catch (NoResultException e) {
            // Nếu không tìm thấy Process, trả về -1
            return -1;
        }
    }

    @Override
    public void addUpProcess(Process process) {
        Session s = this.factory.getObject().getCurrentSession();
        if (process.getId() != null) {
            s.update(process);
        } else {
            s.save(process);
        }
    }

}
