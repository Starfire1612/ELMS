import React, { useEffect, useState } from "react";
import { Button, Dropdown, Form, FormControl } from "react-bootstrap";
import { Funnel, Search, SortAlphaDown, SortDown } from "react-bootstrap-icons";
import { ClipLoader } from "react-spinners";
import { LOADING_COLOR } from "../../utils/constants";
import { compareObjectsForSorting } from "../../utils/util";
import "../../styles/StudentMyLearning.css";
import Courses from "../courses/Courses";
import { getStudentEnrolledCourses } from "./../courses/courses-util";

//if time permits add pagination in this page as well

function StudentMyLearning({ userData }) {
  const [isLoading, setIsLoading] = useState(false);
  const [courseList, setCourseList] = useState([]);
  const [searchField, setSearchField] = useState("");
  const handleSearchFieldChange = (event) => {
    setSearchField(event.target.value);
  };

  const fetchEnrolledCourses = async () => {
    setIsLoading(true);
    const courseData = await getStudentEnrolledCourses(userData.studentId);
    console.log(courseData);
    setCourseList(courseData);
    setIsLoading(false);
  };

  useEffect(() => {
    fetchEnrolledCourses();
  }, [userData]);

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
    <div className="my-learning-wrapper">
      <div className="my-learning-inner">
        <h1 className="mb-5 text-light font-monospace">My Learning</h1>
      </div>
      <div className="my-learning">
        <div className="my-learning-inner">
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
          </div>
          {isLoading ? (
            <ClipLoader
              className="d-block mx-auto mt-5"
              color={LOADING_COLOR}
            />
          ) : (
            <Courses courses={courseList} userData={userData} />
          )}
        </div>
      </div>
    </div>
  );
}

export default StudentMyLearning;
