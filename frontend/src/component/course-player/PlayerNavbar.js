import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import { Link, useParams } from "react-router-dom";
import "./PlayerNavbar.css";
import Progressbar from "./Progressbar";
import { CaretLeft } from "react-bootstrap-icons";
import { sendCertificateCompletionMail } from './../courses/courses-util';



function PlayerNavBar(props) {
  
  const params=useParams();
  const stuId=params.studentId;
  const courseId=params.courseId;

  const generateCertificate=async()=>{
    await sendCertificateCompletionMail(stuId,courseId);
  }

  console.log("Progress in navbar:",props.progress)
  return (
    <Navbar expand="xl" className="nav-bg justify-content-between px-5 ">
      <Link to="../../home" className="return-home">
        <CaretLeft></CaretLeft>
        <span className="back-to-path"> Back to Learning path</span>
      </Link>
      <div className="course-topic">{props.courseName}</div>
      <div className="generate-certificate" onClick={generateCertificate} >
        <Progressbar className="progress-bar" progress={props.progress} />
        <span>Progress</span>
      </div>
    </Navbar>
  );
}

export default PlayerNavBar;
