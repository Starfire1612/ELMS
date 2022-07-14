import React, { useState } from "react";
import { Button, FloatingLabel, Form } from "react-bootstrap";
import { Link } from "react-router-dom";
import axios from "axios";
import "../../styles/Register.css";
import { BASE_URL, LOADING_COLOR } from "../../utils/constants.js";
import AuthAnimation from "../Animations/AuthAnimation";
// import { fetchOtp } from "../../utils/http-requests";
import { requestOtp, verifyOtp } from "../../utils/util";
import { HashLoader } from "react-spinners";

function SignUp() {
  const [user, setUser] = useState({});
  const [passwordMatch, setPasswordMatch] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [isOtpSent, setIsOtpSent] = useState(true);
  const [isOtpVerified, setIsOtpVerified] = useState(false);

  const handleChange = (event) => {
    setUser((prevState) => ({
      ...prevState,
      [event.target.name]: event.target.value,
    }));
  };
  const handleConfirmPassword = (event) => {
    if (event.target.value === user.password) setPasswordMatch(true);
    else setPasswordMatch(false);
    console.log(user);
  };
  const handleSubmit = async (event) => {
    event.preventDefault();
    setIsLoading(true);
    if (!isOtpSent) {
      await requestOtp(setIsOtpSent, setUser, user);
    } else {
      await verifyOtp(user, setUser, setIsOtpVerified);
    }
    if (isOtpVerified) {
      const userData = {
        username: user.name,
        useremail: user.email,
        password: user.password,
      };
      const userType = user.type;
      const response = await axios.post(
        BASE_URL + `/register-user/type/${userType}`,
        userData
      );
      console.log(response.data);
    }
    //set isLoading to false
  };

  return (
    <div className="contain">
      <AuthAnimation />
      <Form onSubmit={handleSubmit}>
        <div className="form p-3">
          <h3 className="mb-3"> Sign-Up</h3>
          <div className="input-fields">
            {isLoading && (
              <div className="loading">
                <HashLoader color={LOADING_COLOR} />
              </div>
            )}
            <Form.Select
              name="type"
              onChange={handleChange}
              required
              disabled={isLoading || isOtpSent}
            >
              <option>Sign-up as</option>
              <option value="student">Student</option>
              <option value="instructor">Instructor</option>
            </Form.Select>
            <FloatingLabel
              controlId="floatingInput"
              label="Name"
              className="mb-3 mt-3"
            >
              <Form.Control
                name="name"
                onChange={handleChange}
                type="text"
                placeholder="name"
                disabled={isLoading || isOtpSent}
                required
              />
            </FloatingLabel>
            <FloatingLabel
              controlId="floatingInput"
              label="Email address"
              className="mb-3 mt-3"
            >
              <Form.Control
                name="email"
                onChange={handleChange}
                type="email"
                placeholder="name@example.com"
                disabled={isLoading || isOtpSent}
                required
              />
            </FloatingLabel>

            <FloatingLabel
              className="mb-3"
              controlId="floatingPassword"
              label="Password"
            >
              <Form.Control
                name="password"
                onChange={handleChange}
                type="password"
                placeholder="Password"
                disabled={isLoading || isOtpSent}
                required
              />
            </FloatingLabel>
            {user?.password && !passwordMatch ? (
              <p className="text-center does-not-match">
                Password does not matches
              </p>
            ) : (
              ""
            )}

            <FloatingLabel
              className="mb-3"
              controlId="floatingPassword"
              label="ConfirmPassword"
            >
              <Form.Control
                name="confirmPassword"
                onChange={handleConfirmPassword}
                type="password"
                placeholder="Password"
                disabled={isLoading || isOtpSent}
                required
              />
            </FloatingLabel>
            {isOtpSent ? (
              <FloatingLabel
                className="mb-3"
                controlId="floatingPassword"
                label="otp"
              >
                <Form.Control
                  name="otp"
                  onChange={handleChange}
                  type="password"
                  placeholder="Enter OTP"
                  disabled={isLoading}
                />
              </FloatingLabel>
            ) : (
              ""
            )}
          </div>
          <Button
            className="btn-center"
            variant="danger"
            type="submit"
            disabled={isLoading}
          >
            {isOtpSent ? "Verify" : "Sign Up"}
          </Button>
        </div>
      </Form>
    </div>
  );
}

export default SignUp;
