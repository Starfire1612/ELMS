import React, { useState } from "react";
import HeaderSection from "./HeaderSection.js";
import { Form, Button } from "react-bootstrap";
import { postProfileDetails } from "../profile/profile-utils.js";
import { ClipLoader } from "react-spinners";

export default function EditBankAccountDetails({ userData }) {
  const userType = localStorage.getItem("userType");
  const [isLoading, setIsLoading] = useState(false);
  const [isIfscValid, setIsIfscValid] = useState(true);
  const [isAccValid, setIsAccValid] = useState(true);
  //0-> default, 1-> password successfullt changed -1->error occured
  const [progress, setProgress] = useState(0);

  const [accountDetails, setAccountDetails] = useState({});
  const handleChange = (event) => {
    setAccountDetails((previousState) => ({
      ...previousState,
      [event.target.name]: event.target.value,
    }));
  };

  const updateInstructorAccountDetails = async (event) => {
    event.preventDefault();
    const regexIfsc = /^[A-Z]{4}[0-9]{4,7}$/;
    const regexAccountNumber = /^[0-9]{5,9}$/;
    if (!accountDetails.ifscCode && !accountDetails.accountNumber) return;
    if (!regexIfsc.test(accountDetails.ifscCode)) {
      console.log("Not valid ifsc");
      setIsIfscValid(false);
      return;
    } else {
      setIsIfscValid(true);
    }

    if (!regexAccountNumber.test(accountDetails.accountNumber)) {
      setIsAccValid(false);
      console.log("Not valid AN");
      return;
    } else {
      setIsAccValid(true);
    }

    userData.accountNumber = parseInt(accountDetails.accountNumber);
    userData.bankIfscCode = accountDetails.ifscCode;
    console.log(userData);
    setIsLoading(true);
    const response = await postProfileDetails(userType, userData);
    console.log(response);
    if (response === 200) {
      setProgress(1);
      setTimeout(() => {
        setProgress(0);
      }, 4000);
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
        title="Bank Details"
        subtitle="Add your bank account details here."
      />
      <Form onSubmit={updateInstructorAccountDetails}>
        <div className="content-section-container left-content">
          {progress === -1 && (
            <p className="text-center font-monospace does-not-match">
              Something went wrong! try again.
            </p>
          )}

          <p className="input-label mb-0">Bank Ifsc Code:</p>
          <Form.Control
            className="input-style"
            placeholder="Enter Ifsc Code"
            name="ifscCode"
            defaultValue={userData?.bankIfscCode}
            onChange={handleChange}
            disabled={isLoading}
            required
          />
          {!isIfscValid && (
            <p className="text-center font-monospace does-not-match">
              Enter valid IFSC code.
            </p>
          )}

          <p className="input-label mt-3 mb-0">Account Number:</p>
          <Form.Control
            className="input-style"
            placeholder="Enter Account Number"
            name="accountNumber"
            defaultValue={userData?.accountNumber}
            onChange={handleChange}
            disabled={isLoading}
            required
          />
          {!isAccValid && (
            <p className="text-center font-monospace does-not-match">
              Enter valid account number.
            </p>
          )}
        </div>
        <div className="content-section-container">
          {progress === 1 && (
            <p className="text-center font-monospace does-not-match text-success">
              Bank account details successfully updated.
            </p>
          )}
          <Button
            disabled={isLoading}
            type="submit"
            className="type-1 mx-auto d-block"
          >
            Update Account Details
            {isLoading && (
              <ClipLoader className="ms-2" color="white" size={15} />
            )}
          </Button>
        </div>
      </Form>
    </div>
  );
}
