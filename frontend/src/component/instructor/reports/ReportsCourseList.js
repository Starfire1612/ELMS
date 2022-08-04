import { useEffect, useState } from "react";
import { Form } from "react-bootstrap";
import { NavLink } from "react-router-dom";
import { ClipLoader } from "react-spinners";
import { LOADING_COLOR } from "../../../utils/constants";
import { getPublishedCourses } from "../instructor-utils";

function ReportsCourseList({ userData }) {
  const [isLoading, setIsLoading] = useState(false);
  const [courseList, setCourseList] = useState([]);
  const [tempCourseList, setTempCourseList] = useState([]);
  // const [searchField, setSearchField] = useState();
  const route = "../reports/";

  const filterCourses = (searchField) => {
    return tempCourseList.filter((course) =>
      course.courseName.toLowerCase().includes(searchField.trim().toLowerCase())
    );
  };
  const handleChange = (event) => {
    const searchField = event.target.value;
    if (!searchField) setTempCourseList(courseList);
    else {
      const _tempCourseList = filterCourses(searchField);
      if (!_tempCourseList.length) setTempCourseList(courseList);
      else setTempCourseList(_tempCourseList);
    }
  };

  const fetchAllPublishedCourses = async () => {
    setIsLoading(true);
    const response = await getPublishedCourses(userData.instructorId);
    // console.log(response);
    if (!response) return;
    setCourseList(response);
    setTempCourseList(response);
    setIsLoading(false);
  };
  useEffect(() => {
    fetchAllPublishedCourses();
  }, [userData]);

  return (
    <div className="reports-courselist">
      <Form.Control
        type="text"
        placeholder="Enter Course"
        className="reports-course-search"
        onChange={handleChange}
        disabled={isLoading}
      />
      {isLoading ? (
        <div className="reports-loader">
          <ClipLoader className="" color={LOADING_COLOR} size="40px" />
        </div>
      ) : (
        tempCourseList.map((course) => (
          <NavLink
            key={course.courseId}
            className={({ isActive }) =>
              isActive
                ? "manage-course-side-navbar-active reports-nav-link"
                : "reports-nav-link"
            }
            to={route + course.courseId}
          >
            {course.courseName}
          </NavLink>
        ))
      )}
    </div>
  );
}

export default ReportsCourseList;
