import React, { useEffect, useState } from "react";
import "../../../styles/manage-course/Filler.css";
import "../../../styles/manage-course/Curriculum.css";
import { useParams } from "react-router-dom";
import Lesson from "./Lesson";
import AddLesson from "./AddLesson";
import { getCourseLessons } from "../instructor-utils.js";
import { ClipLoader } from "react-spinners";
import { LOADING_COLOR } from "../../../utils/constants.js";

export default function Curriculum({ userData }) {
  const params = useParams();
  const courseId = params.courseId;

  const [isLoading, setIsLoading] = useState(false);

  const [lessons, setLessons] = useState([]);

  const fetchLessons = async () => {
    //fetch lessons using courseId and set lessons state
    setIsLoading(true);
    //fetch course details using courseid and set course state then set course same as course state
    //....
    const response=await getCourseLessons(userData.instructorId,courseId);
    setLessons(response);
    setIsLoading(false);
  };
  useEffect(() => {
    fetchLessons();
  }, [userData]);

  const handleDeleteLesson = async (lessonId) => {
    //delete request to delete lesson using lessonId and call fetchlessons to get updated lesson list
    console.log("deleted");
  };
  const handleUpdateLesson = async (lesson) => {
    //update request to update lesson and call fetchLessons to get updated lesson list
  };
  const handleUploadLessons = async (lessons) => {
    //post request to upload lesson and call fetchlessons to get updated lesson list.
  };

  return (
    <div>
      <div className="sub-header">
        <h2 className="heading">Curriculum</h2>
      </div>
      <div className="manage-course">
        <div className="mb-5">
          <AddLesson handleUploadLessons={handleUploadLessons} />
        </div>
        <hr className="mb-5 hr" />
        {isLoading && (
          <div className="loading-courses-list my-5">
            <ClipLoader color={LOADING_COLOR} size="50px" />
          </div>)}
        {lessons.map((lesson) => (
          <Lesson
            key={lesson.lessonId}
            lesson={lesson}
            state={"show-lesson"}
            handleUpdateLesson={handleUpdateLesson}
            handleDeleteLesson={handleDeleteLesson}
          />
        ))}
      </div>
    </div>
  );
}
