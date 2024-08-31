/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.controller;

import com.group8.components.JwtService;
import com.group8.dto.AddUserDTO;
import com.group8.dto.UserDTO;
import com.group8.pojo.User;
import com.group8.service.InstructorService;
import com.group8.service.UserService;
import java.security.Principal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author TAN DAT
 */
@RestController
@RequestMapping("/api")
public class ApiUserController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @CrossOrigin
    public ResponseEntity<String> login(@RequestBody User user) {
        if (this.userService.authUser(user.getUsername(), user.getPassword()) == true) {
            String token = this.jwtService.generateTokenLogin(user.getUsername());

            return new ResponseEntity<>(token, HttpStatus.OK);
        }

        return new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path = "/users",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<UserDTO> addUser(@RequestParam Map<String, String> params, @RequestPart MultipartFile avatar) {
        UserDTO userDTO = this.userService.addUser(params, avatar);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @GetMapping(path = "/current-user", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<UserDTO> details(Principal user) {
        // Lấy đối tượng User từ service
        UserDTO userDTO = this.userService.getUserDTO(user.getName());
        // Trả về DTO trong ResponseEntity
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @DeleteMapping("/instructor/{instructorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "instructorId") int id) {
        AddUserDTO t = this.instructorService.getInstructorById(id);
        System.out.println("Hello" + this.instructorService.getInstructorById(id));
        this.userService.deleteUserInstuctor(t.getIdInstructor());
    }

    @PostMapping("/change-password/{username}")
    public ResponseEntity<String> changePasswordAdmin(
            @PathVariable("username") String username,
            @RequestBody Map<String, String> passwordData) {

        String oldPassword = passwordData.get("oldPassword");
        String newPassword = passwordData.get("newPassword");
        String confirmPassword = passwordData.get("confirmPassword");

        User user = userService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy người dùng!");
        }

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mật khẩu cũ không đúng!");
        }

        if (!newPassword.equals(confirmPassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mật khẩu mới và xác nhận mật khẩu không khớp!");
        }
        user.setPassword(newPassword);
        this.userService.changePassword(user);
        return ResponseEntity.ok("Đổi mật khẩu thành công!");
    }
}
