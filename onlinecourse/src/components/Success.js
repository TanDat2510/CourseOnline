import React from 'react';
import { Container, Row, Col, Card, Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom'; // Import useNavigate from react-router-dom
import '../css/Success.css'; // Import custom CSS file for additional styles

const Success = () => {
    console.log('Success component rerendered'); // Log when component rerenders

    const navigate = useNavigate(); // Initialize the navigate hook

    const handleNavigateHome = () => {
        navigate('/'); // Use navigate instead of href to go to the home page
    };

    return (
        <Container fluid className="success-container">
            <Row className="justify-content-center align-items-center h-100">
                <Col md={20} lg={15}>
                    <Card className="text-center success-card">
                        <Card.Body>
                            <h1 className="text-success">Thanh toán thành công!</h1>
                            <p className="lead">Cảm ơn bạn đã đăng ký khóa học với chúng tôi. Đơn hàng của bạn đã được xử lý thành công. Chúc bạn có trải nghiệm tuyệt vời.</p>
                            <Button variant="primary" onClick={handleNavigateHome} className="mt-3">Trở về trang chủ</Button>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default Success;
