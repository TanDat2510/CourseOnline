import React from 'react';
import { useLocation } from 'react-router-dom';

const Failure = () => {
    const location = useLocation();
    const message = location.state?.message || "Thanh toán thất bại!";

    return (
        <div>
            <h1>{message}</h1>
        </div>
    );
};

export default Failure;
