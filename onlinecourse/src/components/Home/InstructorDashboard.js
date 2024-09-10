import React, { useContext, useEffect, useState } from 'react';
import { authAPIs, endpoints } from '../../configs/APIs';
import '../Style/InstructorDashboard.css';  // Import CSS
import { MyUserContext } from '../../App';  // Import context để lấy thông tin người dùng
import '../../css/Spinner.css';
import { Spinner } from 'react-bootstrap';
import { useNavigate } from 'react-router';

const InstructorDashboard = () => {
    const [courses, setCourses] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [instructorId, setInstructorId] = useState(null);
    const navigate = useNavigate();

    // Lấy thông tin người dùng từ context
    const user = useContext(MyUserContext);

    // Hàm gọi API để lấy danh sách instructor
    const fetchInstructors = async () => {
        try {
            const response = await authAPIs().get(endpoints['instructor']);
            const instructors = response.data;
            const userInstructor = instructors.find(inst => inst.userId.id === user.id);

            if (userInstructor) {
                setInstructorId(userInstructor.id);
            } else {
                throw new Error("Instructor ID not found for the current user");
            }
        } catch (error) {
            console.error("Error fetching instructors:", error);
            setError("Unable to fetch instructors");
            setLoading(false);
        }
    };

    // Hàm gọi API để lấy danh sách khóa học theo instructorId
    const fetchCoursesByInstructorId = async (instructorId) => {
        try {
            const response = await authAPIs().get(endpoints['courses'](instructorId));
            setCourses(response.data);
        } catch (error) {
            console.error("Error fetching courses:", error);
            setError("Unable to fetch courses");
        } finally {
            setLoading(false);  // Hoàn tất tải dữ liệu
        }
    };

    // Fetch instructor khi component mount
    useEffect(() => {
        fetchInstructors();
    }, []);

    // Fetch courses khi instructorId thay đổi và khác null
    useEffect(() => {
        if (instructorId) {
            fetchCoursesByInstructorId(instructorId);
        }
    }, [instructorId]);

    // Kiểm tra loading và error
    if (loading) {
        return (
            <div className="spinner-container">
                <Spinner animation="grow" variant="primary" className="spinner-lg" />
            </div>
        );
    }
    if (error) return <div>{error}</div>;

    return (
        <div className="course-list">
            {courses.length > 0 ? (
                <div className="course-grid">
                    {courses.map((course) => (
                        <div className="course-card" key={course.id}>
                            <img src={course.img} alt={course.title} className="course-image" />
                            <div className="course-content">
                                <h2>{course.title}</h2>
                                <p>{course.description}</p>
                                <p><strong>Status:</strong> {course.status}</p>
                                <p><strong>Time:</strong> {course.timeExperted}</p>
                                <button className="btn-view" onClick={() => {navigate(`/enrollments/${course.id}`)}}>Quản lý khóa học</button>
                            </div>
                        </div>
                    ))}
                </div>
            ) : (
                !loading && <p>No courses found for this instructor.</p>
            )}
        </div>
    );
};

export default InstructorDashboard;
