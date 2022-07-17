import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "../../../styles/manage-course/Filler.css";
import "../../../styles/manage-course/CourseDescription.css";
import { dummyCourse } from "../../dummydata/dummyCourse";
import { HashLoader } from "react-spinners";
import { LOADING_COLOR } from "../../../utils/constants";

export default function CourseDescription() {
  const courseid = useParams();
  const [course, setCourse] = useState(dummyCourse);
  const [tempCourse, setTempCourse] = useState(course);
  const [editMode, setEditMode] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  const compareCourses = () => {
    //return true if course state and tempCourse state are same, else false
  };

  const fetchCourse = () => {
    setIsLoading(true);
    //fetch course details using courseid and set course state then set tempCourse same as course state
    //....
    setIsLoading(false);
  };

  useEffect(() => {
    fetchCourse();
  }, []);

  const handleChange = (event) => {
    setTempCourse((prevCourse) => ({
      ...prevCourse,
      [event.target.name]: event.target.value,
    }));
  };
  const handleSubmit = (event) => {
    event.preventDefault();
    if (!compareCourses) {
      setIsLoading(true);
      // PUT request to update course
      //...
      setIsLoading(false);
    }
  };
  const handleCancel = () => {
    console.log(tempCourse);
    setTempCourse(course);
    setEditMode(!editMode);
    //implementation of reset form to its default state
  };
  const handleModify = () => {
    setEditMode(!editMode);
  };

  return (
    <div>
      <div className="sub-header">
        <h2 className="sub-header-heading">Course Description</h2>
      </div>
      <div className="manage-course-description">
        <div className={isLoading ? "layer" : ""}>
          {isLoading && (
            <div className="abc">
              <HashLoader color={LOADING_COLOR} />
            </div>
          )}
        </div>
        <div>
          <Form onSubmit={handleSubmit}>
            <img
              className="course-image mb-3"
              src={"data:image/jpg;base64, " + tempCourse.image}
              alt=""
            />
            <Form.Group className="mb-3">
              <Form.Label>Course image</Form.Label>
              <Form.Control type="file" accept="image/*" disabled={!editMode} />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Course Name</Form.Label>
              <Form.Control type="text" value={tempCourse.name} readOnly />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Course Description</Form.Label>
              <textarea
                className="form-control"
                rows={"5"}
                placeholder="Insert your course description"
                defaultValue={course.description}
                onChange={handleChange}
                disabled={!editMode}
                required
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Price</Form.Label>
              <Form.Control
                type="number"
                placeholder="Enter price"
                defaultValue={course.price}
                min={"0"}
                onChange={handleChange}
                disabled={!editMode}
                required
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Discount percentage</Form.Label>
              <Form.Control
                type="number"
                placeholder="Enter discount in percentage"
                min={"0"}
                defaultValue={course.discountpercent}
                onChange={handleChange}
                disabled={!editMode}
                required
              />
            </Form.Group>
            <div className="d-flex justify-content-between align-items-center">
              {editMode ? (
                <Button variant="primary" onClick={handleCancel}>
                  Cancel
                </Button>
              ) : (
                <Button variant="primary" onClick={handleModify}>
                  Modify
                </Button>
              )}

              <Button
                variant="primary"
                type="submit"
                style={!editMode ? { display: "none" } : {}}
              >
                Submit
              </Button>
            </div>
          </Form>
        </div>
      </div>
    </div>
  );
}
