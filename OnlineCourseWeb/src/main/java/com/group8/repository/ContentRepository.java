/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.group8.repository;

import com.group8.pojo.Content;
import com.group8.pojo.Information;
import com.group8.pojo.Lecture;
import com.group8.pojo.Video;
import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author thang
 */
public interface ContentRepository {

    List<Content> getContentByCourseId(int id, Map<String, String> params);

    Lecture getLectureById(int id);

    Video getVideoById(int id);

    Information getInformationById(int id);

    void addUpContent(Content content);

    void addUpLecture(Lecture lecture);

    void addUpVideo(Video video);

    void addUpInformation(Information infor);

    Content getContentById(int id);

    void deleteContent(int id);

    void deleteLecture(int id);

    void deleteVideo(int id);

    void deleteInformation(int id);

    Content getContentDTOByCourseIdAndContentId(int contentId);

}
