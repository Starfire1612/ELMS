import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import React from "react";
import { calculateDiscountedPrice, ratingsColor } from "../utils/util";
import "../styles/Courses.css";
import { thumbnailUrl } from "./dummydata/dummyCourses";
import CoursesLoadingAnimation from "./Animations/CoursesLoadingAnimation";
import { useState } from "react";
import { Link } from "react-router-dom";

function Courses({ courses }) {
  // const [isLoading, setIsLoading] = useState(true);

  const discountedPrice = (course) =>
    calculateDiscountedPrice(course.coursePrice, course.courseDiscount);

  const getColor = (ratings) => {
    return "text-" + ratingsColor(ratings);
  };
  const transformDescription = (description) => {
    if (description.length <= 70) {
      return description;
    }
    return description.substring(0, 70) + "...";
  };

  const card = (course) => {
    return (
      <Card className="card" key={course.courseId} style={{ width: "18rem" }}>
        <Card.Img className="img" variant="top" src={thumbnailUrl} />
        <Card.Body>
          <Card.Title className="mb-0">{course.courseName}</Card.Title>
          <p className="fs-6 fw-light">{course.instructorName}</p>
          <Card.Text>
            {transformDescription(course.courseDescription)}
          </Card.Text>
          <div className="d-flex justify-content-between align-items-center">
            <Button className="type-1">
              {localStorage.getItem("userType") === "instructor" ? (
                <Link
                  className="text-white"
                  to={`../instructor/course/${course.courseId}/manage/description`}
                >
                  Edit course
                </Link>
              ) : (
                <>
                  <span className="fs-5">
                    {"$" + discountedPrice(course) + " "}
                  </span>
                  <span className="fs-6 fst-italic text-decoration-line-through">
                    {"$" + course.coursePrice}
                  </span>
                </>
              )}
            </Button>
            <div className={`ratings fst-italic ${getColor(course.ratings)}`}>
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
      {/* <h1 className="text-center"> Courses </h1> */}
      <div className="courses p-3 mt-5">
        {
          /* {isLoading ? (
          <CoursesLoadingAnimation />
        ) : ( */
          courses?.map((course) => card(course))
          /* )} */
        }
      </div>
    </div>
  );
}

export default Courses;
