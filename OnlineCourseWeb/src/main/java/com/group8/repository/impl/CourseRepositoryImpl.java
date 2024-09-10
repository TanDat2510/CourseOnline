/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.repository.impl;

import com.group8.dto.CourseDTO;
import com.group8.pojo.Course;
import com.group8.pojo.Enum.CourseStatus;
import com.group8.pojo.Enum.CourseType;
import com.group8.repository.CourseRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
public class CourseRepositoryImpl implements CourseRepository {

    private static final int PAGE_SIZE = 8;

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Course> getCourse(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Course> q = b.createQuery(Course.class);
        Root root = q.from(Course.class);
        q.select(root);
        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            String kw = params.get("kw");
            String fromPrice = params.get("fromPrice");
            String toPrice = params.get("toPrice");
            String cateId = params.get("cateId");
            String status = params.get("status");
            String courseType = params.get("courseType");
            String instructorId = params.get("instructorId");
            if (kw != null && !kw.isEmpty()) {
                Predicate p1 = b.like(root.get("title"), String.format("%%%s%%", kw));
                predicates.add(p1);
            }
            if (fromPrice != null && !fromPrice.isEmpty()) {
                Predicate p2 = b.greaterThanOrEqualTo(root.get("price"), Double.parseDouble(fromPrice));
                predicates.add(p2);
            }

            if (toPrice != null && !toPrice.isEmpty()) {
                Predicate p3 = b.lessThanOrEqualTo(root.get("price"), Double.parseDouble(toPrice));
                predicates.add(p3);
            }
            if (cateId != null && !cateId.isEmpty()) {
                Predicate p4 = b.equal(root.get("categoryId"), Integer.parseInt(cateId));
                predicates.add(p4);
            }
            if (status != null && !status.isEmpty()) {
                CourseStatus statusEnum = CourseStatus.valueOf(status.toUpperCase());
                Predicate p5 = b.equal(root.get("status"), statusEnum);
                predicates.add(p5);
            }
            if (courseType != null && !courseType.isEmpty()) {
                CourseType courseTypeEnum = CourseType.valueOf(courseType.toUpperCase());
                Predicate p6 = b.equal(root.get("courseType"), courseTypeEnum);
                predicates.add(p6);
            }
            if (instructorId != null && !instructorId.isEmpty()) {
                Predicate p7 = b.equal(root.get("instructorId"), Integer.parseInt(instructorId));
                predicates.add(p7);
            }
            q.where(predicates.toArray(Predicate[]::new));
        }
        Query query = s.createQuery(q);

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
    public void addOrUpCourse(Course course) {
        Session s = this.factory.getObject().getCurrentSession();
        if (course.getId() != null) {
            s.update(course);
        } else {
            s.save(course);
        }
    }

    @Override
    public Course getCourseById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Course.class, id);
    }

    @Override
    public void deleteCourse(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Course c = this.getCourseById(id);
        s.delete(c);
    }

    @Override
    public String getOrderInfor(String stringArray) {
        // Chuyển đổi chuỗi các ID thành danh sách số nguyên
        List<Integer> courseIds = new ArrayList<>();
        if (stringArray != null && !stringArray.isEmpty()) {
            String[] ids = stringArray.split(",");
            for (String id : ids) {
                try {
                    courseIds.add(Integer.parseInt(id.trim()));
                } catch (NumberFormatException e) {
                    // Xử lý trường hợp không thể chuyển đổi ID thành số nguyên
                    e.printStackTrace();
                    return "";
                }
            }
        }

        // Tạo session và CriteriaBuilder
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<String> cq = cb.createQuery(String.class);
        Root<Course> root = cq.from(Course.class);

        // Tạo predicate cho điều kiện tìm kiếm
        Predicate predicate = root.get("id").in(courseIds);
        cq.select(root.get("title")).where(predicate);

        // Thực hiện truy vấn
        List<String> titles = s.createQuery(cq).getResultList();

        // Nối các tiêu đề thành chuỗi ngăn cách bởi ", "
        return String.join(", ", titles);
    }

    @Override
    public List<Course> getCourseDTOByInstructorId(int instructorId) {
        // Mở session hiện tại
        Session session = this.factory.getObject().getCurrentSession();

        // Tạo CriteriaBuilder để xây dựng câu truy vấn
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Tạo CriteriaQuery cho đối tượng Course
        CriteriaQuery<Course> query = builder.createQuery(Course.class);

        // Xác định "Root" (bảng Course)
        Root<Course> root = query.from(Course.class);
        query.select(root);

        // Tạo điều kiện WHERE để lọc theo instructorId
        Predicate instructorPredicate = builder.equal(root.get("instructorId"), instructorId);

        // Áp dụng điều kiện vào câu truy vấn
        query.where(instructorPredicate);
        
        // Thực thi truy vấn bằng cách sử dụng TypedQuery
        TypedQuery<Course> typedQuery = session.createQuery(query);
        return typedQuery.getResultList();
    }


 
}
