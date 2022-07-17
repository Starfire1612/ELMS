import React, { useEffect, useState } from "react";
import { Outlet, useParams } from "react-router-dom";
import "../../../styles/manage-course/ManageCourse.css";
import ManageCourseNavbar from "./ManageCourseNavbar";
import SideNavBar from "./SideNavBar";

export default function ManageCourse() {
  const courseId = useParams();
  const [course, setCourse] = useState({
    courseName: "Course name",
    courseDuration: "0",
  });
  const [shouldStateChange, setShouldStateChange] = useState("0");

  const fetchCourseDuration = async () => {
    //fetchcourse name and course duration, and set details in course state
  };
  useEffect(() => {
    fetchCourseDuration();
  }, []);
  useEffect(() => {
    fetchCourseDuration();
  }, [shouldStateChange]);

  return (
    <div>
      <ManageCourseNavbar
        CourseName={course.courseName}
        CourseDuration={course.courseDuration}
      />
      <div className="manage-course-container">
        <div className="side-navbar">
          <SideNavBar />
        </div>
        <div className="content">
          <Outlet />
        </div>
      </div>
    </div>
  );
}
