import { useContext, useEffect, useState } from 'react';
import { Badge, Button, Container, Image, Nav, Navbar, NavDropdown } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import { MyCartContext, MyDispatchContext, MyUserContext } from '../App';
import APIs, { endpoints } from '../config/APIs';
import '../css/Header.css';

// Font Awesome icons
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUser, faSignInAlt, faSignOutAlt, faCartShopping } from '@fortawesome/free-solid-svg-icons';

const Header = () => {
  const [categories, setCategories] = useState([]);
  const user = useContext(MyUserContext);
  const dispatch = useContext(MyDispatchContext);
  const [cartCounter, cartDispatch] = useContext(MyCartContext);
  const nav = useNavigate();

  const loadCates = async () => {
    try {
      let res = await APIs.get(endpoints['categories']);
      setCategories(res.data);
    } catch (ex) {
      console.error(ex);
    }
  };

  useEffect(() => {
    loadCates();
  }, []);

  const handleLogout = () => {
    cartDispatch({ type: "reset" });
    dispatch({ type: "logout" });
    nav('/');

  };

  return (
    <Navbar expand="lg" bg="dark" variant="dark" className='mb-3 p-3'>
      <Container>
        <Navbar.Brand as={Link} to="/">E-Courses Website</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            <Link className='nav-link' to="/">Trang chủ</Link>
            <NavDropdown title="Danh mục" id="basic-nav-dropdown">
              {categories.map(c => {
                const url = `/courses?cateId=${c.id}`;
                return <Link to={url} className='dropdown-item' key={c.id}>{c.name}</Link>;
              })}
            </NavDropdown>
          </Nav>
          <Nav className="ml-auto d-flex align-items-center">
            {user === null ? (
              <>
                <Link className='nav-link text-success' to="/login">
                  <FontAwesomeIcon icon={faSignInAlt} /> Đăng nhập
                </Link>
                <Link className='nav-link text-success' to="/register">
                  <FontAwesomeIcon icon={faUser} /> Đăng ký
                </Link>
              </>
            ) : (
              <>
                <Link className='nav-link text-success d-flex align-items-center' to="/profile">
                  <Image src={user.avatar} width="25" roundedCircle />
                  <span className="ms-2">Chào {user.firstName} {user.lastName}!</span>
                </Link>
                <Button variant='danger' onClick={handleLogout}>
                  <FontAwesomeIcon icon={faSignOutAlt} /> Đăng xuất
                </Button>
                <Link className='nav-link text-danger d-flex align-items-center ms-3' to="/cart">
                  <FontAwesomeIcon icon={faCartShopping} /> <Badge bg='success' className="ms-2">{cartCounter}</Badge>
                </Link>
              </>
            )}
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default Header;
