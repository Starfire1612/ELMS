import React, { useState } from "react";
import HeaderSection from "./HeaderSection.js";
import { Form, Button } from "react-bootstrap";

import { postProfileDetails } from "../profile/profile-utils.js";
import  bcryptjs  from 'bcryptjs';

export default function EditAccountSecurity({ userData }) {
  // Requiring module
  // Encryption of the string password

  const userType = localStorage.getItem("userType");

  const [passwords, setPasswords] = useState({});
  const handleChange = (event) => {
    setPasswords((previousState) => ({
      ...previousState,
      [event.target.name]: event.target.value,
    }));
  };

  const updateNewPassword = async () => {
    if (passwords.newPassword === passwords.confirmPassword) {
      userData.studentPassword = await bcryptjs.hashSync(passwords.newPassword, 10);
      console.log(userData.studentPassword)
      await postProfileDetails(userType, userData);
    }
  };

  return (
    <div className="edit-profile-container">
      <HeaderSection
        title="Account"
        subtitle="Edit your account settings and change your password here."
      />
      <div className="content-section-container left-content">
        <p className="input-label">Email:</p>
        <Form.Control
          className="input-style"
          value={`${userData.studentEmail}`}
          disabled
        />
      </div>
      <div className="content-section-container left-content">
        <Form.Control
          className="input-style"
          placeholder="New Password"
          name="newPassword"
          type="password"
          onChange={handleChange}
        />
        <Form.Control
          className="input-style mt-4"
          placeholder="Confirm Password"
          name="confirmPassword"
          type="password"
          onChange={handleChange}
        />
      </div>
      <div className="content-section-container">
        <Button className="type-1 mx-auto d-block" onClick={updateNewPassword}>
          Change Password
        </Button>
      </div>
    </div>
  );
}
