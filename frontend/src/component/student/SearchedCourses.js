import React, { useEffect, useState } from "react";
import { getSearchedCourses } from "./../courses/courses-util";
import { ClipLoader } from "react-spinners";
import { LOADING_COLOR } from "../../utils/constants.js";
import Courses from "./../courses/Courses";
import { useNavigate, useParams } from "react-router-dom";
import nodatafound from "../../static/images/nodatafound.png";

function SearchedCourses({ userData }) {
  const [courses, setCourses] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const navigate = useNavigate();

  const params = useParams();
  //fetch all the courses
  const fetchAllCourses = async () => {
    setIsLoading(true);
    const responseData = await getSearchedCourses(
      userData.studentId,
      params.search
    );
    console.log("Feched Courses" + responseData);
    if (!responseData) setCourses([]);
    else setCourses(responseData);
    setIsLoading(false);
  };

  useEffect(() => {
    fetchAllCourses();
  }, [userData, params.search]);

  return (
    <div className="student-dashboard-body">
      <div className="course-list">
        {isLoading ? (
          <div className="loading-courses-list my-5">
            <ClipLoader  className="d-block mx-auto my-auto align-items-center justify-content-center"  color={LOADING_COLOR} size="50px" />
          </div>
        ) : courses.length > 0 ? (
          <Courses courses={courses} userData={userData} />
        ) : (
          <div className="courses-list my-5">
            <p className=" mb-0">No such published courses found</p>
            <p
              className=" go-back-to-your-courses"
              onClick={() => navigate("/home/explore")}
            >
              Go back to explore courses.
            </p>
            <img src={nodatafound} className="search-course-not-found-image" />
          </div>
        )}
      </div>
    </div>
  );
}
export default SearchedCourses;
