import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import InstructorNavbar from "./InstructorNavbar";
import "../../styles/InstructorDashboard.css";
import { Button, Dropdown, Form, FormControl } from "react-bootstrap";
import { Funnel, Search, SortAlphaDown, SortDown } from "react-bootstrap-icons";
import StaticDashboardComponents from "./StaticDashboardComponents";
import { ClipLoader } from "react-spinners";
import { LOADING_COLOR } from "../../utils/constants";
import Courses from "../courses/Courses.js";
import { dummyCourses } from "../dummydata/dummyCourses.js";
import { compareObjectsForSorting } from "../../utils/util";

function InstructorDashboard({ handleLogout, userData }) {
  const [isLoading, setIsLoading] = useState(false);
  const [courseList, setCourseList] = useState(dummyCourses);
  const [searchField, setSearchField] = useState("");

  const fetchCourse = async () => {
    setIsLoading(true);
    //fetch instructor courses using id from userData and set courseList
    // ...
    setIsLoading(false);
  };

  useEffect(() => {
    setIsLoading(true);
    fetchCourse();
    setIsLoading(false);
  }, []);

  const handleSearchFieldChange = (event) => {
    setSearchField(event.target.value);
  };

  const handleSearchCourse = async (event) => {
    event.preventDefault();
    if (!searchField) return;
    setIsLoading(true);
    //fetch courses using searchField and set appropriate courseList
    // ...
    console.log(searchField);
    setSearchField("");
    setIsLoading(false);
  };
  const handleSortCoursesUsingName = () => {
    setCourseList((prevCourseList) =>
      [...prevCourseList].sort((course1, course2) =>
        compareObjectsForSorting(course1, course2, "courseName")
      )
    );
  };
  const handleSortCoursesUsingRating = () => {
    setCourseList((prevCourseList) =>
      [...prevCourseList].sort(
        (course1, course2) => course2.ratings - course1.ratings
      )
    );
  };

  return (
    <div>
      <InstructorNavbar handleLogout={handleLogout} userData={userData} />
      <div className="instructor-dashboard">
        <div className="instructor-dashboard-heading">
          <p className="fs-1 fw-500">Courses</p>
          <Link to="./add-course" className="text-white">
            <Button className="type-1 fw-bolder d-none new-course-button-1">
              New course
            </Button>
          </Link>
        </div>
        <div className="search-course-wrapper">
          <div className="search-course-inner">
            {/* search the course */}
            <Form className="d-flex" onSubmit={handleSearchCourse}>
              <FormControl
                className="instructor-search-course "
                type="text"
                name="searchField"
                placeholder="Enter a course"
                required
                onChange={handleSearchFieldChange}
              />
              <Button className="type-3" type="submit">
                <Search className="d-block my-auto" />
              </Button>
            </Form>

            {/* Sort button */}
            <Dropdown>
              <Dropdown.Toggle className="type-3">
                <span className="me-1">
                  <Funnel />
                </span>
                Sort
              </Dropdown.Toggle>

              <Dropdown.Menu className="bg-white">
                <div
                  className="text-center mt-0 dark-gray dropdown-menu-link"
                  onClick={handleSortCoursesUsingName}
                >
                  <span className="me-1">
                    <SortAlphaDown />
                  </span>
                  Name
                </div>
                <div
                  className="text-center mt-2 dark-gray border-top border-secondary dropdown-menu-link"
                  onClick={handleSortCoursesUsingRating}
                >
                  <span className="me-1">
                    <SortDown />
                  </span>
                  Rating
                </div>
              </Dropdown.Menu>
            </Dropdown>
          </div>
          <Link to="./add-course" className="text-white">
            <Button className="type-1 fw-bolder new-course-button-2">
              New course
            </Button>
          </Link>
        </div>
        {isLoading ? (
          <ClipLoader className="d-block mx-auto mt-5" color={LOADING_COLOR} />
        ) : courseList.length ? (
          // show courses
          <Courses courses={courseList} />
        ) : (
          <>
            {/* if no courses available */}
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
