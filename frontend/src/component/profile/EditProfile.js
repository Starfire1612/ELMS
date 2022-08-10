import React, { useEffect, useState } from "react";
import HeaderSection from "./HeaderSection.js";
import { Form, Button } from "react-bootstrap";
import { postProfileDetails } from "../profile/profile-utils.js";
import { ClipLoader } from "react-spinners";

export default function EditProfile({ userData, reFetchUser }) {
  const userType = localStorage.getItem("userType");
  const [fullName, setFullName] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  //0-> default, 1-> password successfullt changed -1->error occured
  const [progress, setProgress] = useState(0);

  const handleChange = (event) => {
    setFullName(event.target.value);
  };
  const updateProfileDetails = async (event) => {
    event.preventDefault();
    userData[`${userType}Name`] = fullName;
    setIsLoading(true);
    const response = await postProfileDetails(userType, userData);
    console.log(response);
    if (response === 200) {
      setProgress(1);
      setTimeout(() => {
        setProgress(0);
      }, 4000);
      reFetchUser();
    } else {
      setProgress(-1);
      setTimeout(() => {
        setProgress(0);
      }, 4000);
    }
    setIsLoading(false);
  };
  return (
    <div className="edit-profile-container">
      <HeaderSection
        title="Public Profile"
        subtitle="Update your profile information "
      />
      <Form id="profile-name" onSubmit={updateProfileDetails}>
        <div className="content-section-container left-content">
          {progress === -1 && (
            <p className="text-center font-monospace does-not-match">
              Something went wrong! try again.
            </p>
          )}
          <p className="input-label">Basics:</p>
          <Form.Control
            className="input-style"
            placeholder="Full Name"
            defaultValue={userData[`${userType}Name`]}
            onChange={handleChange}
            disabled={isLoading}
            name="name"
            required
          />
        </div>
        <div type="submit" className="content-section-container">
          {progress === 1 && (
            <p className="text-center font-monospace does-not-match text-success">
              Name successfully Updated.
            </p>
          )}
          <Button
            type="submit"
            disabled={isLoading}
            className="type-1 mx-auto d-block"
          >
            Save
            {isLoading && (
              <ClipLoader className="ms-2" color="white" size={15} />
            )}
          </Button>
        </div>
      </Form>
    </div>
  );
}
