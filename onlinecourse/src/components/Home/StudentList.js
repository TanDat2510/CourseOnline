import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { authAPIs, endpoints } from "../../configs/APIs";
import "../Style/StudentList.css"; // Import file CSS

const StudentList = () => {
  const [students, setStudents] = useState([]);
  const [loading, setLoading] = useState(true);
  const { id } = useParams(); // Lấy courseId từ URL

  useEffect(() => {
    // Fetch danh sách học sinh đăng ký vào khóa học với courseId
    authAPIs()
      .get(endpoints['enrollments'](id))
      .then((response) => {
        setStudents(response.data);
        setLoading(false);
      })
      .catch((error) => {
        console.error("Có lỗi xảy ra khi tải dữ liệu", error);
        setLoading(false);
      });
  }, [id]);

  if (loading) {
    return <p>Loading...</p>;
  }

  return (
    <div className="student-list">
      {students.map((student) => (
        <div key={student.id} className="student-card">
          <div className="student-avatar-container">
            <img src={student.userId.avatar} alt="Avatar" className="student-avatar" />
          </div>
          <div className="student-info-container">
            <div className="student-details">
              <h3 className="name">{student.fullName}</h3>
              <p className="email">Email: {student.userId.email}</p>
              <p className="phone">Số điện thoại: {student.userId.phone}</p>
              <p className="date">Ngày đăng ký: {student.userId.createdDate}</p>
              <p className="progres">Tiến trình học: Đã học {student.progress} nội dung</p>
            </div>
          </div>
        </div>
      ))}
    </div>
  );
};

export default StudentList;