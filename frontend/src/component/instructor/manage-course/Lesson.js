import React, { useState } from "react";
import { PencilFill, Trash } from "react-bootstrap-icons";
import { Form, Button } from "react-bootstrap";
import "../../../styles/manage-course/Lesson.css";
import { getVideoDuration } from "../../../utils/http-requests";
import { convertDurationToMinutes } from "../../../utils/util";
import { ClipLoader } from "react-spinners";

export default function Lesson({
  lesson,
  handleDeleteLesson,
  handleUpdateLesson,
  state,
}) {
  const [isActive, setIsActive] = useState(false);
  const [newLesson, setNewLesson] = useState(lesson);
  const [urlChanged, setUrlChanged] = useState(false);
  const [nameChanged, setNameChanged] = useState(false);
  const [deleteMode, setDeleteMode] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

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
    setIsLoading(true);
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
    setIsLoading(false);
  };
  const cancelStates = () => {
    setIsActive(false);
    setNameChanged(false);
    setUrlChanged(false);
    setDeleteMode(false);
    setNewLesson(lesson);
  };
  const setStates = (type) => {
    if (type === "active") {
      setIsActive(true);
      setDeleteMode(false);
    } else {
      setIsActive(false);
      setDeleteMode(true);
    }
  };

  return (
    <div className={"lesson mb-2 " + (isActive || deleteMode ? "bg-gray" : "")}>
      <div className="title d-flex">
        <div className="flex-grow-1">
          <span>
            {"Lecture " + (lesson.lessonId ? lesson.lessonId : "") + ":"}
          </span>
          <span className="ms-3">{lesson.lessonName}</span>
        </div>
        <div className="edit">
          <button
            className={
              "btn-hover border-0 bg-transparent " + (isActive ? "d-none" : "")
            }
            onClick={() => setStates("active")}
          >
            <PencilFill />
            <span className="temp ms-2">Edit</span>
          </button>
        </div>
        <div className="ms-3 delete">
          <button
            className={
              "btn-hover border-0 bg-transparent " +
              (deleteMode ? "d-none" : "")
            }
            onClick={() => setStates("delete")}
          >
            <Trash />
            <span className="temp ms-2"> Delete</span>
          </button>
        </div>
      </div>
      {isActive && (
        <div className="border-top border-dark mt-3">
          <Form>
            <Form.Group className="mt-2 mb-3">
              <Form.Label className="mb-0">Lesson name</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter course name"
                defaultValue={lesson.lessonName}
                name="lessonName"
                onChange={handleChange}
                disabled={isLoading}
              />
            </Form.Group>
            <div className="d-flex align-items-center abc ">
              <div className="flex-grow-1">
                <Form.Group>
                  <Form.Control
                    type="text"
                    placeholder="Enter YouTube video url"
                    defaultValue={lesson.lessonLink}
                    name="lessonLink"
                    onChange={handleChange}
                    disabled={isLoading}
                  />
                </Form.Group>
              </div>
              <div className="lesson-button-group group-1">
                {urlChanged ? (
                  <Button className="type-3" onClick={handleUrlCheck}>
                    Verify
                    {isLoading && (
                      <ClipLoader className="ms-1" size="15px" color="white" />
                    )}
                  </Button>
                ) : (
                  <Button
                    className="type-3"
                    onClick={() => handleUpdateLesson(newLesson)}
                    disabled={!nameChanged || isLoading}
                  >
                    Update
                    {isLoading && (
                      <ClipLoader className="ms-1" size="15px" color="white" />
                    )}
                  </Button>
                )}

                <Button className="type-3" onClick={cancelStates}>
                  Cancel
                </Button>
              </div>
            </div>
          </Form>
        </div>
      )}
      {deleteMode && (
        <div className="border-top border-dark mt-3">
          <div className="d-flex align-items-center mt-3 abc-2">
            <div className="flex-grow-1">
              <p className="text-end my-auto abc-2-p">Remove lesson</p>
            </div>
            <div className="lesson-button-group group-2">
              <Button
                className="type-3"
                onClick={() => {
                  cancelStates();
                  handleDeleteLesson(
                    state === "show-lesson"
                      ? lesson.lessonId
                      : lesson.lessonName
                  );
                }}
              >
                Yes
              </Button>

              <Button className="type-3" onClick={cancelStates}>
                Cancel
              </Button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
