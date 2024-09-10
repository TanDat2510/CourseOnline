/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.service.impl;

import com.group8.dto.AddUserDTO;
import com.group8.dto.InstructorDTO;
import com.group8.pojo.Instructor;
import com.group8.repository.InstructorRepository;
import com.group8.repository.UserRepository;
import com.group8.service.InstructorService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author thang
 */
@Service("instructorDetailService")
public class InstructorServiceImpl implements InstructorService{

    @Autowired
    private InstructorRepository instructorRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<InstructorDTO> getAllInstructorsDTO(Map<String,String>params) {
        List<Instructor> instructors = this.instructorRepo.getAllInstructors(params);
        return instructors.stream()
                .map(i -> {
                    InstructorDTO instructorDTO = new InstructorDTO();
                    modelMapper.map(i, instructorDTO);
                    return instructorDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Instructor> getAllInstructors(Map<String, String> params) {
        return this.instructorRepo.getAllInstructors(params);
    }

    @Override
    public void addInstructor(Instructor instructor) {
        instructor.setCreatedDate(new Date());
        instructor.setUpdatedDate(new Date());
        this.instructorRepo.addInstructor(instructor);
    }

    @Override
    public AddUserDTO getInstructorById(int id) {
        Instructor instructor = this.instructorRepo.getInstructorById(id);
        AddUserDTO addUserDTO = new AddUserDTO();        
        addUserDTO.setId(instructor.getId());
        addUserDTO.setIdInstructor(instructor.getUserId().getId());
        addUserDTO.setFirstName(instructor.getUserId().getFirstName());
        addUserDTO.setLastName(instructor.getUserId().getLastName());
        addUserDTO.setEmail(instructor.getUserId().getEmail());
        addUserDTO.setPhone(instructor.getUserId().getPhone());
        addUserDTO.setUsername(instructor.getUserId().getUsername());
        addUserDTO.setPassword(instructor.getUserId().getPassword());
        addUserDTO.setAvatar(instructor.getUserId().getAvatar());
        addUserDTO.setExpertise(instructor.getExpertise());
        addUserDTO.setDescription(instructor.getDescription());
        addUserDTO.setUserRole(instructor.getUserId().getUserRole());
        return addUserDTO;
    }
    


}
