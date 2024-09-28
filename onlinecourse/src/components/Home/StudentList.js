import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { authAPIs, endpoints } from "../../configs/APIs";
import "../Style/StudentList.css"; // Import file CSS

const StudentList = () => {
  const [students, setStudents] = useState([]);
  const [loading, setLoading] = useState(true);
  const { id } = useParams(); // Lấy courseId từ URL
  const [activeTab, setActiveTab] = useState('students'); // State to manage active tab

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

  const handleTabChange = (tab) => {
    setActiveTab(tab);
  };

  if (loading) {
    return <p>Loading...</p>;
  }

  return (
    <div className="student-list">
      <div className="tabs">
        <button className={`tab ${activeTab === 'students' ? 'active' : ''}`} onClick={() => handleTabChange('students')}>Học sinh</button>
        <button className={`tab ${activeTab === 'courses' ? 'active' : ''}`} onClick={() => handleTabChange('courses')}>Khóa học & Bài tập</button>
      </div>

      {activeTab === 'students' ? (
        <div className="student-info">
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
                  <p className="progress">Tiến trình học: Đã học {student.progress} nội dung</p>
                </div>
              </div>
            </div>
          ))}
        </div>
      ) : (
        <div className="course-content">
          <h3>Khóa học & Nội dung</h3>
          {/* Add your course content and assignments here */}
          <p>Thông tin về khóa học sẽ được hiển thị ở đây.</p>
          <div>
            <button className="add-assignment-button"> Thêm bài tập</button>
          </div>
        </div>
      )}
      
    </div>
  );
};

export default StudentList;