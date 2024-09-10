/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.group8.pojo.Content;
import com.group8.pojo.Enum.EntityType;
import com.group8.pojo.Information;
import com.group8.pojo.Lecture;
import com.group8.pojo.Video;
import com.group8.repository.ContentRepository;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author thang
 */
@Data
public class ContentDTO {

    private Integer id;
    private String title;
    private String entityId;
    private Integer courseId;
    private Object content;
    private EntityType entityType;
    private Boolean status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedDate;
    private int point;

    public static ContentDTO fromContent(Content content, ContentRepository contentRepo) {
        ContentDTO dto = new ContentDTO();
        dto.setId(content.getId());
        dto.setTitle(content.getTitle());
        dto.setEntityId(content.getEntityId());
        dto.setCourseId(content.getCourseId().getId());
        dto.setEntityType(content.getEntityType());
        dto.setStatus(content.getStatus());
        dto.setCreatedDate(content.getCreatedDate());
        dto.setUpdatedDate(content.getUpdatedDate());

        switch (content.getEntityType().name()) {
            case "LECTURE":
                Lecture lecture = contentRepo.getLectureById(Integer.parseInt(content.getEntityId()));
                dto.setContent(lecture);
                break;
            case "VIDEO":
                Video video = contentRepo.getVideoById(Integer.parseInt(content.getEntityId()));
                dto.setContent(video);
                break;
            case "INFORMATION":
                Information i = contentRepo.getInformationById(Integer.parseInt(content.getEntityId()));
                dto.setContent(i);
                break;
            default:
                dto.setContent(null);
                break;
        }

        return dto;
    }
}
