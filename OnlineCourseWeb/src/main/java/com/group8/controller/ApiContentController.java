/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.controller;

import com.group8.dto.ContentDTO;
import com.group8.service.ContentService;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author thang
 */

@Controller
@RequestMapping("/api")
@CrossOrigin
public class ApiContentController {

    @Autowired
    private ContentService contentService;

    @GetMapping("/courses/{courseId}/content")
    public ResponseEntity<List<ContentDTO>> list(Model model, @PathVariable(value = "courseId") int id, Map<String, String> params, Principal principal) {
        return new ResponseEntity<>(this.contentService.getContentDTOsByCourseId(id, params, principal), HttpStatus.OK);
    }

    @GetMapping("/courses/{courseId}/content/{contentId}")
    public ResponseEntity<ContentDTO> retrive(Model model, @PathVariable(value = "courseId") int id, @PathVariable(value = "contentId") int contentId, Map<String, String> params, Principal principal) {
        try {
            ContentDTO c = this.contentService.getContentDTOsByCourseIdAndContentId(id, contentId, principal);
            return new ResponseEntity<>(c, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/courses/{courseId}/content/{contentId}")
    public ResponseEntity<String> post(@PathVariable(value = "courseId") int id, @PathVariable(value = "contentId") int contentId, Principal principal, @RequestBody Map<String, String> processData) {
        try {
            boolean check = this.contentService.handleMarkCompleted(id, contentId, principal, processData);
            if(check == true)
                return new ResponseEntity<>(HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/courses/{courseId}/content/{contentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "courseId") int id, @PathVariable(value = "contentId") int contentId) {
        this.contentService.deleteContent(contentId);
    }

}
