import React from "react";
import { Outlet } from "react-router-dom";
import InstructorNavbar from "./InstructorNavbar";

function InstructorLanding({ handleLogout, userData }) {
  return (
    <div>
      <InstructorNavbar handleLogout={handleLogout} userData={userData} />
      <Outlet />
    </div>
  );
}

export default InstructorLanding;
