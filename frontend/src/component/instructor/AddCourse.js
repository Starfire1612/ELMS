import React, { useState } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import { Link } from "react-router-dom";
import { LOADING_COLOR } from "../../utils/constants";
import { HashLoader } from "react-spinners";
import "../../styles/AddCourse.css";
import { createCourse } from './instructor-utils';

function AddCourse({userData}) {
  const [isCourseNameUnique, setIsCourseNameUnique] = useState(true);
  const [isLoading, setIsLoading] = useState(false);
  const [courseName, setCourseName] = useState("");
  const [courseId, setCourseId] = useState();

  const handleChange = (event) => {
    setCourseName(event.target.value);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setIsLoading(true);
    if (!isCourseNameUnique) {
      //make axios call to verify course name and set course id
    }
    const course={
      "courseName":courseName
    }
    const response=await createCourse(userData.instructorId,course);
    setCourseId(parseInt(response.data))
    //set isLoading to false
    setIsLoading(false);
  };

  return (
    <div className="contain">
      <Form className="form" onSubmit={handleSubmit}>
        <Form.Group className="mb-3" controlId="formBasicEmail">
          <Form.Label>Enter Course name</Form.Label>
          <Form.Control
            type="text"
            placeholder="Course name"
            required
            onChange={handleChange}
            disabled={isLoading}
          />
          <p className=" text-muted">
            {isCourseNameUnique && "Course name should be unique."}
          </p>
        </Form.Group>
        <div className="d-flex align-items-center justify-content-between">
          {isCourseNameUnique ? (
             <Link to={`../../instructor/course/${courseId}/manage/description`}>
              <Button variant="primary" type="submit">
                Confirm
              </Button>
              </Link>
          ) : (
            <Button variant="primary" type="submit" disabled={isLoading}>
              Check
            </Button>
          )}

          {isLoading && <HashLoader size={30} color={LOADING_COLOR} />}
        </div>
      </Form>
    </div>
  );
}

export default AddCourse;
