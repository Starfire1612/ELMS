import axios from "axios";
import React, { useState } from "react";
import { Button, FloatingLabel, Form } from "react-bootstrap";
import { Link } from "react-router-dom";
import "../../styles/Register.css";
import bcryptjs from "bcryptjs";
import AuthAnimation from "../Animations/AuthAnimation";
import { HashLoader } from "react-spinners";
import { LOADING_COLOR } from "../../utils/constants";
import { fetchOtp } from "../../utils/http-requests";
// import { validateFields } from "../../utils/util";

function ForgotPassword() {
  const [user, setUser] = useState({});
  const [isOtpSent, setIsOtpSent] = useState(false);
  const [isOtpVerified, setIsOtpVerified] = useState(false);
  const [inputFields, setInputFields] = useState({
    type: true,
    email: true,
    otp: true,
    password: true,
  });
  const [isLoading, setIsLoading] = useState(false);

  const handleChange = (event) => {
    const eventName = event.target.name;
    const value = event.target.value;
    setUser((prevState) => ({
      ...prevState,
      [eventName]: value,
    }));
    //for validation of fields(not completed)
    // validateFields(eventName, value, inputFields, setInputFields);
  };

  const verifyOtp = async () => {
    if (!user?.otp) {
      return;
    }
    setIsLoading(true);
    const res = await bcryptjs.compare(
      user.otp,
      user.encryptedOtp,
      (err, same) => {
        if (same) {
          setIsOtpVerified(true);
          // setMatch(true);
          setUser((prevUser) => ({
            email: prevUser.email,
            type: prevUser.type,
          }));
        } else {
          setInputFields((prevState) => ({ ...prevState, otp: false }));
        }
      }
    );
    //set isLoading to false
    console.log(res);
  };

  const handleOtp = async () => {
    // if (!checkEmail || !typeMatch) {
    //   return;
    // }
    setIsLoading(true);
    const encryptedOtp = await fetchOtp(user.email, user.type);
    if (!encryptedOtp) {
      setIsLoading(false);
      return;
    }
    setUser((prevState) => ({
      ...prevState,
      encryptedOtp,
    }));
    setIsOtpSent(true);
    console.log(encryptedOtp);
    //set isLoading to false
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
    setIsLoading(true);
    const response = await axios
      .post("http://localhost:8080/forgot-password", requestBody)
      .then((res) => res)
      .catch((err) => console.log(err));
    console.log(response);
    //after response set isLoading to false
  };

  const button = (handleFunction, value) => {
    return (
      <Button
        className="btn-center mb-3"
        variant="danger"
        onClick={handleFunction}
        type="submit"
        disabled={isLoading}
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
      <AuthAnimation />
      <Form onSubmit={handleSubmit}>
        <div className="form p-3">
          <h3 className="mb-3"> Forgot Password</h3>
          {!isOtpVerified ? (
            <>
              <div className="input-fields">
                {isLoading && (
                  <div className="loading">
                    <HashLoader color={LOADING_COLOR} />
                  </div>
                )}
                {!inputFields.type ? (
                  <p className="not-found">Select correct type</p>
                ) : (
                  ""
                )}
                <Form.Select
                  name="type"
                  onChange={handleChange}
                  disabled={isOtpSent || isLoading}
                  required
                >
                  <option>Select one</option>
                  <option value="student">Student</option>
                  <option value="instructor">Instructor</option>
                </Form.Select>
                {!inputFields.email ? (
                  <p className="not-found">Enter email</p>
                ) : (
                  ""
                )}
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
                    disabled={isOtpSent || isLoading}
                    required
                  />
                </FloatingLabel>
                {!inputFields.otp ? (
                  <p className="not-found">Enter correct OTP</p>
                ) : (
                  ""
                )}
                <FloatingLabel className="mb-3" label="OTP">
                  <Form.Control
                    type="password"
                    name="otp"
                    onChange={handleChange}
                    placeholder="otp"
                    disabled={!isOtpSent || isLoading}
                    required
                  />
                </FloatingLabel>
              </div>
              {isOtpSent
                ? button(verifyOtp, "Verify")
                : button(handleOtp, "Send OTP")}
            </>
          ) : (
            <>
              <div className="input-fields">
                {isLoading && (
                  <div className="loading">
                    <HashLoader color={LOADING_COLOR} />
                  </div>
                )}
                <FloatingLabel className="mb-3" label="Password">
                  <Form.Control
                    type="password"
                    name="password"
                    onChange={handleChange}
                    placeholder="Password"
                    disabled={isLoading}
                    required
                  />
                </FloatingLabel>
                {!inputFields.password ? (
                  <p className="not-found mb-1">Password does not match</p>
                ) : (
                  ""
                )}
                <FloatingLabel className="mb-3" label="Confirm Password">
                  <Form.Control
                    type="password"
                    name="confirmPassword"
                    onChange={handleChange}
                    placeholder="Confirm password"
                    disabled={isLoading}
                    required
                  />
                </FloatingLabel>
              </div>
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
