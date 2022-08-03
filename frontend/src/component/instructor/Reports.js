import { useEffect, useState } from "react";
import { ClipLoader } from "react-spinners";
import { LOADING_COLOR } from "../../utils/constants.js";
import "../../styles/Reports.css";

import {
  getAllCourseEnrolledStudents,
  getAllCourseFeedbacksByRatings,
  getCourseDetails,
  getMonthlyCourseReveneue,
  getPublishedCourses,
} from "./instructor-utils";
import ReportsCourseList from "./reports/ReportsCourseList.js";
import { Outlet } from "react-router-dom";

export default function Reports({ userData }) {
  const [isLoading, setIsLoading] = useState(false);
  const [allCourses, setAllCourses] = useState([]);
  const [courseData, setCourseData] = useState({});

  const fetchCourseMonthlyEarnings = async (courseId) => {
    const response = await getMonthlyCourseReveneue(
      userData.instructorId,
      courseId
    );
    console.log("Monthly earnings of ", courseId, "is:", response);
    //  setCourseData(response);
  };

  const fetchFeedbacksByRatings = async (courseId) => {
    const response = await getAllCourseFeedbacksByRatings(
      userData.instructorId,
      courseId,
      5
    );
    console.log("Feedbacks containg 5 star ratings are", response);
    //  setCourseData(response);
  };

  return (
    <div className="reports-main-container">
      <div className="reports-course-list">
        <ReportsCourseList userData={userData} />
      </div>
      <div className="reports-course-container">
        <Outlet />
      </div>
    </div>
  );
}
