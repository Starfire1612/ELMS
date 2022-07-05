import axios from 'axios';
import React, { useState } from 'react'
import { Button, FloatingLabel, Form} from 'react-bootstrap';
import { Link } from 'react-router-dom';
import "../styles/Register.css";

function SignIn() {
  const [user,setUser]=useState({});

  const handleChange=(event)=>{
    setUser(prevState=>({
      ...prevState,
      [event.target.name]:event.target.value
    }))
  }

  const handleSubmit=async (event)=>{
    event.preventDefault();
    const config={
      url:"http://localhost:8080/authenticate",
      auth:{
        username:user.email,
        password:user.password
      }
    }
    const _userToken= await axios(config)
            .then(res=>res.data.token)
            .catch(err=>console.log(err));
    console.log(_userToken);
    localStorage.setItem("userToken", _userToken);
    localStorage.setItem("userType",user.type);
  }

  return (
    <div className="contain" >
        <Form onSubmit={handleSubmit}>
          <div className="form p-3">
            <h3 className="mb-3"> Sign-In</h3>
            {<p className="not-found">Data not found😎</p>}
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
              <Form.Control type="text" name="email" placeholder="name@example.com" onChange={handleChange}/>
            </FloatingLabel>

            <FloatingLabel className='mb-3' controlId="floatingPassword" label="Password">
              <Form.Control type="password" name="password" onChange={handleChange} placeholder="Password" />
            </FloatingLabel>
 
            <Link to="/forgot-password">
              <div className="fs-6 mb-3 forgot-password">
                Forgot Password
              </div>
            </Link>
        
            <Button variant="primary" type="submit">
              Sign In
            </Button>
            <Link to="/sign-up">
              <Button className="signup-button" variant="primary">
                Sign Up
              </Button>
            </Link>
          </div>
        </Form>
    </div>
  )
}

export default SignIn;