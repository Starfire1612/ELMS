import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import React from "react";
import { calculateDiscountedPrice, ratingsColor } from "../../utils/util";
import "../../styles/Courses.css";
import { thumbnailUrl } from "../dummydata/dummyCourses";
import CoursesLoadingAnimation from "../Animations/CoursesLoadingAnimation";
import { useState } from "react";
import { Link, Navigate, useNavigate } from "react-router-dom";
import { StarFill } from "react-bootstrap-icons";
import { PAYMENT_KEY, PAYMENT_KEY_SECRET } from "../../utils/constants.js";
import { getCourseDetails } from "./courses-util.js";


function Courses({ courses, userData }) {
  const [isLoading, setIsLoading] = useState(true);
  const navigate=useNavigate();
  // const handlePayment = (e) => {
  //  e.preventDefault();
  //   let options = {
  //     key: PAYMENT_KEY,
  //     key_secret: PAYMENT_KEY_SECRET,
  //     amount: 1000 * 100,
  //     currency: "INR",
  //     name: "ELMS",
  //     description: "for testing purpose",
  //     handler: function (response) {
  //       alert(response);
  //       console.log(response);
  //     },
  //     prefill: {
  //       name: userData.studentName,
  //       email: userData.studentEmail,
  //       contact: "7004581294",
  //     },
  //     notes: {
  //       address: "Razorpay Corporate office",
  //     },
  //     theme: {
  //       color: "#A020F0",
  //     },
  //   };
  //   var pay = new window.Razorpay(options);
  //   pay.open();
  // };
 


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
      <Card className="card" key={course.courseId} style={{ width: "18rem" }}>
        <Card.Img className="card-image" variant="top" src={thumbnailUrl} />
        <Card.Body>
          <Card.Title className="card-course-title">
            {transformContent(course.courseName, "title")}
          </Card.Title>
          <p className="fs-6 fw-light mb-0">{course.instructorName}</p>
          <Card.Text className="lh-sm">
            {transformContent(course.courseDescription, "description")}
          </Card.Text>
          <div className="d-flex justify-content-between align-items-center">
            {localStorage.getItem("userType") === "instructor" ? (
              <Link
                className="text-white"
                to={`../instructor/course/${course.courseId}/manage/description`}
              >
                <Button className="type-1">Edit course</Button>
              </Link>
            ) : (
              <Link  to={`../home/course/${course.courseId}`}>
                <Button className="type-1" >
                  <span className="fs-5">
                    {"$" + discountedPrice(course) + " "}
                  </span>
                  <span className="fs-6 fst-italic text-decoration-line-through">
                    {"$" + course.coursePrice}
                  </span>
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