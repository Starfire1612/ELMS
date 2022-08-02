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

export default function CourseDescription({ userData }) {
  const params = useParams();
  const courseId = params.courseId;
  const [course, setCourse] = useState({});
  const [editMode, setEditMode] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [image, setImage] = useState();

  const fetchCourse = async () => {
    setIsLoading(true);
    //fetch course details using courseid and set course state then set course same as course state
    //....
    const response = await getCourseDetails(userData.instructorId, courseId);
    console.log("Course Details", response);
    setCourse(response);
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
      console.log(reader.result);
      
    reader.readAsDataURL(_image);
    } 
  };

  const handleImageSubmit=async()=>{
    const updatedCourseImage = await postCoursePic(userData.instructorId,course.courseId, image);
    console.log(updatedCourseImage);
  }
  const handleSubmit = async (event) => {
    event.preventDefault();
    setIsLoading(true);
    // PATCH request to update course
    console.log("From CD", course);
    const status = await updateCourse(userData.instructorId, course);
    console.log(status);
    setIsLoading(false);
  };

  const handleCancel = () => {
    console.log(course);
    setCourse(course);
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
              <ClipLoader color={LOADING_COLOR} />
            </div>
          )}
        </div>
        <div>
          <Form onSubmit={handleSubmit}>
            <div className="img-container">
              <img
                className="course-image mb-3"
                src={course.courseImage}
                alt=""
              />
            </div>

            <div className="d-flex align-items-center">
            <Form.Group className="mb-3 flex-grow-1"> 
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
            <Button className="type-3" onClick={handleImageSubmit} >
                  Upload Image
                </Button>
            </div>
            <Form.Group className="mb-3">
              <Form.Label className="fw-500">Course Name</Form.Label>
              <Form.Control
                type="text"
                value={course.courseName}
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
                defaultValue={course.courseDescription}
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
                  defaultValue={course.coursePrice}
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
                  defaultValue={course.courseDiscountPercent}
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
