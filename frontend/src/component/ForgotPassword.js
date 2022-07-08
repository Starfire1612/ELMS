import axios from "axios";
import React, { useState } from "react";
import { Button, FloatingLabel, Form } from "react-bootstrap";
import { Link } from "react-router-dom";
import "../styles/Register.css";
import bcryptjs from "bcryptjs";
import { BASE_URL } from "./../util/constants";

function ForgotPassword() {
  const [user, setUser] = useState({});
  const [isOtpSent, setIsOtpSent] = useState(false);
  const [isOtpVerified, setIsOtpVerified] = useState(false);
  const [match, setMatch] = useState(false);
  const [typeMatch, setTypeMatch] = useState(false);
  const [checkEmail, setCheckEmail] = useState(false);

  const handleChange = (event) => {
    const eventName = event.target.name;
    const value = event.target.value;
    setUser((prevState) => ({
      ...prevState,
      [eventName]: value,
    }));
    switch (eventName) {
      case "type": {
        if (value === "Select one") {
          setTypeMatch(true);
        } else {
          setTypeMatch(false);
        }
        break;
      }
      case "email": {
        if (value === "") {
          setCheckEmail(true);
        } else {
          setCheckEmail(false);
        }
        break;
      }
      case "otp": {
        break;
      }
    }
  };

  const verifyOtp = async () => {
    if (!user?.otp) {
      setMatch(false);
      return;
    }
    const res = await bcryptjs.compare(
      user.otp,
      user.encryptedOtp,
      (err, same) => {
        if (same) {
          setIsOtpVerified(true);
          setMatch(true);
          setUser((prevUser) => ({
            email: prevUser.email,
            type: prevUser.type,
          }));
        } else {
          setMatch(false);
        }
      }
    );
    console.log(res);
  };

  const handleOtp = async () => {
    // if (!checkEmail || !typeMatch) {
    //   return;
    // }
    setTypeMatch(true);

    const encryptedOtp = await axios
      .get(
        `http://localhost:8080/forgot-password/email/${user.email}/type/${user.type}`
      )
      .then((response) => response.headers.otp)
      .catch((err) => console.log(err));
    if (!encryptedOtp) return;
    setUser((prevState) => ({
      ...prevState,
      encryptedOtp,
    }));
    setIsOtpSent(true);
    console.log(encryptedOtp);
  };

  const handleChangePassword = async () => {
    if (user.password !== user.confirmPassword || user.password.length < 8) {
      return;
    }
    const requestBody = {
      type: user.type,
      useremail: user.email,
      password: user.password,
    };
    const response = await axios
      .post("http://localhost:8080/forgot-password", requestBody)
      .then((res) => res)
      .catch((err) => console.log(err));
    console.log(response);
  };

  const button = (handleFunction, value) => {
    return (
      <Button
        className="btn-center mb-3"
        variant="success"
        onClick={handleFunction}
        type="submit"
      >
        {value}
      </Button>
    );
  };
  const handleSubmit = (event) => {
    event.preventDefault();
  };

  return (
    <div className="contain">
      <Form onSubmit={handleSubmit}>
        <div className="form p-3">
          <h3 className="mb-3"> Forgot Password</h3>
          {!isOtpVerified ? (
            <>
              {typeMatch ? (
                <p className="not-found">Select correct type</p>
              ) : (
                ""
              )}
              <Form.Select
                name="type"
                onChange={handleChange}
                disabled={isOtpSent}
                required
              >
                <option>Select one</option>
                <option value="student">Student</option>
                <option value="instructor">Instructor</option>
              </Form.Select>
              {checkEmail ? <p className="not-found">Enter email</p> : ""}
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
                  disabled={isOtpSent}
                  required
                />
              </FloatingLabel>
              {!match ? <p className="not-found">Enter correct OTP</p> : ""}
              <FloatingLabel className="mb-3" label="OTP">
                <Form.Control
                  type="password"
                  name="otp"
                  onChange={handleChange}
                  placeholder="otp"
                  disabled={!isOtpSent}
                  required
                />
              </FloatingLabel>
              {isOtpSent
                ? button(verifyOtp, "Verify")
                : button(handleOtp, "Send OTP")}
            </>
          ) : (
            <>
              <FloatingLabel className="mb-3" label="Password">
                <Form.Control
                  type="password"
                  name="password"
                  onChange={handleChange}
                  placeholder="Password"
                  required
                />
              </FloatingLabel>
              {!match ? (
                <p className="not-found">password does not match</p>
              ) : (
                ""
              )}
              <FloatingLabel className="mb-3" label="Confirm Password">
                <Form.Control
                  type="password"
                  name="confirmPassword"
                  onChange={handleChange}
                  placeholder="Confirm password"
                  required
                />
              </FloatingLabel>
              {button(handleChangePassword, "Confirm")}
            </>
          )}

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
