import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import "../../../styles/manage-course/Filler.css";
import "../../../styles/manage-course/CourseDescription.css";
import { ClipLoader } from "react-spinners";
import { LOADING_COLOR } from "../../../utils/constants";
import { getCourseDetails, postCoursePic } from "../instructor-utils.js";
import { updateCourse, createCourse } from "./../instructor-utils";
import defaultCourseImage from "../../student/Images/download.jpg";
export default function CourseDescription({ userData }) {
  const params = useParams();
  const courseId = params.courseId;
  const [course, setCourse] = useState({});
  const [editMode, setEditMode] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [courseImage, setCourseImage] = useState();
  const [courseUpdated, setCourseUpdated] = useState(false);
  const [image, setImage] = useState();

  const fetchCourse = async () => {
    setIsLoading(true);
    //fetch course details using courseid and set course state then set course same as course state
    //....
    const response = await getCourseDetails(userData.instructorId, courseId);
    console.log("Course Details", response);
    if (!response) return;
    setCourse(response);
    setCourseImage("data:image/png;base64," + response.courseImage);
    setIsLoading(false);
  };

  useEffect(() => {
    fetchCourse();
  }, [userData]);

  const handleChange = (event) => {
    setCourse((prevCourse) => ({
      ...prevCourse,
      [event.target.name]: event.target.value,
    }));
  };
  const handleImageChange = (event) => {
    const _image = event.target.files[0];
    setImage(_image);
    //convert image to base64 url
    const reader = new FileReader();
    reader.onloadend = () => {
      setCourseImage(reader.result);
    };
    reader.readAsDataURL(_image);
  };

  const handleImageSubmit = async () => {
    const updatedCourseImage = await postCoursePic(
      userData.instructorId,
      course.courseId,
      image
    );
    console.log(updatedCourseImage);
  };
  const handleSubmit = async (event) => {
    event.preventDefault();
    setIsLoading(true);
    // PATCH request to update course
    console.log("From CD", course);
    const status = await updateCourse(userData.instructorId, course);
    if (status === 201) {
      setCourseUpdated(true);
      setTimeout(() => {
        setCourseUpdated(false);
      }, 5000);
    }
    console.log(status);
    setIsLoading(false);
  };

  const handleCancel = () => {
    console.log(course);
    setCourse(course);
    setEditMode(!editMode);
    setImage("");
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
              <ClipLoader className="d-block mx-auto my-auto align-items-center justify-content-center" color={LOADING_COLOR} />
            </div>
          )}
        </div>
        <div>
          <Form onSubmit={handleSubmit}>
            <div className="img-container">
              <img
                className="course-image mb-3"
                src={!courseImage ? defaultCourseImage : courseImage}
                alt={defaultCourseImage}
              />
            </div>

            <p className="fw-500 mb-0">Course image</p>
            <p className={!editMode ? "d-none" : "text-sm black mb-0"}>
              Upload your course image here. Important guidelines: .png. no text
              on the image.
            </p>
            <div className="d-flex align-items-center">
              <Form.Group className="mb-3 flex-grow-1">
                <Form.Control
                  type="file"
                  accept="image/*"
                  onChange={handleImageChange}
                  disabled={!editMode || isLoading}
                  name="courseImage"
                />
              </Form.Group>
              <Button
                className="type-3 ms-2 upload-course-image-button"
                onClick={handleImageSubmit}
                disabled={!editMode || isLoading || !image}
              >
                Upload Image
              </Button>
            </div>
            <Form.Group className="mb-3">
              <Form.Label className="fw-500">Course Name</Form.Label>
              <Form.Control
                type="text"
                value={course?.courseName}
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
                defaultValue={course?.courseDescription}
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
                  defaultValue={course?.coursePrice}
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
                  defaultValue={course?.courseDiscountPercent}
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
                {isLoading && (
                  <span className="ms-2">
                    <ClipLoader className="d-block mx-auto my-auto align-items-center justify-content-center" color="white" size={15} />
                  </span>
                )}
              </Button>
            </div>
          </Form>
          {courseUpdated && (
            <p className="text-center font-monospace text-success">
              Course updated successfully
            </p>
          )}
        </div>
      </div>
    </div>
  );
}
