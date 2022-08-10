import React, { useState } from "react";
import { Button, FloatingLabel, Form } from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import "../../styles/Register.css";
import bcryptjs from "bcryptjs";
import { ClipLoader } from "react-spinners";
import { LOADING_COLOR } from "../../utils/constants";
import { postNewPassword, sendForgotPasswordMail } from "./auth-utils.js";

function ForgotPassword() {
  const navigate = useNavigate();
  const [user, setUser] = useState({});
  const [isOtpSent, setIsOtpSent] = useState(false);
  const [isOtpVerified, setIsOtpVerified] = useState(false);

  // values={-1,0,1}, 1 for successful password change, -1 for something went wrong and 0 is by default
  const [passwordChanged, setPasswordChanged] = useState(0);
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
    // set isLoading to false
    setIsLoading(false);
    console.log(res);
  };

  const handleOtp = async () => {
    // if (!checkEmail || !typeMatch) {
    //   return;
    // }
    setIsLoading(true);
    const encryptedOtp = await sendForgotPasswordMail(user.email, user.type);
    console.log(encryptedOtp);
    if (!encryptedOtp) {
      setIsLoading(false);
      setIsOtpSent(false);
      return;
    }
    setUser((prevState) => ({
      ...prevState,
      encryptedOtp,
    }));
    setIsOtpSent(true);
    setIsLoading(false);
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
    console.log("Inside handle change password", requestBody);
    const response = await postNewPassword(requestBody);
    if (response === 200) {
      setPasswordChanged(1);
      setIsLoading(true);
      setTimeout(() => {
        navigate("/sign-in");
      }, 3000);
    } else {
      setPasswordChanged(-1);
    }
    setIsLoading(false);
  };

  const button = (handleFunction, value) => {
    return (
      <Button
        className="btn-center mb-3 type-1"
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
      <Form onSubmit={handleSubmit}>
        <div className="form p-3">
          <h3 className="mb-3"> Forgot Password</h3>
          {!isOtpVerified ? (
            <>
              <div className="input-fields">
                {isLoading && (
                  <div className="loading">
                    <ClipLoader color={LOADING_COLOR} />
                  </div>
                )}
                {!inputFields.type && (
                  <p className="not-found mb-0">Select correct type</p>
                )}
                <Form.Select
                  name="type"
                  onChange={handleChange}
                  disabled={isOtpSent || isLoading}
                  required
                >
                  <option value="">Select one</option>
                  <option value="student">Student</option>
                  <option value="instructor">Instructor</option>
                </Form.Select>
                {!inputFields.email && (
                  <p className="not-found mb-0">Enter email</p>
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
                {!inputFields.otp && (
                  <p className="not-found mb-0">Enter correct OTP</p>
                )}
                <FloatingLabel className="mb-3" label="OTP">
                  <Form.Control
                    type="password"
                    name="otp"
                    onChange={handleChange}
                    placeholder="OTP"
                    disabled={!isOtpSent || isLoading}
                    required
                  />
                </FloatingLabel>
              </div>
              {isOtpSent
                ? button(verifyOtp, "Verify")
                : button(handleOtp, "Send OTP")}
            </>
          ) : passwordChanged !== 1 ? (
            <>
              {passwordChanged === -1 && (
                <p className="text-center text-danger">
                  Something went wrong, Try again!
                </p>
              )}
              <div className="input-fields">
                {isLoading && (
                  <div className="loading">
                    <ClipLoader color={LOADING_COLOR} />
                  </div>
                )}
                <FloatingLabel className="mb-3" label="Password">
                  <Form.Control
                    type="password"
                    name="password"
                    onChange={handleChange}
                    placeholder="Password"
                    disabled={isLoading || passwordChanged}
                    required
                  />
                </FloatingLabel>
                {!inputFields.password && (
                  <p className="not-found mb-1">Password does not match</p>
                )}
                <FloatingLabel className="mb-3" label="Confirm Password">
                  <Form.Control
                    type="password"
                    name="confirmPassword"
                    onChange={handleChange}
                    placeholder="Confirm password"
                    disabled={isLoading || passwordChanged}
                    required
                  />
                </FloatingLabel>
              </div>
              {button(handleChangePassword, "Confirm")}
            </>
          ) : (
            <div className="password-changed border border-secondary text-center mb-2 mx-auto">
              <p className="font-monospace text-success mb-0">
                Password Changed Successfully
              </p>
            </div>
          )}

          <div className="fs-6 mb-3 forgot-password text-center ">
            <Link className="text-dark" to="/">
              Back To Login
            </Link>
          </div>
        </div>
      </Form>
    </div>
  );
}

export default ForgotPassword;
