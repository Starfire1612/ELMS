import React, { useState } from "react";
import { Form, Button, Col, Row } from "react-bootstrap";
import { useParams } from "react-router-dom";
import { ClipLoader } from "react-spinners";
import "../../../styles/manage-course/Lesson.css";
import { getVideoDuration } from "../../../utils/http-requests";
import { convertDurationToMinutes } from "../../../utils/util";
import Lesson from "./Lesson";

export default function AddLesson({ handleUploadLessons }) {
  const params = useParams();
  const courseId = params.courseId;
  const [lessonList, setLessonList] = useState([]);
  const [lesson, setLesson] = useState({ courseId });
  const [isActive, setIsActive] = useState(false);
  const [urlValidated, setUrlValidated] = useState(false);
  const [isLoading, setIsloading] = useState(false);

  const handleChange = (event) => {
    setLesson((prevLesson) => ({
      ...prevLesson,
      [event.target.name]: event.target.value,
    }));
    if (event.target.name === "lessonLink") {
      setUrlValidated(false);
    }
  };
  const handleUrlCheck = async () => {
    setIsloading(true);
    const url = lesson?.lessonLink;
    if (!url) return;

    const duration = await getVideoDuration(url);
    if (!duration) {
      //send error message to user
      return;
    }
    const durationInMinutes = convertDurationToMinutes(duration);
    console.log(durationInMinutes);
    setLesson((prevLesson) => ({
      ...prevLesson,
      ["lessonDuration"]: durationInMinutes,
    }));
    setUrlValidated(true);
    setIsloading(false);
  };
  const setStates = () => {
    setLesson({ courseId });
    setIsActive(false);
    setUrlValidated(false);
    setIsloading(false);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    setLessonList((prevLessonList) => [...prevLessonList, lesson]);
    setLesson({ courseId });
    setIsActive(false);
    setUrlValidated(false);
  };

  const handleDeleteLesson = (lessonName) => {
    console.log("lesson deleted");
    const filterdLessonList = lessonList.filter(
      (lesson) => lesson.lessonName !== lessonName
    );
    setLessonList(filterdLessonList);
  };

  const handleUpdateLesson = (lesson) => {
    console.log("lesson updated");
  };

  const manageUploadLessons = async () => {
    await handleUploadLessons(lessonList);
    setLessonList([]);
  };

  return (
    <div className="">
      <div className={"lesson mb-5 " + (isActive ? "bg-gray" : "")}>
        <div className="add-lesson-title" onClick={() => setIsActive(true)}>
          {lessonList.length > 0 ? "Add more lessons" : "Add lesson"}
        </div>
        {isActive && (
          <div className="border-top border-dark mt-3 edit-lesson">
            <Form onSubmit={handleSubmit}>
              <Form.Group className="mt-2 mb-3">
                <Form.Label className="mb-0">Lesson name</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Enter course name"
                  name="lessonName"
                  onChange={handleChange}
                  disabled={isLoading}
                  required
                />
              </Form.Group>
              <Row className="align-items-center">
                <Col xs={7} className="flex-grow-1">
                  <Form.Group>
                    <Form.Label visuallyHidden>Video Url</Form.Label>
                    <Form.Control
                      type="text"
                      placeholder="Enter YouTube video url"
                      name="lessonLink"
                      onChange={handleChange}
                      disabled={isLoading}
                      required
                    />
                  </Form.Group>
                </Col>
                <Col>
                  {!urlValidated ? (
                    <Button
                      className="type-3"
                      onClick={handleUrlCheck}
                      disabled={isLoading}
                    >
                      Check
                      {isLoading && (
                        <ClipLoader
                          className="ms-1"
                          size="15px"
                          color="white"
                        />
                      )}
                    </Button>
                  ) : (
                    <Button
                      className="type-3"
                      type="submit"
                      disabled={isLoading}
                    >
                      Save
                    </Button>
                  )}
                </Col>
                <Col>
                  <Button className="type-3" onClick={setStates}>
                    Cancel
                  </Button>
                </Col>
              </Row>
            </Form>
          </div>
        )}
      </div>
      {lessonList &&
        lessonList.map((lesson, index) => (
          <Lesson
            key={index}
            ind={index}
            lesson={lesson}
            handleDeleteLesson={handleDeleteLesson}
            handleUpdateLesson={handleUpdateLesson}
          />
        ))}

      {lessonList.length !== 0 && (
        <Button
          className="d-block my-3 ms-auto type-3"
          onClick={manageUploadLessons}
        >
          Confirm save
        </Button>
      )}
    </div>
  );
}
