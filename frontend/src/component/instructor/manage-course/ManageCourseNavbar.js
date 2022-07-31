import React from "react";
import { CaretLeft } from "react-bootstrap-icons";
import { Link } from "react-router-dom";

import "../../../styles/Navigationbar.css";

export default function ManageCourseNavbar({ CourseName, CourseDuration }) {
  return (
    <div className="nav-bar nav-bar-dark">
      <Link to="../instructor">
        <CaretLeft size={20} color={"#fff"} />
        <span className="b2c">Back to course</span>
      </Link>
    </div>
  );
}

//
