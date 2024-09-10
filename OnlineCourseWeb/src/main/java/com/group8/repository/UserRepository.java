package com.group8.repository;

import com.group8.pojo.User;
import java.util.Map;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
/**
 *
 * @author admin
 */
public interface UserRepository {

    User getUserByUsername(String username);

    boolean authUser(String username, String password);

    User addUser(User user);

    User getUserByID(int id);

    void deleteUserInstructor(int id);

    long countUsers(Map<String, String> params);

    void changePassword(User user);
}
