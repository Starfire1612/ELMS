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
import { compareObjectsForSorting } from "../../utils/util";
import { getPublishedCourses } from "./instructor-utils.js";
import nodatafound from "../../static/images/nodatafound.png";

function InstructorDashboard({ handleLogout, userData }) {
  const [isLoading, setIsLoading] = useState(false);
  const [courseList, setCourseList] = useState([]);
  const [tempCourseList, setTempCourseList] = useState([]);
  const [searchField, setSearchField] = useState("");

  const fetchPublishedCourses = async () => {
    setIsLoading(true);
    const courseData = await getPublishedCourses(userData.instructorId);
    setCourseList(courseData);
    setTempCourseList(courseData);
    setIsLoading(false);
  };
  useEffect(() => {
    setIsLoading(true);
    fetchPublishedCourses();
    setIsLoading(false);
  }, [userData]);

  const handleSearchFieldChange = (event) => {
    setSearchField(event.target.value);
    if (!event.target.value) {
      setTempCourseList(courseList);
    }
  };

  const filterCourses = () => {
    return tempCourseList.filter((course) =>
      course.courseName.toLowerCase().includes(searchField.trim().toLowerCase())
    );
  };
  const handleSearchCourse = (event) => {
    event.preventDefault();
    if (!searchField) return;
    setIsLoading(true);
    setTempCourseList(filterCourses());
    // console.log("filtered" + filterCourses());
    // console.log(searchField);
    setSearchField("");
    setIsLoading(false);
  };

  //sort via name
  const handleSortCoursesUsingName = () => {
    setCourseList((prevCourseList) =>
      [...prevCourseList].sort((course1, course2) =>
        compareObjectsForSorting(course1, course2, "courseName")
      )
    );
  };
  //sort via rating
  const handleSortCoursesUsingRating = () => {
    setCourseList((prevCourseList) =>
      [...prevCourseList].sort(
        (course1, course2) => course2.ratings - course1.ratings
      )
    );
  };
  const handleSetOriginalCourseList = () => {
    setTempCourseList(courseList);
    setSearchField("");
    document.getElementById("search-courses-form").reset();
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
            <Form
              className="d-flex"
              id="search-courses-form"
              onSubmit={handleSearchCourse}
            >
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
        ) : tempCourseList?.length ? (
          // show courses
          <Courses courses={tempCourseList} />
        ) : courseList ? (
          <div className="courses-list text-center my-5">
            <p className="text-center mb-0">No such published course</p>
            <p
              className="text-center go-back-to-your-courses"
              onClick={handleSetOriginalCourseList}
            >
              Go back to your courses.
            </p>
            <img
              src={nodatafound}
              className="mx-auto d-block search-course-not-found-image"
            />
          </div>
        ) : (
          <div className="courses-list text-center my-5">
            <p className="text-center">You do not have any published course.</p>
            <StaticDashboardComponents />
          </div>
        )}
      </div>
    </div>
  );
}

export default InstructorDashboard;
