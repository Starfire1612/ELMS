import React from "react";
import { useState } from "react";
import { getCourseDetails } from "../courses/courses-util.js";
import { useParams } from "react-router-dom";
import { useEffect } from "react";
import { ClipLoader } from "react-spinners";
import { LOADING_COLOR } from "../../utils/constants.js";

export default function CourseViewPage({ userData }) {
  const params = useParams();
  const courseId = params.courseId;
  const [isLoading, setIsLoading] = useState(true);
  const [courseDetails, setCourseDetails] = useState({});
  const fetchCourseDetails = async () => {
    setIsLoading(true);
    const courseData = await getCourseDetails(courseId, userData.studentId);
    console.log(courseData);
    setCourseDetails(courseData);
    setIsLoading(false);
  };
  useEffect(() => {
    fetchCourseDetails();
  }, [userData]);

  return (
    <div>
      {isLoading ? (
        <ClipLoader color={LOADING_COLOR} size="50px" />
      ) : (
        <h1>{courseDetails.course.courseName}</h1>
      )}
    </div>
  );
}
