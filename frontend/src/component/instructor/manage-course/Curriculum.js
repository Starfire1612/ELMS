import React, { useEffect, useState } from "react";
import "../../../styles/manage-course/Filler.css";
import "../../../styles/manage-course/Curriculum.css";
import { useParams } from "react-router-dom";
import { dummyLessons } from "../../dummydata/dummylessons";
import Lesson from "./Lesson";
import AddLesson from "./AddLesson";

export default function Curriculum() {
  const params = useParams();
  const courseId = params.courseId;

  const fetchLessons = async () => {
    //fetch lessons using courseId and set lessons state
  };
  useEffect(() => {
    fetchLessons();
  }, []);

  const handleDeleteLesson = (lessonId) => {
    //delete request to delete lesson using lessonId and call fetchlessons to get updated lesson list
    console.log("deleted");
  };
  const handleUpdateLesson = (lesson) => {
    //update request to update lesson and call fetchLessons to get updated lesson list
  };
  const handleUploadLesson = (lesson) => {
    //post request to upload lesson and call fetchlessons to get updated lesson list.
  };

  return (
    <div>
      <div className="sub-header">
        <h2 className="heading">Curriculum</h2>
      </div>
      <div className="manage-course">
        <div className="mb-5">
          <AddLesson handleUploadLesson={handleUploadLesson} />
        </div>
        <hr className="mb-5 hr" />

        {dummyLessons.map((lesson) => (
          <Lesson
            key={lesson.lessonId}
            lesson={lesson}
            index={lesson.lessonId}
            handleUpdateLesson={handleUpdateLesson}
            handleDeleteLesson={handleDeleteLesson}
          />
        ))}
      </div>
    </div>
  );
}
