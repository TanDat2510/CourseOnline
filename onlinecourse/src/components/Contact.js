import React from 'react';
import { Container, Form, Button } from 'react-bootstrap';

const Contact = () => {
  return (
    <Container>
      <h1>Liên Hệ</h1>
      <Form>
        <Form.Group controlId="formBasicName">
          <Form.Label>Tên</Form.Label>
          <Form.Control type="text" placeholder="Nhập tên của bạn" />
        </Form.Group>

        <Form.Group controlId="formBasicEmail">
          <Form.Label>Email</Form.Label>
          <Form.Control type="email" placeholder="Nhập email của bạn" />
        </Form.Group>

        <Form.Group controlId="formBasicMessage">
          <Form.Label>Thông Điệp</Form.Label>
          <Form.Control as="textarea" rows={3} placeholder="Nhập thông điệp của bạn" />
        </Form.Group>

        <Button variant="primary" type="submit">
          Gửi
        </Button>
      </Form>
    </Container>
  );
};

export default Contact;
