import { Alert } from "react-bootstrap";
import { useState } from "react";
import { Button, Table } from "react-bootstrap";
import cookie from "react-cookies";
import { useContext } from "react";
import { useNavigate } from "react-router-dom"; 
import { MyCartContext } from "../App";

const DEFAULT_IMAGE = "https://res.cloudinary.com/dh1irfap0/image/upload/v1725264150/NANG-CAP-BAN-THAN_pdmrgk.png";

const Cart = () => {
    const [cart, setCart] = useState(cookie.load("cart") || {});
    const [, dispatch] = useContext(MyCartContext);
    const navigate = useNavigate(); 

    const removeFromCart = (id) => {
        const updatedCart = { ...cart };
        delete updatedCart[id];
        setCart(updatedCart);
        cookie.save("cart", updatedCart);
        dispatch({ type: "update", payload: -1 });
    };

    const handleCheckout = () => {
        navigate("/payment", { state: { cart } }); 
    };

    if (Object.keys(cart).length === 0)
        return <Alert variant="warning">KHÔNG có sản phẩm trong giỏ!</Alert>;

    return (
        <>
            <h1 className="text-center text-info mt-1">GIỎ HÀNG</h1>
            <Table striped bordered hover className="container">
                <thead>
                    <tr>
                        <th>Id</th>
                        <th style={{ width: "150px" }}>Ảnh</th> 
                        <th>Tên khóa học</th>
                        <th>Giá</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    {Object.values(cart).map(c => (
                        <tr key={c.id}>
                            <td>{c.id}</td>
                            <td>
                                <img
                                    src={c.img || DEFAULT_IMAGE}
                                    alt={c.title}
                                    style={{ width: "100%", height: "auto", objectFit: "contain" }} 
                                />
                            </td>
                            <td>{c.title}</td>
                            <td>{c.price.toLocaleString("en")} VNĐ</td>
                            <td>
                                <Button variant="danger" onClick={() => removeFromCart(c.id)}>&times;</Button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </Table>
            <div className="d-flex justify-content-end"> 
                <Button 
                    variant="success" 
                    onClick={handleCheckout} 
                    style={{ marginRight: "20px", marginBottom: "20px" }} // Thêm margin-right và margin-bottom
                >
                    Thanh toán
                </Button>
            </div>
        </>
    );
}

export default Cart;
