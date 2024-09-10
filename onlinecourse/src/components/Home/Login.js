import React, { useContext, useState, useEffect } from "react";
import cookie from "react-cookies";
import { Navigate, useNavigate } from "react-router";
import { MyDispatchContext, MyUserContext } from "../../App";
import APIs, { authAPIs, changePassword, endpoints, requestOTP, verifyOtp } from "../../configs/APIs";
import { Alert, Button, Form, Modal } from "react-bootstrap";
import { MDBContainer, MDBCol, MDBRow, MDBBtn, MDBIcon, MDBInput, MDBCheckbox } from 'mdb-react-ui-kit';
import { LoginSocialFacebook } from 'reactjs-social-login';
import { FacebookLoginButton } from "react-social-login-buttons";
import OtpVerification from "./OtpVerification";

const Login = () => {
    const [profile, setProfile] = useState(null);
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [showForgotPasswordModal, setShowForgotPasswordModal] = useState(false);
    const [forgotUsername, setForgotUsername] = useState('');
    const [forgotPasswordMessage, setForgotPasswordMessage] = useState('');
    const [otpSent, setOtpSent] = useState(false);
    const [otp, setOtp] = useState('');
    const [otpVerified, setOtpVerified] = useState(false);
    const [newPassword, setNewPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [passwordChanged, setPasswordChanged] = useState(false);
    const navigate = useNavigate();
    const user = useContext(MyUserContext);
    const dispatch = useContext(MyDispatchContext);

    // useEffect(() => {
    //     if (user !== null) {
    //         navigate("/");  // Nếu người dùng đã đăng nhập, điều hướng về trang chính
    //     }
    // }, [user, navigate]);

    const login = async (e) => {
        e.preventDefault();
        setErrorMessage('');
        try {
            let res = await APIs.post(endpoints['login'], {
                "username": username,
                "password": password
            });

            cookie.save("access-token", res.data);
            let user = await authAPIs().get(endpoints['current-user']);
            cookie.save("user", user.data);
            dispatch({
                "type": "login",
                "payload": user.data
            });

            
            if (user.data && user.data.userRole === "ROLE_INSTRUCTOR") {
                navigate("/instructor-dashboard");
            } else {
                navigate("/");
            }
        } catch (error) {
            if (error.response && error.response.status === 401) {
                setErrorMessage('Sai tài khoản hoặc mật khẩu.');
            } else if (error.response && error.response.status === 500) {
                setErrorMessage('Lỗi máy chủ, vui lòng thử lại sau.');
            } else {
                setErrorMessage('Đăng nhập thất bại. Vui lòng kiểm tra lại thông tin.');
            }
          
        }
    
    };

    const handleForgotPassword = async (e) => {
        e.preventDefault();
        setForgotPasswordMessage('');
        try {
            const response = await requestOTP(forgotUsername);
            setForgotPasswordMessage(response);
            setOtpSent(true);
        } catch (error) {
            setForgotPasswordMessage(error.response ? error.response.data : 'Lỗi khi gửi mã OTP!');
        }
    };

    const handleOtpSubmit = async (otpCode) => {
        setForgotPasswordMessage('');
        try {
            const response = await verifyOtp(otpCode, forgotUsername);
            setForgotPasswordMessage("OTP đã xác thực thành công.");
            setOtpVerified(true);
        } catch (error) {
            setForgotPasswordMessage(error.response ? error.response.data : 'Lỗi xác thực OTP!');
        }
    };

    const handleChangePassword = async (e) => {
        e.preventDefault();
        setForgotPasswordMessage('');
        setPasswordChanged(false);
        if (newPassword !== confirmPassword) {
            setForgotPasswordMessage("Mật khẩu mới và xác nhận mật khẩu không khớp!");
            return;
        }
        try {
            const response = await changePassword(forgotUsername, newPassword, confirmPassword);
            setForgotPasswordMessage(response.data);
            setPasswordChanged(true);
        } catch (error) {
            setForgotPasswordMessage(error.response ? error.response.data : 'Lỗi khi đổi mật khẩu!');
        }
    };

    return (
        <>
            <h1 className="text-center text-info mt-1">ĐĂNG NHẬP NGƯỜI DÙNG</h1>
            <Form method="post" onSubmit={login}>
                <MDBContainer fluid className="p-3 my-5">
                    <MDBRow>
                        <MDBCol col='10' md='6'>
                            <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.svg" className="img-fluid" alt="Phone image" />
                        </MDBCol>
                        <MDBCol col='4' md='6'>
                            <MDBInput wrapperClass='mb-4' placeholder='Username' id='formControlLg' type='text' size="lg" value={username} onChange={e => setUsername(e.target.value)} />
                            <MDBInput wrapperClass='mb-4' placeholder='Password' id='formControlLg' type='password' size="lg" value={password} onChange={e => setPassword(e.target.value)} />
                            <div className="d-flex justify-content-between mx-4 mb-4">
                                <MDBCheckbox name='flexCheck' value='' id='flexCheckDefault' label='Remember me' />
                                <a href="#!" onClick={() => setShowForgotPasswordModal(true)}>Forgot password?</a>
                            </div>
                            <MDBBtn className="mb-4 w-100" size="lg" type="submit">Sign in</MDBBtn>
                            {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}
                            <div className="divider d-flex align-items-center my-4">
                                <p className="text-center fw-bold mx-3 mb-0">OR</p>
                            </div>
                            <div>
                                {!profile ? 
                                    <LoginSocialFacebook
                                        appId="1967856000323374"
                                        onResolve={(response) => {
                                            setProfile(response.data);
                                        }}
                                        onReject={(error) => {
                                            console.log(error);
                                        }}>
                                        <FacebookLoginButton />
                                    </LoginSocialFacebook>
                                : ''}
                                {profile && 
                                    <div>
                                        <h1>{profile.name}</h1>
                                        <img src={profile.picture.data.url} alt="Profile" />
                                    </div>
                                }
                            </div>
                            <MDBBtn className="mb-4 w-100" size="lg" style={{ backgroundColor: '#55acee' }}>
                                <MDBIcon fab icon="twitter" className="mx-2" />
                                Continue with Twitter
                            </MDBBtn>
                        </MDBCol>
                    </MDBRow>
                </MDBContainer>
            </Form>
            {/* Forgot Password Modal */}
            <Modal show={showForgotPasswordModal} onHide={() => setShowForgotPasswordModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>Forgot Password</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {!otpSent && (
                        <Form onSubmit={handleForgotPassword}>
                            <Form.Group controlId="forgotUsername">
                                <Form.Label>Nhập tên đăng nhập</Form.Label>
                                <Form.Control
                                    type="text"
                                    placeholder="Tên đăng nhập"
                                    value={forgotUsername}
                                    onChange={(e) => setForgotUsername(e.target.value)}
                                    required
                                />
                            </Form.Group>
                            {forgotPasswordMessage && <Alert variant="info" className="mt-3">{forgotPasswordMessage}</Alert>}
                            <Button variant="primary" type="submit" className="mt-3">
                                Gửi mã OTP
                            </Button>
                        </Form>
                    )}
                    {otpSent && !otpVerified && (
                        <OtpVerification email={forgotUsername} onOtpSubmit={handleOtpSubmit} />
                    )}
                    {otpVerified && (
                        <Form onSubmit={handleChangePassword}>
                            <Form.Group controlId="newPassword">
                                <Form.Label>Nhập mật khẩu mới</Form.Label>
                                <Form.Control
                                    type="password"
                                    placeholder="Mật khẩu mới"
                                    value={newPassword}
                                    onChange={(e) => setNewPassword(e.target.value)}
                                    required
                                />
                            </Form.Group>
                            <Form.Group controlId="confirmPassword">
                                <Form.Label>Xác nhận mật khẩu mới</Form.Label>
                                <Form.Control
                                    type="password"
                                    placeholder="Xác nhận mật khẩu mới"
                                    value={confirmPassword}
                                    onChange={(e) => setConfirmPassword(e.target.value)}
                                    required
                                />
                            </Form.Group>
                            {forgotPasswordMessage && <Alert variant="info" className="mt-3">{forgotPasswordMessage}</Alert>}
                            {passwordChanged && <Alert variant="success" className="mt-3">Mật khẩu đã được đổi thành công!</Alert>}
                            <Button variant="primary" type="submit" className="mt-3">
                                Đổi mật khẩu
                            </Button>
                        </Form>
                    )}
                </Modal.Body>
            </Modal>
        </>
    );
}

export default Login;
