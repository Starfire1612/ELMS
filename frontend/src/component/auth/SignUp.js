import React, { useState } from "react";
import { Button, FloatingLabel, Form } from "react-bootstrap";
import "../../styles/Register.css";
import { LOADING_COLOR } from "../../utils/constants.js";
import { ClipLoader } from "react-spinners";
import { Link } from "react-router-dom";
import { getEmailVerificationMail, postRegisteredUser } from "./auth-utils";
import bcryptjs from "bcryptjs";
import { useNavigate } from "react-router-dom";
import toast, { Toaster } from "react-hot-toast";

function SignUp() {
  const navigate = useNavigate();
  const [user, setUser] = useState({});
  const [passwordMatch, setPasswordMatch] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [isOtpSent, setIsOtpSent] = useState(false);
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
  };

  //send and fetch OTP from server
  const getOtp = async () => {
    const encryptedOtp = await getEmailVerificationMail(user.email, user.type);
    console.log(encryptedOtp);
    if (!encryptedOtp || encryptedOtp === 400) {
      setIsLoading(false);
      setIsOtpSent(false);
      toast.error("User already exists!");
      return;
    }
    setUser((prevState) => ({
      ...prevState,
      encryptedOtp,
    }));
    setIsOtpSent(true);
  };

  //verify if user has entered correct OTP
  const verifyOtp = async () => {
    if (!user?.otp) {
      return;
    }
    setIsLoading(true);
    await bcryptjs.compare(user.otp, user.encryptedOtp, (err, same) => {
      if (same) {
        setIsOtpVerified(true);
        // setMatch(true);
        setUser((prevUser) => ({
          name: prevUser.name,
          email: prevUser.email,
          password: prevUser.password,
          type: prevUser.type,
        }));
      }
    });
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setIsLoading(true);
    if (!isOtpSent) {
      getOtp();
    } else {
      verifyOtp();
      // await verifyOtp(user, setUser, setIsOtpVerified);
    }
    if (isOtpVerified) {
      const userData = {
        username: user.name,
        useremail: user.email,
        password: user.password,
      };
      const userType = user.type;
      const status = await postRegisteredUser(userType, userData);
      console.log(status);
      if (status === 201) {
        toast.success("Registration successful", { duration: 2000 });
        setIsLoading(true);
        setTimeout(() => {
          navigate("/sign-in");
        }, 2000);
      } else if (status === 400) {
        toast.error("User already exists!");
      } else {
        toast.error("Something went wrong");
      }
    }
    //set isLoading to false
    setIsLoading(false);
  };

  return (
    <div className="contain">
      <Toaster />
      <Form onSubmit={handleSubmit}>
        <div className="form p-3">
          <h3 className="mb-3 "> Sign-Up</h3>
          <div className="input-fields">
            {isLoading && (
              <div className="loading">
                <ClipLoader color={LOADING_COLOR} />
              </div>
            )}
            <Form.Select
              name="type"
              onChange={handleChange}
              required
              disabled={isLoading || isOtpSent}
            >
              <option value="">Sign-up as</option>
              <option value="student">Student</option>
              <option value="instructor">Instructor</option>
            </Form.Select>
            <FloatingLabel label="Name" className="mb-3 mt-3">
              <Form.Control
                name="name"
                onChange={handleChange}
                type="text"
                placeholder="name"
                disabled={isLoading || isOtpSent}
                required
              />
            </FloatingLabel>
            <FloatingLabel label="Email address" className="mb-3 mt-3">
              <Form.Control
                name="email"
                onChange={handleChange}
                type="email"
                placeholder="name@example.com"
                disabled={isLoading || isOtpSent}
                required
              />
            </FloatingLabel>

            <FloatingLabel className="mb-3" label="Password">
              <Form.Control
                name="password"
                onChange={handleChange}
                type="password"
                placeholder="Password"
                disabled={isLoading || isOtpSent}
                required
              />
            </FloatingLabel>
            {user?.password && !passwordMatch && (
              <p className="text-center does-not-match mb-0">
                Password does not matches
              </p>
            )}

            <FloatingLabel className="mb-3" label="Confirm Password">
              <Form.Control
                name="confirmPassword"
                onChange={handleConfirmPassword}
                type="password"
                placeholder="Confirm Password"
                disabled={isLoading || isOtpSent}
                required
              />
            </FloatingLabel>
            {isOtpSent && (
              <FloatingLabel className="mb-3" label="otp">
                <Form.Control
                  name="otp"
                  onChange={handleChange}
                  type="password"
                  placeholder="Enter OTP"
                  disabled={isLoading}
                />
              </FloatingLabel>
            )}
          </div>
          <div className="fs-6 mb-3 forgot-password text-dark text-center ">
            <Link className="text-dark" to="/">
              Back To Login
            </Link>
          </div>
          <Button
            className="btn-center type-1"
            type="submit"
            disabled={isLoading}
          >
            {isOtpSent ? "Proceed" : "Sign Up"}
          </Button>
        </div>
      </Form>
    </div>
  );
}

export default SignUp;
