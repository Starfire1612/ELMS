import React from "react";
import { CaretLeft } from "react-bootstrap-icons";
import { Link } from "react-router-dom";

import "../../../styles/manage-course/ManageCourseNavbar.css";

export default function ManageCourseNavbar({ CourseName, CourseDuration }) {
  return (
    <div className="nav-bar">
      <Link to="../instructor">
        <CaretLeft size={20} color={"#fff"} />
        <span className="b2c">Back to course</span>
      </Link>
      <p className="ms-3 name">{CourseName}</p>
      <p className="ms-3">{CourseDuration + "min of video content added."}</p>
    </div>
  );
}

//
