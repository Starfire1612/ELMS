import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";

export default function ProfileNavBar({userData}) {
  const image = localStorage.getItem("userImage");
  
  const [userImage, setuserImage] = useState("second");
  useEffect(() => {
    setuserImage(localStorage.getItem("userImage"));
    console.log("Image changed",userImage.toString())
  }, [localStorage.getItem("userImage")]);

  return (
    <div className="navbar-container">
      <div className="navbar-profile-pic-container">
        <img
          src={
            image === null
              ? "https://img-c.udemycdn.com/user/200_H/anonymous_3.png"
              : userImage.toString()
          }
          alt="account.jpeg"
          className="account-circle-avatar"
        />
      </div>
      <div className="profile-navbar-link">
        <Link to="edit-profile">Profile</Link>
      </div>
      <div className="profile-navbar-link">
        <Link to="edit-profile-pic">Photo</Link>
      </div>
      <div className="profile-navbar-link">
        <Link to="edit-account-security">Account Security</Link>
      </div>
      {localStorage.getItem("userType")==="instructor" &&
      <div className="profile-navbar-link">
        <Link to="edit-bank-account-details">Bank Account Details</Link>
      </div>
      }
    </div>
  );
}
