import React, { useEffect, useState, useContext } from 'react';
import { useParams } from 'react-router-dom';
import { MyUserContext } from '../App'; // Giả sử context được định nghĩa ở đây
import { authAPIs, endpoints } from '../config/APIs';
import { Button, Spinner } from 'react-bootstrap';
import '../css/CourseContent.css';
import '../css/Spinner.css';

const CourseContent = () => {
  const { courseId, contentId } = useParams();
  const [content, setContent] = useState({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isCompleted, setIsCompleted] = useState(false);
  const user = useContext(MyUserContext);

  useEffect(() => {
    const fetchContent = async () => {
      try {
        setLoading(true);
        const response = await authAPIs().get(endpoints['content-details'](courseId, contentId));
        setContent(response.data);
        setLoading(false);
      } catch (err) {
        setError('Không thể tải nội dung.');
        setLoading(false);
      }
    };

    fetchContent();
  }, [courseId, contentId, isCompleted]);

  // Xử lý khi đánh dấu hoàn thành
  const handleMarkAsCompleted = async () => {
    try {
      setLoading(true);
      const response = await authAPIs().post(endpoints['content-details'](courseId, contentId), {
        point: '10', // Mặc định 10 điểm cho nội dung video hoặc lecture
      });

      if (response.status === 201) {
        setLoading(false);
        alert('Nội dung đã được đánh dấu là hoàn thành.');
        setIsCompleted(true);
        
      }
    } catch (error) {
      console.error('Lỗi khi đánh dấu hoàn thành:', error);
      setLoading(false);
      alert('Đánh dấu hoàn thành thất bại. Vui lòng thử lại.');
    }
  };

  // Render nội dung dựa trên loại nội dung
  const renderContent = () => {
    if (!content) return <p>Không có nội dung.</p>;

    if (content.entityType === 'LECTURE') {
      return <div dangerouslySetInnerHTML={{ __html: content.content.content }} style={{ width: '800px' }} />;
    } else if (content.entityType === 'VIDEO') {
      return (
        <video width="800" controls >
          <source src={content.content.url} type="video/mp4" />
          Trình duyệt của bạn không hỗ trợ thẻ video.
        </video>
      );
    } else {
      return <p>Loại nội dung không được hỗ trợ.</p>;
    }
  };

  // Kiểm tra điều kiện hoàn thành

  if (loading) {
    return (
      <div className="spinner-container">
        <Spinner animation="grow" variant="primary" className="spinner-lg" />
      </div>
    );
  }

  if (error) {
    return <p>{error}</p>;
  }

  return (
    <div className="course-content">
      {user && user.userRole === 'ROLE_STUDENT' ? (
        <>
          {content.point > 0 ? (
            <h4>Đã hoàn thành nội dung. Số điểm: {content.point}</h4>
          ) : (
            <h4>Chưa hoàn thành nội dung.</h4>
          )}

          <h1>{content.title}</h1>
          {renderContent()}

          {/* Hiển thị nút đánh dấu đã hoàn thành */}
          {content.point < 0 && (
            <Button onClick={handleMarkAsCompleted} variant="success" className='mt-4'>
              Đánh dấu là đã hoàn thành
            </Button>
          )}
        </>
      ) : (
        <p><strong>Bạn không có quyền truy cập nội dung này.</strong></p>
      )}
    </div>
  );
};

export default CourseContent;
