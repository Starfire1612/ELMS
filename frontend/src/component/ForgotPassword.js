import axios from "axios";
import React, { useState } from "react";
import { Button, FloatingLabel, Form } from "react-bootstrap";
import { Link } from "react-router-dom";
import "../styles/Register.css";
import { BASE_URL } from './../util/constants';

function ForgotPassword() {
  const [user, setUser] = useState({});
  const handleChange = (event) => {
    setUser((prevState) => ({
      ...prevState,
      [event.target.name]: event.target.value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    const response =await axios.get(`http://localhost:8080/forgot-password/email/${user.email}/type/${user.type}`)
    console.log(response);
  };

  return (
    <div className="contain">
      <Form onSubmit={handleSubmit}>
        <div className="form p-3">
          <h3 className="mb-3"> Forgot Password</h3>

          <Form.Select name="type" onChange={handleChange}>
            <option>Select one</option>
            <option value="student">Student</option>
            <option value="instructor">Instructor</option>
          </Form.Select>
          <FloatingLabel
            controlId="floatingInput"
            label="Email address"
            className="mb-3 mt-3"
          >
            <Form.Control
              type="email"
              name="email"
              onChange={handleChange}
              placeholder="name@example.com"
            />
          </FloatingLabel>

          <FloatingLabel className="mb-3" label="OTP">
            <Form.Control
              type="password"
              name="otp"
              onChange={handleChange}
              placeholder="otp"
            />
          </FloatingLabel>

          <Button className="signup-btn mb-3" variant="success" type="submit">
            Forgot Password
          </Button>

          <Link to="/">
            <div className="fs-6 mb-3 forgot-password text-center">
              Back To Login
            </div>
          </Link>
        </div>
      </Form>
    </div>
  );
}

export default ForgotPassword;
