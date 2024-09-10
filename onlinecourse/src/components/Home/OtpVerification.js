import React, { useState } from 'react';
import { Form, Button, Alert } from 'react-bootstrap';
import '../Style/OtpInput.css';  // Đảm bảo bạn tạo một file CSS để tùy chỉnh giao diện cho giống với hình

const OtpVerification = ({ email, onOtpSubmit }) => {
    const [otp, setOtp] = useState(new Array(6).fill(""));  // Tạo một mảng 6 ký tự rỗng
    const [errorMessage, setErrorMessage] = useState('');

    // Hàm xử lý khi người dùng nhập OTP vào các ô
    const handleChange = (element, index) => {
        if (isNaN(element.value)) return;  // Kiểm tra chỉ cho phép nhập số

        let newOtp = [...otp];
        newOtp[index] = element.value;
        setOtp(newOtp);

        // Di chuyển tới ô tiếp theo nếu người dùng nhập một số
        if (element.nextSibling && element.value) {
            element.nextSibling.focus();
        }
    };

    // Xử lý khi người dùng nhấn nút 'Tiếp tục'
    const handleSubmit = (e) => {
        e.preventDefault();
        const otpCode = otp.join("");  // Kết hợp các giá trị OTP thành chuỗi
        if (otpCode.length !== 6) {
            setErrorMessage("Vui lòng nhập đầy đủ 6 số.");
        } else {
            setErrorMessage("");
            onOtpSubmit(otpCode);  // Gọi callback để xác thực OTP
        }
    };

    return (
        <div className="otp-verification">
            <h3>XÁC THỰC OTP</h3>
            <p>
                Vui lòng nhập mã số chúng tôi đã gửi cho bạn qua email <b>{email}</b>.<br />
                Mã xác thực có giá trị trong 120 giây.
            </p>
            <Form onSubmit={handleSubmit}>
                <div className="otp-input-container">
                    {otp.map((data, index) => (
                        <input
                            type="text"
                            name="otp"
                            maxLength="1"
                            key={index}
                            value={data}
                            onChange={(e) => handleChange(e.target, index)}
                            onFocus={(e) => e.target.select()}  // Tự động chọn khi người dùng nhấn vào ô
                            className="otp-input"
                        />
                    ))}
                </div>
                {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}
                <Button variant="primary" type="submit" className="mt-3">
                    Tiếp tục
                </Button>
            </Form>
        </div>
    );
};

export default OtpVerification;