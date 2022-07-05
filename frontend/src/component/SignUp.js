import React, { useState } from 'react';
import { Button, FloatingLabel, Form } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { checkEmailExists} from 'check-email-exists';
import "../styles/Register.css";

function SignUp() {

  const [user, setUser]=useState({});
  const [passwordMatch,setPasswordMatch]=useState(false)

  const handleChange=(event)=>{
    setUser(prevState=>({
      ...prevState,
      [event.target.name]:event.target.value
    }))
  }
  const handleConfirmPassword=(event)=>{
    if(event.target.value===user.password)
      setPasswordMatch(true);
    else setPasswordMatch(false);
  }
  const handleSubmit=async(event)=>{
    event.preventDefault();
    // await checkEmailExists(user.email).then(res=>console.log(res.data)).catch(err=>console.log(err));
  }

  return (
    <div className="contain" >
        <Form onSubmit={handleSubmit}>
          <div className="form p-3">
            <h3 className="mb-3"> Sign-Up</h3>
          
            <Form.Select name="type" onChange={handleChange} aria-label="Default select example">
              <option>Sign-up as</option>
              <option value="student">Student</option>
              <option value="instructor">Instructor</option>
            </Form.Select>
            <FloatingLabel
              controlId="floatingInput"
              label="Name"
              className="mb-3 mt-3"
            >
              <Form.Control name="name" onChange={handleChange} type="text" placeholder="name" />
            </FloatingLabel>
            <FloatingLabel
              controlId="floatingInput"
              label="Email address"
              className="mb-3 mt-3"
            >
              <Form.Control name="email" onChange={handleChange} type="email" placeholder="name@example.com" />
            </FloatingLabel>

            <FloatingLabel className='mb-3' controlId="floatingPassword" label="Password">
              <Form.Control name="password" onChange={handleChange} type="password" placeholder="Password" />
            </FloatingLabel>
            {/* <p>{passwordMatch?"Password does not matches</p>": ""}</p> */}
            {!passwordMatch ? <p>Password does not matches</p>:""}

            <FloatingLabel className='mb-3' controlId="floatingPassword" label="ConfirmPassword">
              <Form.Control name="confirmPassword" onChange={handleConfirmPassword} type="password" placeholder="Password" />
            </FloatingLabel>
 
            <Link to="/forgot-password">
              <div className="fs-6 mb-3 forgot-password">
                Forgot Password
              </div>
            </Link>
        
            <Button className="signup-btn" variant="primary" type="submit">
              Sign Up
            </Button>
          </div>
        </Form>
    </div>
  )
}

export default SignUp;