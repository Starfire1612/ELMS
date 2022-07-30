import React, { useEffect, useState } from "react";
import Courses from "../courses/Courses.js";
import Pagination from "react-bootstrap/Pagination";
import StudentNavigationbar from "./StudentNavigationbar";
import "../../styles/StudentDashboard.css";
import DashboardCarousel from "./DashboardCarousel";
import { getAllPublishedCourse } from "./../courses/courses-util";
import { ClipLoader } from "react-spinners";
import { LOADING_COLOR } from "../../utils/constants.js";

function StudentDashboard({ handleLogout, userData }) {
  const userType = localStorage.getItem("userType");
  const [courses, setCourses] = useState([]);
  const [pageCounter, setpageCounter] = useState(0);
  
  const [isLoading, setIsLoading] = useState(false);
  let totalPages=0;
  //fetch all the courses
  const fetchAllCourses = async () => {
    setIsLoading(true);
    const responseData = await getAllPublishedCourse(userData.studentId, pageCounter);
    // console.log("Feched Courses" + responseData);
    setCourses(responseData.content);
    totalPages=responseData.totalPages;
    console.log(totalPages);
    setIsLoading(false);
  };
  const handlePrevPage=async()=>{
    if(pageCounter===0) return;
    setpageCounter((prev)=>prev-1);
    console.log("PrevPage"+pageCounter)
    await fetchAllCourses();
  }
  const handleNextPage=async()=>{
    if(pageCounter===totalPages-1) return;
    setpageCounter((prev)=>prev+1);
    console.log("NextPage"+pageCounter)
    await fetchAllCourses();
  }
  useEffect(() => {
    fetchAllCourses();
  },[]);

 

  return (
    <div>
      <StudentNavigationbar
        handleLogout={handleLogout}
        userName={userData.studentName}
      />
      <div className="student-dashboard-body">
        <div className="carousel-container mb-5">
          <DashboardCarousel />
        </div>
        <div className="explore-dashboard text-light d-table">
          <p className="my-auto d-table-cell fs-3 fw-500 font-monospace">
            Explore...
          </p>
        </div>
        <div className="course-list">
        {isLoading ? (
              <div className="loading mx-auto width-auto">
                <ClipLoader color={LOADING_COLOR} size="80px"/>
              </div>
            ):(<Courses courses={courses} userData={userData} />)}
          <div className="mt-4">
            <Pagination className="justify-content-center" size="lg">
            {/* disabled={pageCounter===0 */}
              <Pagination.Prev className="me-5" onClick={handlePrevPage} disabled={pageCounter===0} />
              <Pagination.Next onClick={handleNextPage}   disabled={pageCounter===totalPages-1}/>
              {/* disabled={pageCounter===totalPages */}
            </Pagination>
          </div>
        </div>
      </div>
    </div>
  );
}

export default StudentDashboard;
