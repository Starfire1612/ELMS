import React, { useState } from "react";
import { Button, FloatingLabel, Form } from "react-bootstrap";
import toast, { Toaster } from "react-hot-toast";
import { Link } from "react-router-dom";
import { ClipLoader } from "react-spinners";
import "../../styles/Register.css";
import { LOADING_COLOR } from "../../utils/constants";
import { postAuthenticatedUser } from "./auth-utils";

function SignIn({ handleLogin }) {
  const [user, setUser] = useState({});
  const [isLoading, setIsLoading] = useState(false);

  const handleChange = (event) => {
    setUser((prevState) => ({
      ...prevState,
      [event.target.name]: event.target.value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setIsLoading(true);
    const loadingToast = toast.loading("Signing in");
    const data = {
      useremail: user.email,
      password: user.password,
      type: user.type,
    };
    const response = await postAuthenticatedUser(data);
    toast.dismiss(loadingToast);
    if (response && response.status !== 200)
      toast.error("Something went wrong.");
    console.log(response);
    const _userToken = response.data.jwttoken;
    if (_userToken) {
      localStorage.setItem("userToken", _userToken);
      localStorage.setItem("userType", user.type);

      toast.success("Login successful", { duration: 2000 });
      setTimeout(() => {
        handleLogin();
      }, 2000);
      setIsLoading(false);
    }
  };

  return (
    <div className="contain">
      <Form onSubmit={handleSubmit}>
        <Toaster />
        <div className="form p-3">
          <h3 className="mb-3"> Sign-In</h3>
          {/* {<p className="not-found">Data not found😎</p>} */}
          <div className="input-fields">
            {isLoading && (
              <div className="loading">
                <ClipLoader color={LOADING_COLOR} />
              </div>
            )}
            <Form.Select
              name="type"
              onChange={handleChange}
              disabled={isLoading}
              required
            >
              <option value="">Select one</option>
              <option value="student">Student</option>
              <option value="instructor">Instructor</option>
            </Form.Select>
            <FloatingLabel label="Email address" className="mb-3 mt-3">
              <Form.Control
                type="email"
                name="email"
                placeholder="name@example.com"
                onChange={handleChange}
                disabled={isLoading}
                required
              />
            </FloatingLabel>

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
          </div>
          <div className="fs-6 mb-3 forgot-password text-center">
            <Link className="text-dark" to="/forgot-password">
              Forgot Password
            </Link>
          </div>

          <Link to="/sign-up">
            <Button className="signup-button type-1">Sign Up</Button>
          </Link>
          <Button className="type-1" type="submit" disabled={isLoading}>
            Sign In
          </Button>
        </div>
      </Form>
    </div>
  );
}

export default SignIn;
