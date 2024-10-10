import React, { useState, useRef } from "react";
import { Alert, Button, Form, Container, Row, Col, Spinner } from "react-bootstrap";
import { useNavigate, Link } from "react-router-dom";
import '../Style/Register.css'; // Đảm bảo tạo và thêm file CSS này nếu cần
import '../Style/Spinner.css';
import APIs, { endpoints } from "../../configs/APIs";


const Register = () => {
    const [user, setUser] = useState({});
    const [err, setErr] = useState();
    const nav = useNavigate();
    const avatar = useRef();
    const [loading, setLoading] = useState(false);


    const register = async (e) => {
        e.preventDefault();
        setLoading(true); // Bắt đầu trạng thái tải

        // Kiểm tra các trường
        if (!user.firstName || !user.lastName || !user.username || !user.password || !user.confirmPassword || !user.email || !user.phone) {
            setErr("Vui lòng điền đầy đủ thông tin.");
            return;
        }

        // Kiểm tra mật khẩu xác nhận
        if (user.password !== user.confirmPassword) {
            setErr("Mật khẩu và xác nhận mật khẩu không khớp.");
            return;
        }

        let form = new FormData();
        for (let f in user) {
            form.append(f, user[f]);
        }

        form.append('avatar', avatar.current.files[0]);

        try {
            let res = await APIs.post(endpoints['register'], form, {
                headers: {
                    'Content-Type': "multipart/form-data"
                }
            });
            console.info(res.data);
            // Kiểm tra mã trạng thái phản hồi
            if (res.status === 201) {

                nav("/login");
            } else {
                setErr("Đăng ký không thành công. Vui lòng thử lại.");
            }
        } catch (error) {
            console.error(error);
            setErr("Đăng ký không thành công. Vui lòng thử lại.");
        } finally {
            setLoading(false); // Kết thúc trạng thái tải
        }
    }


    const change = (e, field) => {
        setUser({ ...user, [field]: e.target.value });
    }

    if (loading) {
        return (
            <div className="spinner-container">
                <Spinner animation="grow" variant="primary" className="spinner-lg" />
            </div>
        );
    }

    return (
        <Container className="register-wrapper">
            <Row className="justify-content-center">
                <h1 className="text-center mb-4">ĐĂNG KÝ</h1>
                <Col md={10} lg={8} className="register-form">

                    {err && <Alert variant="danger">{err}</Alert>}

                    <Form onSubmit={register}>
                        <Form.Group className="mb-3" controlId="formBasicFirstName">
                            <Form.Label>Tên</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="Nhập tên..."
                                value={user.firstName}
                                onChange={e => change(e, "firstName")}
                            />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicLastName">
                            <Form.Label>Họ và tên lót</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="Nhập họ và tên lót..."
                                value={user.lastName}
                                onChange={e => change(e, "lastName")}
                            />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicUsername">
                            <Form.Label>Username</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="Nhập username..."
                                value={user.username}
                                onChange={e => change(e, "username")}
                            />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicEmail">
                            <Form.Label>Email</Form.Label>
                            <Form.Control
                                type="email"
                                placeholder="Nhập email..."
                                value={user.email}
                                onChange={e => change(e, "email")}
                            />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicPhone">
                            <Form.Label>Số điện thoại</Form.Label>
                            <Form.Control
                                type="tel"
                                placeholder="Nhập số điện thoại..."
                                value={user.phone}
                                onChange={e => change(e, "phone")}
                            />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicPassword">
                            <Form.Label>Mật khẩu</Form.Label>
                            <Form.Control
                                type="password"
                                placeholder="Nhập mật khẩu..."
                                value={user.password}
                                onChange={e => change(e, "password")}
                            />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicConfirmPassword">
                            <Form.Label>Xác nhận mật khẩu</Form.Label>
                            <Form.Control
                                type="password"
                                placeholder="Nhập lại mật khẩu..."
                                value={user.confirmPassword}
                                onChange={e => change(e, "confirmPassword")}
                            />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicAvatar">
                            <Form.Label>Ảnh đại diện</Form.Label>
                            <Form.Control
                                type="file"
                                accept=".png,.jpg,.jpeg"
                                ref={avatar}
                            />
                        </Form.Group>

                        <Button type="submit" className="btn-block">Đăng ký</Button>

                        <Form.Group className="text-center mt-3">
                            <span className="text-black-important">Bạn đã có tài khoản? </span>
                            <Link to="/login" className="text-info">Đăng nhập</Link>
                        </Form.Group>

                    </Form>
                </Col>
            </Row>
        </Container>
    );
}

export default Register;