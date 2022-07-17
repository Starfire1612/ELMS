import React, { useState } from "react";
import { Form, Button, Col, Row } from "react-bootstrap";
import { useParams } from "react-router-dom";
import "../../../styles/manage-course/Lesson.css";
import { getVideoDuration } from "../../../utils/http-requests";
import { convertDurationToMinutes } from "../../../utils/util";

export default function AddLesson({ handleUploadLesson }) {
  const params = useParams();
  const courseId = params.courseId;
  const [lesson, setLesson] = useState({ courseId });
  const [isActive, setIsActive] = useState(false);
  const [urlValidated, setUrlValidated] = useState(false);

  const handleChange = (event) => {
    setLesson((prevLesson) => ({
      ...prevLesson,
      [event.target.name]: event.target.value,
    }));
  };
  const handleUrlCheck = async () => {
    const url = lesson?.lessonLink;
    if (!url) return;

    const duration = await getVideoDuration(url);
    if (!duration) {
      //send error message to user
      return;
    }
    const durationInMinutes = convertDurationToMinutes(duration);
    // console.log(durationInMinutes);
    setLesson((prevLesson) => ({
      ...prevLesson,
      ["lessonDuration"]: durationInMinutes,
    }));
    setUrlValidated(true);
  };
  const setStates = () => {
    setLesson({ courseId });
    setIsActive(false);
    setUrlValidated(false);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    handleUploadLesson(lesson);
  };

  return (
    <div className={"lesson mb-2 " + (isActive ? "bg-gray" : "")}>
      <div className="add-lesson-title" onClick={() => setIsActive(true)}>
        Add Lesson
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
                    required
                  />
                </Form.Group>
              </Col>
              <Col>
                {!urlValidated ? (
                  <Button onClick={handleUrlCheck}> Check</Button>
                ) : (
                  <Button type="submit">Save</Button>
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
