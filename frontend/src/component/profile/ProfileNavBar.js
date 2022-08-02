import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";

export default function ProfileNavBar({ userData }) {
  const userType = localStorage.getItem("userType");
  const [profilePicUrl, setProfilePicUrl] = useState(
    "https://img-c.udemycdn.com/user/200_H/anonymous_3.png"
  );

  const [userImage, setuserImage] = useState("second");
  useEffect(() => {
    setProfilePicUrl("data:image/png;base64," + userData[`${userType}Image`]);
  }, [userData]);

  return (
    <div className="navbar-container">
      <div className="navbar-profile-pic-container">
        <img
          src={profilePicUrl}
          alt="account.jpeg"
          className="account-circle-avatar"
        />
      </div>
      <div className="my-2">
        <Link to="edit-profile">
          <div className="profile-navbar-link">Profile</div>
        </Link>
        <Link to="edit-profile-pic">
          <div className="profile-navbar-link">Photo</div>
        </Link>
        <Link to="edit-account-security">
          <div className="profile-navbar-link">Account Security</div>
        </Link>
        {localStorage.getItem("userType") === "instructor" && (
          <Link to="edit-bank-account-details">
            <div className="profile-navbar-link">Bank Account Details</div>
          </Link>
        )}
      </div>
    </div>
  );
}
