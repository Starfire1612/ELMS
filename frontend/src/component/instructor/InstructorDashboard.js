import React from "react";
import { Link } from "react-router-dom";
import InstructorNavbar from "./InstructorNavbar";
import "../../styles/InstructorDashboard.css";
import { Button, FormControl } from "react-bootstrap";
import { Search } from "react-bootstrap-icons";

function InstructorDashboard({ handleLogout, userData }) {
  return (
    <div>
      <InstructorNavbar handleLogout={handleLogout} userData={userData} />
      <div className="instructor-dashboard">
        <div className="instructor-dashboard-heading">
          <p className="fs-1 fw-500">Courses</p>
          <Button className="type-1 fw-bolder d-none new-course-button-1">
            <Link to="./add-course" className="text-white">
              New course
            </Link>
          </Button>
        </div>
        <div className="search-course-wrapper">
          <div className="search-course-inner">
            <div className="d-flex ">
              <FormControl
                className="instructor-search-course "
                type="text"
                name="searchField"
                placeholder="Enter a course"
              />
              <Button className="type-3 ">
                <Search className="d-block my-auto" />
              </Button>
            </div>
            <Button className="type-3">
              <p className="filter">filter</p>
            </Button>
          </div>
          <Button className="type-1 fw-bolder new-course-button-2">
            <Link to="./add-course" className="text-white">
              New course
            </Link>
          </Button>
        </div>
      </div>
    </div>
  );
}

export default InstructorDashboard;
