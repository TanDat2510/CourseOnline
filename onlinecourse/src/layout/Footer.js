import React from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import '../css/Footer.css'; // Import file CSS cho Footer nếu cần

const Footer = () => {
  return (
    <footer className="bg-dark text-white py-4">
      <Container>
        <Row>
          <Col md={4}>
            <h5>Về Chúng Tôi</h5>
            <p>Chúng tôi cung cấp các khóa học chất lượng cao với đội ngũ giảng viên giàu kinh nghiệm.</p>
          </Col>
          <Col md={4}>
            <h5>Liên Kết Nhanh</h5>
            <ul className="list-unstyled">
              <li><Link to="/" className="text-white">Trang Chủ</Link></li>
              <li><Link to="/courses" className="text-white">Khóa Học</Link></li>
              <li><Link to="/about" className="text-white">Giới Thiệu</Link></li>
              <li><Link to="/contact" className="text-white">Liên Hệ</Link></li>
            </ul>
          </Col>
          <Col md={4}>
            <h5>Liên Hệ</h5>
            <p>
              <i className="fa fa-map-marker"></i> Địa chỉ: 123 Đường ABC, Thành phố XYZ<br/>
              <i className="fa fa-phone"></i> Điện thoại: (0123) 456-789<br/>
              <i className="fa fa-envelope"></i> Email: info@example.com
            </p>
          </Col>
        </Row>
        <Row className="mt-3">
          <Col className="text-center">
            <p>&copy; 2024 OnlineCourse. All rights reserved.</p>
          </Col>
        </Row>
      </Container>
    </footer>
  );
}

export default Footer;
