import React, { useRef, useState } from "react";
import { Alert, Form } from "react-bootstrap";
import { useNavigate } from "react-router";
import APIs, { endpoints } from "../../configs/APIs";
import { MDBContainer, MDBCol, MDBRow, MDBBtn, MDBInput } from 'mdb-react-ui-kit';

const Register = () => {
    const [user, setUser] = useState({});
    const [err, setErr] = useState();
    const [success, setSuccess] = useState();
    const nav = useNavigate();
    const avatar = useRef();

    const register = async (e) => {
        e.preventDefault();
        setErr("");  // Reset error message
        setSuccess("");  // Reset success message

        // Kiểm tra mật khẩu
        if (user.password === undefined || user.password !== user.confirm) {
            setErr("Mật khẩu KHÔNG khớp!");
            return;
        }

        // Kiểm tra xem người dùng có chọn tệp ảnh không
        if (!avatar.current.files.length) {
            setErr("Vui lòng chọn ảnh đại diện!");
            return;
        }

        let form = new FormData();
        for (let f in user) {
            if (f !== 'confirm') {
                form.append(f, user[f]);
            }
        }

        // Thêm tệp ảnh đại diện vào form
        form.append('avatar', avatar.current.files[0]);

        try {
            const res = await APIs.post(endpoints['register'], form, {
                headers: {
                    // Không cần chỉ định Content-Type cho FormData,
                    // Axios sẽ tự động thêm đúng loại
                }
            });

            console.info(res.data);  // Log the response data

            if (res.status === 201) {  // Kiểm tra nếu đăng ký thành công
                setSuccess("Đăng ký thành công! Bạn có thể đăng nhập ngay.");
                setTimeout(() => {
                    nav("/login");  // Redirect to login page after success
                }, 2000);
            } else {
                setErr("Đăng ký không thành công. Vui lòng thử lại!");
            }
        } catch (error) {
            console.error("Registration error:", error);
            setErr(error.response?.data?.message || "Có lỗi xảy ra!");  // Hiển thị thông báo lỗi
        }
    };

    const change = (e, field) => {
        setUser({ ...user, [field]: e.target.value });
    };

    return (
        <>
            <h1 className="text-center text-info mt-1">ĐĂNG KÝ NGƯỜI DÙNG</h1>
            {err && <Alert variant="danger">{err}</Alert>}
            {success && <Alert variant="success">{success}</Alert>}  {/* Hiển thị thông báo thành công */}

            <Form method="post" onSubmit={register}>
                <MDBContainer fluid className="p-3 my-5">
                    <MDBRow>
                        <MDBCol col='10' md='6'>
                            <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.svg" className="img-fluid" alt="Phone image" />
                        </MDBCol>
                        <MDBCol col='4' md='6'>
                            <MDBInput wrapperClass='mb-4' placeholder='Tên...' id='formControlLg' type='text' size="lg" value={user["firstName"]} onChange={e => change(e, "firstName")} />
                            <MDBInput wrapperClass='mb-4' placeholder='Họ và tên lót...' id='formControlLg' type='text' size="lg" value={user["lastName"]} onChange={e => change(e, "lastName")} />
                            <MDBInput wrapperClass='mb-4' placeholder='Username...' id='formControlLg' type='text' size="lg" value={user["username"]} onChange={e => change(e, "username")} />
                            <MDBInput wrapperClass='mb-4' placeholder='Password...' id='formControlLg' type='password' size="lg" value={user["password"]} onChange={e => change(e, "password")} />
                            <MDBInput wrapperClass='mb-4' placeholder='Confirm Password...' id='formControlLg' type='password' size="lg" value={user["confirm"]} onChange={e => change(e, "confirm")} />
                            <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea7">
                                <Form.Label>Ảnh đại diện</Form.Label>
                                <Form.Control accept=".png,.jpg" type="file" ref={avatar} />
                            </Form.Group>
                            <MDBBtn className="mb-4 w-100" type="submit" variant="success" size="lg">Đăng ký</MDBBtn>
                        </MDBCol>
                    </MDBRow>
                </MDBContainer>
            </Form>
        </>
    );
};

export default Register;
