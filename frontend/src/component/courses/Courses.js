import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import React from "react";
import { calculateDiscountedPrice, ratingsColor } from "../../utils/util";
import "../../styles/Courses.css";
import { thumbnailUrl } from "../dummydata/dummyCourses";
import CoursesLoadingAnimation from "../Animations/CoursesLoadingAnimation";
import { useState } from "react";
import { Link, Navigate, useNavigate, useLocation } from "react-router-dom";
import { StarFill } from "react-bootstrap-icons";
import { PAYMENT_KEY, PAYMENT_KEY_SECRET } from "../../utils/constants.js";
import { getCourseDetails, getEnrolledStudentCourseDetails } from "./courses-util.js";

function Courses({ courses, userData }) {
  const location = useLocation();
  const currentLocationPath = "/home/my-learning";
  const [isLoading, setIsLoading] = useState(true);
  const discountedPrice = (course) =>
    calculateDiscountedPrice(course.coursePrice, course.courseDiscount);

  const getColor = (ratings) => {
    return "text-" + ratingsColor(ratings);
  };
  const transformContent = (content, type) => {
    let len;
    if (type === "description") {
      len = 70;
    } else {
      len = 25;
    }
    if (content.length <= len) {
      return content;
    }
    return content.substring(0, len) + "...";
  };

  const card = (course) => {
    return (
      <Card
        className="course-card"
        key={course.courseId}
        style={{ width: "18rem" }}
      >
        <Card.Img
          className="card-image"
          variant="top"
          src={"data:image/png;base64," + course.courseImage}
        />
        <Card.Body>
          <Card.Title className="card-course-title">
            {transformContent(course.courseName, "title")}
          </Card.Title>
          <p className="fs-6 fw-light mb-0">{course.instructorName}</p>
          <Card.Text className="lh-sm">
            {transformContent(course.courseDescription, "description")}
          </Card.Text>
          <div className="d-flex justify-content-between align-items-center mt-5">
            {localStorage.getItem("userType") === "instructor" ? (
              <Link
                className="text-white"
                to={`../instructor/course/${course.courseId}/manage/description`}
              >
                <Button className="type-1">Edit course</Button>
              </Link>
            ) : location.pathname === currentLocationPath ? (
              <Link to={`../../student/${userData.studentId}/course/${course.courseId}/lesson/`}>
                <Button className="type-1">Start Lesson</Button>
              </Link>
            ) : (
              <Link to={`../../home/course/${course.courseId}`}>
                <Button className="type-1">
                  <span className="fs-5">
                    {"$" + discountedPrice(course) + " "}
                  </span>
                  {course.courseDiscount ? (
                    <span className="fs-6 fst-italic text-decoration-line-through">
                      {"$" + course.coursePrice}
                    </span>
                  ) : (
                    <span></span>
                  )}
                </Button>
              </Link>
            )}
            <div className={`fst-italic ${getColor(course.ratings)}`}>
              <StarFill className="me-2" />
              <span className="fs-5 fw-semibold">{course.ratings}</span>
              <span className="fs-6">{" /5"} </span>
            </div>
          </div>
        </Card.Body>
      </Card>
    );
  };

  return (
    <div>
      <div className="courses p-3 mt-5">
        {
          /* {isLoading ? (
          <CoursesLoadingAnimation />
        ) : ( */
          courses && courses?.map((course) => card(course))
          /* )} */
        }
      </div>
    </div>
  );
}

export default Courses;
