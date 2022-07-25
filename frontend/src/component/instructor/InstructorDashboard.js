import React from "react";
import { Link } from "react-router-dom";
import InstructorNavbar from "./InstructorNavbar";

function InstructorDashboard({ handleLogout, userData }) {
  return (
    <div>
      <InstructorNavbar handleLogout={handleLogout} userData={userData} />
      {/* <Link to="/instructor/add-course">
        <button>add course</button>
      </Link> */}
    </div>
  );
}

export default InstructorDashboard;
