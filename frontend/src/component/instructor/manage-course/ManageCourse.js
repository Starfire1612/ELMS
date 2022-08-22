import React, { useEffect, useState } from "react";
import { Outlet, useParams, useNavigate } from "react-router-dom";
import Button from "react-bootstrap/Button";
// import "../../../styles/manage-course/ManageCourse.css";
import "../../../styles/Navigationbar.css";
import ManageCourseNavbar from "./ManageCourseNavbar";
import SideNavBar from "./SideNavBar";
import DeleteCourse from "./DeleteCourse";
import { deleteCourse, getCourseDetails} from "../instructor-utils.js";

export default function ManageCourse({ userData }) {
  const params = useParams();
  const navigate = useNavigate();

  const courseId = params.courseId;
  const instructorId=userData.instructorId;

  const [course, setCourse] = useState({
    courseName: "Course name",
    courseDuration: "0",
  });
  const [shouldStateChange, setShouldStateChange] = useState(true);
  const [showDelete, setShowDelete] = useState(false);

  useEffect(() => {
    fetchCourseDuration();
  }, []);
  useEffect(() => {
    fetchCourseDuration();
  }, [shouldStateChange]);

  const fetchCourseDuration = async () => {
    await getCourseDetails()
  };
  const handleDeleteCourse = async () => {
    console.log("deleted",instructorId,courseId);
    //delete the course using courseId
    setShouldStateChange(true);
    await deleteCourse(instructorId,courseId);
    navigate("/instructor");
  };
  const manageDeleteCourse = () => {
    setShowDelete(true);
  };

  return (
    <div>
      <ManageCourseNavbar
        CourseName={course.courseName}
        CourseDuration={course.courseDuration}
      />
      <div className="manage-course-container">
        <div className="side-navbar">
          <SideNavBar
            manageDeleteCourse={manageDeleteCourse}
            courseId={courseId}
          />
        </div>
        {showDelete && (
          <DeleteCourse
            showDelete={showDelete}
            setShowDelete={setShowDelete}
            handleDeleteCourse={handleDeleteCourse}
          />
        )}
        <div className="manage-course-content">
          <Outlet />
        </div>
      </div>
    </div>
  );
}
