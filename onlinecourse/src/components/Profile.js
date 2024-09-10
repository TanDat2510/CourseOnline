import React, { useContext } from 'react';
import { Container, Row, Col, Card } from 'react-bootstrap';
import '../css/Profile.css';
import { MyUserContext } from '../App';

const DEFAULT_IMAGE = "https://res.cloudinary.com/dh1irfap0/image/upload/v1725264150/NANG-CAP-BAN-THAN_pdmrgk.png"; // Đường dẫn đến hình ảnh mặc định của bạn

const Profile = () => {
    const user = useContext(MyUserContext);
    // Sử dụng hình ảnh mặc định nếu không có hình ảnh người dùng
    const avatarSrc = user.avatar || DEFAULT_IMAGE;

    return (
        <Container>
            <h1>Thông Tin Cá Nhân</h1>
            <Row className="profile-container">
                <Col md={4} className="profile-card">
                    <Card className="mb-3">
                        <Card.Img
                            variant="top"
                            src={avatarSrc}
                            alt="Avatar"
                            style={{ height: 'auto' }} // Đảm bảo hình ảnh có kích thước đồng nhất
                        />
                    </Card>
                </Col>
                <Col md={8} className="profile-card">


                    <Card className="mb-3">
                        <Card.Body>
                            <Card.Title>Họ và tên : {user.firstName} {user.lastName}</Card.Title>
                            <Card.Text>
                                <strong>Email:</strong> {user.email}
                            </Card.Text>
                            <Card.Text>
                                <strong>Số Điện Thoại:</strong> {user.phone}
                            </Card.Text>
                            <Card.Text>
                                <strong>Trạng Thái:</strong> {user.active ? 'Hoạt động' : 'Không hoạt động'}
                            </Card.Text>
                            <Card.Text>
                                <strong>Ngày Tạo:</strong> {new Date(user.createdDate).toLocaleString()}
                            </Card.Text>
                            <Card.Text>
                                <strong>Ngày Cập Nhật:</strong> {new Date(user.updatedDate).toLocaleString()}
                            </Card.Text>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default Profile;
