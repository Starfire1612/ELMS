import React from "react";
import { Outlet } from "react-router-dom";
import ProfileNavBar from "./ProfileNavBar.js";
import "../../styles/Profile.css"

export default function Profile() {
  return (
    <div className="profile-container">
      <div className="profile-navbar">
        <ProfileNavBar />
      </div>
      <div className="profile-content-section">
        <Outlet />
      </div>
    </div>
  );
}
