import axios from "axios"
import cookie from "react-cookies"

const BASE_URL = 'http://localhost:8082/OnlineCourseWeb'

export const endpoints = {
    'categories': 'api/categories',
    'courses':'api/courses',
    'course-details': (id) => `api/courses/${id}`,
    'content':(id) => `api/courses/${id}/content`, 
    'login': 'api/login',
    'current-user': 'api/current-user',
    'register': 'api/users',
    'payment': '/payments/pay',
    'enrollment': (id) =>`api/courses/${id}/enrollments`,
    'content-details':(courseId, contentId) => `api/courses/${courseId}/content/${contentId}`
}

export const authAPIs = () => {
    return axios.create({
        baseURL: BASE_URL,
        headers: {
            'Authorization': cookie.load("access-token")
        }
    })
}

export default axios.create({
    baseURL: BASE_URL
});