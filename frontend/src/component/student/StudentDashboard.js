import React, { useEffect, useState } from "react";
import StudentNavigationbar from "./StudentNavigationbar";
import "../../styles/StudentDashboard.css";
import { Outlet } from "react-router-dom";

function StudentDashboard({ handleLogout, userData }) {
  return (
    <div>
      <StudentNavigationbar
        handleLogout={handleLogout}
        userName={userData.studentName}
      />
      <h2 className="ms-5 mt-3">Hi  {userData.studentName} 👋</h2>
      <Outlet />
      {/* place for footer here */}
    </div>
  );
}

export default StudentDashboard;
