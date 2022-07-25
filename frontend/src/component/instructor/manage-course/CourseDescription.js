import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import "../../../styles/manage-course/Filler.css";
import "../../../styles/manage-course/CourseDescription.css";
import { dummyCourse } from "../../dummydata/dummyCourse";
import { HashLoader } from "react-spinners";
import { LOADING_COLOR } from "../../../utils/constants";

export default function CourseDescription() {
  const params = useParams();
  const courseId = params.courseId;
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
  const handleImageChange = (event) => {
    const image = event.target.files[0];
    //convert image to base64 url
    const reader = new FileReader();
    reader.onloadend = () => {
      console.log(reader.result);
      setTempCourse((prevCourse) => ({
        ...prevCourse,
        [event.target.name]: reader.result,
      }));
    };
    reader.readAsDataURL(image);
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
        <h2 className="heading">Course Description</h2>
      </div>
      <div className="manage-course manage-course-description">
        <div className={isLoading ? "layer" : ""}>
          {isLoading && (
            <div className="abc">
              <HashLoader color={LOADING_COLOR} />
            </div>
          )}
        </div>
        <div>
          <Form onSubmit={handleSubmit}>
            <div className="img-container">
              <img
                className="course-image mb-3"
                src={tempCourse.courseImage}
                alt=""
              />
            </div>
            <Form.Group className="mb-3">
              <Form.Label className="fw-500">Course image</Form.Label>
              <p className={!editMode ? "d-none" : "text-sm black mb-0"}>
                Upload your course image here. Important guidelines: .jpg, .jpeg
                or .png. no text on the image.
              </p>
              <Form.Control
                type="file"
                accept="image/*"
                onChange={handleImageChange}
                disabled={!editMode || isLoading}
                name="courseImage"
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label className="fw-500">Course Name</Form.Label>
              <Form.Control
                type="text"
                value={tempCourse.courseName}
                name="courseName"
                readOnly
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label className="fw-500">Course Description</Form.Label>
              <textarea
                className="form-control"
                name="courseDescription"
                rows={"5"}
                placeholder="Insert your course description"
                defaultValue={tempCourse.courseDescription}
                onChange={handleChange}
                disabled={!editMode || isLoading}
                required
              />
            </Form.Group>

            <Row>
              <Form.Group as={Col} className="mb-3">
                <Form.Label className="fw-500">Price</Form.Label>
                <Form.Control
                  type="number"
                  name="coursePrice"
                  placeholder="Enter price"
                  defaultValue={tempCourse.coursePrice}
                  min={"0"}
                  onChange={handleChange}
                  disabled={!editMode || isLoading}
                  required
                />
              </Form.Group>
              <Form.Group as={Col} className="mb-3">
                <Form.Label className="fw-500">Discount percentage</Form.Label>
                <Form.Control
                  type="number"
                  name="courseDiscountPercent"
                  placeholder="Enter discount in percentage"
                  min={"0"}
                  defaultValue={tempCourse.courseDiscountPercent}
                  onChange={handleChange}
                  disabled={!editMode || isLoading}
                  required
                />
              </Form.Group>
            </Row>
            <div className="d-flex justify-content-between align-items-center">
              {editMode ? (
                <Button className="type-3" onClick={handleCancel}>
                  Cancel
                </Button>
              ) : (
                <Button className="type-3" onClick={handleModify}>
                  Modify
                </Button>
              )}

              <Button
                className="type-3"
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
