import React, { useState } from "react";
import { PencilFill, Trash } from "react-bootstrap-icons";
import { Form, Row, Col, Button } from "react-bootstrap";
import "../../../styles/manage-course/Lesson.css";
import { getVideoDuration } from "../../../utils/http-requests";
import { convertDurationToMinutes } from "../../../utils/util";

export default function ({ lesson, index, handleDelete, handleLessonUpdate }) {
  const [isActive, setIsActive] = useState(false);
  const [newLesson, setNewLesson] = useState(lesson);
  const [urlChanged, setUrlChanged] = useState(false);
  const [nameChanged, setNameChanged] = useState(false);

  const handleChange = (event) => {
    const eventName = event.target.name;
    setNewLesson((prevLesson) => ({
      ...prevLesson,
      [eventName]: event.target.value,
    }));
    if (eventName === "lessonLink") {
      setUrlChanged(true);
    } else {
      setNameChanged(true);
    }
  };
  const handleUrlCheck = async () => {
    const url = newLesson?.lessonLink;
    if (!url) return;
    console.log(newLesson);
    const duration = await getVideoDuration(url);
    if (!duration) {
      //send error message to user
      return;
    }
    const durationInMinutes = convertDurationToMinutes(duration);
    console.log(durationInMinutes);
    setNewLesson((prevLesson) => ({
      ...prevLesson,
      ["lessonDuration"]: durationInMinutes,
    }));
    setUrlChanged(false);
  };
  const setStates = () => {
    setIsActive(false);
    setNameChanged(false);
    setUrlChanged(false);
    setNewLesson(lesson);
  };

  return (
    <div className={"lesson mb-2 " + (isActive ? "bg-gray" : "")}>
      <div className="title d-flex">
        <div className="flex-grow-1">
          <span>{"Lecture " + index + ":"}</span>
          <span className="ms-3">{lesson.lessonName}</span>
        </div>
        <div className="edit">
          <button
            className={
              "btn-hover border-0 bg-transparent " + (isActive ? "d-none" : "")
            }
            onClick={() => setIsActive(true)}
          >
            <PencilFill />
            <span className="temp ms-2">Edit</span>
          </button>
        </div>
        <div className="ms-3 delete">
          <button
            className="btn-hover border-0 bg-transparent"
            onClick={() => handleDelete(lesson.lessonId)}
          >
            <Trash />
            <span className="temp ms-2"> Delete</span>
          </button>
        </div>
      </div>
      {isActive && (
        <div className="border-top border-dark mt-3 edit-lesson">
          <Form>
            <Form.Group className="mt-2 mb-3">
              <Form.Label className="mb-0">Lesson name</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter course name"
                defaultValue={lesson.lessonName}
                name="lessonName"
                onChange={handleChange}
              />
            </Form.Group>
            <Row className="align-items-center">
              <Col xs={7} className=" flex-grow-1">
                <Form.Group>
                  <Form.Label visuallyHidden>Video Url</Form.Label>
                  <Form.Control
                    type="text"
                    placeholder="Enter YouTube video url"
                    defaultValue={lesson.lessonLink}
                    name="lessonLink"
                    onChange={handleChange}
                  />
                </Form.Group>
              </Col>
              <Col>
                {urlChanged ? (
                  <Button onClick={handleUrlCheck}> Check</Button>
                ) : (
                  <Button
                    onClick={() => handleLessonUpdate(newLesson)}
                    disabled={!nameChanged}
                  >
                    Update
                  </Button>
                )}
              </Col>
              <Col>
                <Button onClick={setStates}> Cancel</Button>
              </Col>
            </Row>
          </Form>
        </div>
      )}
    </div>
  );
}
