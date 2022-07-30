import React from "react";
import { Link, Outlet } from "react-router-dom";
import ProfileNavBar from "./ProfileNavBar.js";
import "../../styles/Profile.css";
import { CaretLeft } from "react-bootstrap-icons";

export default function Profile({ userData }) {
  return (
    ///
    <div>
      {/* navbar */}
      <div className="nav-bar nav-bar-dark mb-5">
        <Link to={localStorage.getItem("userType")==="student"?"../home":"../instructor"}>
          <CaretLeft size={20} color={"#fff"} />
          <span className="b2c">Back to Home</span>
        </Link>
      </div>
      <div className="profile-container">
        <div className="profile-navbar">
          <ProfileNavBar userData={userData} />
        </div>
        <div className="profile-content-section">
          <Outlet />
        </div>
      </div>
    </div>
  );
}
