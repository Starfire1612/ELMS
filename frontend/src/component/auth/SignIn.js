import axios from "axios";
import React, { useState } from "react";
import { Button, FloatingLabel, Form } from "react-bootstrap";
import { Link } from "react-router-dom";
import { HashLoader } from "react-spinners";
import "../../styles/Register.css";
import Animation from "./Animation";
import { LOADING_COLOR } from "../../utils/constants";

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
    const config = {
      url: `http://localhost:8080/authenticate`,
      method: "post",
      data: {
        useremail: user.email,
        password: user.password,
        type: user.type,
      },
    };
    const _userToken = await axios(config)
      .then((res) => res.data.jwttoken)
      .catch((err) => console.log(err));
    console.log(_userToken);

    if (_userToken) {
      localStorage.setItem("userToken", _userToken);
      localStorage.setItem("userType", user.type);
      console.log(localStorage.getItem("userToken"));
      handleLogin();
    }
  };

  return (
    <div className="contain">
      <Animation />
      <div className="anim"></div>
      <Form onSubmit={handleSubmit}>
        <div className="form p-3">
          <h3 className="mb-3"> Sign-In</h3>
          {<p className="not-found">Data not found😎</p>}
          <div className={"input-fields"}>
            {isLoading && (
              <div className="loading">
                <HashLoader color={LOADING_COLOR} />
              </div>
            )}
            <Form.Select
              name="type"
              onChange={handleChange}
              disabled={isLoading}
            >
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
                placeholder="name@example.com"
                onChange={handleChange}
                disabled={isLoading}
              />
            </FloatingLabel>

            <FloatingLabel
              className="mb-3"
              controlId="floatingPassword"
              label="Password"
            >
              <Form.Control
                type="password"
                name="password"
                onChange={handleChange}
                placeholder="Password"
                disabled={isLoading}
              />
            </FloatingLabel>
          </div>
          <Link to="/forgot-password">
            <div className="fs-6 mb-3 forgot-password">Forgot Password</div>
          </Link>

          <Button variant="danger" type="submit" disabled={isLoading}>
            Sign In
          </Button>
          <Link to="/sign-up">
            <Button className="signup-button" variant="danger">
              Sign Up
            </Button>
          </Link>
        </div>
      </Form>
    </div>
  );
}

export default SignIn;
