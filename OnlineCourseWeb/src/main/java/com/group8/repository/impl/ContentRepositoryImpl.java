/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.repository.impl;

import com.group8.dto.CourseContentDTO;
import com.group8.pojo.Content;
import com.group8.pojo.Information;
import com.group8.pojo.Lecture;
import com.group8.pojo.Video;
import com.group8.repository.ContentRepository;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author thang
 */
@Repository
@Transactional
public class ContentRepositoryImpl implements ContentRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private Environment env;

    @Override
    public List<Content> getContentByCourseId(int id, Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Content> q = b.createQuery(Content.class);
        Root root = q.from(Content.class);
        q.select(root);
        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            Predicate p1 = b.equal(root.get("courseId"), id);
            predicates.add(p1);
            // Lấy giá trị từ params
            String kw = params.get("kw");
            // Viết truy vấn cho param
            if (kw != null && !kw.isEmpty()) {
                Predicate p2 = b.like(root.get("title"), String.format("%%%s%%", kw));
                predicates.add(p2);
            }
            // Thực hiện tổng hợp các truy vấn
            q.where(predicates.toArray(Predicate[]::new));
        }

        q.orderBy(b.asc(root.get("createdDate")));
        Query query = s.createQuery(q);
        int PAGE_SIZE = Integer.parseInt(env.getProperty("page.size.content"));
        if (params != null) {
            String page = params.get("page");
            if (page != null && !page.isEmpty()) {
                int p = Integer.parseInt(page);
                int start = (p - 1) * PAGE_SIZE;

                query.setFirstResult(start);
                query.setMaxResults(PAGE_SIZE);
            }
        }

        return query.getResultList();

    }

    @Override
    public Lecture getLectureById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Lecture.class, id);

    }

    @Override
    public Video getVideoById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Video.class, id);
    }

    @Override
    @Transactional
    public void addUpContent(Content content) {
        Session s = this.factory.getObject().getCurrentSession();
        if (content.getId() != null) {
            s.update(content);
        } else {
            s.save(content);
        }
    }

    @Override
    public void addUpLecture(Lecture lecture) {
        Session s = this.factory.getObject().getCurrentSession();
        if (lecture.getId() != null) {
            s.update(lecture);
        } else {
            s.save(lecture);
        }
    }

    @Override
    public void addUpVideo(Video video) {
        Session s = this.factory.getObject().getCurrentSession();
        if (video.getId() != null) {
            s.update(video);
        } else {
            s.save(video);
        }
    }

    @Override
    public Content getContentById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Content.class, id);
    }

    @Override
    public void deleteContent(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Content c = this.getContentById(id);
        s.delete(c);
    }

    @Override
    public void deleteLecture(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Lecture l = this.getLectureById(id);
        s.delete(l);
    }

    @Override
    public void deleteVideo(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Video v = this.getVideoById(id);
        s.delete(v);
    }

    @Override
    public Information getInformationById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Information.class, id);
    }

    @Override
    public void addUpInformation(Information infor) {
        Session s = this.factory.getObject().getCurrentSession();
        if (infor.getId() != null) {
            s.update(infor);
        } else {
            s.save(infor);
        }
    }

    @Override
    public void deleteInformation(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Information i = this.getInformationById(id);
        s.delete(i);
    }

    @Override
    public Content getContentDTOByCourseIdAndContentId(int contentId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Content> q = b.createQuery(Content.class);
        Root root = q.from(Content.class);
        q.select(root).where(b.equal(root.get("id"), contentId));
        Content c = s.createQuery(q).getSingleResult();
        return c;
    }

}
