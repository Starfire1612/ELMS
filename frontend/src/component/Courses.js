import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import React from "react";
import { calculateDiscountedPrice, ratingsColor } from "../utils/util";
import "../styles/Courses.css";
import { thumbnailUrl } from "./dummydata/dummyCourses";
import CoursesLoadingAnimation from "./Animations/CoursesLoadingAnimation";
import { useState } from "react";

function Courses({ courses }) {
  const [isLoading, setIsLoading] = useState(true);

  const discountedPrice = (course) =>
    calculateDiscountedPrice(course.price, course.discountpercent);

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
      <Card className="card" id={course.id} style={{ width: "18rem" }}>
        <Card.Img variant="top" src={thumbnailUrl} />
        <Card.Body>
          <Card.Title className="mb-0">{course.name}</Card.Title>
          <p className="fs-6 fw-light">{course.instructorname}</p>
          <Card.Text>{transformDescription(course.description)}</Card.Text>
          <div className="d-flex justify-content-between align-items-center">
            <Button variant="success">
              <span className="fs-5">
                {"$" + discountedPrice(course) + " "}
              </span>
              <span className="fs-6 fst-italic text-decoration-line-through">
                {"$" + course.price}
              </span>
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
      <h1 className="text-center"> Courses </h1>
      <div className="courses p-3">
        {isLoading ? (
          <CoursesLoadingAnimation />
        ) : (
          courses?.map((course) => card(course))
        )}
      </div>
    </div>
  );
}

export default Courses;
