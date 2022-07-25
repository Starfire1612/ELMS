import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import InstructorNavbar from "./InstructorNavbar";
import "../../styles/InstructorDashboard.css";
import { Button, FormControl } from "react-bootstrap";
import { Search } from "react-bootstrap-icons";
import StaticDashboardComponents from "./StaticDashboardComponents";
import { ClipLoader } from "react-spinners";
import { LOADING_COLOR } from "../../utils/constants";
import Courses from "../Courses";
import { dummyCourses } from "../dummydata/dummyCourses.js";

function InstructorDashboard({ handleLogout, userData }) {
  const [isLoading, setIsLoading] = useState(false);
  const [courseList, setCourseList] = useState(dummyCourses);

  const fetchCourse = async () => {
    //fetch instructor courses using id from userData and set courseList
  };

  useEffect(() => {
    setIsLoading(true);
    fetchCourse();
    setIsLoading(false);
  }, []);

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
              <p className="filter m-0">filter</p>
            </Button>
          </div>
          <Button className="type-1 fw-bolder new-course-button-2">
            <Link to="./add-course" className="text-white">
              New course
            </Link>
          </Button>
        </div>
        {isLoading ? (
          <ClipLoader className="d-block mx-auto mt-5" color={LOADING_COLOR} />
        ) : courseList.length ? (
          <Courses courses={courseList} />
        ) : (
          <>
            <div className="courses-list text-center my-5">
              You do not have published any courses yet.
            </div>
            <StaticDashboardComponents />
          </>
        )}
      </div>
    </div>
  );
}

export default InstructorDashboard;
