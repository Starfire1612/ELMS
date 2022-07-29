import React, { useState } from "react";
import HeaderSection from "./HeaderSection.js";
import { Form, Button } from "react-bootstrap";

import { postProfileDetails } from "../profile/profile-utils.js";

export default function EditBankAccountDetails({ userData2 }) {
  const userType = "instructor"//localStorage.getItem("userType");

  const [accountDetails, setAccountDetails] = useState({});
  const handleChange = (event) => {
    setAccountDetails((previousState) => ({
      ...previousState,
      [event.target.name]: event.target.value,
    }));
  };

  const updateInstructorAccountDetails = async () => {
    userData2.accountNumber =parseInt(accountDetails.accountNumber);
    userData2.bankIfscCode = accountDetails.ifscCode;
    console.log(userData2)
    await postProfileDetails(userType, userData2);
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
          name="ifscCode"
          onChange={handleChange}
        />
        <p className="input-label mt-3">Account Number:</p>
        <Form.Control
          className="input-style"
          placeholder="Enter Account Number"
          name="accountNumber"
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
