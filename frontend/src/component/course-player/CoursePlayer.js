import React, { useEffect, useState } from "react";
import "./CoursePlayer.css";
import LessonList from "./LessonList";
import Player from "./Player";
import TabsB from "./TabsB";
import PlayerNavBar from "./PlayerNavbar";
import { ClipLoader } from "react-spinners";

import { useParams } from "react-router-dom";
import {
  ArrowLeftCircleFill,
  ArrowRightCircleFill,
} from "react-bootstrap-icons";
import { LOADING_COLOR } from "../../utils/constants.js";
import {
  addLessonInStudentCourse,
  getEnrolledStudentCourseDetails,
  updateStudentCourseCurrentLesson,
} from "./../courses/courses-util";

export default function CoursePlayer({ userData }) {
  const params = useParams();
  const courseId = params.courseId;
  const [currentLessonIndex, setCurrentLessonIndex] = useState(0);
  const [isLoading, setIsLoading] = useState(false);
  const [lessonList, setLessonList] = useState();
  const [courseLessonDetails, setCourseLessonDetails] = useState({});
  const [progressPercent, setProgressPercent] = useState();

  const loadCourseLessons = async () => {
    setIsLoading(true);
    const response = await getEnrolledStudentCourseDetails(
      userData.studentId,
      courseId
    );
    console.log(response);
    setCourseLessonDetails(response);
    setLessonList(response.courseId.lessons);
    setProgressPercent(response.courseCompletionPercent);
    setIsLoading(false);
  };

  useEffect(() => {
    loadCourseLessons();
  }, []);

  const updateCurrentLessonId = async (index) => {
    const lessonId = lessonList[currentLessonIndex].lessonId;
    console.log("lessonId ->", lessonId);
    await updateStudentCourseCurrentLesson(
      userData.studentId,
      courseLessonDetails.courseId.courseId,
      lessonId,
      lessonList[index]
    );
  };

  const handleLessonChange = (index) => {
    setCurrentLessonIndex(index);
    updateCurrentLessonId(index);
  };

  const autoPlayNext = async () => {
    if (currentLessonIndex === lessonList.length - 1) {
      //generate certificate and take feedback
    } else {
      setCurrentLessonIndex(currentLessonIndex + 1);
    }
    const lessonId = lessonList[currentLessonIndex].lessonId;
    const _progressPercent = await addLessonInStudentCourse(
      userData.studentId,
      courseLessonDetails.courseId.courseId,
      lessonId
    );
    setProgressPercent(_progressPercent);
  };

  const nextLessonButton = async () => {
    if (currentLessonIndex === lessonList.length - 1) {
      return;
    } else {
      await updateCurrentLessonId(currentLessonIndex + 1);
      console.log(
        "Next Lesson Button",
        currentLessonIndex + 1,
        "- lessonId " + lessonList[currentLessonIndex + 1]
      );
      setCurrentLessonIndex(currentLessonIndex + 1);
    }
  };

  const previousLessonButton = async () => {
    if (currentLessonIndex === 0) {
      return;
    } else {
      await updateCurrentLessonId(currentLessonIndex - 1);
      console.log(
        "Next Lesson Button",
        currentLessonIndex - 1,
        "- lessonId " + lessonList[currentLessonIndex - 1]
      );
      setCurrentLessonIndex(currentLessonIndex - 1);
    }
  };

  return (
    <>
      {!isLoading && courseLessonDetails && lessonList ? (
        <div>
          <PlayerNavBar
            courseName={courseLessonDetails.courseId.courseName}
            progress={progressPercent}
          ></PlayerNavBar>
          <div className="parent">
            <div className="player">
              <Player
                videoId={lessonList[currentLessonIndex].lessonLink}
                playNext={autoPlayNext}
              ></Player>
            </div>

            <div className="course">
              <p className="fs-3 fw-bolder text-center">Course Content</p>
              <LessonList
                className="lesson-list"
                handleLessonChange={handleLessonChange}
                lessonList={lessonList}
                current={currentLessonIndex}
              ></LessonList>
            </div>

            <div className="lesson-change">
              <p className="mb-0" onClick={previousLessonButton}>
                <ArrowLeftCircleFill className="m-3"></ArrowLeftCircleFill>{" "}
                Previous
              </p>
              <p className="mb-0" onClick={nextLessonButton}>
                Next
                <ArrowRightCircleFill className="m-3"></ArrowRightCircleFill>
              </p>
            </div>
          </div>
        </div>
      ) : (
        <ClipLoader color={LOADING_COLOR} size="50px" />
      )}
    </>
  );
}

// <div className="course-description">
//             feedback={props.feedback}
//            <TabsB desc={courseLessonDetails.courseId.courseDescription}></TabsB>
//         </div>
