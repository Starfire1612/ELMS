import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { OverlayTrigger } from "react-bootstrap";
import Progressbar from "./Progressbar";
import { CaretLeft } from "react-bootstrap-icons";
import { sendCertificateCompletionMail } from "./../courses/courses-util";
import "./PlayerNavbar.css";

function PlayerNavBar(props) {
  const params = useParams();
  const stuId = params.studentId;
  const courseId = params.courseId;
  const [shouldGenerateCertificate, setShouldGenerateCertificate] =
    useState(false);

  const generateCertificate = async () => {
    console.log(shouldGenerateCertificate);
    if (shouldGenerateCertificate)
      await sendCertificateCompletionMail(stuId, courseId);
  };
  useEffect(() => {
    if (props.progress === 100) {
      setShouldGenerateCertificate(true);
    } else {
      setShouldGenerateCertificate(false);
    }
  }, [props.progress]);

  console.log("Progress in navbar:", props.progress);
  return (
    <div className="nav-bar nav-bar-dark ">
      <div className="d-flex align-items-center flex-grow-1">
        <Link to="../../home/my-learning" className="return-home">
          <CaretLeft></CaretLeft>
          <span className="back-to-path"> Back to Learning path</span>
        </Link>
        <h4 className="ms-4 mb-0">{props.courseName}</h4>
      </div>
      <OverlayTrigger
        placement="bottom"
        className="tool-tip-container"
        overlay={<div className="tool-tip">{props.progress}% completed</div>}
      >
        <div className="generate-certificate" onClick={generateCertificate}>
          <Progressbar className="progress-bar" progress={props.progress} />
          <span>Progress</span>
        </div>
      </OverlayTrigger>
    </div>
  );
}

export default PlayerNavBar;
