import React, { useEffect, useState } from "react";
import { Outlet, useParams, useNavigate } from "react-router-dom";
import Button from "react-bootstrap/Button";
import "../../../styles/manage-course/ManageCourse.css";
import ManageCourseNavbar from "./ManageCourseNavbar";
import SideNavBar from "./SideNavBar";

export default function ManageCourse() {
  const params = useParams();
  const navigate = useNavigate();

  const courseId = params.courseId;

  const [course, setCourse] = useState({
    courseName: "Course name",
    courseDuration: "0",
  });
  const [shouldStateChange, setShouldStateChange] = useState(true);

  useEffect(() => {
    fetchCourseDuration();
  }, []);
  useEffect(() => {
    fetchCourseDuration();
  }, [shouldStateChange]);

  const fetchCourseDuration = async () => {
    //fetchcourse name and course duration, and set details in course state
  };
  const handleDeleteCourse = (courseId) => {
    console.log("deleted");
    //delete the course using courseId
    // navigate("/instructor");
  };

  return (
    <div>
      <ManageCourseNavbar
        CourseName={course.courseName}
        CourseDuration={course.courseDuration}
      />
      <div className="manage-course-container">
        <div className="side-navbar">
          <SideNavBar handleDeleteCourse={handleDeleteCourse} />
        </div>
        <div className="content">
          <Outlet />
        </div>
      </div>
    </div>
  );
}
