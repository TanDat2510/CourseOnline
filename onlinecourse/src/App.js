import 'bootstrap/dist/css/bootstrap.min.css';
import Course from './components/Course';
import Footer from './layout/Footer';
import Header from './layout/Header';
import Cart from './components/Cart';
import Profile from './components/Profile';
import { createContext, useReducer } from 'react';
import MyCartReducer from './reducers/MyCartReducer';
import MyUserReducer from './reducers/MyUserReducer';
import Payment from './components/Payment';
import Success from './components/Success';
import Failure from './components/Failure';
import CourseContent from './components/CourseContent';
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import Login from './components/Home/Login';
import Register from './components/Home/Register';
import { Container } from 'react-bootstrap';
import CourseDetail from './components/CourseDetail';
import InstructorDashboard from './components/Home/InstructorDashboard';
import ProtectedRoute from './components/Home/ProtectedRoute';
import StudentList from './components/Home/StudentList';
// import Login from './components/Login';


export const MyUserContext = createContext();
export const MyDispatchContext = createContext();
export const MyCartContext = createContext();


const App = () => {
  const [user, dispatch] = useReducer(MyUserReducer, null);
  const [cartCounter, cartDispatch] = useReducer(MyCartReducer, 0);

  return (
    <BrowserRouter>
      <MyUserContext.Provider value={user}>
        <MyDispatchContext.Provider value={dispatch}>
          <MyCartContext.Provider value={[cartCounter, cartDispatch]}>
            <Header />
            <Container>

              <Routes>                                       
                <Route path='/' element={<Course />} />
                <Route path='/login' element={<Login />} />
                <Route path='/register' element={<Register />} />
                <Route path='/cart' element={<Cart />} /> {/* Route cho trang Giỏ Hàng */}
                <Route path='/profile' element={<Profile />} /> {/* Route cho trang Hồ Sơ */}
                <Route path='/payment' element={<Payment />} /> {/* Route cho trang Hồ Sơ */}
                <Route path="/success" element={<Success />} />
                <Route path="/failure" element={<Failure />} />
                <Route path="/courses/:courseId/content/:contentId" element={<CourseContent />} />
                <Route path="/courses/:id" element={<CourseDetail />} />
                <Route
                path="/instructor-dashboard"
                element={
                  <ProtectedRoute roles={['ROLE_INSTRUCTOR']}>
                    <InstructorDashboard />
                  </ProtectedRoute>
                }
                />
                  <Route
                path="/enrollments/:id"
                element={
                  <ProtectedRoute roles={['ROLE_INSTRUCTOR']}>
                    <StudentList />
                  </ProtectedRoute>
                }
                />
              </Routes>

            </Container>
            <Footer />
          </MyCartContext.Provider>
        </MyDispatchContext.Provider>
      </MyUserContext.Provider>
    </BrowserRouter>

  );
}

export default App;
