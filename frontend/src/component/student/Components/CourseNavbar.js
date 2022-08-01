import "../Styles/CourseNavbar.css";
import { StarFill } from "react-bootstrap-icons";
import Button from "react-bootstrap/Button";
import { Rating } from "react-simple-star-rating";

function CourseNavBar(props) {
  return (
    <div className="nav-bar nav-bar-dark content-wrap px-5">
      <div>
        <div>
          {props.course.courseName}
          <br></br>
          <Rating
            initialValue={props.course.ratings}
            readonly={true}
            size={20}
          />
        </div>
      </div>
      <Button className="enroll type-1 d-none">Enroll Now</Button>
    </div>
  );
}

export default CourseNavBar;
