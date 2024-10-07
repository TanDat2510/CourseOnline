import { useContext, useEffect, useState } from "react";
import { Button, Card, Col, Row, Spinner, Toast, ToastContainer } from "react-bootstrap";
import cookie from "react-cookies";
import { useNavigate } from "react-router-dom";
import { MyCartContext, MyUserContext } from "../App";
import { authAPIs, endpoints } from "../config/APIs";
import { FaEye, FaShoppingCart, FaSignInAlt } from 'react-icons/fa';
import '../css/Course.css';
import '../css/Spinner.css';
import '../css/ToastContainer.css';
import myImg from '../components/resource/img/BackgroundWeb.jpg'

const DEFAULT_IMAGE = "https://res.cloudinary.com/djrv1hnrc/image/upload/v1726311831/siobzpxrmha1mqpihz5k.jpg";

const Course = () => {
  const [courses, setCourses] = useState([]);
  const [, dispatch] = useContext(MyCartContext);
  const [showToast, setShowToast] = useState(false);
  const [toastMessage, setToastMessage] = useState("");
  const [toastBgColor, setToastBgColor] = useState("");
  const [loading, setLoading] = useState(true);

  const user = useContext(MyUserContext);
  const navigate = useNavigate();

  const loadCourses = async () => {
    try {
      const url = `${endpoints['courses']}`;
      const res = await authAPIs().get(url);
      setCourses(res.data);
      setLoading(false);
    } catch (ex) {
      console.error(ex);
    } 
  }

  useEffect(() => {
    setLoading(true);
    loadCourses();
  }, [user]);

  const order = (c) => {
    let cart = cookie.load("cart") || {};
    if (c.id in cart) {
      setToastMessage("Khóa học đã có trong giỏ hàng.");
      setToastBgColor("bg-danger");
      setShowToast(true);
    } else {
      cart[c.id] = {
        "id": c.id,
        "title": c.title,
        "price": c.price,
        "img": c.img,
      };

      cookie.save("cart", cart);
      dispatch({ "type": "update", "payload": 1 });
      setToastMessage("Đã thêm khóa học vào giỏ hàng.");
      setToastBgColor("bg-success");
      setShowToast(true);
    }
  }

  if (loading) {
    return (
      <div className="spinner-container">
        <Spinner animation="grow" variant="primary" className="spinner-lg" />
      </div>
    );
  }

  return (
    <>
      {/* Banner */}
      <Card className="banner-card text-white">
        <Card.Img src={myImg} alt="Banner Image" className="banner-image" />
        <Card.ImgOverlay className="d-flex flex-column justify-content-center align-items-center">
          <h1 className="banner-title">Chào Mừng Bạn Đến Với Khóa Học</h1>
          <p className="banner-text">Khám phá những khóa học hấp dẫn và phát triển kỹ năng của bạn ngay hôm nay!</p>
          <Button variant="primary" size="lg">Tìm Hiểu Ngay</Button>
        </Card.ImgOverlay>
      </Card>

      <Row className="p-5">
      <h1>Tuyển tập khóa học rộng lớn</h1>
      <h5>Lựa chọn trong số hơn 250000 khóa học video online với nhiều nội dung bổ sung mới được xuất bản hàng tháng</h5>
        {courses.map(c => {
          const imgSrc = c.img || DEFAULT_IMAGE;
          return (
            <Col className="p-2" key={c.id} md={4} xs={12}>
              <Card className="course-card">
                <Card.Img
                  variant="top"
                  src={imgSrc}
                  style={{ width: "100%", height: "auto", objectFit: "contain" }} 
                  alt={c.title}
                />
                <Card.Body>
                  <Card.Title>{c.title}</Card.Title>
                  <Card.Text className="course-description">{c.description}</Card.Text>
                  <Card.Text>{c.price.toLocaleString("en")} VNĐ</Card.Text>
                  <div className="button-group">
                    {c.register ? (
                      <Button variant="primary" onClick={() => navigate(`/courses/${c.id}`)}>
                        Xem chi tiết <FaEye className="icon" />
                      </Button>
                    ) : (
                      <>
                        {user && user.userRole === 'ROLE_STUDENT' ? (
                          <>
                            <Button variant="danger" onClick={() => order(c)}>
                              Đặt hàng <FaShoppingCart className="icon" />
                            </Button>
                            <Button variant="primary" onClick={() => navigate(`/courses/${c.id}`)}>
                              Xem chi tiết <FaEye className="icon" />
                            </Button>
                          </>
                        ) : (
                          <>
                            <Button variant="success" onClick={() => navigate("/login")}>
                              Đăng nhập <FaSignInAlt className="icon" />
                            </Button>
                            <Button variant="primary" onClick={() => navigate(`/courses/${c.id}`)}>
                              Xem chi tiết <FaEye className="icon" />
                            </Button>
                          </>
                        )}
                      </>
                    )}
                  </div>
                </Card.Body>
              </Card>
            </Col>
          );
        })}
      </Row>

      <ToastContainer className="custom-toast position-fixed top-0 start-50 translate-middle-x">
        <Toast
          show={showToast}
          onClose={() => setShowToast(false)}
          delay={2000}
          autohide
          className={toastBgColor}
        >
          <Toast.Body>{toastMessage}</Toast.Body>
        </Toast>
      </ToastContainer>
    </>
  );
}

export default Course;
