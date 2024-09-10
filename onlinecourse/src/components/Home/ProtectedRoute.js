import React from 'react';
import { Navigate } from 'react-router-dom';
import { MyUserContext } from '../../App';


const ProtectedRoute = ({ roles, children }) => {
    const user = React.useContext(MyUserContext);

    // Kiểm tra nếu người dùng không đăng nhập hoặc vai trò không khớp
    if (!user || !roles.includes(user.userRole)) {
        return <Navigate to="/" />;
    }

    return children;
};

export default ProtectedRoute;