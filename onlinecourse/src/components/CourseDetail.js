import React, { useEffect, useState, useContext } from 'react';
import { useParams } from 'react-router-dom';
import { Card, Container, Row, Col, Button, Spinner } from 'react-bootstrap';
import { FaBook, FaInfoCircle, FaVideo } from 'react-icons/fa';
import { authAPIs, endpoints } from '../config/APIs';
import '../css/CourseDetail.css';
import '../css/Enrollment.css';
import { MyUserContext } from '../App';
import Certificate from './Certificate';
import '../css/Spinner.css';
import { useNavigate } from 'react-router-dom';

const CourseDetail = () => {
  const { id } = useParams();
  const [course, setCourse] = useState(null);
  const [contents, setContents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const user = useContext(MyUserContext);
  const [progress, setProgress] = useState(0);
  const [countContent, setCountContent] = useState(0);
  const [register, setRegister] = useState(false);
  const [gpa, setGpa] = useState(''); //Note: không cần này nữa
  
  useEffect(() => {
    const fetchCourse = async () => {
      try {
        const response = await authAPIs().get(endpoints['course-details'](id));
        setCourse(response.data);
        console.log(course);
        setRegister(response.data.register);
        setLoading(false); // Set loading to false when data is fetched
      } catch (err) {
        setError('Không thể tải thông tin khóa học.');
        setLoading(false); // Set loading to false on error
      }
    };

    fetchCourse();
  }, [id]);

  useEffect(() => {
    const fetchContents = async () => {
      try {
        const response = await authAPIs().get(endpoints['content'](id));
        setContents(response.data);
        setCountContent(response.data.length);
        // note: không cần tính nữa 
        if (response.data.length > 0) {
          const totalPoints = response.data.reduce((acc, content) => acc + content.point, 0);
          const averagePoint = totalPoints / response.data.length;
          setGpa(calculateGPA(averagePoint));
        }
      } catch (error) {
        console.error("Error fetching course content:", error);
      }
    };

    fetchContents();
  }, [id]);

  useEffect(() => {
    const checkEnrollment = async () => {
      try {
        const response = await authAPIs().get(endpoints['enrollment'](id));
        setProgress(response.data.progress); // Note: phải lấy nguyên cái progress
      } catch (err) {
        setProgress(0); //Note: Set Progress bằng {}
      }
    };

    if (user && user.userRole === 'ROLE_STUDENT') {
      checkEnrollment();
    }
  }, [id, user]);


  // Note: Bỏ hàm tính này luôn
  const calculateGPA = (averagePoint) => {
    if (averagePoint >= 9) return "A+";
    if (averagePoint >= 8) return "A";
    if (averagePoint >= 7) return "B+";
    if (averagePoint >= 6) return "B";
    return "None";
  };

  const getIcon = (entityType) => {
    switch (entityType) {
      case 'LECTURE':
        return <FaBook />;
      case 'INFORMATION':
        return <FaInfoCircle />;
      case 'VIDEO':
        return <FaVideo />;
      default:
        return <FaBook />;
    }
  };



  // Bên trong component CourseDetail
  const navigate = useNavigate();

  const handleContentAction = (content) => {
    // Điều hướng tới trang CourseContent với state content và register
    navigate(`/courses/${course.id}/content/${content.id}`);
  };


  if (loading) {
    return (
      <div className="spinner-container">
        <Spinner animation="grow" variant="primary" className="spinner-lg" />
      </div>
    );
  }

  if (error) {
    return (
      <Container className="text-center">
        <p>{error}</p>
      </Container>
    );
  }

  if (!course) {
    return (
      <Container className="text-center">
        <p>Khóa học không tồn tại</p>
      </Container>
    );
  }

  return (
    <Container>
      <Card className="mb-4">
        <Card.Body className="bg-info text-white text-center">
          <h3 className="my-2">TÌM HIỂU CHI TIẾT VỀ KHÓA HỌC</h3>
        </Card.Body>
      </Card>

      <Row className="mb-4">
        <Col md={8}>
          <Card>
            <Card.Body>
              <Card.Title className="course-title">Thông tin khóa học</Card.Title>
              <Card.Text><strong>Mô tả: </strong> {course.description}</Card.Text>
              <Card.Text><strong>Thời gian học: </strong> {course.timeExperted}</Card.Text>
              <Card.Text><strong>Giá: </strong> {course.price} VNĐ</Card.Text>
              <Card.Text><strong>Trạng thái: </strong> {course.status}</Card.Text>
              <Card.Text><strong>Loại khóa học: </strong> {course.courseType}</Card.Text>
              <Card.Text><strong>Danh mục: </strong> {course.categoryId.name}</Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col md={4}>
          <Card.Img variant="top" src={course.img || 'https://via.placeholder.com/150'} alt={course.title} style={{ width: '100%', height: '300px', objectFit: 'contain' }} />
        </Col>
      </Row>

      <Row className="mb-4">
        <Col md={4}>
          <Card>
            <Card.Img
              variant="top"
              src={course.instructorId.userId.avatar || 'https://via.placeholder.com/150'}
              alt={`${course.instructorId.userId.firstName} ${course.instructorId.userId.lastName}`}
              style={{ width: '100%', height: '300px', objectFit: 'contain' }} />
          </Card>
        </Col>
        <Col md={8}>
          <Card>
            <Card.Body>
              <Card.Title className="instructor-title">Thông tin giảng viên</Card.Title>
              <Card.Text><strong>Giảng viên: </strong> {course.instructorId.userId.firstName} {course.instructorId.userId.lastName}</Card.Text>
              <Card.Text><strong>Email: </strong> {course.instructorId.userId.email}</Card.Text>
              <Card.Text><strong>Phone: </strong> {course.instructorId.userId.phone}</Card.Text>
              <Card.Text><strong>Chuyên môn: </strong> {course.instructorId.expertise}</Card.Text>
              <Card.Text><strong>Mô tả: </strong> {course.instructorId.description}</Card.Text>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      {user && user.userRole === 'ROLE_STUDENT' && (
        <Container className={`text-center mb-3 ${register ? 'registered' : ''}`}>
          {register ? (
            <div className='register'>
              <p>Bạn đã đăng ký khóa học này.</p>
              <p>Đã hoàn thành {progress} nội dung</p>
            </div>
          ) : (
            <p>Bạn chưa đăng ký khóa học này.</p>
          )}
        </Container>
      )}

      <Card className="mb-4">
        <Card.Body className="bg-info text-white text-center">
          <h3 className="my-2">DANH SÁCH NỘI DUNG KHÓA HỌC</h3>
          <h4>{`Tổng số nội dung: ${countContent}`}</h4>
        </Card.Body>
      </Card>

      <Row className="mb-4 justify-content-center">
        {contents.map((content, index) => (
          <Col md={10} key={index} className="mb-3">
            <Card>
              <Card.Body>
                <Row>
                  <Col xs={2} className="text-center">
                    <div style={{ fontSize: '2rem' }}>
                      {getIcon(content.entityType)}
                    </div>
                  </Col>
                  <Col xs={8}>
                    <Card.Title>{content.title}</Card.Title>
                  </Col>
                  <Col xs={2} className="text-right">
                    {user && user.userRole === 'ROLE_STUDENT' && register ?
                      (content.point > 0 ? (
                        <Button variant="secondary" onClick={() => handleContentAction(content)}>Xem lại</Button>
                      ) : (
                        <Button variant="primary" onClick={() => handleContentAction(content)}>Thực hiện</Button>
                      )) :
                      <></>
                    }
                  </Col>
                </Row>
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>

      {user && user.userRole === 'ROLE_STUDENT' && (
        <Certificate userName={user.lastName + user.firstName} courseTitle={course.title} progress={progress} countContent={countContent} gpa={gpa} /> // Note: Truyền nguyên đối tượng progress, truyền cái gpa
      )}
    </Container>
  );
};

export default CourseDetail;
