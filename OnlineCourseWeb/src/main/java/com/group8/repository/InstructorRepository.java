/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.group8.repository;

import com.group8.pojo.Instructor;
import java.util.List;

/**
 *
 * @author thang
 */
public interface InstructorRepository {
    List<Instructor> getAllInstructors();
    Instructor addInstructor(Instructor instructor);
    Instructor getInstructorById(int id);
}
