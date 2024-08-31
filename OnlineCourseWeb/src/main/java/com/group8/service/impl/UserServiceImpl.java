/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.group8.dto.AddUserDTO;
import com.group8.dto.UserDTO;
import com.group8.pojo.CustomUserDetails;
import com.group8.pojo.User;
import com.group8.repository.UserRepository;
import com.group8.service.UserService;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author admin
 */
//@Service("userDetailsService")
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private BCryptPasswordEncoder passEncoder;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public User getUserByUsername(String username) {
        return this.userRepo.getUserByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = this.getUserByUsername(username);
        if (u == null) {
            throw new UsernameNotFoundException("Invalid User!");
        }

        return new CustomUserDetails(u);
    }

    @Override
    public boolean authUser(String username, String password) {
        return this.userRepo.authUser(username, password);
    }

    @Override
    public UserDTO addUser(Map<String, String> params, MultipartFile avatar) {
        User u = new User();
        AddUserDTO addUserDTO = new AddUserDTO();
        UserDTO userDTO = new UserDTO();
        addUserDTO.setFirstName(params.get("firstName"));
        addUserDTO.setLastName(params.get("lastName"));
        addUserDTO.setPhone(params.getOrDefault("phone", "9999999999"));
        addUserDTO.setEmail(params.getOrDefault("email", "a@gmail.com"));
        addUserDTO.setUsername(params.get("username"));
        addUserDTO.setPassword(this.passEncoder.encode(params.get("password")));
        addUserDTO.setUserRole(params.getOrDefault("role", "ROLE_STUDENT"));
        if (!avatar.isEmpty()) {
            try {
                Map<String, Object> res = this.cloudinary.uploader().upload(avatar.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                addUserDTO.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        modelMapper.map(addUserDTO, u);
        u.setActive(Boolean.TRUE);
        u.setCreatedDate(new Date());
        u.setUpdatedDate(new Date());

        this.userRepo.addUser(u);
        modelMapper.map(u, userDTO);
        return userDTO;
    }

    @Override
    public UserDTO getUserDTO(String username) {
        User u = this.userRepo.getUserByUsername(username);
        UserDTO userDTO = new UserDTO();
        modelMapper.map(u, userDTO);
        return userDTO;
    }

    @Override
    public AddUserDTO getUserById(Integer id) {
        User user = this.userRepo.getUserByID(id);
        AddUserDTO addUserDTO = new AddUserDTO();
        addUserDTO.setId(user.getId());
        addUserDTO.setFirstName(user.getFirstName());
        addUserDTO.setLastName(user.getLastName());
        addUserDTO.setEmail(user.getEmail());
        addUserDTO.setPhone(user.getPhone());
        addUserDTO.setUsername(user.getUsername());
        addUserDTO.setPassword(user.getPassword());
        addUserDTO.setFile(user.getFile());

        return addUserDTO;
    }

    @Override//Thêm User role INSTRUCTOR
    public void addUserInstructor(User user) {
        if (!user.getFile().isEmpty()) {
            try {
                Map<String, Object> res = this.cloudinary.uploader().upload(user.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                user.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        user.setActive(Boolean.TRUE);
        user.setCreatedDate(new Date());
        user.setUpdatedDate(new Date());

        this.userRepo.addUser(user);
    }

    @Override
    public void deleteUserInstuctor(int id) {
        this.userRepo.deleteUserInstructor(id);
    }
    
    @Override
    public void changePassword(User user) {
        user.setPassword(this.passEncoder.encode(user.getPassword()));
        this.userRepo.changePassword(user);
    }

}
