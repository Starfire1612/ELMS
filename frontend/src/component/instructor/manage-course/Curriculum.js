import React, { useEffect, useState } from "react";
import "../../../styles/manage-course/Filler.css";
import "../../../styles/manage-course/Curriculum.css";
import { useParams } from "react-router-dom";
import Lesson from "./Lesson";
import AddLesson from "./AddLesson";
import {
  deleteLesson,
  getCourseLessons,
  updateLesson,
} from "../instructor-utils.js";
import { ClipLoader } from "react-spinners";
import { LOADING_COLOR } from "../../../utils/constants.js";
import { updateCourseLessons } from "./../instructor-utils";

export default function Curriculum({ userData }) {
  const params = useParams();
  const courseId = params.courseId;
  const [isLoading, setIsLoading] = useState(false);
  const [listChanged, setListChanged] = useState(false);
  const [lessonSuccessfullyUploaded, setLessonSuccessfullyUploaded] =
    useState(false);
  const [lessonSuccessfullyUpdated, setLessonSuccessfullyUpdated] =
    useState(false);
  const [lessonSuccessfullyDeleted, setLessonSuccessfullyDeleted] =
    useState(false);
  const [somethingWentWrong, setSomethingWentWrong] = useState(false);
  const [lessons, setLessons] = useState([]);

  const fetchLessons = async () => {
    //fetch lessons using courseId and set lessons state
    setIsLoading(true);
    //fetch course details using courseid and set course state then set course same as course state
    //....
    const response = await getCourseLessons(userData.instructorId, courseId);
    setLessons(response?.data.lessons);
    setIsLoading(false);
  };
  useEffect(() => {
    fetchLessons();
  }, [userData, listChanged]);

  const handleSomethingWentWrong = () => {
    setSomethingWentWrong(true);
    setTimeout(() => {
      setSomethingWentWrong(false);
    }, 4000);
  };

  const handleDeleteLesson = async (lessonId) => {
    //delete request to delete lesson using lessonId and call fetchlessons to get updated lesson list
    const response = await deleteLesson(
      userData.instructorId,
      courseId,
      lessonId
    );
    console.log(response);
    if (response.status === 201) {
      setLessonSuccessfullyDeleted(true);
      setTimeout(() => {
        setLessonSuccessfullyDeleted(false);
      }, 4000);
    } else if (response && response.status !== 201) {
      handleSomethingWentWrong();
    }
    setListChanged(!listChanged);
  };
  const handleUpdateLesson = async (lesson) => {
    //update request to update lesson and call fetchLessons to get updated lesson list
    const response = await updateLesson(
      userData.instructorId,
      courseId,
      lesson.lessonId,
      lesson
    );
    console.log(response);
    if (response.status === 201) {
      setLessonSuccessfullyUpdated(true);
      setTimeout(() => {
        setLessonSuccessfullyUpdated(false);
      }, 4000);
    } else if (response && response.status !== 201) {
      handleSomethingWentWrong();
    }
    setListChanged(!listChanged);
  };
  const handleUploadLessons = async (lessons) => {
    //post request to upload lesson and call fetchlessons to get updated lesson list.
    console.log(lessons);
    const response = await updateCourseLessons(
      userData.instructorId,
      courseId,
      lessons
    );
    console.log(response);
    if (response.status === 201) {
      setLessonSuccessfullyUploaded(true);
      setTimeout(() => {
        setLessonSuccessfullyUploaded(false);
      }, 4000);
    } else if (response && response.status !== 201) {
      handleSomethingWentWrong();
    }
    setListChanged(!listChanged);
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
        {lessonSuccessfullyUploaded && (
          <p className="text-center text-monospace text-success mb-0">
            Lessons uploaded successfully
          </p>
        )}
        {lessonSuccessfullyUpdated && (
          <p className="text-center text-monospace text-success mb-0">
            Lesson updated successfully
          </p>
        )}
        {lessonSuccessfullyDeleted && (
          <p className="text-center text-monospace text-success mb-0">
            Lesson deleted successfully
          </p>
        )}
        {somethingWentWrong && (
          <p className="text-center text-monospace text-danger mb-0">
            Something went wrong. Please try again.
          </p>
        )}
        <hr className="mb-5 hr" />
        {isLoading && (
          <div className="loading-courses-list my-5">
            <ClipLoader color={LOADING_COLOR} size="50px" />
          </div>
        )}
        {!lessons?.length && !isLoading ? (
          <p className="text-center mb-0">
            You do not have any lessons yet. Add lessons to publish your course.
          </p>
        ) : (
          lessons.map((lesson, index) => (
            <Lesson
              key={lesson.lessonId}
              ind={index}
              lesson={lesson}
              state={"show-lesson"}
              handleUpdateLesson={handleUpdateLesson}
              handleDeleteLesson={handleDeleteLesson}
            />
          ))
        )}
      </div>
    </div>
  );
}
