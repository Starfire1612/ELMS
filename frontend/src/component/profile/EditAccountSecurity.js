import React, { useState } from "react";
import HeaderSection from "./HeaderSection.js";
import { Form, Button } from "react-bootstrap";
import { postProfileDetails } from "../profile/profile-utils.js";
import bcryptjs from "bcryptjs";
import { ClipLoader } from "react-spinners";

export default function EditAccountSecurity({ userData }) {
  const userType = localStorage.getItem("userType");
  const [isLoading, setIsLoading] = useState(false);
  const [passwordMatch, setPasswordMatch] = useState(false);
  const [validLength, setIsValidLength] = useState(true);
  const [passwords, setPasswords] = useState({
    newPassword: "",
    confirmPassword: "",
  });
  //0-> default, 1-> password successfullt changed -1->error occured
  const [progress, setProgress] = useState(0);

  const handleConfirmPassword = (event) => {
    if (event.target.value === passwords.newPassword) setPasswordMatch(true);
    else setPasswordMatch(false);
  };
  const handleChange = (event) => {
    const value = event.target.value;
    setPasswords((previousState) => ({
      ...previousState,
      [event.target.name]: value,
    }));
    if (value === passwords.confirmPassword) setPasswordMatch(true);
    else setPasswordMatch(false);
    if (value.length >= 8) setIsValidLength(true);
    else setIsValidLength(false);
  };

  const updateNewPassword = async (event) => {
    event.preventDefault();
    if (passwordMatch && passwords.newPassword.length >= 8) {
      userData[`${userType}Password`] = await bcryptjs.hashSync(
        passwords.newPassword,
        10
      );
      setIsLoading(true);
      const response = await postProfileDetails(userType, userData);
      console.log(response);
      if (response === 201) {
        setProgress(1);
        setTimeout(() => {
          setProgress(0);
        }, 4000);
        document.getElementById("profile-account-security").reset();
      } else {
        setProgress(-1);
        setTimeout(() => {
          setProgress(0);
        }, 4000);
      }
      setIsLoading(false);
    }
  };

  return (
    <div className="edit-profile-container">
      <HeaderSection
        title="Account"
        subtitle="Edit your account settings and change your password here."
      />
      <Form id="profile-account-security" onSubmit={updateNewPassword}>
        <div className="content-section-container left-content">
          <p className="input-label">Email:</p>
          <Form.Control
            className="input-style"
            value={userData[`${userType}Email`]}
            disabled
          />
        </div>
        {progress === -1 && (
          <p className="text-center font-monospace does-not-match">
            Something went wrong! try again.
          </p>
        )}

        <div className="content-section-container left-content">
          {!validLength && (
            <p className="text-center does-not-match mb-0">
              Password must be atleast 8 characters long.
            </p>
          )}
          <Form.Control
            className="input-style"
            placeholder="New Password"
            name="newPassword"
            type="password"
            onChange={handleChange}
            disabled={isLoading}
            required
          />
          {passwords?.newPassword && !passwordMatch && (
            <p className="text-center does-not-match mb-0">
              Password does not matches
            </p>
          )}
          <Form.Control
            className="input-style mt-2"
            placeholder="Confirm Password"
            name="confirmPassword"
            type="password"
            onChange={handleConfirmPassword}
            disabled={isLoading}
            required
          />
        </div>

        <div className="content-section-container">
          {progress === 1 && (
            <p className="text-center font-monospace does-not-match text-success">
              Password successfully updated.
            </p>
          )}
          <Button
            type="submit"
            disabled={isLoading || !validLength || !passwordMatch}
            className="type-1 mx-auto d-block"
          >
            Change Password
            {isLoading && (
              <ClipLoader className="ms-2" color="white" size={15} />
            )}
          </Button>
        </div>
      </Form>
    </div>
  );
}
