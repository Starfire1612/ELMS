import React from "react";
import { useState } from "react";
import { getCourseDetails } from "../courses/courses-util.js";
import { useParams } from "react-router-dom";
import { useEffect } from "react";

export default function CourseViewPage({ userData }) {
  const params = useParams();
  const courseId = params.courseId;
  const [courseDetails, setcourseDetails] = useState({});
  const fetchCourseDetails = async () => {
    await getCourseDetails(courseId, userData.studentId)
      .then((res) => {
        return res.data;
      })
      .then((data) => {
        console.log(data);
      });
  };
  useEffect(() => {
    fetchCourseDetails();
  }, []);

  return <div>Course View Page</div>;
}
