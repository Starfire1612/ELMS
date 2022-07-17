import React, { useEffect, useState } from "react";
import "../../../styles/manage-course/Filler.css";
import "../../../styles/manage-course/Curriculum.css";
import { useParams } from "react-router-dom";
import { Form, Button, Row, Col } from "react-bootstrap";
import { dummyLessons } from "../../dummydata/dummylessons";
import Lesson from "./Lesson";
import AddLesson from "./AddLesson";

export default function Curriculum() {
  const courseid = useParams();
  const [lessons, setLessons] = useState();
  const [urlChecking, setUrlChecking] = useState();

  const fetchLessons = async () => {
    //fetch lessons using courseid and set lessons state
  };
  useEffect(() => {
    fetchLessons();
  }, []);

  const handleDelete = (lessonId) => {
    //delete request to delete lesson using lessonId and call fetchlessons to get updated lesson list
  };
  const handleLessonUpdate = (lesson) => {
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
      <div className="manage-course-curriculum">
        <div className="mb-5">
          <AddLesson handleUploadLesson={handleUploadLesson} />
        </div>
        <hr className="mb-5 hr" />

        {dummyLessons.map((lesson) => (
          <Lesson
            key={lesson.lessonId}
            lesson={lesson}
            index={lesson.lessonId}
            handleLessonUpdate={handleLessonUpdate}
            handleDelete={handleDelete}
          />
        ))}
      </div>
    </div>
  );
}
