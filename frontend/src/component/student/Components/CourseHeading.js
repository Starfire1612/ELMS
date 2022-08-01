import { React } from "react";
import "../Styles/CourseHeading.css";
import { Rating } from 'react-simple-star-rating';

export default function CourseHeading(props) {
  const datePublished = new Date(props.course.datePublished);
  const coursePublished = datePublished.toDateString();
  const desc = () => {
    if (props.course.courseDescription.length <= 60) {
      return props.course.courseDescription;
    } else {
      return props.course.courseDescription.substring(0, 60) + "...";
    }
  };
  return (
    <div className="parent-div">
      <div className="child-div">
        <div className="course-head">
          <h1>{props.course.courseName}</h1>
        </div>
        <div>
          <p className="fs-4">{desc()}</p>
        </div>
        <div>
          <p className="fs-5 mb-1">
            Created by - {props.course.instructorName}
          </p>
          <p className="fs-5 mb-1">Published on - {coursePublished}</p>
          <p> <span className="fs-6 mt-1">Course Ratings </span>
            <Rating
              initialValue={props.course.ratings}
              readonly={true}
              size={30}
            />
          </p>
        </div>
      </div>
    </div>
  );
}
