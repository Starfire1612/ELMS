import {
  CameraVideo,
  Files,
  PeopleFill,
  PhoneFill,
  Trophy,
} from "react-bootstrap-icons";
import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import "../Styles/Card.css";
import download from "../Images/download.jpg";

function Cards(props) {
  const hours = props.course.totalDuration / 60;

  return (
    <div className="enroll-component">
      <Card style={{ width: "24rem" }}>
        <Card.Img className="card-image" src={download} />
        <div className="courseview-card-body">
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
