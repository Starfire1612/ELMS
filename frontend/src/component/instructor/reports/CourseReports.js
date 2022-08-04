import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getCourseDetails } from "../instructor-utils";
import CourseReportsCards from "./CourseReportsCards";

function CourseReports({ userData }) {
  const params = useParams();
  const courseId = params.courseId;
  const [isLoading, setIsLoading] = useState(false);
  const [course, setCourse] = useState({});

  const fetchCourseDetails = async () => {
    setIsLoading(true);
    const response = await getCourseDetails(userData.instructorId, courseId);
    console.log(response);
    setCourse(response);
    setIsLoading(false);
  };
  useEffect(() => {
    fetchCourseDetails();
  }, [userData, courseId]);

  return (
    <div>
      <div className="course-report-heading">
        <h2 className="font-monospace">{course?.courseName}</h2>
      </div>

      <CourseReportsCards course={course} userData={userData} />
    </div>
  );
}

export default CourseReports;
