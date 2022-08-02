import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import { Link } from "react-router-dom";
import "./PlayerNavbar.css";
import Progressbar from "./Progressbar";
import {CaretLeft} from "react-bootstrap-icons"

function PlayerNavBar(props) {
  return (
    <Navbar  expand="xl" className="nav-bg justify-content-between px-2" variant="dark">
      
      <Link to="home" className="return-home ">
        
         <CaretLeft></CaretLeft><span className="back-to-path"> Back to Learning path</span>
      </Link>
      <div className="course-topic">{props.courseName}</div>
          <Link to="certificate" className="generate-certificate">
               <Progressbar className="progress-bar" />
                <span>Certificate</span>
          </Link>
  </Navbar>
  );
}

export default PlayerNavBar;
