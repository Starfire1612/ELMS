import React, { useEffect, useState } from "react";
import { ClipLoader } from "react-spinners";
import { LOADING_COLOR } from "../../utils/constants.js";

import {
  getAllCourseEnrolledStudents,
  getAllCourseFeedbacksByRatings,
  getCourseDetails,
  getMonthlyCourseReveneue,
  getPublishedCourses,
} from "./instructor-utils";
import { Card } from "react-bootstrap";

export default function Reports({ userData }) {
  const [isLoading, setIsLoading] = useState(false);
  const [allCourses, setAllCourses] = useState([]);
  const [courseData, setCourseData] = useState({});

  const fetchAllPublishedCourses = async () => {
    setIsLoading(true);
    const response = await getPublishedCourses(userData.instructorId);
    console.log(response);
    if (!response) return;

    setAllCourses(response);
    setIsLoading(false);
  };

  const fetchCourseDetails = async (courseId) => {
    const response = await getCourseDetails(userData.instructorId, courseId);
    console.log(response);
    setCourseData(response);
  };

  const fetchCourseMonthlyEarnings = async (courseId) => {
    const response = await getMonthlyCourseReveneue(
      userData.instructorId,
      courseId
    );
    console.log("Monthly earnings of ", courseId, "is:", response);
    //  setCourseData(response);
  };

  const fetchTotalStudentsOfCourse = async (courseId) => {
    const response = await getAllCourseEnrolledStudents(
      userData.instructorId,
      courseId
    );
    console.log("Total student registered in ", courseId, "are:", response);
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
  useEffect(() => {
    fetchAllPublishedCourses();
  }, [userData]);

  return (
    <>
      {!isLoading ? (
        <div>
          <div>
            {allCourses?.map((c) => {
              return (
                <Card
                  key={c.courseId}
                  // onClick={()=>fetchCourseDetails(c.courseId)}
                  // onClick={()=>fetchCourseMonthlyEarnings(c.courseId)}
                  // onClick={()=>fetchTotalStudentsOfCourse(c.courseId)}
                //   onClick={() => fetchFeedbacksByRatings(c.courseId)}
                >
                  {c.courseName}
                </Card>
              );
            })}
          </div>
          <div className="course-description"></div>
          <div className="course-earnings"></div>
          <div className="coourse-enrolled-student"></div>
          <div className="course-feedbacks"></div>
        </div>
      ) : (
        <ClipLoader color={LOADING_COLOR} size="50px" />
      )}
    </>
  );
}
