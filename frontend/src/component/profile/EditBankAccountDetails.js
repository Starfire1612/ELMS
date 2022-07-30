import React, { useState } from "react";
import HeaderSection from "./HeaderSection.js";
import { Form, Button } from "react-bootstrap";

import { postProfileDetails } from "../profile/profile-utils.js";

export default function EditBankAccountDetails({ userData }) {
  const userType = localStorage.getItem("userType");

  const [accountDetails, setAccountDetails] = useState({});
  const handleChange = (event) => {
    setAccountDetails((previousState) => ({
      ...previousState,
      [event.target.name]: event.target.value,
    }));
  };

  const updateInstructorAccountDetails = async () => {
    if(!accountDetails.ifscCode && !accountDetails.accountNumber) return;
    userData.accountNumber =parseInt(accountDetails.accountNumber);
    userData.bankIfscCode = accountDetails.ifscCode;
    console.log(userData)
    await postProfileDetails(userType, userData);
  };

  return (
    <div className="edit-profile-container">
      <HeaderSection
        title="Bank Details"
        subtitle="Add your bank account details here."
      />
      <div className="content-section-container left-content">
        <p className="input-label">Bank Ifsc Code:</p>
        <Form.Control
          className="input-style"
          placeholder="Enter Ifsc Code"
          regex="^[A_Z]{4}[0-9]{7}$"
          name="ifscCode"
          defaultValue={userData.bankIfscCode}
          onChange={handleChange}
        />
        <p className="input-label mt-3">Account Number:</p>
        <Form.Control
          className="input-style"
          placeholder="Enter Account Number"
          regex="^[0-9]{5}$"
          name="accountNumber"
          defaultValue={userData.accountNumber}
          onChange={handleChange}
        />
      </div>
      <div className="content-section-container">
        <Button
          className="type-1 mx-auto d-block"
          onClick={updateInstructorAccountDetails}
        >
          Update Account Details
        </Button>
      </div>
    </div>
  );
}
