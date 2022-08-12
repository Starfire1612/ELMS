import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { OverlayTrigger } from "react-bootstrap";
import Progressbar from "./Progressbar";
import { CaretLeft } from "react-bootstrap-icons";
import { sendCertificateCompletionMail } from "./../courses/courses-util";
import "./PlayerNavbar.css";
import toast,{ Toaster } from "react-hot-toast";

function PlayerNavBar(props) {
  const params = useParams();
  const stuId = params.studentId;
  const courseId = params.courseId;
  const [shouldGenerateCertificate, setShouldGenerateCertificate] =
    useState(false);

  const generateCertificate = async () => {
    console.log(shouldGenerateCertificate);
    if (shouldGenerateCertificate){
      const status=await sendCertificateCompletionMail(stuId, courseId);
      if(status===201){
        toast.success("Certificate sent via mail.");
      }else if(status===404){
        toast.error("Course not completed yet.");
      }else{
        toast.error("Something went wrong.");
      }
    }
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
      <Toaster />
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
        overlay={
          <div className="tool-tip">
            <p>{props.progress}% completed</p>
            {props.progress === 100 ? (
              <p className="text-secondary">
                Get certificate after completion of course.
              </p>
            ) : (
              <p className="text-secondary"> Click to get certificate.</p>
            )}
          </div>
        }
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
