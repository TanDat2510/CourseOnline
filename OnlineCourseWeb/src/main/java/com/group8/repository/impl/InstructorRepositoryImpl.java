/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.repository.impl;

import com.group8.pojo.Instructor;
import com.group8.repository.InstructorRepository;
import java.util.List;
import javax.persistence.Query;
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
public class InstructorRepositoryImpl implements InstructorRepository{

    @Autowired
    private LocalSessionFactoryBean factory;
    
    @Override
    public List<Instructor> getAllInstructors() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("Instructor.findAll");
        return q.getResultList();
    }
    
    public Instructor addInstructor(Instructor i){
        Session s = this.factory.getObject().getCurrentSession();
        if(i.getId()!=null){
            s.update(i);
        }else{
           s.save(i); 
        }   
        return i;
    }
    @Override
    public Instructor getInstructorById(int id){
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Instructor.class, id);
    }
}
