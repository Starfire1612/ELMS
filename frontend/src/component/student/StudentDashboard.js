import React, { useEffect, useState } from "react";
import Courses from "../Courses";
import { dummyCourses } from "../dummydata/dummyCourses";
import StudentNavigationbar from "./StudentNavigationbar";
import "../../styles/StudentDashboard.css";
import DashboardCarousel from "./DashboardCarousel";

function StudentDashboard({ handleLogout, userData }) {
  const [courses, setCourses] = useState([]);

  //fetch all the courses
  const fetchAllCourses = async () => {
    //fetch the courses using axios and set state
    setCourses(dummyCourses);
  };
  useEffect(() => {
    fetchAllCourses();
  }, []);

  return (
    <div>
      <StudentNavigationbar
        handleLogout={handleLogout}
        userName={userData.studentName}
      />
      <div className="student-dashboard-body">
        <div className="carousel-container mb-5">
          <DashboardCarousel />
        </div>
        <div className="explore-dashboard text-light d-table">
          <p className="my-auto d-table-cell fs-3 fw-500 font-monospace">
            Explore...
          </p>
        </div>
        <div className="course-list">
          <Courses courses={courses} />
        </div>
      </div>
    </div>
  );
}

export default StudentDashboard;
