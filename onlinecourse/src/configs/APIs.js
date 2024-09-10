import axios from "axios"
import cookie from "react-cookies"

const BASE_URL = 'http://localhost:8082/OnlineCourseWeb/api'

export const endpoints = {
    'login': '/login/',
    'current-user': '/current-user/',
    'register': '/users/',
    'verifyAccount': '/verifyAccount/',
    'verifyOtp':'/verifyOtp/',
    'changePassword': '/change-password/',
    'courses':(id) => `/instructor/${id}/courses`,
    'instructor':'/instructor/',
    'enrollments':(id) => `/enrollments/${id}/`

}
console.info(cookie.load('token'))
export const authAPIs = () => {
    return axios.create({
        baseURL: BASE_URL,
        headers: {
            'Authorization': cookie.load("access-token")
        }
    })
}
// Thêm phương thức để gửi OTP khi quên mật khẩu
export const requestOTP = async (username) => {
    try {
        // Gọi API để gửi OTP đến email của người dùng
        const response = await axios.post(`${BASE_URL}${endpoints.verifyAccount}${username}`);
        return response.data;  // Trả về phản hồi từ server
    } catch (error) {
        // Nếu có lỗi, trả về lỗi
        throw error.response ? error.response.data : error.message;
    }
};

export const verifyOtp = async (otp, username) => {
    try {
        const response = await axios.post(`${BASE_URL}${endpoints.verifyOtp}${otp}/${username}`);
        return response.data;
    } catch (error) {
        throw error.response ? error.response.data : error.message;
    }
};

// Thay đổi mật khẩu sau khi xác thực OTP
export const changePassword = async (username, newPassword, confirmPassword) => {
    try {
        const response = await axios.post(`${BASE_URL}${endpoints.changePassword}${username}`, {
            newPassword,
            confirmPassword
        });
        return response.data;  // Trả về phản hồi từ server
    } catch (error) {
        throw error.response ? error.response.data : error.message;
    }
};

export const getCurrentUser = async () => {
    try {
        const response = await authAPIs().get(endpoints['current-user']);
        console.log("Current User Data:", response.data);  // Log ra để xem dữ liệu trả về
        return response.data;
    } catch (error) {
        throw error.response ? error.response.data : error.message;
    }
};


export const getInstructors = async () => {
    try {
        const response = await authAPIs().get(endpoints['instructor']);
        console.log("Instructors Data:", response.data);  // Debug: Kiểm tra dữ liệu trả về từ server
        return response.data;  // Trả về danh sách instructors từ server
    } catch (error) {
        throw error.response ? error.response.data : error.message;
    }
};

export const getCoursesByInstructorId = async (instructorId) => {
    try {
        const response = await authAPIs().get(`${endpoints.courses}${instructorId}`);
        console.log("Response data from API:", response.data);  // Kiểm tra dữ liệu trả về từ API
        return response.data;  // Trả về dữ liệu khóa học từ server
    } catch (error) {
        console.error("Error in getCoursesByInstructorId:", error.response ? error.response.data : error.message);
        throw error.response ? error.response.data : error.message;
    }
};

export default axios.create({
    baseURL: BASE_URL
});