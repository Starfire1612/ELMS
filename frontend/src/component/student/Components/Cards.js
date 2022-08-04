import {
  CameraVideo,
  Files,
  PeopleFill,
  PhoneFill,
  Trophy,
} from "react-bootstrap-icons";
import { calculateDiscountedPrice } from "../../../utils/util.js";
import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import "../Styles/Card.css";

function Cards(props) {
  const hours = Math.ceil(props.course.totalDuration / 60);

  return (
    <div className="enroll-component">
      <Card style={{ width: "24rem" }}>
        <Card.Img className="card-image" src={"data:image/png;base64,"+props.course.courseImage} />
        <div className="courseview-card-body">
        <div className="price-component">
          {props.course.courseDiscount?(<div><span className="fs-3 fw-bold">
                    {"$" + calculateDiscountedPrice(
      props.course.coursePrice,
      props.course.courseDiscount
    ) + " "}
                  </span>
                  &nbsp; 
                  <span className="fs-6 fst-italic text-decoration-line-through">
                    {"$" + props.course.coursePrice}
                  </span>
                  &nbsp;&nbsp;
                  <span className="fs-6 fw-semibold">{props.course.courseDiscount}% off</span></div>):(<div><span className="fs-3 fw-bold">
                    {"$" + calculateDiscountedPrice(
      props.course.coursePrice,
      props.course.courseDiscount
    ) + " "}
                  </span></div>)
                    }
                  </div>
          
              <Button
            className="course-enroll type-1 mb-5"
            onClick={props.handleEnrollStudent}
          >
            Enroll Now
          </Button>
          <div className="card-head">This Course includes : </div>
          <div>
            <div className="card-list">
              <li>
                <span className="me-3">
                  <CameraVideo />
                </span>
                {hours} hours on-demand video
              </li>
              <li>
                <span className="me-3">
                  <Files />
                </span>
                {props.course.lessonsCount} Lessons
              </li>

              <li>
                <span className="me-3">
                  <PeopleFill />
                </span>
                Best Instructor
              </li>
              <li>
                <span className="me-3">
                  <PhoneFill />
                </span>
                Access on Mobile
              </li>
              <li>
                <span className="me-3">
                  <Trophy />
                </span>
                Certificate of Completion
              </li>
            </div>
          </div>
        </div>
      </Card>
    </div>
  );
}

export default Cards;
