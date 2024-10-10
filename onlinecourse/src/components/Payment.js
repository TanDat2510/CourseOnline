import React, { useContext, useState, useEffect } from 'react';
import { Button, Card, Col, Container, Row, Table, Form, Alert } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { MyCartContext, MyUserContext } from '../App';
import { authAPIs, endpoints } from '../config/APIs';
import cookie from "react-cookies";

const Payment = () => {
    const navigate = useNavigate();
    const [cart] = useState(cookie.load("cart") || {});
    const [requestId, setRequestId] = useState('');

    // Trạng thái cho thông tin người dùng
    const [fullName, setFullName] = useState('');
    const [email, setEmail] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [errors, setErrors] = useState({});
    const [loading, setLoading] = useState(false);
    const [waitingMessage, setWaitingMessage] = useState(false);
    const [socket, setSocket] = useState(null);
    const [intervalId, setIntervalId] = useState(null);
    const user = useContext(MyUserContext);
    const [cartCounter, cartDispatch] = useContext(MyCartContext);

    useEffect(() => {
        // Cleanup WebSocket connection and interval when the component unmounts
        return () => {
            if (socket) {
                socket.close();
            }
            if (intervalId) {
                clearInterval(intervalId);
            }
        };
    }, [socket, intervalId]);

    const handleConfirmPayment = async () => {
        try {
            const response = await authAPIs().post(endpoints['payment'], {
                paymentChannel: "MOMO",
                payerName: fullName,
                payerEmail: email,
                phone: phoneNumber,
                userId: user.id,
                courses: Object.values(cart).map(item => ({
                    id: item.id,
                    price: item.price
                }))
            });
    
            if (response.data.payment_url) {
                // Hiển thị thông báo chờ và thiết lập kết nối WebSocket
                window.open(response.data.payment_url, '_blank', 'noopener,noreferrer');
                setWaitingMessage(true);
                setRequestId(response.data.requestId);
                startWebSocket(response.data.requestId);
            } else {
                alert('Thanh toán không thành công. Vui lòng thử lại.');
            }
        } catch (error) {
            console.error('Lỗi khi thực hiện thanh toán:', error);
            alert('Đã xảy ra lỗi trong quá trình thanh toán. Vui lòng thử lại.');
        }
    };

    const startWebSocket = (requestId) => {
        const socket = new WebSocket('ws://localhost:8082/OnlineCourseWeb/ws');
        setSocket(socket);

        const interval = setInterval(() => {
            if (socket.readyState === WebSocket.OPEN) {
                console.log('Gửi: ' + requestId);
                socket.send(requestId);
            }
        }, 3000); // Gửi yêu cầu mỗi 3 giây

        setIntervalId(interval);

        const timeout = setTimeout(() => {
            if (socket.readyState === WebSocket.OPEN) {
                console.log('Giao dịch quá hạn.');
                setWaitingMessage(false);
                socket.close();
            }
        }, 30 * 60 * 1000); // 30 phút

        socket.onopen = () => {
            console.log('WebSocket đã kết nối.');
            if (requestId) {
                console.log('Gửi: ' + requestId);
                socket.send(requestId);
            } else {
                console.error('Request ID không hợp lệ.');
            }
        };

        socket.onmessage = (event) => {
            console.log('Tin nhắn từ máy chủ:', event.data);
            if (event.data === 'Transaction Sucess') {
                clearInterval(interval);
                clearTimeout(timeout);
                cookie.remove('cart');
                cartDispatch({ type: "reset" });
                navigate('/success');
                socket.close();
            }
        };

        socket.onerror = (error) => {
            console.error('Lỗi WebSocket:', error);
            clearInterval(interval);
            clearTimeout(timeout);
            setWaitingMessage(false);
        };

        socket.onclose = (event) => {
            clearInterval(interval);
            clearTimeout(timeout);
            console.log('WebSocket đã đóng.');
            if (event.wasClean) {
                console.log(`Kết thúc sạch sẽ: code=${event.code}, reason=${event.reason}`);
            } else {
                console.error('Kết thúc bất ngờ.');
            }
        };
    };

    const totalAmount = Object.values(cart || {}).reduce((acc, item) => acc + item.price, 0);

    const validateForm = () => {
        const newErrors = {};
        if (!fullName) newErrors.fullName = 'Vui lòng nhập họ tên';
        if (!email) newErrors.email = 'Vui lòng nhập email';
        if (!phoneNumber) newErrors.phoneNumber = 'Vui lòng nhập số điện thoại';
        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleSubmit = () => {
        if (validateForm()) {
            setLoading(true);
            handleConfirmPayment();
        }
    };

    if (!cart || Object.keys(cart).length === 0) {
        return <div>Không có sản phẩm trong giỏ hàng.</div>;
    }

    return (
        <Container className="mt-4">
            <h1 className="text-center text-info">Xác Nhận Thanh Toán</h1>
            <Row>
                <Col md={8}>
                    <Card>
                        <Card.Header>Chi Tiết Đơn Hàng</Card.Header>
                        <Card.Body>
                            <Table striped bordered hover>
                                <thead>
                                    <tr>
                                        <th>Id</th>
                                        <th>Tên khóa học</th>
                                        <th>Giá</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {Object.values(cart).map((item) => (
                                        <tr key={item.id}>
                                            <td>{item.id}</td>
                                            <td>{item.title}</td>
                                            <td>{item.price.toLocaleString("en")} VNĐ</td>
                                        </tr>
                                    ))}
                                </tbody>
                            </Table>
                            <div className="text-end">
                                <h4>Tổng cộng: {totalAmount.toLocaleString("en")} VNĐ</h4>
                            </div>
                        </Card.Body>
                    </Card>
                </Col>
                <Col md={4}>
                    <Card>
                        <Card.Header>Thông Tin Thanh Toán</Card.Header>
                        <Card.Body>
                            {waitingMessage ? (
                                <Alert variant="info">Đang chờ xác nhận giao dịch thanh toán...</Alert>
                            ) : (
                                <Form>
                                    <Form.Group controlId="formFullName">
                                        <Form.Label>Họ Tên</Form.Label>
                                        <Form.Control 
                                            type="text" 
                                            placeholder="Nhập họ tên" 
                                            value={fullName} 
                                            onChange={(e) => setFullName(e.target.value)} 
                                            isInvalid={!!errors.fullName}
                                        />
                                        <Form.Control.Feedback type="invalid">
                                            {errors.fullName}
                                        </Form.Control.Feedback>
                                    </Form.Group>

                                    <Form.Group controlId="formEmail">
                                        <Form.Label>Email</Form.Label>
                                        <Form.Control 
                                            type="email" 
                                            placeholder="Nhập email" 
                                            value={email} 
                                            onChange={(e) => setEmail(e.target.value)} 
                                            isInvalid={!!errors.email}
                                        />
                                        <Form.Control.Feedback type="invalid">
                                            {errors.email}
                                        </Form.Control.Feedback>
                                    </Form.Group>

                                    <Form.Group controlId="formPhoneNumber">
                                        <Form.Label>Số Điện Thoại</Form.Label>
                                        <Form.Control 
                                            type="tel" 
                                            placeholder="Nhập số điện thoại" 
                                            value={phoneNumber} 
                                            onChange={(e) => setPhoneNumber(e.target.value)} 
                                            isInvalid={!!errors.phoneNumber}
                                        />
                                        <Form.Control.Feedback type="invalid">
                                            {errors.phoneNumber}
                                        </Form.Control.Feedback>
                                    </Form.Group>

                                    <Button 
                                        variant="success" 
                                        onClick={handleSubmit} 
                                        className="w-100 mt-5"
                                        disabled={loading || !fullName || !email || !phoneNumber} // Disable button if any field is empty or loading
                                    >
                                        Xác Nhận Thanh Toán
                                    </Button>
                                </Form>
                            )}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default Payment;
