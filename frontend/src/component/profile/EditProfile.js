import React, { useEffect, useState } from "react";
import HeaderSection from "./HeaderSection.js";
import { Form, Button } from "react-bootstrap";
import { postProfileDetails } from "../profile/profile-utils.js";
export default function EditProfile({ userData }) {
  const [fullName, setFullName] = useState("");
  const userType = localStorage.getItem("userType")
  

  const handleChange = (event) => {
    setFullName(event.target.value);
  };
  const updateProfileDetails = () => {
    //axios call
    if (userType === "student")
      userData.studentName=fullName;
      else
      userData.instructorName = fullName;
    postProfileDetails(userType, userData);
  };
  return (
    <div className="edit-profile-container">
      <HeaderSection
        title="Public Profile"
        subtitle="Update your profile information "
      />
      <div className="content-section-container left-content">
        <p className="input-label">Basics:</p>
        <Form.Control
          className="input-style"
          placeholder="Full Name"
          defaultValue={fullName}
          onChange={handleChange}
          name="name"
        />
      </div>
      <div className="content-section-container">
        <Button
          className="type-1 mx-auto d-block"
          onClick={updateProfileDetails}
        >
          Save
        </Button>
      </div>
    </div>
  );
}
