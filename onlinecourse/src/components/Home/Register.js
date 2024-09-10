import React from "react";
import '../Style/Login.css'
import { useReducer, useRef, useState } from "react";
import { Alert, Button, Form } from "react-bootstrap";
import { useNavigate } from "react-router";
import APIs, { endpoints } from "../../configs/APIs";
import {
    MDBContainer,
    MDBCol,
    MDBRow,
    MDBBtn,
    MDBIcon,
    MDBInput,
    MDBCheckbox
}
from 'mdb-react-ui-kit';


const Register = () => {
    const [user, setUser] = useState({});
    const [err, setErr] = useState();
    const nav = useNavigate();
    const avatar = useRef();

    const register = async (e) => {
        e.preventDefault();

        if (user.password === undefined || user.password !== user.confirm)
            setErr("Mật khẩu KHÔNG khớp!");
        else {
            let form = new FormData();
            for (let f in user)
                if (f !== 'confirm') {
                    form.append(f, user[f]);
                }

            form.append('avatar', avatar.current.files[0]);

            let res = await APIs.post(endpoints['register'], form, {
                headers: {
                    'Content-Type': "multipart/form-data"
                }
            });
            console.info(res.data);

            nav("/login");
        }
    }

    const change = (e, field) => {
        setUser({ ...user, [field]: e.target.value })
    }

    return (
        <>
            <h1 className="text-center text-info mt-1">ĐĂNG KÝ NGƯỜI DÙNG</h1>
            {err && <Alert variant="danger">{err}</Alert>}

            {<Form method="post" onSubmit={register}>
                <MDBContainer fluid className="p-3 my-5">

                    <MDBRow>

                        <MDBCol col='10' md='6'>
                            <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.svg" class="img-fluid" alt="Phone image" />
                        </MDBCol>

                        <MDBCol col='4' md='6'>


                            <MDBInput wrapperClass='mb-4' placeholder='Tên...' id='formControlLg' type='firstname' size="lg" value={user["firstName"]} onChange={e => change(e, "firstName")} />
                            <MDBInput wrapperClass='mb-4' placeholder='Họ và tên lót...' id='formControlLg' type='lastname' size="lg" value={user["lastName"]} onChange={e => change(e, "lastName")} />
                            <MDBInput wrapperClass='mb-4' placeholder='Usernam...' id='formControlLg' type='username' size="lg" value={user["username"]} onChange={e => change(e, "username")} />
                            <MDBInput wrapperClass='mb-4' placeholder='Password...' id='formControlLg' type='username' size="lg" value={user["password"]} onChange={e => change(e, "password")} />
                            <MDBInput wrapperClass='mb-4' placeholder='Confirm..' id='formControlLg' type='password' size="lg" value={user["confirm"]} onChange={e => change(e, "confirm")} />
                            <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea7">
                                <Form.Label>Ảnh đại diện</Form.Label>
                                <Form.Control accept=".png,.jpg" type="file" ref={avatar} />
                            </Form.Group>
                            
                            <MDBBtn className="mb-4 w-100" type="submit" variant="success" size="lg">Đăng ký</MDBBtn>



                        </MDBCol>

                    </MDBRow>

                </MDBContainer>

            </Form>}

        </>
    );
}

export default Register;