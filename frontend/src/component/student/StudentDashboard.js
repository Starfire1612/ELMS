import React, { useEffect, useState } from "react";
import StudentNavigationbar from "./StudentNavigationbar";
import "../../styles/StudentDashboard.css";
import { Outlet } from "react-router-dom";
import { ClipLoader } from "react-spinners";
import { LOADING_COLOR } from "../../utils/constants";

function StudentDashboard({ handleLogout, userData }) {
  return (
    <>
      {userData ? (
        <div>
          <StudentNavigationbar
            handleLogout={handleLogout}
            userName={userData.studentName}
          />
          <h2 className="ms-5 mt-3">Hi {userData.studentName} 👋</h2>
          <Outlet />
          {/* place for footer here */}
        </div>
      ) : (
        <div class="course-list">
          <ClipLoader color={LOADING_COLOR} size="50px" />
          <p className="font-monspace text-secondary">Please wait</p>
        </div>
      )}
    </>
  );
}

export default StudentDashboard;
